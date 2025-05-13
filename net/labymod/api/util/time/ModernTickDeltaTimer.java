// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.time;

public class ModernTickDeltaTimer
{
    private float tickDelta;
    private long lastMs;
    private final float msPerTick;
    
    public ModernTickDeltaTimer() {
        this(20.0f);
    }
    
    private ModernTickDeltaTimer(final float ticksPerSecond) {
        this.msPerTick = 1000.0f / ticksPerSecond;
        this.lastMs = this.getCurrentMillis();
    }
    
    public void advanceTime() {
        final long currentTimeMillis = this.getCurrentMillis();
        this.tickDelta = (currentTimeMillis - this.lastMs) / this.msPerTick;
        this.lastMs = currentTimeMillis;
    }
    
    public float getTickDelta() {
        return this.tickDelta;
    }
    
    public long getCurrentMillis() {
        return TimeUtil.getNanoTime() / 1000000L;
    }
}
