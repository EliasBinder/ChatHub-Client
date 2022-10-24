package it.eliasandandrea.chathub;

import it.eliasandandrea.chathub.model.RSACipher;
import it.eliasandandrea.chathub.util.LocalPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatHubMain {

    public static void main(String[] args) throws Exception {
        final Path pub = Paths.get(
                LocalPaths.getData().toAbsolutePath().toString(), "chathub_id_rsa.pub");
        final Path priv = Paths.get(
                LocalPaths.getData().toAbsolutePath().toString(), "chathub_id_rsa");
        RSACipher.init(pub, priv, "test");
    }

}
