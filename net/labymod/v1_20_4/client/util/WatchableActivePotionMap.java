// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<blg, bli>
{
    private final Map<blg, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<blg, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final blg key, final bli value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final blg key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final blg key, final bli value) {
        this.activePotions.remove(key, value);
    }
}
