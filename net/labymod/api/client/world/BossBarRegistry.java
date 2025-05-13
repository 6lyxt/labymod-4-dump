// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

import org.spongepowered.include.com.google.common.collect.Sets;
import java.util.Set;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public abstract class BossBarRegistry
{
    private final Set<BossBar> bossBars;
    
    protected BossBarRegistry() {
        this.bossBars = Sets.newHashSet();
    }
    
    public Set<BossBar> getBossBars() {
        return this.bossBars;
    }
    
    public void registerBossBar(final BossBar bossBar) {
        this.bossBars.add(bossBar);
    }
    
    public void unregisterBossBar(final BossBar bossBar) {
        this.bossBars.remove(bossBar);
    }
    
    public abstract float getOffset();
}
