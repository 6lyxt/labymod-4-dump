// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import java.util.Enumeration;
import java.util.List;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Formatter;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HardwareUtils
{
    private static final String[] MAIN_NAMES;
    
    public static String sha1(final String string) {
        String sha1 = "";
        try {
            final MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string.getBytes(StandardCharsets.UTF_8));
            sha1 = byteToHex(crypt.digest());
        }
        catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1;
    }
    
    private static String byteToHex(final byte[] hash) {
        final Formatter formatter = new Formatter();
        for (final byte b : hash) {
            formatter.format("%02x", b);
        }
        final String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public static String encodeAddress(final byte[] address) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < address.length; ++i) {
            builder.append(String.format(Locale.ROOT, "%02X%s", address[i], (i < address.length - 1) ? "-" : ""));
        }
        return builder.toString();
    }
    
    public static byte[] getMainNetworkInterfaceAddress() throws Exception {
        final List<NetworkInterface> candidates = new ArrayList<NetworkInterface>();
        final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface network = networkInterfaces.nextElement();
            if (!network.isLoopback() && !network.isVirtual() && network.isUp() && network.getHardwareAddress() != null) {
                candidates.add(network);
            }
        }
        candidates.sort((a, b) -> {
            final String[] main_NAMES = HardwareUtils.MAIN_NAMES;
            final int length = main_NAMES.length;
            int i = 0;
            while (i < length) {
                final String mainName = main_NAMES[i];
                if (a.getName().equals(mainName)) {
                    return -1;
                }
                else if (b.getName().equals(mainName)) {
                    return 1;
                }
                else {
                    ++i;
                }
            }
            return 0;
        });
        return (byte[])(candidates.isEmpty() ? null : candidates.get(0).getHardwareAddress());
    }
    
    static {
        MAIN_NAMES = new String[] { "eth0", "en0", "wlan0" };
    }
}
