package it.eliasandandrea.chathub.model.message;

@FunctionalInterface
public interface MessageCallback {
    void onMessage(Message message);
}
