// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.event.Phase;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.renderer.entity.VersionedShopItemLayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bln.class })
public abstract class MixinRenderPlayer extends bjl<bet>
{
    @Shadow
    public abstract bbr g();
    
    public MixinRenderPlayer(final biu manager, final bbo base, final float scale) {
        super(manager, base, scale);
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V" }, at = { @At("RETURN") })
    private void labyMod$addCustomLayers(final biu rendermanager, final boolean flag, final CallbackInfo ci) {
        super.a((blb)new VersionedShopItemLayer((bln)this));
    }
    
    @Inject(method = { "renderOffsetLivingLabel(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;FD)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderPlayer;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE, ordinal = 0) })
    public void labyMod$setScoreboardNameTagType(final bet entity, final double x, final double y, final double z, final String displayName, final float f, final double d, final CallbackInfo ci) {
        ((Entity)entity).setNameTagType(TagType.SCOREBOARD);
    }
    
    @Inject(method = { "renderOffsetLivingLabel(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;FD)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;renderOffsetLivingLabel(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;FD)V", shift = At.Shift.BEFORE, ordinal = 0) })
    public void labyMod$setMainNameTagType(final bet entity, final double x, final double y, final double z, final String displayName, final float f, final double d, final CallbackInfo ci) {
        ((Entity)entity).setNameTagType(TagType.MAIN_TAG);
    }
    
    @Inject(method = { "renderLeftArm" }, at = { @At("HEAD") })
    private void labyMod$preLeftHandRender(final bet clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.PRE);
    }
    
    @Inject(method = { "renderLeftArm" }, at = { @At("TAIL") })
    private void labyMod$postLeftHandRender(final bet clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.POST);
    }
    
    @Inject(method = { "renderRightArm" }, at = { @At("HEAD") })
    private void labyMod$preRightHandRender(final bet clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.PRE);
    }
    
    @Inject(method = { "renderRightArm" }, at = { @At("TAIL") })
    private void labyMod$postRightHandRender(final bet clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.POST);
    }
    
    private void labyMod$firePlayerModelRenderEvent(final bet clientPlayer, final MainHand hand, final Phase phase) {
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        final int packedLight = MinecraftUtil.getPackedLight(clientPlayer);
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player)clientPlayer, (PlayerModel)this.g(), stack, phase, hand, packedLight));
    }
}
