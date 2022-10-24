package it.eliasandandrea.chathub.model;

import it.eliasandandrea.chathub.ObjectByteConverter;
import it.eliasandandrea.chathub.model.encryption.Keystore;
import it.eliasandandrea.chathub.model.encryption.RSACipher;
import it.eliasandandrea.chathub.model.messageTypes.BroadcastKeyMessage;
import it.eliasandandrea.chathub.model.messageTypes.Message;
import it.eliasandandrea.chathub.model.messageTypes.MessageCallback;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;

public class TCPClient {

    private BufferedInputStream in;
    private BufferedOutputStream out;

    public TCPClient(String host, int port, Runnable onConnectionFail, MessageCallback onMessage) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Socket socket = new Socket(host, port);
                in = new BufferedInputStream(socket.getInputStream());
                out = new BufferedOutputStream(socket.getOutputStream());
                Executors.newSingleThreadExecutor().submit(() -> {
                    try {
                        sendMessage(new BroadcastKeyMessage(), "#");
                    } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
                        e.printStackTrace();
                    }
                });
                while (true) {
                    //create byte array from in
                    byte[] bytes = in.readAllBytes();
                    bytes = RSACipher.getInstance().decrypt(bytes);
                    //Convert bytes to object and execute callback
                    onMessage.onMessage((Message) ObjectByteConverter.deserialize(bytes));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                onConnectionFail.run();
            }
        });
    }

    public void sendMessage(Message message, String receiver) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (receiver.startsWith(".")){ //receiver is user
           RSACipher.getInstance().encrypt(message, Keystore.getInstance().getKey(receiver));
        }
    }
}