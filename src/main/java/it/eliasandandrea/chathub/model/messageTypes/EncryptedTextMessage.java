package it.eliasandandrea.chathub.model.messageTypes;

import it.eliasandandrea.chathub.model.encryption.RSACipher;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class EncryptedTextMessage implements Message{

    private String message;

    public EncryptedTextMessage(String message) {
        this.message = message;
    }

    @Override
    public byte[] getContent() {
        //TODO
        return null;
    }
}
