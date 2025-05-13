// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bjg.class })
public class MixinRenderItemFrame
{
    @Inject(method = { "renderName(Lnet/minecraft/entity/item/EntityItemFrame;DDD)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/tileentity/RenderItemFrame;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE, ordinal = 0) })
    public void labyMod$setMainNameTagType(final uo entity, final double x, final double y, final double z, final CallbackInfo callbackInfo) {
        ((Entity)entity).setNameTagType(TagType.MAIN_TAG);
    }
}
