// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.statistics;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class FrameTimer
{
    private int frame;
    private int pausedFrame;
    private int targetFrame;
    
    public FrameTimer() {
        this.frame = 0;
        this.pausedFrame = -1;
        this.targetFrame = -1;
    }
    
    @Subscribe
    public void increment(final GameRenderEvent event) {
        if (this.pausedFrame != -1) {
            return;
        }
        if (event.phase() == Phase.POST) {
            if (this.frame == Integer.MAX_VALUE) {
                this.frame = 0;
            }
            ++this.frame;
        }
    }
    
    public int getFrame() {
        return this.frame;
    }
    
    public int getPausedFrame() {
        return this.pausedFrame;
    }
    
    public boolean isPaused() {
        return this.pausedFrame != -1;
    }
    
    public void pause() {
        if (this.isPaused()) {
            return;
        }
        this.pausedFrame = this.frame;
        this.targetFrame = this.frame;
    }
    
    public void resume() {
        if (!this.isPaused()) {
            return;
        }
        this.pausedFrame = -1;
    }
    
    public void nextFrame() {
        this.nextFrame(1);
    }
    
    public void nextFrame(final int delta) {
        if (!this.isPaused()) {
            throw new IllegalStateException("Rendering is not paused at the moment");
        }
        this.targetFrame += delta;
    }
    
    public void onPauseFrameRendered() {
        ++this.frame;
    }
    
    public boolean isPauseInterrupted() {
        return this.pausedFrame != -1 && this.targetFrame > this.frame;
    }
}
