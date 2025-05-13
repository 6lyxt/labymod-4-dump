// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class HeadRotationOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "head_rotation";
    private static final int OLD_HEAD_ROTATION_PITCH_STEP = 1;
    private final IntaveProtocol intaveProtocol;
    
    public HeadRotationOldAnimation() {
        super("head_rotation");
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }
    
    @Override
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldHeadRotation()));
    }
    
    public int getOldHeadRotationPitchStep() {
        return 1;
    }
}
