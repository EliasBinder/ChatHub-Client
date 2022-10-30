package it.eliasandandrea.chathub.model.message.types;

import it.eliasandandrea.chathub.model.crypto.CryptManager;
import it.eliasandandrea.chathub.model.message.Message;

import java.security.PublicKey;

public class BroadcastKeyMessage implements Message {

    PublicKey publicKey;

    public BroadcastKeyMessage() {
        this.publicKey = CryptManager.getInstance().getPublicKey();
    }
}
