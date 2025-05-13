// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ frn.class })
public abstract class MixinTntRenderer extends fox<bvi>
{
    protected MixinTntRenderer(final foy.a context) {
        super(context);
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("TAIL") })
    public void render(final bvi entity, final float f1, final float f2, final eij poseStack, final fjx bufferSource, final int packedLight, final CallbackInfo callbackInfo) {
        if (this.b((bfj)entity) || this.c.b((bfj)entity) >= 2048.0) {
            return;
        }
        poseStack.a();
        poseStack.a(0.0, (double)(entity.de() + 0.5f), 0.0);
        poseStack.a(this.c.b());
        poseStack.b(-0.025f, -0.025f, 0.025f);
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.labyAPI().tagRegistry().render(stack, (Entity)entity, 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
