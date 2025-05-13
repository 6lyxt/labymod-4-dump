// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

public enum OrientationTarget
{
    CAMERA, 
    NORTH;
    
    public static OrientationTarget getById(final String id) {
        for (final OrientationTarget target : values()) {
            if (target.name().equalsIgnoreCase(id)) {
                return target;
            }
        }
        return null;
    }
}
