// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.gui;

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
import net.labymod.v1_21.client.util.MinecraftUtil;
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

@Mixin({ fhy.class })
public abstract class MixinGui
{
    private fhz labyMod$graphics;
    @Shadow
    @Final
    private fgo W;
    @Shadow
    @Final
    private static akr e;
    @Shadow
    @Final
    private static akr d;
    @Shadow
    @Final
    private static akr c;
    @Shadow
    @Nullable
    public wz al;
    @Shadow
    private wz Z;
    @Shadow
    private int aa;
    @Shadow
    private boolean ab;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final fhz p0, final int p1, final int p2, final fgf p3, final cmx p4, final cuq p5, final int p6);
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$renderGameOverlayPre(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.PRE, this.W.m.Y));
    }
    
    @Inject(method = { "render" }, at = { @At("TAIL") })
    public void labyMod$renderGameOverlayPost(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.POST, this.W.m.Y));
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callCrossHairPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (!cancelled) {
            return;
        }
        ci.cancel();
        if (this.W.m.D().c() != ffx.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final float strengthScale = this.W.s.F(0.0f);
        boolean lvt_5_1_ = false;
        if (this.W.u instanceof btn && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.W.s.gr() > 5.0f);
            lvt_5_1_ &= this.W.u.bE();
        }
        final int x = graphics.a() / 2 - 8;
        final int y = graphics.b() / 2 - 7 + 16;
        if (lvt_5_1_) {
            graphics.a(MixinGui.c, x, y, 16, 16);
        }
        else if (strengthScale < 1.0f) {
            graphics.a(MixinGui.d, x, y, 16, 4);
            graphics.a(MixinGui.e, 16, 4, 0, 0, x, y, (int)(strengthScale * 17.0f), 4);
        }
        IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        gfx.restoreBlaze3DStates();
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("TAIL") })
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final fhz graphics, final fgf deltaTracker, final CallbackInfo callbackInfo) {
        if (this.W.m.D().c() == ffx.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderCrosshair" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final geb instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.F(value);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final geb instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.F(value);
    }
    
    @Inject(method = { "renderItemHotbar" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPre(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        if (this.W.m.D().c() == ffx.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Inject(method = { "renderItemHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        if (this.W.m.D().c() == ffx.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Redirect(method = { "renderPlayerHealth" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final fhy gui, final int health) {
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
    
    @Inject(method = { "displayScoreboardSidebar" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderScoreboardPre(final fhz graphics, final exy objective, final CallbackInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "displayScoreboardSidebar" }, at = { @At("TAIL") })
    public void labyMod$renderScoreboardPost(final fhz graphics, final exy objective, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "renderEffects" }, at = { @At("HEAD") }, cancellable = true)
    public void labymod$renderEffectsPre(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderEffects" }, at = { @At("TAIL") })
    public void labymod$renderEffectsPost(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storeGuiGraphicsInstance(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final fhz graphics, final akr location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final fhz graphics, final akr location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final fhz graphics, final akr location, final int x, final int y, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        graphics.a(location, x, y, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final fhy instance, final fhz graphics, final int x, final int y, final fgf deltaTracker, final cmx player, final cuq itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final fhy instance, final fhz graphics, final int x, final int y, final fgf deltaTracker, final cmx player, final cuq itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final fhz graphics, final int x, final int y, final fgf v, final cmx player, final cuq itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        this.a(graphics, x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Overwrite
    public void a(final wz message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.Z = (wz)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.aa = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.ab = animatedMessage);
    }
    
    @Inject(method = { "renderTitle" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireIngameOverlayElementRenderEventTitlePre(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(MinecraftUtil.obtainStackFromGraphics(graphics))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderTitle" }, at = { @At("TAIL") })
    private void labyMod$fireIngameOverlayElementRenderEventTitlePost(final fhz graphics, final fgf deltaTracker, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
}
