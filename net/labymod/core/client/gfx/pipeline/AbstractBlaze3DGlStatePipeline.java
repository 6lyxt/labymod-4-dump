// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline;

import java.util.function.Supplier;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.core.client.gfx.pipeline.state.StateStorageStack;
import net.labymod.core.client.gfx.pipeline.state.StateStorageApplier;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;

public abstract class AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline
{
    protected static final Logging LOGGER;
    protected static final int DIFFUSE_TEXTURE_SLOT = 0;
    protected static final int LIGHTMAP_TEXTURE_SLOT = 2;
    private final Blaze3DShaderUniformPipeline shaderUniformPipeline;
    private final List<StateStorageApplier<?>> stateStorageAppliers;
    private StateStorageStack stateStorageStack;
    private int framebuffer;
    
    public AbstractBlaze3DGlStatePipeline() {
        this.framebuffer = -1;
        this.stateStorageAppliers = new ArrayList<StateStorageApplier<?>>();
        this.shaderUniformPipeline = Laby.references().blaze3DShaderUniformPipeline();
    }
    
    protected <T> void addStateStorage(final StateStorage<T> stateStorage, final Supplier<T> storageType) {
        this.stateStorageAppliers.add(new StateStorageApplier<Object>((StateStorage<Object>)stateStorage, (Supplier<Object>)storageType));
    }
    
    @Override
    public Blaze3DShaderUniformPipeline shaderUniformPipeline() {
        return this.shaderUniformPipeline;
    }
    
    @Override
    public void storeStates() {
        if (this.stateStorageStack == null) {
            this.stateStorageStack = new StateStorageStack(this.stateStorageAppliers.toArray(new StateStorageApplier[0]));
        }
        this.stateStorageStack.push();
    }
    
    @Override
    public void restoreStates() {
        this.stateStorageStack.pop();
    }
    
    public void setBindingFramebuffer(final int framebuffer) {
        this.framebuffer = framebuffer;
    }
    
    @Override
    public int getBindingFramebuffer() {
        return this.framebuffer;
    }
    
    static {
        LOGGER = Logging.create(Blaze3DGlStatePipeline.class);
    }
}
