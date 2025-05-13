// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.WorldRenderer;

@Mixin({ bfr.class })
public class MixinRenderGlobal implements WorldRenderer
{
    @Shadow
    private int S;
    
    @Override
    public int getRenderedEntities() {
        return this.S;
    }
    
    @Inject(method = { "renderEntities" }, at = { @At("HEAD") })
    private void labyMod$fireEntityRenderPassEventBefore(final pk p_renderEntities_1_, final bia p_renderEntities_2_, final float p_renderEntities_3_, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }
    
    @Inject(method = { "renderEntities" }, at = { @At("TAIL") })
    private void labyMod$fireEntityRenderPassEventAfter(final pk p_renderEntities_1_, final bia p_renderEntities_2_, final float p_renderEntities_3_, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }
}
