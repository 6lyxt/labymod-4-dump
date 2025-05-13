// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<bdi, bdk>
{
    private final Map<bdi, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<bdi, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final bdi key, final bdk value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final bdi key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final bdi key, final bdk value) {
        this.activePotions.remove(key, value);
    }
}
