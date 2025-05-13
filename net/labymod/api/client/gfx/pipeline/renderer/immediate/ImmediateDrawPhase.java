// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.immediate;

import net.labymod.api.loader.MinecraftVersions;

public interface ImmediateDrawPhase
{
    public static final boolean RENDER_TARGET_RENDER_PASS = MinecraftVersions.V25w09b.orNewer();
    public static final ImmediateDrawPhase DEFAULT_DRAW_PHASE = new ImmediateDrawPhase() {
        @Override
        public void setup() {
        }
        
        @Override
        public void end() {
        }
    };
    public static final ImmediateDrawPhase IDENTITY_MODEL_MATRIX_DRAW_PHASE = new IdentiyModelMatrixImmediateDrawPhase();
    
    void setup();
    
    void end();
}
