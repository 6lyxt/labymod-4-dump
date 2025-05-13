// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity.layers;

import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bkp.class })
public class MixinLayerCape
{
    private static final CapeParticleController labyMod$capeParticleController;
    @Shadow
    @Final
    private bln a;
    
    @Inject(method = { "doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$firePreCapeRenderEvent(final bet player, final float p_doRenderLayer_2_, final float p_doRenderLayer_3_, final float p_doRenderLayer_4_, final float p_doRenderLayer_5_, final float p_doRenderLayer_6_, final float p_doRenderLayer_7_, final float p_doRenderLayer_8_, final CallbackInfo ci) {
        if (this.labyMod$fireCapeRenderEvent(player, Phase.PRE)) {
            bfl.F();
            ci.cancel();
        }
    }
    
    @Inject(method = { "doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPlayer;renderCape(F)V", shift = At.Shift.AFTER) })
    private void labyMod$firePostCapeRenderEvent(final bet player, final float p_doRenderLayer_2_, final float p_doRenderLayer_3_, final float partialTicks, final float p_doRenderLayer_5_, final float p_doRenderLayer_6_, final float p_doRenderLayer_7_, final float p_doRenderLayer_8_, final CallbackInfo ci) {
        this.labyMod$fireCapeRenderEvent(player, Phase.POST);
    }
    
    @Inject(method = { "doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPlayer;renderCape(F)V", shift = At.Shift.AFTER) })
    private void labyMod$renderCapeParticles(final bet player, final float p_doRenderLayer_2_, final float p_doRenderLayer_3_, final float partialTicks, final float p_doRenderLayer_5_, final float p_doRenderLayer_6_, final float p_doRenderLayer_7_, final float p_doRenderLayer_8_, final CallbackInfo ci) {
        if (player instanceof OptiFinePlayer && ((OptiFinePlayer)player).getOptiFineCapeLocation() != null) {
            return;
        }
        MixinLayerCape.labyMod$capeParticleController.spawn(VersionedStackProvider.DEFAULT_STACK.getProvider().getPosition(), (Player)player, partialTicks);
    }
    
    private boolean labyMod$fireCapeRenderEvent(final bet clientPlayer, final Phase phase) {
        final Player player = (Player)clientPlayer;
        final PlayerModel playerModel = (PlayerModel)this.a.g();
        return Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, VersionedStackProvider.DEFAULT_STACK, phase)).isCancelled();
    }
    
    static {
        labyMod$capeParticleController = LabyMod.references().capeParticleController();
    }
}
