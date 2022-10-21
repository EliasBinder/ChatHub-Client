package it.eliasandandrea.chathub.model.messageTypes;

import it.eliasandandrea.chathub.model.RSACipher;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class EncryptedTextMessage implements Message{

    private String message;

    public EncryptedTextMessage(String message) {
        this.message = message;
    }

    @Override
    public byte[] getContent() {
        try {
            return RSACipher.getInstance().encrypt(message);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
