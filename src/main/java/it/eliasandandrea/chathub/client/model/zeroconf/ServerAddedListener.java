package it.eliasandandrea.chathub.client.model.zeroconf;

import it.eliasandandrea.chathub.client.model.Server;

@FunctionalInterface
public interface ServerAddedListener {
    void onServerAdded(Server server);
}
