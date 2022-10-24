package it.eliasandandrea.chathub.model.messageTypes;

import it.eliasandandrea.chathub.model.encryption.RSACipher;

import java.security.PublicKey;

public class BroadcastKeyMessage implements Message{

    PublicKey publicKey;

    public BroadcastKeyMessage() {
        this.publicKey = RSACipher.getInstance().getPublicKey();
    }
}
