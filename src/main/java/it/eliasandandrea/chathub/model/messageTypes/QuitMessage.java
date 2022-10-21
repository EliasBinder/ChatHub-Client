package it.eliasandandrea.chathub.model.messageTypes;

import java.nio.charset.StandardCharsets;

public class QuitMessage implements Message{

    @Override
    public byte[] getContent() {
        return "".getBytes(StandardCharsets.UTF_8);
    }
}
