// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public enum TextFormat permits TextFormat$1, TextFormat$2, TextFormat$3
{
    CAMEL_CASE {
        @NotNull
        @Override
        public String toCamelCase(@NotNull final String text, final boolean lower) {
            if (text.isEmpty()) {
                return text;
            }
            final char c = text.charAt(0);
            return (Character.isLowerCase(c) == lower) ? text : ((lower ? Character.toLowerCase(c) : Character.toUpperCase(c)) + text.substring(1));
        }
        
        @NotNull
        @Override
        public String toDashCase(@NotNull final String text) {
            final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < text.length(); ++i) {
                final char c = text.charAt(i);
                if (Character.isUpperCase(c)) {
                    if (i != 0) {
                        final char prev = text.charAt(i - 1);
                        if (Character.isAlphabetic(prev) || Character.isDigit(prev)) {
                            builder.append('-');
                        }
                    }
                    builder.append(Character.toLowerCase(c));
                }
                else {
                    builder.append(c);
                }
            }
            return builder.toString();
        }
        
        @NotNull
        @Override
        public String toSnakeCase(@NotNull final String text) {
            return this.toDashCase(text).replace('-', '_').toUpperCase(Locale.ENGLISH);
        }
    }, 
    DASH_CASE {
        @NotNull
        @Override
        public String toCamelCase(@NotNull final String text, final boolean lower) {
            final StringBuilder builder = new StringBuilder();
            boolean newWord = false;
            for (int i = 0; i < text.length(); ++i) {
                char character = text.charAt(i);
                if (character == '-') {
                    newWord = true;
                }
                else {
                    character = ((newWord || (i == 0 && !lower)) ? Character.toUpperCase(character) : Character.toLowerCase(character));
                    newWord = false;
                    builder.append(character);
                }
            }
            return builder.toString();
        }
        
        @NotNull
        @Override
        public String toDashCase(@NotNull final String text) {
            return text;
        }
        
        @NotNull
        @Override
        public String toSnakeCase(@NotNull final String text) {
            return text.replace('-', '_').toUpperCase(Locale.ENGLISH);
        }
    }, 
    SNAKE_CASE {
        @NotNull
        @Override
        public String toCamelCase(@NotNull final String text, final boolean lower) {
            final StringBuilder builder = new StringBuilder();
            boolean newWord = false;
            for (int i = 0; i < text.length(); ++i) {
                char character = text.charAt(i);
                if (character == '_') {
                    newWord = true;
                }
                else {
                    character = ((newWord || (i == 0 && !lower)) ? Character.toUpperCase(character) : Character.toLowerCase(character));
                    newWord = false;
                    builder.append(character);
                }
            }
            return builder.toString();
        }
        
        @NotNull
        @Override
        public String toDashCase(@NotNull final String text) {
            return text.replace('_', '-').toLowerCase(Locale.ENGLISH);
        }
        
        @NotNull
        @Override
        public String toSnakeCase(@NotNull final String text) {
            return text;
        }
    };
    
    @Deprecated
    @NotNull
    public abstract String toCamelCase(@NotNull final String p0, final boolean p1);
    
    @NotNull
    public abstract String toDashCase(@NotNull final String p0);
    
    @NotNull
    public abstract String toSnakeCase(@NotNull final String p0);
    
    @NotNull
    public String toUpperCamelCase(final String text) {
        return this.toCamelCase(text, false);
    }
    
    @NotNull
    public String toLowerCamelCase(final String text) {
        return this.toCamelCase(text, true);
    }
}
