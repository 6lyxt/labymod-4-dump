// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class RangeOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "range";
    private static final float OLD_PICK_RADIUS = 0.1f;
    private final IntaveProtocol intaveProtocol;
    
    public RangeOldAnimation() {
        super("range");
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }
    
    @Override
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(this.permissionRegistry.isPermissionEnabled("range", this.classicPvPConfig.oldRange()));
    }
    
    public float getOldPickRadius() {
        return this.isEnabled() ? 0.1f : 0.0f;
    }
}
