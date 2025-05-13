// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

public class AddressUtil
{
    public static boolean isLocalHost(final String address) {
        return address.equals("localhost") || isSpecial(address);
    }
    
    public static boolean isSpecial(final String address) {
        boolean isSpecialIP = true;
        for (final char c : address.toCharArray()) {
            if (c != '0' && c != '.') {
                isSpecialIP = false;
                break;
            }
        }
        return isSpecialIP;
    }
}
