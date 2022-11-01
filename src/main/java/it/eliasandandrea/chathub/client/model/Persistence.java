package it.eliasandandrea.chathub.client.model;

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

    private Persistence() {
    }

}
