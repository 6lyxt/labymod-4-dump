// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.collection;

import net.labymod.api.util.time.TimeUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import java.util.List;
import java.util.HashMap;

public class TimestampedCache<K, V> extends HashMap<K, TimestampedValue<V>>
{
    private static final long NULL = 0L;
    private static final long DEFAULT_MAX_LIFE_DURATION = 30000L;
    private static final long DEFAULT_CHECK_DURATION = 5000L;
    private final List<K> removableEntries;
    private final long maxLifeDuration;
    private final long checkDuration;
    private long lastCheckTimestamp;
    @Nullable
    private Consumer<V> removeConsumer;
    
    public TimestampedCache(final int initialCapacity) {
        this(initialCapacity, 0L, 0L);
    }
    
    public TimestampedCache(final int initialCapacity, final long maxLifeDuration) {
        this(initialCapacity, maxLifeDuration, 0L);
    }
    
    public TimestampedCache(final int initialCapacity, final long maxLifeDuration, final long checkDuration) {
        super(initialCapacity);
        this.removableEntries = new ArrayList<K>();
        this.removeConsumer = null;
        this.maxLifeDuration = ((maxLifeDuration == 0L) ? 30000L : maxLifeDuration);
        this.checkDuration = ((checkDuration == 0L) ? 5000L : checkDuration);
    }
    
    public TimestampedValue<V> putTimestamped(final K key, final V value) {
        return this.put(key, new TimestampedValue<V>(value));
    }
    
    @Override
    public TimestampedValue<V> computeIfAbsent(final K key, final Function<? super K, ? extends TimestampedValue<V>> mappingFunction) {
        return super.computeIfAbsent(key, mappingFunction);
    }
    
    @Override
    public TimestampedValue<V> put(final K key, final TimestampedValue<V> value) {
        this.checkEntries();
        return super.put(key, value);
    }
    
    @Override
    public TimestampedValue<V> remove(final Object key) {
        final TimestampedValue<V> value = super.remove(key);
        this.consumeRemove(value);
        return value;
    }
    
    @Override
    public TimestampedValue<V> get(final Object key) {
        this.checkEntries();
        return super.get(key);
    }
    
    public V getValue(final Object key) {
        final TimestampedValue<V> timestampedValue = this.get(key);
        return (timestampedValue == null) ? null : timestampedValue.getValue();
    }
    
    @Override
    public int size() {
        this.checkEntries();
        return super.size();
    }
    
    public void forEachPlain(final BiConsumer<? super K, ? super V> action) {
        for (final Map.Entry<K, TimestampedValue<V>> entry : this.entrySet()) {
            final K key = entry.getKey();
            final TimestampedValue<V> value = entry.getValue();
            action.accept((Object)key, (Object)((value == null) ? null : value.getValue()));
        }
    }
    
    public void onEntryRemove(final Consumer<V> removeConsumer) {
        this.removeConsumer = removeConsumer;
    }
    
    private void checkEntries() {
        final long millis = TimeUtil.getCurrentTimeMillis();
        if (this.lastCheckTimestamp + this.checkDuration > millis) {
            return;
        }
        this.lastCheckTimestamp = millis;
        for (final Map.Entry<K, TimestampedValue<V>> entry : this.entrySet()) {
            final K key = entry.getKey();
            final TimestampedValue<V> value = entry.getValue();
            final long timestamp = value.getTimestamp();
            if (timestamp + this.maxLifeDuration > millis) {
                continue;
            }
            this.removableEntries.add(key);
        }
        for (final K k : this.removableEntries) {
            this.remove(k);
        }
        this.removableEntries.clear();
    }
    
    private void consumeRemove(final TimestampedValue<V> value) {
        if (value == null || this.removeConsumer == null) {
            return;
        }
        this.removeConsumer.accept(value.getValue());
    }
}
