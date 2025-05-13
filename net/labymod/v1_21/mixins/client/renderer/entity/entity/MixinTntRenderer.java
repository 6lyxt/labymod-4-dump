// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gmz.class })
public abstract class MixinTntRenderer extends gki<cji>
{
    protected MixinTntRenderer(final gkj.a context) {
        super(context);
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("TAIL") })
    public void render(final cji entity, final float f1, final float partialTicks, final fbi poseStack, final gez bufferSource, final int packedLight, final CallbackInfo callbackInfo) {
        if (this.b((bsr)entity) || this.d.b((bsr)entity) >= 2048.0) {
            return;
        }
        final exc position = entity.dl().a(bss.c, 0, (float)packedLight);
        if (position != null) {
            poseStack.a();
            poseStack.a(position.a(), position.b() + 0.5, position.c());
            poseStack.a(this.d.b());
            poseStack.b(0.025f, -0.025f, 0.025f);
            final Stack stack = ((VanillaStackAccessor)poseStack).stack();
            Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
            Laby.labyAPI().tagRegistry().render(stack, (Entity)entity, 0.0f, TagType.MAIN_TAG);
            poseStack.b();
        }
    }
}
