// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fgz.class })
public abstract class MixinTntRenderer extends fek<bqw>
{
    protected MixinTntRenderer(final fel.a context) {
        super(context);
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("TAIL") })
    public void render(final bqw entity, final float f1, final float f2, final eaq poseStack, final ezs bufferSource, final int packedLight, final CallbackInfo callbackInfo) {
        if (this.b((bbn)entity) || this.c.b((bbn)entity) >= 2048.0) {
            return;
        }
        poseStack.a();
        poseStack.a(0.0, (double)(entity.cX() + 0.5f), 0.0);
        poseStack.a(this.c.b());
        poseStack.a(-0.025f, -0.025f, 0.025f);
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.labyAPI().tagRegistry().render(stack, (Entity)entity, 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
