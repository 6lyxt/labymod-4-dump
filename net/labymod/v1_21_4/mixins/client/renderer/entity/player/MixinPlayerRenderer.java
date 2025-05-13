// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.renderer.entity.player;

import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_21_4.client.renderer.entity.layers.VersionedShopItemLayer;
import net.labymod.v1_21_4.client.render.LivingEntityRendererAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gxm.class })
public class MixinPlayerRenderer
{
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$addNewLayers(final gsf.a context, final boolean slim, final CallbackInfo ci) {
        final gxm playerRenderer = (gxm)this;
        ((LivingEntityRendererAccessor)playerRenderer).addCustomLayer(new VersionedShopItemLayer((guc<gzx, gdh>)playerRenderer));
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") })
    private void labyMod$preHandRender(final ffv poseStack, final glz bufferSource, final int packedLightCoords, final akv skinTexture, final gfe modelPart, final boolean secondLayer, final CallbackInfo ci) {
        final bum cameraEntity = flk.Q().ao();
        if (cameraEntity instanceof final gku player) {
            this.labyMod$firePlayerModelRenderEvent(player, poseStack, bufferSource, modelPart, Phase.PRE, packedLightCoords);
        }
    }
    
    @Inject(method = { "renderHand" }, at = { @At("TAIL") })
    private void labyMod$postHandRender(final ffv poseStack, final glz bufferSource, final int packedLightCoords, final akv skinTexture, final gfe modelPart, final boolean secondLayer, final CallbackInfo ci) {
        final bum cameraEntity = flk.Q().ao();
        if (cameraEntity instanceof final gku player) {
            this.labyMod$firePlayerModelRenderEvent(player, poseStack, bufferSource, modelPart, Phase.POST, packedLightCoords);
        }
    }
    
    private void labyMod$firePlayerModelRenderEvent(final gku clientPlayer, final ffv poseStack, final glz bufferSource, final gfe modelPart, final Phase phase, final int packedLight) {
        final Stack stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final gdh playerModel = (gdh)((gxm)this).c();
        final MainHand hand = playerModel.s.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        final GFXBridge gfx = Laby.gfx();
        final Blaze3DBufferSource blaze3DBufferSource = gfx.blaze3DBufferSource();
        blaze3DBufferSource.setTemporaryBuffer(bufferSource);
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player)clientPlayer, (PlayerModel)playerModel, stack, phase, hand, packedLight));
        blaze3DBufferSource.resetTemporaryBuffer();
    }
}
