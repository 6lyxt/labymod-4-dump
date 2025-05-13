// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.primitive;

import java.util.HashMap;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import java.util.Map;

public final class Primitives
{
    private static final Map<String, Primitive> PRIMITIVES;
    public static final Primitive INTEGER;
    public static final Primitive DOUBLE;
    public static final Primitive LONG;
    public static final Primitive FLOAT;
    public static final Primitive BYTE;
    public static final Primitive SHORT;
    public static final Primitive BOOLEAN;
    public static final Primitive VOID;
    
    @NotNull
    private static Primitive register(final String bytecodeName, final Type type, final String methodName, final String methodDescriptor, final int returnOpcode, final int loadOpcode, final int arrayLoadOpcode, final int storeOpcode, final int arrayStoreOpcode) {
        final Primitive primitive = new Primitive(bytecodeName, type, methodName, methodDescriptor, returnOpcode, loadOpcode, arrayLoadOpcode, storeOpcode, arrayStoreOpcode);
        Primitives.PRIMITIVES.put(primitive.getBytecodeName(), primitive);
        return primitive;
    }
    
    public static boolean isPrimitive(final String bytecodeName) {
        return getPrimitive(bytecodeName) != null;
    }
    
    public static boolean isPrimitive(final Type type) {
        for (final Primitive primitive : Primitives.PRIMITIVES.values()) {
            if (primitive.isPrimitive(type.getInternalName())) {
                return true;
            }
        }
        return false;
    }
    
    public static Primitive getPrimitive(final Type type) {
        for (final Primitive primitive : Primitives.PRIMITIVES.values()) {
            if (primitive.isPrimitive(type.getInternalName())) {
                return primitive;
            }
        }
        return null;
    }
    
    public static Primitive getPrimitive(final String bytecodeName) {
        return Primitives.PRIMITIVES.get(bytecodeName);
    }
    
    static {
        PRIMITIVES = new HashMap<String, Primitive>();
        INTEGER = register("I", Type.getType((Class)Integer.class), "intValue", "()I", 172, 21, 46, 54, 79);
        DOUBLE = register("D", Type.getType((Class)Double.class), "doubleValue", "()D", 175, 24, 49, 57, 82);
        LONG = register("J", Type.getType((Class)Long.class), "longValue", "()J", 173, 22, 47, 55, 80);
        FLOAT = register("F", Type.getType((Class)Float.class), "floatValue", "()F", 174, 23, 48, 56, 81);
        BYTE = register("B", Type.getType((Class)Byte.class), "byteValue", "()B", 172, 21, 46, 54, 79);
        SHORT = register("S", Type.getType((Class)Short.class), "shortValue", "()S", 172, 21, 53, 54, 86);
        BOOLEAN = register("Z", Type.getType((Class)Boolean.class), "booleanValue", "()Z", 172, 21, 46, 54, 79);
        VOID = register("V", Type.VOID_TYPE, null, null, 177, 0, 0, 0, 0);
    }
}
