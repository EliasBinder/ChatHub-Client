package it.eliasandandrea.chathub.model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8EncryptorBuilder;
import org.bouncycastle.operator.OutputEncryptor;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

public class RSACipher {

    private static final int SALT_ROUNDS = 20;

    private static RSACipher singleton;
    public static RSACipher getInstance(){
        return singleton;
    }
    public static void init(String password) throws Exception {
        singleton = new RSACipher(password);
    }

    private String password;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public RSACipher(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException {
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

    public RSACipher()

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public byte[] encrypt(String message) throws IllegalBlockSizeException, BadPaddingException {
        return encryptCipher.doFinal(message.getBytes());
    }

    public String decrypt(byte[] message) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decryptCipher.doFinal(message));
    }

    private static byte[] encryptPrivateKey(PrivateKey privateKey, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, IOException {
        final SecureRandom secr = new SecureRandom();
        byte[] salt = new byte[8];
        secr.nextBytes(salt);

        final PBEParameterSpec paramSpec = new PBEParameterSpec(salt, SALT_ROUNDS);
        final PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("HmacSHA512");;
        final SecretKey pbeKey = keyFactory.generateSecret(keySpec);
        
        final Cipher pbeCipher = Cipher.getInstance("RSA");
        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, paramSpec);
        byte[] encr = pbeCipher.doFinal(privateKey.getEncoded());

        final AlgorithmParameters algParams = AlgorithmParameters.getInstance("HmacSHA512");
        algParams.init(paramSpec);

        return new EncryptedPrivateKeyInfo(algParams, encr).getEncoded();
    }

    private static PrivateKey decryptPrivateKey(byte[] encPrivKey, String password) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        final EncryptedPrivateKeyInfo encPrivKeyInfo = new EncryptedPrivateKeyInfo(encPrivKey);
        final Cipher cipher = Cipher.getInstance(encPrivKeyInfo.getAlgName());

        final PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encPrivKeyInfo.getAlgName());
        Key pbeKey = keyFactory.generateSecret(pbeKeySpec);

        final AlgorithmParameters algParams = encPrivKeyInfo.getAlgParameters();
        cipher.init(Cipher.DECRYPT_MODE, pbeKey, algParams);

        final KeySpec privKeySpec = encPrivKeyInfo.getKeySpec(cipher);
        final KeyFactory privKeyFactory = KeyFactory.getInstance("RSA");

        JceOpenSSLPKCS8EncryptorBuilder builder = 
            new JceOpenSSLPKCS8EncryptorBuilder(JceOpenSSLPKCS8EncryptorBuilder.AES_256_CBC);
        PKCS8Generator generator = new PKCS8Generator(encPrivKeyInfo, new FileEnc() {
            
        })
        return privKeyFactory.generatePrivate(privKeySpec);
    }

}
