// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<jr<btp>, btr>
{
    private final Map<jr<btp>, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<jr<btp>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final jr<btp> key, final btr value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final jr<btp> key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final jr<btp> key, final btr value) {
        this.activePotions.remove(key, value);
    }
}
