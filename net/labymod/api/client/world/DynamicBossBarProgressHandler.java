// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

import net.labymod.api.util.function.FloatSupplier;
import it.unimi.dsi.fastutil.floats.FloatConsumer;

public class DynamicBossBarProgressHandler implements BossBarProgressHandler
{
    private final FloatConsumer progressConsumer;
    private final FloatSupplier progressSupplier;
    
    public DynamicBossBarProgressHandler(final FloatConsumer progressConsumer, final FloatSupplier progressSupplier) {
        this.progressConsumer = progressConsumer;
        this.progressSupplier = progressSupplier;
    }
    
    @Override
    public float getProgress() {
        return this.progressSupplier.get();
    }
    
    @Override
    public void setProgress(final float progress) {
        this.progressConsumer.accept(progress);
    }
}
