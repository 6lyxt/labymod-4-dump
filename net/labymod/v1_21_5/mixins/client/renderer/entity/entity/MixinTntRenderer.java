// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.joml.Quaternionfc;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ham.class })
public abstract class MixinTntRenderer extends gxu<cop, hgk>
{
    protected MixinTntRenderer(final gxv.a context) {
        super(context);
    }
    
    @Inject(method = { "render(Lnet/minecraft/client/renderer/entity/state/TntRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("TAIL") })
    public void render(final hgk state, final fld poseStack, final grn bufferSource, final int packedLight, final CallbackInfo callbackInfo) {
        final EntityRenderStateAccessor<cop> tntState = EntityRenderStateAccessor.self(state);
        if (tntState == null) {
            return;
        }
        final fgc position = state.F;
        if (position == null) {
            return;
        }
        poseStack.a();
        poseStack.a(position.a(), position.b() + 0.5, position.c());
        poseStack.a((Quaternionfc)this.d.b());
        poseStack.b(0.025f, -0.025f, 0.025f);
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.labyAPI().tagRegistry().render(stack, (Entity)tntState.labyMod$getEntity(), 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
