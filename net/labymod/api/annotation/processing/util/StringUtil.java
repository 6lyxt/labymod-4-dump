// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.annotation.processing.util;

import net.labymod.api.util.javapoet.type.TypeFinder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

public final class StringUtil
{
    public static CharSequence capitalize(final CharSequence sequence) {
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < sequence.length(); ++index) {
            char character = sequence.charAt(index);
            if (index == 0) {
                character = Character.toUpperCase(sequence.charAt(index));
            }
            builder.append(character);
        }
        return builder.toString();
    }
    
    public static String validateName(String sequence) {
        if (sequence == null) {
            sequence = "unknown";
        }
        return sequence.replace("-", "_");
    }
    
    public static String getSimpleName(final TypeName name) {
        if (name instanceof final ClassName className) {
            return className.simpleName();
        }
        if (name instanceof final ParameterizedTypeName parameterizedTypeName) {
            return parameterizedTypeName.rawType.simpleName();
        }
        return name.toString();
    }
    
    public static String getSimpleTypeName(final String name) {
        return getSimpleName(TypeFinder.findTypeName(name));
    }
}
