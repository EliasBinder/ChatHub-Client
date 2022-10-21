package it.eliasandandrea.chathub.model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

    public byte[] encrypt(String message) throws IllegalBlockSizeException, BadPaddingException {
        return encryptCipher.doFinal(message.getBytes());
    }

    public String decrypt(byte[] message) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decryptCipher.doFinal(message));
    }

}
