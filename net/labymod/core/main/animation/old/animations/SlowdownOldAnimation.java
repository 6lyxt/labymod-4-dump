// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class SlowdownOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "slowdown";
    private final IntaveProtocol intaveProtocol;
    
    public SlowdownOldAnimation() {
        super("slowdown");
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }
    
    @Override
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(this.permissionRegistry.isPermissionEnabled("slowdown", this.classicPvPConfig.oldSlowdown()));
    }
    
    public boolean isEnabled(final float forwardImpulse) {
        return forwardImpulse > 0.8f;
    }
}
