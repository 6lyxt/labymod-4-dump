// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

public enum PhysicMapping
{
    X, 
    Y, 
    Z, 
    F, 
    G, 
    S, 
    N;
    
    public static PhysicMapping get(final char character) {
        return (character == 'x') ? PhysicMapping.X : ((character == 'y') ? PhysicMapping.Y : ((character == 'z') ? PhysicMapping.Z : ((character == 'f') ? PhysicMapping.F : ((character == 's') ? PhysicMapping.S : ((character == 'g') ? PhysicMapping.G : PhysicMapping.N)))));
    }
}
