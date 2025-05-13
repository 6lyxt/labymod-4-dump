// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.util;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptManager
{
    public static SecretKey createNewSharedKey() {
        try {
            final KeyGenerator key = KeyGenerator.getInstance("AES");
            key.init(128);
            return key.generateKey();
        }
        catch (final NoSuchAlgorithmException var1) {
            throw new Error(var1);
        }
    }
    
    public static KeyPair createNewKeyPair() {
        try {
            final KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
            keyPair.initialize(1024);
            return keyPair.generateKeyPair();
        }
        catch (final NoSuchAlgorithmException var1) {
            var1.printStackTrace();
            return null;
        }
    }
    
    public static byte[] getServerIdHash(final String input, final PublicKey publicKey, final SecretKey secretKey) {
        try {
            return digestOperation(new byte[][] { input.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
        }
        catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static byte[] digestOperation(final byte[]... bytes) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            for (final byte[] b : bytes) {
                digest.update(b);
            }
            return digest.digest();
        }
        catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static PublicKey decodePublicKey(final byte[] p_75896_0_) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final X509EncodedKeySpec var1 = new X509EncodedKeySpec(p_75896_0_);
        final KeyFactory var2 = KeyFactory.getInstance("RSA");
        return var2.generatePublic(var1);
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey p_75887_0_, final byte[] p_75887_1_) {
        return new SecretKeySpec(decryptData(p_75887_0_, p_75887_1_), "AES");
    }
    
    public static byte[] encryptData(final Key p_75894_0_, final byte[] p_75894_1_) {
        return cipherOperation(1, p_75894_0_, p_75894_1_);
    }
    
    public static byte[] decryptData(final Key p_75889_0_, final byte[] p_75889_1_) {
        return cipherOperation(2, p_75889_0_, p_75889_1_);
    }
    
    private static byte[] cipherOperation(final int direction, final Key key, final byte[] payload) {
        try {
            return createTheCipherInstance(direction, key.getAlgorithm(), key).doFinal(payload);
        }
        catch (final IllegalBlockSizeException | BadPaddingException var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    private static Cipher createTheCipherInstance(final int direction, final String type, final Key key) {
        try {
            final Cipher cipher = Cipher.getInstance(type);
            cipher.init(direction, key);
            return cipher;
        }
        catch (final InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    public static Cipher createNetCipherInstance(final int opMode, final Key key) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        }
        catch (final GeneralSecurityException generalsecurityexception) {
            throw new RuntimeException(generalsecurityexception);
        }
    }
}
