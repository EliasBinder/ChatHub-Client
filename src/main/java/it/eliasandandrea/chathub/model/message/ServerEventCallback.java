package it.eliasandandrea.chathub.model.message;

@FunctionalInterface
public interface ServerEventCallback {
    void onEvent(ServerEvent event);
}
