// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<bbe, bbg>
{
    private final Map<bbe, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<bbe, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final bbe key, final bbg value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final bbe key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final bbe key, final bbg value) {
        this.activePotions.remove(key, value);
    }
}
