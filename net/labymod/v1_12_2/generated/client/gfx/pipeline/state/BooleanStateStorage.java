// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class BooleanStateStorage implements StateStorage<bus.c>
{
    private boolean enabled;
    private final Runnable enableRunnable;
    private final Runnable disableRunnable;
    
    public BooleanStateStorage(final Runnable enableRunnable, final Runnable disableRunnable) {
        this.enableRunnable = enableRunnable;
        this.disableRunnable = disableRunnable;
    }
    
    @Override
    public void store(final bus.c state) {
        this.enabled = state.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            this.enableRunnable.run();
        }
        else {
            this.disableRunnable.run();
        }
    }
    
    @Override
    public BooleanStateStorage copy() {
        final BooleanStateStorage newObject = new BooleanStateStorage(this.enableRunnable, this.disableRunnable);
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "BooleanStateStorage[enabled=" + this.enabled + ", enableRunnable=" + String.valueOf(this.enableRunnable) + ", disableRunnable=" + String.valueOf(this.disableRunnable);
    }
}
