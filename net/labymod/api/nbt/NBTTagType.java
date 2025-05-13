// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.nbt;

public enum NBTTagType
{
    BYTE((byte)1), 
    SHORT((byte)2), 
    INT((byte)3), 
    LONG((byte)4), 
    FLOAT((byte)5), 
    DOUBLE((byte)6), 
    BYTE_ARRAY((byte)7), 
    STRING((byte)8), 
    LIST((byte)9), 
    COMPOUND((byte)10), 
    INT_ARRAY((byte)11), 
    LONG_ARRAY((byte)12), 
    ANY_NUMERIC((byte)99);
    
    private static final NBTTagType[] TYPES;
    private final byte id;
    
    private NBTTagType(final byte id) {
        this.id = id;
    }
    
    public static NBTTagType getById(final byte id) {
        return (id >= 0 && id < NBTTagType.TYPES.length) ? NBTTagType.TYPES[id] : null;
    }
    
    public byte getId() {
        return this.id;
    }
    
    static {
        TYPES = new NBTTagType[127];
        for (final NBTTagType type : values()) {
            NBTTagType.TYPES[type.id] = type;
        }
    }
}
