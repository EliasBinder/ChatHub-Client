package it.eliasandandrea.chathub.model.encryption;

import it.eliasandandrea.chathub.ObjectByteConverter;
import it.eliasandandrea.chathub.model.message.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

public class RSACipher {

    private static RSACipher singleton;
    public static RSACipher getInstance(){
        return singleton;
    }
    public static void init(String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        singleton = new RSACipher(password);
    }

    private String password;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public RSACipher(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        this.password = password;
        //TODO: write to file
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();

        encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public byte[] encrypt(Message message, PublicKey key) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Cipher encryptor = Cipher.getInstance("RSA");
        encryptor.init(Cipher.ENCRYPT_MODE, key);
        return encryptor.doFinal(ObjectByteConverter.serialize(message));
    }

    public byte[] decrypt(byte[] message) throws IllegalBlockSizeException, BadPaddingException {
        return decryptCipher.doFinal(message);
    }

}
