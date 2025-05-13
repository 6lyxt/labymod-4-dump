// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old;

import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.user.permission.PermissionRegistry;

public abstract class AbstractOldAnimation implements OldAnimation
{
    protected static final float UNIT = 0.0625f;
    protected final PermissionRegistry permissionRegistry;
    protected final OldAnimationRegistry animationRegistry;
    protected final ClassicPvPConfig classicPvPConfig;
    private final String name;
    
    public AbstractOldAnimation(final String name) {
        this.name = name;
        this.permissionRegistry = Laby.references().permissionRegistry();
        this.animationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        this.classicPvPConfig = Laby.labyAPI().config().multiplayer().classicPvP();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    protected <T extends OldAnimation> T getAnimation(final String id) {
        return this.animationRegistry.get(id);
    }
}
