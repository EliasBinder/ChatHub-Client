package it.eliasandandrea.chathub.model;

import it.eliasandandrea.chathub.model.control.response.Response;
import it.eliasandandrea.chathub.model.crypto.CryptManager;
import it.eliasandandrea.chathub.model.crypto.Keystore;
import it.eliasandandrea.chathub.model.message.ClientEvent;
import it.eliasandandrea.chathub.model.message.ServerEvent;
import it.eliasandandrea.chathub.model.message.types.clientEvents.PublicKeySubmissionEvent;
import it.eliasandandrea.chathub.model.message.ServerEventCallback;
import it.eliasandandrea.chathub.util.ObjectByteConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;

public class TCPClient {

    private DataInputStream in;
    private DataOutputStream out;
    ServerEventCallback onServerEvent;
    private CryptManager rsaCipher;

    public TCPClient(String host, int port, CryptManager cryptManager,
                     Runnable onConnectionFail, Runnable onConnectionSuccess,
                     ServerEventCallback onMessage) {
        this.onServerEvent = onMessage;
        this.rsaCipher = cryptManager;
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 7000);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                //handshake
                Executors.newSingleThreadExecutor().submit(() -> {
                    try {
                        PublicKeySubmissionEvent event = new PublicKeySubmissionEvent(Keystore.getInstance().getPublicKey("client"));
                        byte[] encoded = ObjectByteConverter.serialize(event);
                        out.writeInt(encoded.length);
                        out.write(encoded);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                onConnectionSuccess.run();
                while (true) {
                    try {
                        // Read the message length
                        byte[] length = in.readNBytes(4);
                        int lengthInt = ByteBuffer.wrap(length).getInt();
                        // Create byte array from reading bytes
                        byte[] bytes = in.readNBytes(lengthInt);
                        // Decrypt the message
                        bytes = CryptManager.decrypt(bytes);
                        //Convert decrypted message to object and execute callback
                        this.onServerEvent.onEvent((ServerEvent) ObjectByteConverter.deserialize(bytes));
                    }catch (Exception ex){
                        System.out.println("Could not read message from server");
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

    public void sendEvent(ClientEvent event) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] encryptedObject = CryptManager.encrypt((Response) event, Keystore.getInstance().getPublicKey("server"));
        byte[] length = ByteBuffer.allocate(4).putInt(encryptedObject.length).array();
        out.write(length);
        out.flush();
        out.write(encryptedObject);
        out.flush();
    }
}