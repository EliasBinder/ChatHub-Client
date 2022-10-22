package it.eliasandandrea.chathub.model.zeroconf;

import it.eliasandandrea.chathub.model.Server;
import javafx.application.Platform;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.util.Arrays;

public class ZeroconfServiceListener implements ServiceListener {

    private ServerAddedListener serverAddedListener;
    private ServerRemovedListener serverRemovedListener;

    public ZeroconfServiceListener() {
        this.serverAddedListener = serverAddedListener;
        this.serverRemovedListener = serverRemovedListener;

    }

    @Override
    public void serviceAdded(ServiceEvent serviceEvent) {
    }

    @Override
    public void serviceRemoved(ServiceEvent serviceEvent) {
    }

    @Override
    public void serviceResolved(ServiceEvent serviceEvent) {
    }

}
