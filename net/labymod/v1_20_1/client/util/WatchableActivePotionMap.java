// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<bey, bfa>
{
    private final Map<bey, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<bey, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final bey key, final bfa value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final bey key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final bey key, final bfa value) {
        this.activePotions.remove(key, value);
    }
}
