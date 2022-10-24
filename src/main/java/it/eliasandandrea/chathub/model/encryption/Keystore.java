package it.eliasandandrea.chathub.model.encryption;

import java.security.PublicKey;
import java.util.HashMap;

public class Keystore {

    //singleton
    private static Keystore singleton;
    public static Keystore getInstance(){
        if (singleton == null)
            singleton = new Keystore();
        return singleton;
    }

    private HashMap<String, PublicKey> keyHashMap;

    public Keystore(){
        this.keyHashMap = new HashMap<>();
    }

    public void addKey(String user, PublicKey publicKey){
        keyHashMap.put(user, publicKey);
    }

    public PublicKey getKey(String user){
        return keyHashMap.get(user);
    }

}
