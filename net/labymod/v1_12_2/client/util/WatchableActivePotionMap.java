// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<uz, va>
{
    private final Map<uz, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<uz, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final uz key, final va value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final uz key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final uz key, final va value) {
        this.activePotions.remove(key, value);
    }
}
