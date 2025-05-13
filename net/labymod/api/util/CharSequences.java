// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

public final class CharSequences
{
    private CharSequences() {
    }
    
    public static boolean isEmpty(final CharSequence sequence) {
        return sequence.length() == 0;
    }
    
    public static CharSequence[] split(final CharSequence sequence, final String regex) {
        final Pattern compile = Pattern.compile(regex);
        final String[] splitStrings = compile.split(sequence);
        final CharSequence[] sequences = new CharSequence[splitStrings.length];
        System.arraycopy(splitStrings, 0, sequences, 0, sequences.length);
        return sequences;
    }
    
    public static boolean containsLowercase(final CharSequence sequence, final CharSequence s) {
        return contains(toLowerCase(sequence), toLowerCase(s));
    }
    
    public static boolean contains(final CharSequence sequence, final CharSequence s) {
        if (sequence instanceof final String text) {
            return text.contains(s);
        }
        return sequence.toString().contains(s);
    }
    
    public static int indexOf(final CharSequence sequence, final char c) {
        if (sequence instanceof final String text) {
            return text.indexOf(c);
        }
        if (sequence instanceof final StringBuilder builder) {
            return builder.indexOf(String.valueOf(c));
        }
        return sequence.toString().indexOf(c);
    }
    
    public static int lastIndexOf(final CharSequence sequence, final char c) {
        if (sequence instanceof final String text) {
            return text.lastIndexOf(c);
        }
        if (sequence instanceof final StringBuilder builder) {
            return builder.lastIndexOf(String.valueOf(c));
        }
        return sequence.toString().lastIndexOf(c);
    }
    
    public static CharSequence toLowerCase(final CharSequence sequence) {
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < sequence.length(); ++index) {
            final char character = Character.toLowerCase(sequence.charAt(index));
            builder.append(character);
        }
        return builder.toString();
    }
    
    public static CharSequence toUpperCase(final CharSequence sequence) {
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < sequence.length(); ++index) {
            final char character = Character.toUpperCase(sequence.charAt(index));
            builder.append(character);
        }
        return builder.toString();
    }
    
    public static CharSequence capitalize(final CharSequence sequence) {
        return capitalize(sequence, false);
    }
    
    public static CharSequence capitalize(final CharSequence sequence, final boolean everyWord) {
        final StringBuilder builder = new StringBuilder();
        final int length = sequence.length();
        boolean shouldNextCapitalize = false;
        for (int index = 0; index < length; ++index) {
            final char c = sequence.charAt(index);
            if (index == 0 || shouldNextCapitalize) {
                builder.append(Character.toUpperCase(c));
                shouldNextCapitalize = false;
            }
            else {
                if (everyWord && c == ' ') {
                    shouldNextCapitalize = true;
                }
                builder.append(c);
            }
        }
        return builder.toString();
    }
    
    public static String toString(final CharSequence sequence) {
        return String.valueOf(sequence);
    }
    
    public static byte[] toByteArray(final CharSequence sequence, final Charset charset) {
        if (sequence == null) {
            return new byte[0];
        }
        return sequence.toString().getBytes(charset);
    }
}
