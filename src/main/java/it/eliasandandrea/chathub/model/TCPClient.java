package it.eliasandandrea.chathub.model;

import it.eliasandandrea.chathub.model.messageTypes.Message;
import it.eliasandandrea.chathub.model.messageTypes.MessageCallback;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

public class TCPClient {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public TCPClient(String host, int port, Runnable onConnectionFail, MessageCallback onMessage) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Socket socket = new Socket(host, port);
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    Message message = (Message) in.readObject();
                    onMessage.onMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                onConnectionFail.run();
            }
        });
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}