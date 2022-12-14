package it.eliasandandrea.chathub.client.model.persistence;

import it.eliasandandrea.chathub.client.model.TCPClient;
import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallbackRouter;
import it.eliasandandrea.chathub.shared.model.ChatEntity;

import java.util.LinkedList;

public class Persistence {

    private static Persistence instance;
    public static Persistence getInstance() {
        if(instance == null) {
            instance = new Persistence();
        }
        return instance;
    }

    //Vars
    public TCPClient client;
    public String myUUID;
    public String username;
    public LinkedList<ChatEntity> chats = new LinkedList<>();
    public ServerEventCallbackRouter serverEventCallbackRouter = null;

    private Persistence() {
    }

}
