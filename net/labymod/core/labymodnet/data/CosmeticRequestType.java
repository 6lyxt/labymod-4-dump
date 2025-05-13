// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.data;

import net.labymod.api.util.StringUtil;

public enum CosmeticRequestType
{
    SWITCH, 
    MULTI;
    
    private static final CosmeticRequestType[] VALUES;
    private final String lowercaseName;
    
    public static CosmeticRequestType[] getValues() {
        return CosmeticRequestType.VALUES;
    }
    
    private CosmeticRequestType() {
        this.lowercaseName = StringUtil.toLowercase(this.name());
    }
    
    public static CosmeticRequestType get(final String name) {
        for (final CosmeticRequestType value : CosmeticRequestType.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + CosmeticRequestType.class.getCanonicalName() + "." + name);
    }
    
    public static CosmeticRequestType getOrDefault(final String name, final CosmeticRequestType defaultValue) {
        for (final CosmeticRequestType value : CosmeticRequestType.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
    
    @Override
    public String toString() {
        return this.lowercaseName;
    }
    
    static {
        VALUES = values();
    }
}
