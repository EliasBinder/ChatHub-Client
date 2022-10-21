package it.eliasandandrea.chathub.model.messageTypes;

import it.eliasandandrea.chathub.model.RSACipher;

public class BroadcastKeyMessage implements Message{


    @Override
    public byte[] getContent() {
        return RSACipher.getInstance().getPublicKey().getEncoded();
    }
}
