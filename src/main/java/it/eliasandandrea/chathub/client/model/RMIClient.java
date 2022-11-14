package it.eliasandandrea.chathub.client.model;

import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallback;
import it.eliasandandrea.chathub.shared.protocol.ClientEvent;

public class RMIClient extends GenericClient{

    public RMIClient(ServerEventCallback onMessage) {
        super(onMessage);
        //TODO: implement RMI Client
    }

    @Override
    public void sendEvent(ClientEvent event) throws Exception {
        //TODO: implement send event
    }


}
