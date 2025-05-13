// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

public class HexUtil
{
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;
    
    public static char[] encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }
    
    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? HexUtil.DIGITS_LOWER : HexUtil.DIGITS_UPPER);
    }
    
    protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        int i = 0;
        int j = 0;
        while (i < l) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0xF & data[i]];
            ++i;
        }
        return out;
    }
    
    static {
        DIGITS_LOWER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        DIGITS_UPPER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
}
