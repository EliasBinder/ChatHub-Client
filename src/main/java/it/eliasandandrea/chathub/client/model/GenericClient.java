package it.eliasandandrea.chathub.client.model;

import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallback;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.protocol.ClientEvent;

import java.security.PublicKey;

public abstract class GenericClient {

    public ServerEventCallback onServerEvent;
    public PublicKey serverPublicKey;

    public GenericClient(ServerEventCallback onMessage){
        this.onServerEvent = onMessage;
    }

    public abstract void sendEvent(ClientEvent event) throws Exception;

}
