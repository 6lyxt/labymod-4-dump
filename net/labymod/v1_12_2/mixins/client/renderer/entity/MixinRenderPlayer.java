// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.entity;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.api.event.Phase;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_12_2.client.renderer.entity.VersionedShopItemLayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ cct.class })
public abstract class MixinRenderPlayer extends caa<bua>
{
    @Shadow
    public abstract bqj h();
    
    public MixinRenderPlayer(final bzf manager, final bqf base, final float scale) {
        super(manager, base, scale);
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V" }, at = { @At("RETURN") })
    private void labyMod$addCustomLayers(final bzf rendermanager, final boolean flag, final CallbackInfo ci) {
        super.a((ccg)new VersionedShopItemLayer((cct)this));
    }
    
    @Inject(method = { "renderEntityName(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderPlayer;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE, ordinal = 0) })
    public void labyMod$setScoreboardNameTagType(final bua lvt_1_1_, final double lvt_2_1_, final double lvt_4_1_, final double lvt_6_1_, final String lvt_8_1_, final double lvt_9_1_, final CallbackInfo ci) {
        ((Entity)lvt_1_1_).setNameTagType(TagType.SCOREBOARD);
    }
    
    @Inject(method = { "renderEntityName(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderLivingBase;renderEntityName(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;D)V", shift = At.Shift.BEFORE) })
    public void labyMod$setMainNameTagType(final bua lvt_1_1_, final double lvt_2_1_, final double lvt_4_1_, final double lvt_6_1_, final String lvt_8_1_, final double lvt_9_1_, final CallbackInfo ci) {
        ((Entity)lvt_1_1_).setNameTagType(TagType.MAIN_TAG);
    }
    
    @Inject(method = { "renderLeftArm" }, at = { @At("HEAD") })
    private void labyMod$preLeftHandRender(final bua clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.PRE);
    }
    
    @Inject(method = { "renderLeftArm" }, at = { @At("TAIL") })
    private void labyMod$postLeftHandRender(final bua clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.POST);
    }
    
    @Inject(method = { "renderRightArm" }, at = { @At("HEAD") })
    private void labyMod$preRightHandRender(final bua clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.PRE);
    }
    
    @Inject(method = { "renderRightArm" }, at = { @At("TAIL") })
    private void labyMod$postRightHandRender(final bua clientPlayer, final CallbackInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.POST);
    }
    
    private void labyMod$firePlayerModelRenderEvent(final bua clientPlayer, final MainHand hand, final Phase phase) {
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        final int packedLight = MinecraftUtil.getPackedLight(clientPlayer);
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player)clientPlayer, (PlayerModel)this.h(), stack, phase, hand, packedLight));
    }
}
