// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import it.unimi.dsi.fastutil.chars.CharPredicate;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Iterator;
import java.util.Objects;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.function.Predicate;

public final class StringUtil
{
    private static final Predicate<Character> MINECRAFT_ILLEGAL_CHARACTERS;
    
    private StringUtil() {
    }
    
    public static String format(final String format, final Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
    
    @NotNull
    public static String removeIllegalCharacters(final String value) {
        return removeIllegalCharacters(value, StringUtil.MINECRAFT_ILLEGAL_CHARACTERS);
    }
    
    @NotNull
    public static String removeIllegalCharacters(final String value, @NotNull final Predicate<Character> filter) {
        if (value == null) {
            return "";
        }
        StringBuilder builder = null;
        for (int length = value.length(), index = 0; index < length; ++index) {
            final char character = value.charAt(index);
            if (filter.test(character)) {
                if (builder == null) {
                    builder = new StringBuilder(length);
                    builder.append(value, 0, index);
                }
            }
            else if (builder != null) {
                builder.append(character);
            }
        }
        return (builder == null) ? value : builder.toString();
    }
    
    @NotNull
    public static String toUppercase(@NotNull final String value) {
        final StringBuilder upperTheBuilder = new StringBuilder();
        for (final char c : value.toCharArray()) {
            upperTheBuilder.append(Character.toUpperCase(c));
        }
        return upperTheBuilder.toString();
    }
    
    @NotNull
    public static String toLowercase(@NotNull final String value) {
        final StringBuilder lowerTheBuilder = new StringBuilder();
        for (final char c : value.toCharArray()) {
            lowerTheBuilder.append(Character.toLowerCase(c));
        }
        return lowerTheBuilder.toString();
    }
    
    @NotNull
    public static String join(@NotNull final Collection<?> entries) {
        return join(entries, ", ");
    }
    
    @NotNull
    public static String join(@NotNull final Iterable<?> entries, @NotNull final CharSequence delimiter) {
        Objects.requireNonNull(entries, "entries must not be null");
        Objects.requireNonNull(delimiter, "delimiter must not be null");
        final StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final Object argument : entries) {
            if (!first) {
                builder.append(delimiter);
            }
            first = false;
            builder.append((argument == null) ? null : argument.toString());
        }
        return builder.toString();
    }
    
    @NotNull
    public static String join(@NotNull final Collection<?> entries, @NotNull final CharSequence delimiter) {
        return join((Iterable<?>)entries, delimiter);
    }
    
    @NotNull
    public static String join(@NotNull final Map<?, ?> entries) {
        return join(entries, ", ");
    }
    
    public static String join(@NotNull final Map<?, ?> entries, @NotNull final CharSequence delimiter) {
        Objects.requireNonNull(entries, "Map must not be null");
        Objects.requireNonNull(delimiter, "Delimiter must not be null");
        final StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final Map.Entry<?, ?> entry : entries.entrySet()) {
            if (!first) {
                builder.append(delimiter);
            }
            first = false;
            final Object value = entry.getValue();
            String valueString;
            if (value instanceof Collection) {
                valueString = join((Collection<?>)value, ", ");
            }
            else {
                valueString = ((value == null) ? null : value.toString());
            }
            final Object key = entry.getKey();
            builder.append((key == null) ? null : key.toString()).append(": ").append(valueString);
        }
        return builder.toString();
    }
    
    public static boolean isNumeric(final String value) {
        if (value == null) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        }
        catch (final NumberFormatException exception) {
            return false;
        }
    }
    
    public static boolean containsIgnoreCase(final String s, final String search) {
        if (s == null || search == null) {
            return false;
        }
        final int length = search.length();
        if (length == 0) {
            return true;
        }
        for (int i = s.length() - length; i >= 0; --i) {
            if (s.regionMatches(true, i, search, 0, length)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean startsWithIgnoreCase(final String s, final String prefix) {
        return s.regionMatches(true, 0, prefix, 0, prefix.length());
    }
    
    public static boolean endsWithIgnoreCase(final String s, final String suffix) {
        final int suffixLength = suffix.length();
        return s.regionMatches(true, s.length() - suffixLength, suffix, 0, suffixLength);
    }
    
    public static String capitalizeWords(final String input) {
        if (input == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            }
            else if (nextTitleCase) {
                c = Character.toUpperCase(c);
                nextTitleCase = false;
            }
            else if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
            }
            builder.append(c);
        }
        return builder.toString();
    }
    
    public static String parseEscapedUnicode(final String input) {
        if (input == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        for (int length = input.getBytes(StandardCharsets.UTF_8).length, i = 0; i <= length - 1; ++i) {
            final char c = input.charAt(i);
            if (c == '\\' && i + 5 < length && input.charAt(i + 1) == 'u') {
                try {
                    final int code = Integer.parseInt(input.substring(i + 2, i + 6), 16);
                    builder.append((char)code);
                    i += 5;
                    continue;
                }
                catch (final NumberFormatException ex) {}
            }
            builder.append(c);
        }
        return builder.toString();
    }
    
    public static String sanitizePath(final String name) {
        return sanitizeName(name, StringUtil::validPathChar);
    }
    
    public static String sanitizeName(final String name, final CharPredicate tester) {
        final String lowerCase = name.toLowerCase(Locale.ROOT);
        final char[] chars = lowerCase.toCharArray();
        final StringBuilder bobTheBuilder = new StringBuilder();
        for (final char c : chars) {
            if (tester.test(c)) {
                bobTheBuilder.append(c);
            }
            else {
                bobTheBuilder.append('_');
            }
        }
        return bobTheBuilder.toString();
    }
    
    public static boolean validPathChar(final char value) {
        return value == '_' || value == '-' || (value >= 'a' && value <= 'z') || (value >= '0' && value <= '9') || value == '/' || value == '.';
    }
    
    static {
        MINECRAFT_ILLEGAL_CHARACTERS = (character -> character == '§' || character < ' ' || character == '\u007f');
    }
}
