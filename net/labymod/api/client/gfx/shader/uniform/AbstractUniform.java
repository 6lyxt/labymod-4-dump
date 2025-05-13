// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.logging.Logging;

public abstract class AbstractUniform implements Uniform
{
    private static final Logging LOGGING;
    protected final GFXBridge bridge;
    private final String name;
    private int location;
    protected boolean shouldUpload;
    
    protected AbstractUniform(final String name) {
        this.bridge = Laby.labyAPI().gfxRenderPipeline().gfx();
        this.name = name;
    }
    
    @Override
    public void setProgram(final String debugName, final int programId) {
        this.location = this.bridge.getUniformLocation(programId, this.name);
        if (this.location == -1) {
            AbstractUniform.LOGGING.warn("Shader {} could not find uniform named {} in the specified shader program!", debugName, this.name);
        }
    }
    
    protected abstract void updateUniform();
    
    @Override
    public void upload(final boolean force) {
        if (!force && !this.shouldUpload) {
            return;
        }
        this.updateUniform();
        this.shouldUpload = false;
    }
    
    @Override
    public int getLocation() {
        return this.location;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    static {
        LOGGING = Logging.create("Uniform");
    }
}
