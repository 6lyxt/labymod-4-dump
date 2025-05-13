// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.Laby;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class HeartOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "heart";
    private static final int FULL_FLASH = 70;
    private static final int HALF_FLASH = 79;
    private static final int FLASHING_BACKGROUND = 25;
    private final PermissionRegistry permissionRegistry;
    
    public HeartOldAnimation() {
        super("heart");
        this.permissionRegistry = Laby.references().permissionRegistry();
    }
    
    public int getU(final int u) {
        if (!this.isEnabled()) {
            return u;
        }
        if (u == 70 || u == 79) {
            return 25;
        }
        return u;
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldHeart());
    }
}
