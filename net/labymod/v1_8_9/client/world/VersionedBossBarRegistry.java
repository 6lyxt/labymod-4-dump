// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world;

import java.util.Objects;
import net.labymod.api.client.world.BossBar;
import net.labymod.v1_8_9.client.world.phys.hit.VersionedBossBar;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.BossBarRegistry;

@Singleton
@Implements(BossBarRegistry.class)
public class VersionedBossBarRegistry extends BossBarRegistry
{
    private VersionedBossBar bossBar;
    
    public VersionedBossBarRegistry() {
        this.bossBar = new VersionedBossBar(null);
    }
    
    @Override
    public void registerBossBar(final BossBar bossBar) {
        if (Objects.equals(this.bossBar, bossBar) || !(bossBar instanceof VersionedBossBar)) {
            return;
        }
        this.bossBar = (VersionedBossBar)bossBar;
        this.getBossBars().clear();
        super.registerBossBar(bossBar);
    }
    
    @Override
    public void unregisterBossBar(final BossBar bossBar) {
        if (this.bossBar == null) {
            return;
        }
        super.unregisterBossBar(this.bossBar);
        this.bossBar = null;
    }
    
    @Override
    public float getOffset() {
        if (this.bossBar == null) {
            return 0.0f;
        }
        return 19.0f;
    }
    
    public void registerBossBar(final uc displayData) {
        if (this.bossBar != null && Objects.equals(this.bossBar.getLastDisplayData(), displayData)) {
            this.bossBar.setIdentifier(displayData);
            return;
        }
        this.registerBossBar(new VersionedBossBar(displayData));
    }
}
