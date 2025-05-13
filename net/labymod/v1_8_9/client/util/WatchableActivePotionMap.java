// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<Integer, pf>
{
    private final Map<Integer, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<Integer, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final Integer key, final pf value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final Integer key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final Integer key, final pf value) {
        this.activePotions.remove(key, value);
    }
}
