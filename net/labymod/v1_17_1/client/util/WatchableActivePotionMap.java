// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<asy, ata>
{
    private final Map<asy, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<asy, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final asy key, final ata value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final asy key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final asy key, final ata value) {
        this.activePotions.remove(key, value);
    }
}
