package it.eliasandandrea.chathub.model.zeroconf;

import it.eliasandandrea.chathub.model.Server;

public interface ServerRemovedListener {
    void onServerRemoved(Server server);
}
