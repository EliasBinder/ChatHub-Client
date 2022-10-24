package it.eliasandandrea.chathub.model.message.types;

import it.eliasandandrea.chathub.model.message.Message;

public class EncryptedTextMessage implements Message {

    private String message;

    public EncryptedTextMessage(String message) {
        this.message = message;
    }

    public byte[] getContent() {
        //TODO
        return null;
    }
}
