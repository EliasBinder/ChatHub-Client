package it.eliasandandrea.chathub.model;

import it.eliasandandrea.chathub.ObjectByteConverter;
import it.eliasandandrea.chathub.model.encryption.Keystore;
import it.eliasandandrea.chathub.model.encryption.RSACipher;
import it.eliasandandrea.chathub.model.message.types.BroadcastKeyMessage;
import it.eliasandandrea.chathub.model.message.Message;
import it.eliasandandrea.chathub.model.message.MessageCallback;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;

public class TCPClient {

    private BufferedInputStream in;
    private BufferedOutputStream out;
    MessageCallback onMessage;

    public TCPClient(String host, int port, Runnable onConnectionFail, Runnable onConnectionSuccess, MessageCallback onMessage) {
        this.onMessage = onMessage;
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 7000);
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
                    try {
                        //create byte array from in
                        byte[] bytes = in.readAllBytes();
                        bytes = RSACipher.getInstance().decrypt(bytes);
                        //Convert bytes to object and execute callback
                        this.onMessage.onMessage((Message) ObjectByteConverter.deserialize(bytes));
                    }catch (Exception ex){}
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                onConnectionFail.run();
            }
        });
    }

    public void setOnMessage(MessageCallback onMessage) {
        this.onMessage = onMessage;
    }

    public void sendMessage(Message message, String receiver) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (receiver.startsWith(".")){ //receiver is user
           RSACipher.encrypt(message, Keystore.getInstance().getKey(receiver));
        }
    }
}