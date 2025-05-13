// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.renderer.cape.particle;

public class CapeParticle
{
    private final float x;
    private final float y;
    private final float spawnTick;
    private float prevProgress;
    
    public CapeParticle(final float x, final float y, final float spawnTick) {
        this.prevProgress = -1.0f;
        this.x = x;
        this.y = y;
        this.spawnTick = spawnTick;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getSpawnTick() {
        return this.spawnTick;
    }
    
    public float getPreviousProgress() {
        return this.prevProgress;
    }
    
    public void setPreviousProgress(final float progress) {
        this.prevProgress = progress;
    }
}
