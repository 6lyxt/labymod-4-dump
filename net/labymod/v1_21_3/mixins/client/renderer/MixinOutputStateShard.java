// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net/minecraft/client/renderer/RenderStateShard$OutputStateShard" })
public class MixinOutputStateShard extends glu
{
    public MixinOutputStateShard(final String name, final Runnable setup, final Runnable clear) {
        super(name, setup, clear);
    }
    
    public void a() {
        boolean setupRenderState = true;
        if ("main_target".equals(this.b)) {
            setupRenderState = !Laby.references().gfxRenderPipeline().isActivityRenderTarget();
        }
        if (setupRenderState) {
            super.a();
        }
    }
    
    public void b() {
        super.b();
    }
}
