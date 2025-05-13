// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.Matrices;
import net.labymod.api.client.gfx.pipeline.Fog;
import net.labymod.api.client.gfx.pipeline.MojangLight;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;

public abstract class AbstractBlaze3DShaderUniformPipeline implements Blaze3DShaderUniformPipeline
{
    protected final FloatVector2 screenSize;
    protected final FloatVector4 colorModulator;
    protected MojangLight light;
    protected Fog fog;
    protected Matrices matrices;
    
    public AbstractBlaze3DShaderUniformPipeline() {
        this.matrices = new Matrices();
        this.light = new MojangLight();
        this.fog = new Fog();
        this.screenSize = new FloatVector2();
        this.colorModulator = new FloatVector4(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void beginFrame() {
        this.colorModulator.set(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void endFrame() {
    }
    
    @Override
    public FloatVector2 screenSize() {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.screenSize.set((float)window.getRawWidth(), (float)window.getRawHeight());
        return this.screenSize;
    }
    
    @Override
    public FloatVector4 colorModulator() {
        return this.colorModulator;
    }
    
    @Override
    public Matrices matrices() {
        return this.matrices;
    }
    
    @Override
    public MojangLight light() {
        return this.light;
    }
    
    @Override
    public Fog fog() {
        return this.fog;
    }
}
