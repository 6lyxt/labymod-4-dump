// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection.map;

import java.util.HashMap;
import java.util.Map;

public class BiMap<K, V>
{
    private final Map<K, V> keysToValues;
    private final Map<V, K> valuesToKeys;
    
    public BiMap() {
        this.keysToValues = new HashMap<K, V>();
        this.valuesToKeys = new HashMap<V, K>();
    }
    
    public void put(final K key, final V value) {
        this.keysToValues.put(key, value);
        this.valuesToKeys.put(value, key);
    }
    
    public Map<K, V> getKeysToValues() {
        return this.keysToValues;
    }
    
    public Map<V, K> getValuesToKeys() {
        return this.valuesToKeys;
    }
}
