// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.pool;

import org.jetbrains.annotations.Nullable;
import net.labymod.core.util.collection.TimestampedCache;
import net.labymod.core.client.render.font.text.model.StringWidth;
import net.labymod.core.util.collection.TimestampedValue;
import java.util.Map;

public class StringWidthPool
{
    private static final int MAX_POOL_SIZE = 4096;
    private final Map<Integer, TimestampedValue<StringWidth>> pool;
    
    public StringWidthPool() {
        this((Map<Integer, TimestampedValue<StringWidth>>)new TimestampedCache(1024));
    }
    
    public StringWidthPool(final Map<Integer, TimestampedValue<StringWidth>> pool) {
        this.pool = pool;
    }
    
    @Nullable
    public StringWidth get(final int key) {
        final TimestampedValue<StringWidth> timestampedValue = this.pool.get(key);
        return (timestampedValue == null) ? null : timestampedValue.getValue();
    }
    
    public void addStringWidth(final int key, final String text, final float width, final float boldWidth) {
        final int size = this.pool.size();
        if (size > 4096) {
            this.invalidate();
        }
        this.pool.put(key, new TimestampedValue<StringWidth>(new StringWidth(text, width, boldWidth)));
    }
    
    public void invalidate() {
        this.pool.clear();
    }
}
