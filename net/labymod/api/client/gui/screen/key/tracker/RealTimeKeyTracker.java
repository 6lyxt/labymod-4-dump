// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key.tracker;

import it.unimi.dsi.fastutil.longs.LongListIterator;
import net.labymod.api.util.time.TimeUtil;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.Key;
import it.unimi.dsi.fastutil.longs.LongList;

public class RealTimeKeyTracker extends KeyTracker
{
    protected final LongList clicks;
    
    protected RealTimeKeyTracker(@NotNull final Key key) {
        super(key);
        this.clicks = (LongList)new LongArrayList();
    }
    
    public static RealTimeKeyTracker of(@NotNull final Key key) {
        return new RealTimeKeyTracker(key);
    }
    
    @Override
    public int getClicksPerSecond() {
        return this.clicks.size();
    }
    
    @Override
    public void press() {
        this.clicks.add(TimeUtil.getMillis() + 1000L);
    }
    
    @Override
    public void update() {
        if (this.getClicksPerSecond() == 0) {
            return;
        }
        final long currentTime = TimeUtil.getMillis();
        final LongListIterator iterator = this.clicks.iterator();
        while (iterator.hasNext()) {
            if (iterator.nextLong() < currentTime) {
                iterator.remove();
            }
        }
    }
}
