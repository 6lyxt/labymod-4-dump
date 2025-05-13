// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.gui;

import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gfx.GFXBridge;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eow.class })
public abstract class MixinGui
{
    private eox labyMod$graphics;
    @Shadow
    @Final
    private enn t;
    @Shadow
    private int T;
    @Shadow
    private int S;
    @Shadow
    @Final
    private static acq g;
    @Shadow
    @Nullable
    public sw J;
    @Shadow
    private sw x;
    @Shadow
    private int y;
    @Shadow
    private boolean z;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final eox p0, final int p1, final int p2, final float p3, final byo p4, final cfz p5, final int p6);
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$renderGameOverlayPre(final eox guiGraphics, final float $$1, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)guiGraphics.c()).stack(), Phase.PRE, false));
    }
    
    @Insert(method = { "render" }, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(final eox graphics, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.POST, false));
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final eox graphics, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callCrossHairPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (!cancelled) {
            return;
        }
        ci.cancel();
        if (this.t.m.z().c() != emy.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final float strengthScale = this.t.t.A(0.0f);
        boolean lvt_5_1_ = false;
        if (this.t.v instanceof bfz && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.t.t.gg() > 5.0f);
            lvt_5_1_ &= this.t.v.bs();
        }
        final int x = this.S / 2 - 8;
        final int y = this.T / 2 - 7 + 16;
        if (lvt_5_1_) {
            graphics.a(MixinGui.g, x, y, 68, 94, 16, 16);
        }
        else if (strengthScale < 1.0f) {
            graphics.a(MixinGui.g, x, y, 36, 94, 16, 4);
            graphics.a(MixinGui.g, x, y, 52, 94, (int)(strengthScale * 17.0f), 4);
        }
        IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        gfx.restoreBlaze3DStates();
    }
    
    @Insert(method = { "renderCrosshair" }, at = @At("TAIL"))
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final eox graphics, final InsertInfo callbackInfo) {
        if (this.t.m.z().c() == emy.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderCrosshair" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final fiy instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.A(value);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final fiy instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.A(value);
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPre(final float lvt_1_1_, final eox graphics, final CallbackInfo ci) {
        if (this.t.m.z().c() == emy.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final float lvt_1_1_, final eox graphics, final CallbackInfo ci) {
        if (this.t.m.z().c() == emy.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Redirect(method = { "renderPlayerHealth" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final eow gui, final int health) {
        final HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        final int visibleVehicleHeartRows = this.a(health);
        if (saturation != null && saturation.isEnabled() && saturation.isVisibleInGame()) {
            final HudWidgetDropzone attachedDropzone = saturation.getAttachedDropzone();
            if (attachedDropzone != null && attachedDropzone.getId().equals("saturation")) {
                return visibleVehicleHeartRows + 1;
            }
        }
        return visibleVehicleHeartRows;
    }
    
    @Insert(method = { "displayScoreboardSidebar" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(final eox graphics, final efd objective, final InsertInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "displayScoreboardSidebar" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final eox graphics, final efd objective, final InsertInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Insert(method = { "renderEffects" }, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(final eox graphics, final InsertInfo insertInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            insertInfo.cancel();
        }
    }
    
    @Insert(method = { "renderEffects" }, at = @At("TAIL"))
    public void labymod$renderEffectsPost(final eox graphics, final InsertInfo insertInfo) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storeGuiGraphicsInstance(final eox graphics, final float $$1, final CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final eox graphics, final acq location, final int $$1, final int $$2, final int $$3, final int $$4, final int $$5, final int $$6) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4, $$5, $$6);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final eox graphics, final acq location, final int $$1, final int $$2, final int $$3, final int $$4, final int $$5, final int $$6) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4, $$5, $$6);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final eox graphics, final acq location, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        graphics.a(location, x, y, u, v, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final eow gui, final eox graphics, final int x, final int y, final float v, final byo player, final cfz itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, v, player, itemStack, i);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final eow gui, final eox graphics, final int x, final int y, final float v, final byo player, final cfz itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, v, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final eox graphics, final int x, final int y, final float v, final byo player, final cfz itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        this.a(graphics, x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Overwrite
    public void a(final sw message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.x = (sw)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.y = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.z = animatedMessage);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(final enr options) {
        if (!Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get()) {
            return options.Z;
        }
        return options.Z && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private sw labyMod$fireIngameOverlayElementRenderEventTitle(final eow instance) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics))) {
            return null;
        }
        return this.J;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE) })
    private void labyMod$fireIngameOverlayElementRenderEventTitle(final eox $$0, final float $$1, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(MinecraftUtil.obtainStackFromGraphics($$0));
    }
}
