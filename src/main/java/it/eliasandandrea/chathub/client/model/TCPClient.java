package it.eliasandandrea.chathub.client.model;

import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallback;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.crypto.EncryptedObjectPacket;
import it.eliasandandrea.chathub.shared.crypto.Packet;
import it.eliasandandrea.chathub.shared.protocol.ClientEvent;
import it.eliasandandrea.chathub.shared.protocol.ServerEvent;
import it.eliasandandrea.chathub.shared.protocol.clientEvents.HandshakeRequestEvent;
import it.eliasandandrea.chathub.shared.util.SocketStreams;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.concurrent.Executors;

public class TCPClient {

    ServerEventCallback onServerEvent;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private CryptManager cryptManager;
    public PublicKey serverPublicKey;

    public TCPClient(String host, int port, CryptManager cryptManager,
                     Runnable onConnectionFail, Runnable onConnectionInterrupted,
                     ServerEventCallback onMessage) {
        this.onServerEvent = onMessage;
        this.cryptManager = cryptManager;

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                socket = new Socket();
                socket.setKeepAlive(true);
                socket.connect(new InetSocketAddress(host, port), 7000);
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());

                //initial handshake for submitting public key
                Executors.newSingleThreadExecutor().submit(() -> {
                    try {
                        Thread.sleep(1000); //wait until client is ready to receive just to be sure
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final HandshakeRequestEvent handshakeRequestEvent = new HandshakeRequestEvent(cryptManager.getPublicKey());
                    final Packet packet = new Packet(handshakeRequestEvent);
                    SocketStreams.writeObject(outputStream, packet);
                });

                //Listen for incoming messages
                boolean connected = true;
                while (connected) {
                    try {
                        EncryptedObjectPacket encryptedObjectPacket = (EncryptedObjectPacket) SocketStreams.readObject(inputStream);
                        Packet data = new Packet(cryptManager.decrypt(encryptedObjectPacket));
                        ServerEvent serverEvent = (ServerEvent) data.getSerializable();
                        onMessage.onServerEvent(this, serverEvent);
                    } catch (Exception ex){
                        ex.printStackTrace();
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

    public void sendEvent(ClientEvent event) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        EncryptedObjectPacket toSend = CryptManager.encrypt(event, serverPublicKey);
        if (outputStream != null)
            Executors.newSingleThreadExecutor().submit(() -> SocketStreams.writeObject(outputStream, toSend));
    }
}