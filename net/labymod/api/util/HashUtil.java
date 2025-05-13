// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.IOException;
import java.io.InputStream;

public final class HashUtil
{
    private static final String SHA_1 = "SHA-1";
    private static final String MD5 = "MD5";
    
    public static byte[] sha1(final byte[] data) {
        return getSha1().digest(data);
    }
    
    public static byte[] sha1(final InputStream data) throws IOException {
        return digest(data, getSha1());
    }
    
    public static String sha1Hex(final byte[] data) {
        return new String(HexUtil.encodeHex(sha1(data)));
    }
    
    public static String sha1Hex(final InputStream data) throws IOException {
        return new String(HexUtil.encodeHex(sha1(data)));
    }
    
    public static byte[] md5(final byte[] data) {
        return getMd5().digest(data);
    }
    
    public static byte[] md5(final InputStream data) throws IOException {
        return digest(data, getMd5());
    }
    
    public static String md5Hex(final InputStream data) throws IOException {
        return new String(HexUtil.encodeHex(md5(data)));
    }
    
    public static String md5Hex(final byte[] data) {
        return new String(HexUtil.encodeHex(md5(data)));
    }
    
    public static byte[] digest(final InputStream inputStream, final MessageDigest digest) throws IOException {
        final byte[] buffer = new byte[256];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, len);
        }
        return digest.digest();
    }
    
    public static MessageDigest getSha1() {
        return getDigest("SHA-1");
    }
    
    public static MessageDigest getMd5() {
        return getDigest("MD5");
    }
    
    public static MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (final NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
