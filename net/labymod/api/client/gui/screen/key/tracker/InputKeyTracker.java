// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key.tracker;

import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.Key;

public class InputKeyTracker extends KeyTracker
{
    protected long nextCPSCheck;
    protected int cpsCount;
    protected int cpsCountSecond;
    
    protected InputKeyTracker(@NotNull final Key key) {
        super(key);
    }
    
    public static InputKeyTracker of(@NotNull final Key key) {
        return new InputKeyTracker(key);
    }
    
    @Override
    public int getClicksPerSecond() {
        return this.cpsCountSecond;
    }
    
    @Override
    public void press() {
        ++this.cpsCount;
    }
    
    @Override
    public void update() {
        if (this.nextCPSCheck < TimeUtil.getMillis()) {
            this.nextCPSCheck = TimeUtil.getMillis() + 1000L;
            this.cpsCountSecond = this.cpsCount;
            this.cpsCount = 0;
        }
    }
}
