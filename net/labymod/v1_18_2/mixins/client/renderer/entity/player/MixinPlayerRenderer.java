// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer.entity.player;

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
import net.labymod.v1_18_2.client.renderer.entity.layers.VersionedShopItemLayer;
import net.labymod.v1_18_2.client.render.LivingEntityRendererAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ faf.class })
public class MixinPlayerRenderer
{
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$addNewLayers(final evk.a context, final boolean slim, final CallbackInfo ci) {
        final faf playerRenderer = (faf)this;
        ((LivingEntityRendererAccessor)playerRenderer).addCustomLayer(new VersionedShopItemLayer((exe<ept, ekm<ept>>)playerRenderer));
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") })
    private void labyMod$preHandRender(final dtm poseStack, final eqs bufferSource, final int packedLight, final ept clientPlayer, final emb modelPart, final emb layerModelPart, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, poseStack, bufferSource, modelPart, Phase.PRE, packedLight);
    }
    
    @Inject(method = { "renderHand" }, at = { @At("TAIL") })
    private void labyMod$postHandRender(final dtm poseStack, final eqs bufferSource, final int packedLight, final ept clientPlayer, final emb modelPart, final emb layerModelPart, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, poseStack, bufferSource, modelPart, Phase.POST, packedLight);
    }
    
    private void labyMod$firePlayerModelRenderEvent(final ept clientPlayer, final dtm poseStack, final eqs bufferSource, final emb modelPart, final Phase phase, final int packedLight) {
        final Stack stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final ekm<ept> playerModel = (ekm<ept>)((faf)this).a();
        final MainHand hand = playerModel.l.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        final GFXBridge gfx = Laby.gfx();
        final Blaze3DBufferSource blaze3DBufferSource = gfx.blaze3DBufferSource();
        blaze3DBufferSource.setTemporaryBuffer(bufferSource);
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player)clientPlayer, (PlayerModel)playerModel, stack, phase, hand, packedLight));
        blaze3DBufferSource.resetTemporaryBuffer();
    }
}
