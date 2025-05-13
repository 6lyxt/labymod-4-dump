// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<jg<bwg>, bwi>
{
    private final Map<jg<bwg>, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<jg<bwg>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final jg<bwg> key, final bwi value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final jg<bwg> key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final jg<bwg> key, final bwi value) {
        this.activePotions.remove(key, value);
    }
}
