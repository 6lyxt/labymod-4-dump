// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.util;

import net.labymod.api.volt.asm.primitive.Primitive;
import net.labymod.api.volt.asm.primitive.Primitives;
import org.objectweb.asm.Type;

public final class OpcodesUtil
{
    public static int getField(final boolean isStatic) {
        return isStatic ? 178 : 180;
    }
    
    public static int putField(final boolean isStatic) {
        return isStatic ? 179 : 181;
    }
    
    public static int invokeMethod(final boolean isStatic) {
        return isStatic ? 184 : 182;
    }
    
    public static int publicAccess(final boolean isStatic) {
        return isStatic ? 9 : 1;
    }
    
    public static int protectedAccess(final boolean isStatic) {
        return isStatic ? 12 : 4;
    }
    
    public static int privateAccess(final boolean isStatic) {
        return isStatic ? 10 : 2;
    }
    
    public static boolean isReturnOpcode(final int opcode) {
        return opcode == 177 || opcode == 176 || opcode == 172 || opcode == 174 || opcode == 175 || opcode == 173;
    }
    
    public static int getOpcodeLoad(final Type type, final boolean array) {
        final Primitive primitive = Primitives.getPrimitive(type);
        if (primitive == null) {
            return array ? 50 : 25;
        }
        return primitive.getLoadOpcode(array);
    }
    
    public static int getOpcodeStore(final Type type, final boolean array) {
        final Primitive primitive = Primitives.getPrimitive(type);
        if (primitive == null) {
            return array ? 83 : 58;
        }
        return primitive.getStoreOpcode(array);
    }
}
