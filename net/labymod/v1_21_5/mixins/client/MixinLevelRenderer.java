// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.WorldRenderer;

@Mixin({ gri.class })
public class MixinLevelRenderer implements WorldRenderer
{
    @Shadow
    private int L;
    
    @Inject(method = { "renderEntities" }, at = { @At("HEAD") })
    private void labyMod$beforeEntityRenderPass(final fld $$0, final grn.a $$1, final fpy $$2, final fqg $$3, final List<bxe> $$4, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }
    
    @Inject(method = { "renderEntities" }, at = { @At("TAIL") })
    private void labyMod$afterEntityRenderPass(final fld $$0, final grn.a $$1, final fpy $$2, final fqg $$3, final List<bxe> $$4, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }
    
    @Override
    public int getRenderedEntities() {
        return this.L;
    }
}
