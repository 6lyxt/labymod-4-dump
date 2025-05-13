// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity;

import java.util.function.Function;
import java.util.HashMap;
import net.labymod.api.client.entity.datawatcher.DataPoint;
import java.util.Map;
import net.labymod.api.client.entity.datawatcher.DataWatcher;

public class DefaultDataWatcher implements DataWatcher
{
    private final Map<String, DataPoint> dataMap;
    
    public DefaultDataWatcher() {
        this.dataMap = new HashMap<String, DataPoint>();
    }
    
    @Override
    public <T> void set(final String key, final T value) {
        this.dataMap.put(key, new DataPoint(value));
    }
    
    @Override
    public void remove(final String key) {
        this.dataMap.remove(key);
    }
    
    @Override
    public <T> DataPoint<T> get(final String key) {
        return this.get(key, (T)null);
    }
    
    @Override
    public <T> DataPoint<T> get(final String key, final T defaultValue) {
        return this.dataMap.getOrDefault(key, new DataPoint(defaultValue));
    }
    
    @Override
    public <T> DataPoint<T> computeIfAbsent(final String key, final Function<String, ? extends T> compute) {
        return this.dataMap.computeIfAbsent(key, absent -> new DataPoint(compute.apply(key)));
    }
    
    @Override
    public boolean has(final String key) {
        final DataPoint dataPoint = this.dataMap.get(key);
        return dataPoint != null && dataPoint.isPresent();
    }
}
