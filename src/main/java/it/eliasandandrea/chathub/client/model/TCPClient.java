package it.eliasandandrea.chathub.client.model;

import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallback;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.protocol.ClientEvent;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executors;

public class TCPClient {

    ServerEventCallback onServerEvent;
    private CryptManager rsaCipher;

    public TCPClient(String host, int port, CryptManager cryptManager,
                     Runnable onConnectionFail, Runnable onConnectionInterrupted, Runnable onConnectionSuccess,
                     ServerEventCallback onMessage) {
        this.onServerEvent = onMessage;
        this.rsaCipher = cryptManager;

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 7000);

                //initial handshake for submitting public key
                Executors.newSingleThreadExecutor().submit(() -> {
                    //TODO: send public key
                });

                onConnectionSuccess.run();
                boolean connected = true;

                //Listen for incoming messages
                while (connected) {
                    try {
                        //TODO: read message
                    } catch (Exception ex){
                        connected = false;
                        onConnectionInterrupted.run();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                onConnectionFail.run();
            }
        });
    }

    public void setOnServerEventCallback(ServerEventCallback serverEventCallback) {
        this.onServerEvent = serverEventCallback;
    }

    public void sendEvent(ClientEvent event) {
        //TODO: send event
    }
}