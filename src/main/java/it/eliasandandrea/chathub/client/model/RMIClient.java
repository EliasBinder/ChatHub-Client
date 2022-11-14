package it.eliasandandrea.chathub.client.model;

import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallback;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.crypto.EncryptedObjectPacket;
import it.eliasandandrea.chathub.shared.crypto.Packet;
import it.eliasandandrea.chathub.shared.protocol.ClientEvent;
import it.eliasandandrea.chathub.shared.protocol.ServerEvent;
import it.eliasandandrea.chathub.shared.protocol.clientEvents.HandshakeRequestEvent;
import it.eliasandandrea.chathub.shared.protocol.rmi.MessageCallbackEvent;
import it.eliasandandrea.chathub.shared.protocol.rmi.RMIExchange;
import it.eliasandandrea.chathub.shared.protocol.rmi.RMIHandshake;
import it.eliasandandrea.chathub.shared.util.SocketStreams;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;

//TODO: Optimize: Remove redundant code

public class RMIClient extends GenericClient{

    private CryptManager cryptManager;
    private String url;


    public RMIClient(String host, int port, CryptManager cryptManager,
                     Runnable onConnectionFail, Runnable onConnectionInterrupted,
                     ServerEventCallback onMessage) {
        super(onMessage);
        this.cryptManager = cryptManager;
        this.url = "rmi://" + host + ":" +port + "/";

        MessageCallbackEvent asyncCallbacks = encryptedObjectPacket -> {
            try {
                Packet data = new Packet(cryptManager.decrypt(encryptedObjectPacket));
                ServerEvent serverEvent = (ServerEvent) data.getSerializable();
                onMessage.onServerEvent(RMIClient.this, serverEvent);
            }catch (Exception ex){
                onConnectionInterrupted.run();
            }
        };
        Executors.newSingleThreadExecutor().submit(() -> {
            try{
                RMIHandshake handshake = (RMIHandshake) Naming.lookup(url + "handshake");
                EncryptedObjectPacket handshakeResponse = handshake.doHandshake(new HandshakeRequestEvent(cryptManager.getPublicKey()), asyncCallbacks);
                Packet data = new Packet(cryptManager.decrypt(handshakeResponse));
                ServerEvent serverEvent = (ServerEvent) data.getSerializable();
                onMessage.onServerEvent(this, serverEvent);
            }catch (Exception ex){
                onConnectionFail.run();
            }
        });
    }

    @Override
    public void sendEvent(ClientEvent event) throws Exception {
        RMIExchange exchange = (RMIExchange) Naming.lookup(url + "exchange");
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                EncryptedObjectPacket toSend = CryptManager.encrypt(event, serverPublicKey);
                EncryptedObjectPacket response = exchange.sendMessage(toSend);
                Packet data = new Packet(cryptManager.decrypt(response));
                ServerEvent serverEvent = (ServerEvent) data.getSerializable();
                onServerEvent.onServerEvent(this, serverEvent);
            }catch (Exception ex){
            }
        });
    }
}


