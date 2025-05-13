// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.injection.Slice;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import java.nio.FloatBuffer;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bjl.class })
public abstract class MixinRendererLivingEntity
{
    @Redirect(method = { "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isSneaking()Z", ordinal = 1))
    public boolean labyMod$alwaysRedirectNameTagRendering(final pr entity) {
        if (!(entity instanceof wn)) {
            ((Entity)entity).setNameTagType(TagType.MAIN_TAG);
        }
        return false;
    }
    
    @Insert(method = { "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$showOwnName(final pr entity, final InsertInfoReturnable<Boolean> cir) {
        if (entity == ave.A().h) {
            cir.setReturnValue(LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }
    
    @Redirect(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;"), slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 0), to = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 3)))
    private FloatBuffer labyMod$damageColored(final FloatBuffer buffer, float value) {
        final DamageOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("damage");
        if (animation == null) {
            return buffer.put(value);
        }
        return animation.updateBuffer(buffer, value);
    }
}
