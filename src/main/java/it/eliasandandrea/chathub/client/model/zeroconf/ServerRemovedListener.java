package it.eliasandandrea.chathub.client.model.zeroconf;

import it.eliasandandrea.chathub.client.model.Server;

public interface ServerRemovedListener {
    void onServerRemoved(Server server);
}
