// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<axc, axe>
{
    private final Map<axc, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<axc, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final axc key, final axe value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final axc key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final axc key, final axe value) {
        this.activePotions.remove(key, value);
    }
}
