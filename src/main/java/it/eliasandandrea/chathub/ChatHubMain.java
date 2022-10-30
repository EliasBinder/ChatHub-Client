package it.eliasandandrea.chathub;

import it.eliasandandrea.chathub.model.control.request.JoinServerRequest;
import it.eliasandandrea.chathub.model.crypto.CryptManager;
import it.eliasandandrea.chathub.model.crypto.EncryptedObjectPacket;
import it.eliasandandrea.chathub.util.LocalPaths;
import it.eliasandandrea.chathub.util.Log;
import it.eliasandandrea.chathub.util.ObjectByteConverter;
import it.eliasandandrea.chathub.util.SocketStreams;

import java.io.BufferedInputStream;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatHubMain {

    public static void main(String[] args) throws Exception {
        final Path pub = Paths.get(
                LocalPaths.getData().toAbsolutePath().toString(), "chathub_id_rsa.pub");
        final Path priv = Paths.get(
                LocalPaths.getData().toAbsolutePath().toString(), "chathub_id_rsa");
        CryptManager.init(pub, priv, "test");

        final JoinServerRequest jsr = new JoinServerRequest("nyancat",
                CryptManager.getInstance().getPublicKey());
        final byte[] c = ObjectByteConverter.serialize(jsr);

        final EncryptedObjectPacket packet = new EncryptedObjectPacket(c, null);

        Socket s = new Socket("localhost", 5476);
        SocketStreams.writeObject(s, packet);

        EncryptedObjectPacket packet1 = (EncryptedObjectPacket) SocketStreams.readObject(s);

        Object obj = CryptManager.getInstance().decryptToObject(packet1);
        System.out.println(obj);
    }

}
