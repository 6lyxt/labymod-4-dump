// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

public final class ScreenName
{
    private final Type type;
    private final Class<?> identifier;
    
    private ScreenName(final Type type, final Class<?> identifier) {
        this.type = type;
        this.identifier = identifier;
    }
    
    public static ScreenName typed(final Type type, final Class<?> identifier) {
        return new ScreenName(type, identifier);
    }
    
    public static ScreenName minecraft(final Class<?> identifier) {
        return typed(Type.MINECRAFT, identifier);
    }
    
    public static ScreenName fabric(final Class<?> identifier) {
        return typed(Type.FABRIC, identifier);
    }
    
    public static ScreenName forge(final Class<?> identifier) {
        return typed(Type.FORGE, identifier);
    }
    
    public static ScreenName optifine(final Class<?> identifier) {
        return typed(Type.OPTIFINE, identifier);
    }
    
    public static ScreenName unknown(final Class<?> identifier) {
        return typed(Type.UNKNOWN, identifier);
    }
    
    public Type getType() {
        return this.type;
    }
    
    public Class<?> getIdentifier() {
        return this.identifier;
    }
    
    public enum Type
    {
        MINECRAFT, 
        FABRIC, 
        FORGE, 
        OPTIFINE, 
        UNKNOWN;
    }
}
