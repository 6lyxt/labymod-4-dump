// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ biq.class })
public abstract class MixinGuiIngame extends bir
{
    private static final int LABYMOD$CROSSHAIR = 1;
    private static final int LABYMOD$HOTBAR = 2;
    @Shadow
    @Final
    private bib j;
    @Shadow
    public int x;
    @Shadow
    private String n;
    @Shadow
    private int o;
    @Shadow
    private boolean p;
    
    @Shadow
    protected abstract void a(final int p0, final int p1, final float p2, final aed p3, final aip p4);
    
    @Inject(method = { "renderAttackIndicator" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final float partialTicks, final bit resolution, final CallbackInfo ci) {
        if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(VersionedStackProvider.DEFAULT_STACK)) {
            return;
        }
        ci.cancel();
        if (this.j.t.N != 1) {
            return;
        }
        if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(VersionedStackProvider.DEFAULT_STACK)) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        bus.a(bus.r.i, bus.l.k, bus.r.e, bus.l.n);
        final float strengthScale = this.j.h.n(0.0f);
        boolean lvt_5_1_ = false;
        if (this.j.i instanceof vp && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.j.h.dr() > 5.0f);
            lvt_5_1_ &= this.j.i.aC();
        }
        final int x = resolution.a() / 2 - 8;
        final int y = resolution.b() / 2 - 7 + 16;
        if (lvt_5_1_) {
            this.b(x, y, 68, 94, 16, 16);
        }
        else if (strengthScale < 1.0f) {
            this.b(x, y, 36, 94, 16, 4);
            this.b(x, y, 52, 94, (int)(strengthScale * 17.0f), 4);
        }
        IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(VersionedStackProvider.DEFAULT_STACK);
        gfx.restoreBlaze3DStates();
    }
    
    @Insert(method = { "renderAttackIndicator" }, at = @At("TAIL"))
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final float partialTicks, final bit resolution, final InsertInfo callbackInfo) {
        if (this.j.t.N == 1) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(VersionedStackProvider.DEFAULT_STACK);
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Redirect(method = { "renderAttackIndicator" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;getCooledAttackStrength(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final bud instance, float value) {
        if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(VersionedStackProvider.DEFAULT_STACK)) {
            return 1.0f;
        }
        return instance.n(value);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;getCooledAttackStrength(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final bud instance, float value) {
        if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(VersionedStackProvider.DEFAULT_STACK)) {
            return 1.0f;
        }
        return instance.n(value);
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final bit scaledResolution, final float partialTicks, final CallbackInfo ci) {
        if (this.j.t.N == 2) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(VersionedStackProvider.DEFAULT_STACK);
        }
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
    
    @Insert(method = { "renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(final bhg objective, final bit scaledResolution, final InsertInfo callbackInfo) {
        if (IngameOverlayElementRenderEventCaller.callScoreboardPre(VersionedStackProvider.DEFAULT_STACK)) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final bhg objective, final bit scaledResolution, final InsertInfo callbackInfo) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @ModifyConstant(method = { "renderScoreboard" }, constant = { @Constant(intValue = 553648127) })
    private int labyMod$addOpacity(final int color) {
        return color | 0xFF000000;
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final biq gui, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        this.labyMod$renderOffhandHotbarSlot((bir)gui, x, y, u, v, uWidth, vHeight);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final biq gui, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        this.labyMod$renderOffhandHotbarSlot((bir)gui, x, y, u, v, uWidth, vHeight);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final bir gui, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(VersionedStackProvider.DEFAULT_STACK)) {
            return;
        }
        gui.b(x, y, u, v, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderHotbarItem(IIFLnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final biq gui, final int x, final int y, final float partialTicks, final aed player, final aip itemStack) {
        this.labyMod$renderOffhandHotbarItem(x, y, partialTicks, player, itemStack);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderHotbarItem(IIFLnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final biq gui, final int x, final int y, final float partialTicks, final aed player, final aip itemStack) {
        this.labyMod$renderOffhandHotbarItem(x, y, partialTicks, player, itemStack);
    }
    
    private void labyMod$renderOffhandHotbarItem(final int x, final int y, final float partialTicks, final aed player, final aip itemStack) {
        if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(VersionedStackProvider.DEFAULT_STACK)) {
            return;
        }
        this.a(x, y, partialTicks, player, itemStack);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(VersionedStackProvider.DEFAULT_STACK);
    }
    
    @Overwrite
    public void a(final String message, final boolean animatedMessage) {
        final LegacyComponentSerializer serializer = Laby.references().componentRenderer().legacySectionSerializer();
        final Component mapped = serializer.deserialize(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        final ComponentMapper componentMapper = Laby.references().componentMapper();
        this.n = ((modifiedMessage == mapped) ? message : ((hh)componentMapper.toMinecraftComponent(modifiedMessage)).d());
        this.o = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.p = animatedMessage);
    }
    
    @Redirect(method = { "renderPlayerStats" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 13))
    private void labyMod$renderAirBubble(final biq instance, final int i1, int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.labyMod$isSaturationHudWidgetDisplayed()) {
            i2 -= 10;
        }
        this.b(i1, i2, i3, i4, i5, i6);
    }
    
    @Redirect(method = { "renderPlayerStats" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 14))
    private void labyMod$renderPoppingAirBubble(final biq instance, final int i1, int i2, final int i3, final int i4, final int i5, final int i6) {
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
    private int labyMod$fireIngameOverlayElementRenderEventTitle(final biq instance) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(VersionedStackProvider.DEFAULT_STACK)) {
            return 0;
        }
        return this.x;
    }
    
    @Inject(method = { "renderGameOverlay" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", shift = At.Shift.BEFORE) })
    private void labyMod$fireIngameOverlayElementRenderEventTitle(final float partialTicks, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(VersionedStackProvider.DEFAULT_STACK);
    }
}
