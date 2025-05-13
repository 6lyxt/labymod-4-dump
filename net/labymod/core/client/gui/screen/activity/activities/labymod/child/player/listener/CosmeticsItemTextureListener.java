// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.listener;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;

public class CosmeticsItemTextureListener implements ItemTextureListener
{
    private State state;
    private long lastStateUpdate;
    
    public CosmeticsItemTextureListener() {
        this.state = State.SUCCESS;
    }
    
    @Override
    public void onBegin() {
        this.setState(State.BEGIN);
    }
    
    @Override
    public void onSuccess() {
        this.setState(State.SUCCESS);
    }
    
    @Override
    public void onError() {
        this.setState(State.ERROR);
    }
    
    public boolean isTimeout(final long time) {
        return this.lastStateUpdate + time < TimeUtil.getCurrentTimeMillis();
    }
    
    public State state() {
        return this.state;
    }
    
    private void setState(final State state) {
        this.state = state;
        this.lastStateUpdate = TimeUtil.getCurrentTimeMillis();
    }
    
    public enum State
    {
        BEGIN, 
        SUCCESS, 
        ERROR;
    }
}
