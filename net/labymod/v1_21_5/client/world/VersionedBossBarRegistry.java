// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.world;

import net.labymod.api.client.world.BossBar;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.BossBarRegistry;

@Singleton
@Implements(BossBarRegistry.class)
public class VersionedBossBarRegistry extends BossBarRegistry
{
    private final LabyAPI labyAPI;
    
    @Inject
    public VersionedBossBarRegistry(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Override
    public float getOffset() {
        final int size = this.getBossBars().size();
        if (size == 0) {
            return 0.0f;
        }
        final float offset = size * 18.0f + (size - 1) + 1.0f;
        final float maxHeight = this.labyAPI.minecraft().minecraftWindow().getScaledHeight() / 3.0f;
        return Math.min(offset, maxHeight);
    }
    
    public void reset() {
        for (final BossBar bossBar : this.getBossBars().toArray(new BossBar[0])) {
            this.unregisterBossBar(bossBar);
        }
    }
}
