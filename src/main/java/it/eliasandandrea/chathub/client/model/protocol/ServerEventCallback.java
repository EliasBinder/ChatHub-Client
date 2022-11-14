package it.eliasandandrea.chathub.client.model.protocol;

import it.eliasandandrea.chathub.client.model.GenericClient;
import it.eliasandandrea.chathub.client.model.TCPClient;
import it.eliasandandrea.chathub.shared.protocol.ServerEvent;

@FunctionalInterface
public interface ServerEventCallback {

    void onServerEvent(GenericClient client, ServerEvent event);

}
