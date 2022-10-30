package it.eliasandandrea.chathub.model.crypto;

import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyCombination {

    public PublicKey publicKey;
    public PrivateKey privateKey;

    public KeyCombination(PublicKey publicKey, PrivateKey privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

}
