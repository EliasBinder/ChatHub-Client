package it.eliasandandrea.chathub.client.model.protocol;

import it.eliasandandrea.chathub.client.model.Persistence;
import it.eliasandandrea.chathub.client.model.TCPClient;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.model.ChatEntity;
import it.eliasandandrea.chathub.shared.protocol.ServerEvent;
import it.eliasandandrea.chathub.shared.protocol.clientEvents.SetUsernameEvent;
import it.eliasandandrea.chathub.shared.protocol.serverEvents.*;

public class ServerEventCallbackRouter implements ServerEventCallback{

    private Runnable onConnectionSucceess;

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
                for (ChatEntity chat : e.chats) {
                    if (!Persistence.getInstance().chats.contains(chat))
                        Persistence.getInstance().chats.add(chat);
                }
                SetUsernameEvent setUsernameEvent = new SetUsernameEvent();
                setUsernameEvent.username = Persistence.getInstance().username;
                client.sendEvent(setUsernameEvent);
                onConnectionSucceess.run();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
