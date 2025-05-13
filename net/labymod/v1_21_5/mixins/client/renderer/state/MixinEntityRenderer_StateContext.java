// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.state;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gxu.class })
public class MixinEntityRenderer_StateContext<T extends bxe, S extends hec>
{
    @Inject(method = { "createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;" }, at = { @At("TAIL") })
    private void labyMod$setEntity(final T entity, final float $$1, final CallbackInfoReturnable<S> cir) {
        final S returnValue = (S)cir.getReturnValue();
        if (returnValue instanceof final EntityRenderStateAccessor accessor) {
            accessor.labyMod$setEntity(entity);
        }
    }
}
