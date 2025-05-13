// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<jm<brx>, brz>
{
    private final Map<jm<brx>, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<jm<brx>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final jm<brx> key, final brz value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final jm<brx> key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final jm<brx> key, final brz value) {
        this.activePotions.remove(key, value);
    }
}
