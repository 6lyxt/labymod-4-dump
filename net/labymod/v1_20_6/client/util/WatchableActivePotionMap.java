// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<ji<bsc>, bse>
{
    private final Map<ji<bsc>, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<ji<bsc>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final ji<bsc> key, final bse value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final ji<bsc> key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final ji<bsc> key, final bse value) {
        this.activePotions.remove(key, value);
    }
}
