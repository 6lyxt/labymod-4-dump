// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<bib, bid>
{
    private final Map<bib, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<bib, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final bib key, final bid value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final bib key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final bib key, final bid value) {
        this.activePotions.remove(key, value);
    }
}
