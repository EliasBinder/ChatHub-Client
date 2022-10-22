package it.eliasandandrea.chathub.model.zeroconf;

import it.eliasandandrea.chathub.model.Server;

@FunctionalInterface
public interface ServerAddedListener {
    void onServerAdded(Server server);
}
