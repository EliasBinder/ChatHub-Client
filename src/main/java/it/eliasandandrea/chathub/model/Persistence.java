package it.eliasandandrea.chathub.model;

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

    private Persistence() {
    }

}
