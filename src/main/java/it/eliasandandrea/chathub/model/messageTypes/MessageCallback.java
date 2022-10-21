package it.eliasandandrea.chathub.model.messageTypes;

@FunctionalInterface
public interface MessageCallback {

    void onMessage(Message message);

}
