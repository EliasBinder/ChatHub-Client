package it.eliasandandrea.chathub.client.model.protocol;

import it.eliasandandrea.chathub.client.model.persistence.Persistence;
import it.eliasandandrea.chathub.client.model.TCPClient;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.model.ChatEntity;
import it.eliasandandrea.chathub.shared.model.Group;
import it.eliasandandrea.chathub.shared.model.User;
import it.eliasandandrea.chathub.shared.protocol.ServerEvent;
import it.eliasandandrea.chathub.shared.protocol.clientEvents.SetUsernameEvent;
import it.eliasandandrea.chathub.shared.protocol.serverEvents.*;
import it.eliasandandrea.chathub.shared.protocol.sharedEvents.MessageEvent;

public class ServerEventCallbackRouter implements ServerEventCallback{

    private Runnable onConnectionSucceess;

    private OnChatEntityAddedCallback onChatEntityAddedCallback;
    private OnChatEntityRemovedCallback onChatEntityRemovedCallback;
    private OnChangeUsernameCallback onChangeUsernameCallback;

    public ServerEventCallbackRouter(Runnable onConnectionSucceess) {
        this.onConnectionSucceess = onConnectionSucceess;
    }

    @Override
    public void onServerEvent(TCPClient client, ServerEvent event){
        if (event == null) {
            return;
        }
        if (HandshakeResponseEvent.class.equals(event.getClass())) {
            try {
                HandshakeResponseEvent e = (HandshakeResponseEvent) event;
                Persistence.getInstance().myUUID = e.uuid;
                try {
                    client.serverPublicKey = CryptManager.bytesToPublicKey(e.serverPublicKey);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                for (Group group : e.groups) {
                    System.out.println("Adding Group: " + group.name);
                    Persistence.getInstance().chats.add(group);
                }
                for (User user : e.users) {
                    System.out.println("Adding User: " + user.username);
                    Persistence.getInstance().chats.add(user);
                }
                Persistence.getInstance().chats.forEach(chatEntity -> {
                    System.out.println("ChatEntity: " + chatEntity.getUUID());
                });
                SetUsernameEvent setUsernameEvent = new SetUsernameEvent();
                setUsernameEvent.username = Persistence.getInstance().username;
                client.sendEvent(setUsernameEvent);
                onConnectionSucceess.run();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }else if (ChatEntityAdded.class.equals(event.getClass())) {
            ChatEntityAdded e = (ChatEntityAdded) event;
            System.out.println("ChatEntityAdded: " + e.entity.UUID);
            Persistence.getInstance().chats.add(e.entity);
            if (onChatEntityAddedCallback != null)
                onChatEntityAddedCallback.onChatEntityAdded(e.entity);


        }else if (ChatEntityRemoved.class.equals(event.getClass())) {
            ChatEntityRemoved e = (ChatEntityRemoved) event;
            System.out.println("ChatEntityRemoved: " + e.uuid);
            for (ChatEntity chat : Persistence.getInstance().chats) {
                if (chat.getUUID().equals(e.uuid)) {
                    Persistence.getInstance().chats.remove(chat);
                    break;
                }
            }
            if (onChatEntityRemovedCallback != null)
                onChatEntityRemovedCallback.onChatEntityRemoved(e.uuid);


        }else if (ChangeUsernameEvent.class.equals(event.getClass())) {
            ChangeUsernameEvent e = (ChangeUsernameEvent) event;
            System.out.println("ChangeUsernameEvent: " + e.uuid + " " + e.username);
            for (ChatEntity chat : Persistence.getInstance().chats) {
                if (chat.getUUID().equals(e.uuid)) {
                    ((User)chat).username = e.username;
                    break;
                }
            }
            if (onChangeUsernameCallback != null)
                onChangeUsernameCallback.onChangeUsername(e.uuid, e.username);


        } else if (MessageEvent.class.equals(event.getClass())) {
            try{

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnChatEntityAddedCallback(OnChatEntityAddedCallback onChatEntityAddedCallback) {
        this.onChatEntityAddedCallback = onChatEntityAddedCallback;
    }

    public void setOnChatEntityRemovedCallback(OnChatEntityRemovedCallback onChatEntityRemovedCallback) {
        this.onChatEntityRemovedCallback = onChatEntityRemovedCallback;
    }

    public void setOnChangeUsernameCallback(OnChangeUsernameCallback onChangeUsernameCallback) {
        this.onChangeUsernameCallback = onChangeUsernameCallback;
    }
}
