// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fvw.class })
public abstract class MixinTntRenderer extends ftg<byo>
{
    protected MixinTntRenderer(final fth.a context) {
        super(context);
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("TAIL") })
    public void render(final byo entity, final float f1, final float f2, final elp poseStack, final foe bufferSource, final int packedLight, final CallbackInfo callbackInfo) {
        if (this.b((biq)entity) || this.c.b((biq)entity) >= 2048.0) {
            return;
        }
        poseStack.a();
        poseStack.a(0.0, (double)(entity.dg() + 0.5f), 0.0);
        poseStack.a(this.c.b());
        poseStack.b(-0.025f, -0.025f, 0.025f);
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.labyAPI().tagRegistry().render(stack, (Entity)entity, 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
