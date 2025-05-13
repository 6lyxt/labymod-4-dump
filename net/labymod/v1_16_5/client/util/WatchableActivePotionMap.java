// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<aps, apu>
{
    private final Map<aps, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<aps, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final aps key, final apu value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final aps key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final aps key, final apu value) {
        this.activePotions.remove(key, value);
    }
}
