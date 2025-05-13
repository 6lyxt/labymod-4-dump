// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avo.class })
public abstract class MixinGuiIngame extends avp
{
    @Shadow
    public int w;
    @Shadow
    private String o;
    @Shadow
    private int p;
    @Shadow
    private boolean q;
    
    @Shadow
    public abstract avn shadow$f();
    
    @Shadow
    protected abstract boolean shadow$b();
    
    @Overwrite
    private void j() {
        if (bfc.c == null || bfc.b <= 0) {
            Laby.references().bossBarRegistry().unregisterBossBar(null);
            return;
        }
        if (IngameOverlayElementRenderEventCaller.callBossBarPre(VersionedStackProvider.DEFAULT_STACK)) {
            return;
        }
        final ave mc = ave.A();
        --bfc.b;
        final avn fontRenderer = mc.k;
        final avr scaledResolution = new avr(mc);
        final int scaledWidth = scaledResolution.a();
        final short bossBarLength = 182;
        final int bossBarPositionX = scaledWidth / 2 - bossBarLength / 2;
        final int bossBarHealth = (int)(bfc.a * (bossBarLength + 1));
        final byte bossBarPositionY = 12;
        this.b(bossBarPositionX, (int)bossBarPositionY, 0, 74, (int)bossBarLength, 5);
        this.b(bossBarPositionX, (int)bossBarPositionY, 0, 74, (int)bossBarLength, 5);
        if (bossBarHealth > 0) {
            this.b(bossBarPositionX, (int)bossBarPositionY, 0, 79, bossBarHealth, 5);
        }
        final String bossName = bfc.c;
        fontRenderer.a(bossName, (float)(scaledWidth / 2 - this.shadow$f().a(bossName) / 2), (float)(bossBarPositionY - 10), 16777215);
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        mc.P().a(MixinGuiIngame.d);
        IngameOverlayElementRenderEventCaller.callBossBarPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Insert(method = { "renderGameOverlay(F)V" }, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(final float partialTicks, final InsertInfo callback) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        Laby.fireEvent(new IngameOverlayRenderEvent(VersionedStackProvider.DEFAULT_STACK, Phase.PRE, false));
        gfx.restoreBlaze3DStates();
    }
    
    @Insert(method = { "renderGameOverlay(F)V" }, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(final float partialTicks, final InsertInfo callback) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        Laby.fireEvent(new IngameOverlayRenderEvent(VersionedStackProvider.DEFAULT_STACK, Phase.POST, false));
        gfx.restoreBlaze3DStates();
    }
    
    @Redirect(method = { "renderGameOverlay(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;showCrosshair()Z"))
    public boolean labyMod$renderCrosshairPre(final avo instance) {
        return !IngameOverlayElementRenderEventCaller.callCrossHairPre(VersionedStackProvider.DEFAULT_STACK) && this.shadow$b();
    }
    
    @Insert(method = { "renderGameOverlay(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", shift = At.Shift.AFTER))
    public void labyMod$renderCrosshairPost(final float partialTicks, final InsertInfo callbackInfo) {
        IngameOverlayElementRenderEventCaller.callCrossHairPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Insert(method = { "renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(final auk objective, final avr scaledResolution, final InsertInfo callbackInfo) {
        if (IngameOverlayElementRenderEventCaller.callScoreboardPre(VersionedStackProvider.DEFAULT_STACK)) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final auk objective, final avr scaledResolution, final InsertInfo callbackInfo) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @ModifyConstant(method = { "renderScoreboard" }, constant = { @Constant(intValue = 553648127) })
    private int labyMod$addOpacity(final int color) {
        return color | 0xFF000000;
    }
    
    @Redirect(method = { "renderPlayerStats" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 18))
    private void labyMod$renderAirBubble(final avo instance, final int i1, int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.labyMod$isSaturationHudWidgetDisplayed()) {
            i2 -= 10;
        }
        this.b(i1, i2, i3, i4, i5, i6);
    }
    
    @Redirect(method = { "renderPlayerStats" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 19))
    private void labyMod$renderPoppingAirBubble(final avo instance, final int i1, int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.labyMod$isSaturationHudWidgetDisplayed()) {
            i2 -= 10;
        }
        this.b(i1, i2, i3, i4, i5, i6);
    }
    
    private boolean labyMod$isSaturationHudWidgetDisplayed() {
        final HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        if (saturation != null && saturation.isEnabled() && saturation.isVisibleInGame()) {
            final HudWidgetDropzone attachedDropzone = saturation.getAttachedDropzone();
            return attachedDropzone != null && attachedDropzone.getId().equals("saturation");
        }
        return false;
    }
    
    @Redirect(method = { "renderGameOverlay" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiIngame;titlesTimer:I"))
    private int labyMod$fireIngameOverlayElementRenderEventTitle(final avo instance) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(VersionedStackProvider.DEFAULT_STACK)) {
            return 0;
        }
        return this.w;
    }
    
    @Inject(method = { "renderGameOverlay" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", shift = At.Shift.BEFORE) })
    private void labyMod$fireIngameOverlayElementRenderEventTitle(final float partialTicks, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Overwrite
    public void a(final String message, final boolean animatedMessage) {
        final LegacyComponentSerializer serializer = Laby.references().componentRenderer().legacySectionSerializer();
        final Component mapped = serializer.deserialize(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final ComponentMapper componentMapper = Laby.references().componentMapper();
        final Component modifiedMessage = event.getMessage();
        this.o = ((modifiedMessage == mapped) ? message : ((eu)componentMapper.toMinecraftComponent(modifiedMessage)).d());
        this.p = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.q = animatedMessage);
    }
}
