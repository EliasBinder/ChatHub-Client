package it.eliasandandrea.chathub.client.model.protocol;

import it.eliasandandrea.chathub.shared.protocol.ServerEvent;

@FunctionalInterface
public interface ServerEventCallback {

    void onServerEvent(ServerEvent event);

}
