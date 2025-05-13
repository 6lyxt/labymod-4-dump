// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class BackwardsOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "backwards";
    private final IntaveProtocol intaveProtocol;
    private final PermissionRegistry permissionRegistry;
    
    public BackwardsOldAnimation() {
        super("backwards");
        this.intaveProtocol = LabyMod.references().intaveProtocol();
        this.permissionRegistry = Laby.references().permissionRegistry();
    }
    
    @Override
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldBackwards()));
    }
    
    public float modify(final float value) {
        return this.isEnabled() ? 0.0f : value;
    }
}
