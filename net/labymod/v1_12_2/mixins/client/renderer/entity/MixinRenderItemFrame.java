// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bzv.class })
public class MixinRenderItemFrame
{
    @Inject(method = { "renderName(Lnet/minecraft/entity/item/EntityItemFrame;DDD)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItemFrame;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE) })
    public void labyMod$setMainNameTagType(final acb entity, final double x, final double y, final double z, final CallbackInfo callbackInfo) {
        ((Entity)entity).setNameTagType(TagType.MAIN_TAG);
    }
}
