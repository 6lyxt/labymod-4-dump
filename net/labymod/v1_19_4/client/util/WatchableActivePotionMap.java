// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.util;

import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import net.labymod.core.watcher.map.WatchableMap;

public class WatchableActivePotionMap implements WatchableMap<bew, bey>
{
    private final Map<bew, PotionEffect> activePotions;
    
    public WatchableActivePotionMap(final Map<bew, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }
    
    @Override
    public void onPut(final bew key, final bey value) {
        this.activePotions.put(key, (PotionEffect)value);
    }
    
    @Override
    public void onClear() {
        this.activePotions.clear();
    }
    
    @Override
    public void onRemove(final bew key) {
        this.activePotions.remove(key);
    }
    
    @Override
    public void onRemove(final bew key, final bey value) {
        this.activePotions.remove(key, value);
    }
}
