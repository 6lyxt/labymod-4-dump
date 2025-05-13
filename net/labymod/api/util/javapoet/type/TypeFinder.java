// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.javapoet.type;

import java.util.HashMap;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.WildcardTypeName;
import java.lang.reflect.Type;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.TypeName;

public final class TypeFinder
{
    private static final char OPEN_ANGLE_BRACKET = '<';
    private static final char CLOSING_ANGLE_BRACKET = '>';
    private static final char CLOSING_SQUARE_BRACKET = ']';
    private static final char COMMA = ',';
    private static final char QUESTION_MARK = '?';
    private static final String EXTENDS = "extends";
    private static final String SUPER = "super";
    
    public static TypeName findTypeName(final String type) {
        try {
            final String trimmedType = type.trim();
            if (trimmedType.endsWith(String.valueOf(']'))) {
                final String typeWithoutArray = trimmedType.substring(0, trimmedType.length() - 2);
                return (TypeName)ArrayTypeName.of(findTypeName(typeWithoutArray));
            }
            return findType(trimmedType);
        }
        catch (final StringIndexOutOfBoundsException exception) {
            throw new IllegalArgumentException(type, exception);
        }
    }
    
    private static TypeName findType(final String type) {
        final TypeName name = PrimitiveType.getName(type);
        return (name == null) ? findObjectType(type) : name;
    }
    
    private static TypeName findObjectType(final String type) {
        if (type.startsWith(String.valueOf('?'))) {
            return findWildcardType(type);
        }
        if (type.contains(String.valueOf('<'))) {
            return findGenericType(type);
        }
        return (TypeName)ClassName.bestGuess(type);
    }
    
    private static TypeName findWildcardType(final String type) {
        final int indexOfExtends = type.indexOf("extends");
        final int indexOfSuper = type.indexOf("super");
        if (firstBeforeSecond(indexOfExtends, indexOfSuper)) {
            return findExtendingType(type);
        }
        if (firstBeforeSecond(indexOfSuper, indexOfExtends)) {
            return findSuperType(type);
        }
        return (TypeName)WildcardTypeName.subtypeOf(TypeName.get((Type)Object.class));
    }
    
    private static TypeName findExtendingType(final String type) {
        return (TypeName)WildcardTypeName.subtypeOf(findTypeName(type.substring(type.indexOf("extends") + "extends".length())));
    }
    
    private static TypeName findSuperType(final String type) {
        return (TypeName)WildcardTypeName.supertypeOf(findTypeName(type.substring(type.indexOf("super") + "super".length())));
    }
    
    private static TypeName findGenericType(final String type) {
        final String className = type.substring(0, type.indexOf(60)).trim();
        final String genericContent = type.substring(type.indexOf(60) + 1, type.lastIndexOf(62));
        final ClassName name = ClassName.bestGuess(className);
        return (TypeName)ParameterizedTypeName.get(name, findGenericTypeArguments(genericContent));
    }
    
    private static TypeName[] findGenericTypeArguments(final String content) {
        final List<TypeName> types = new ArrayList<TypeName>();
        String parse = content;
        while (!parse.isEmpty()) {
            final int indexComma = parse.indexOf(44);
            final int indexAngle = parse.indexOf(60);
            if (firstBeforeSecond(indexComma, indexAngle)) {
                types.add(findTypeName(parse.substring(0, indexComma)));
                parse = parse.substring(indexComma + 1);
            }
            else if (firstBeforeSecond(indexAngle, indexComma)) {
                final int endIndex = endIndexOfGenericType(parse);
                types.add(findTypeName(parse.substring(0, endIndex)));
                parse = parse.substring(Math.min(endIndex + 1, parse.length()));
            }
            else {
                types.add(findTypeName(parse));
                parse = "";
            }
        }
        return types.toArray(new TypeName[0]);
    }
    
    private static boolean firstBeforeSecond(final int first, final int second) {
        return first(first, second) || only(first, second);
    }
    
    private static boolean first(final int first, final int second) {
        return exists(first, second) && first < second;
    }
    
    private static boolean only(final int first, final int second) {
        return exists(first) && !firstBeforeSecond(second, first);
    }
    
    private static boolean exists(final int first, final int second) {
        return exists(first) && exists(second);
    }
    
    private static boolean exists(final int index) {
        return index >= 0;
    }
    
    private static int endIndexOfGenericType(final String content) {
        int countOpenAngles = 0;
        int countCloseAngles = 0;
        int endIndex;
        for (int contentLength = endIndex = content.length(), index = 0; index < contentLength; ++index) {
            if (content.charAt(index) == '<') {
                ++countOpenAngles;
            }
            if (content.charAt(index) == '>') {
                ++countCloseAngles;
                if (countOpenAngles == countCloseAngles) {
                    endIndex = index + 1;
                    break;
                }
            }
        }
        return endIndex;
    }
    
    enum PrimitiveType
    {
        BOOLEAN(TypeName.BOOLEAN), 
        BYTE(TypeName.BYTE), 
        SHORT(TypeName.SHORT), 
        LONG(TypeName.LONG), 
        CHAR(TypeName.CHAR), 
        FLOAT(TypeName.FLOAT), 
        DOUBLE(TypeName.DOUBLE), 
        INT(TypeName.INT);
        
        private static final Map<String, PrimitiveType> TYPES;
        private final String name;
        private final TypeName typeName;
        
        private PrimitiveType(final TypeName typeName) {
            this.name = this.name().toLowerCase(Locale.ENGLISH);
            this.typeName = typeName;
        }
        
        @Nullable
        public static TypeName getName(final String type) {
            final PrimitiveType primitiveType = PrimitiveType.TYPES.getOrDefault(type, null);
            if (primitiveType == null) {
                return null;
            }
            return primitiveType.typeName;
        }
        
        static {
            TYPES = new HashMap<String, PrimitiveType>();
            for (final PrimitiveType value : values()) {
                PrimitiveType.TYPES.put(value.name, value);
            }
        }
    }
}
