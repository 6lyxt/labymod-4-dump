// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.gui;

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
import net.labymod.v1_20_6.client.util.MinecraftUtil;
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

@Mixin({ fgs.class })
public abstract class MixinGui
{
    private fgt labyMod$graphics;
    @Shadow
    @Final
    private ffh X;
    @Shadow
    @Final
    private static alf e;
    @Shadow
    @Final
    private static alf d;
    @Shadow
    @Final
    private static alf c;
    @Shadow
    @Nullable
    public xp am;
    @Shadow
    private xp aa;
    @Shadow
    private int ab;
    @Shadow
    private boolean ac;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final fgt p0, final int p1, final int p2, final float p3, final cmz p4, final cur p5, final int p6);
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$renderGameOverlayPre(final fgt guiGraphics, final float $$1, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)guiGraphics.c()).stack(), Phase.PRE, this.X.m.Y));
    }
    
    @Insert(method = { "render" }, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(final fgt graphics, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.POST, this.X.m.Y));
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final fgt graphics, final float partialTicks, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callCrossHairPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (!cancelled) {
            return;
        }
        ci.cancel();
        if (this.X.m.D().c() != fer.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final float strengthScale = this.X.s.D(0.0f);
        boolean lvt_5_1_ = false;
        if (this.X.u instanceof btr && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.X.s.gv() > 5.0f);
            lvt_5_1_ &= this.X.u.bD();
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
    
    @Insert(method = { "renderCrosshair" }, at = @At("TAIL"))
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final fgt graphics, final float partialTicks, final InsertInfo callbackInfo) {
        if (this.X.m.D().c() == fer.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderCrosshair" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final gcs instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.D(value);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final gcs instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.D(value);
    }
    
    @Inject(method = { "renderItemHotbar" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPre(final fgt graphics, final float lvt_1_1_, final CallbackInfo ci) {
        if (this.X.m.D().c() == fer.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Inject(method = { "renderItemHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final fgt graphics, final float lvt_1_1_, final CallbackInfo ci) {
        if (this.X.m.D().c() == fer.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Redirect(method = { "renderPlayerHealth" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final fgs gui, final int health) {
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
    public void labyMod$renderScoreboardPre(final fgt graphics, final ewp objective, final InsertInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "displayScoreboardSidebar" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final fgt graphics, final ewp objective, final InsertInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "renderEffects" }, at = { @At("HEAD") }, cancellable = true)
    public void labymod$renderEffectsPre(final fgt graphics, final float partialTicks, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderEffects" }, at = { @At("TAIL") })
    public void labymod$renderEffectsPost(final fgt graphics, final float partialTicks, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storeGuiGraphicsInstance(final fgt graphics, final float $$1, final CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final fgt graphics, final alf location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final fgt graphics, final alf location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final fgt graphics, final alf location, final int x, final int y, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        graphics.a(location, x, y, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final fgs gui, final fgt graphics, final int x, final int y, final float v, final cmz player, final cur itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, v, player, itemStack, i);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final fgs gui, final fgt graphics, final int x, final int y, final float v, final cmz player, final cur itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, v, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final fgt graphics, final int x, final int y, final float v, final cmz player, final cur itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        this.a(graphics, x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Overwrite
    public void a(final xp message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.aa = (xp)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.ab = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.ac = animatedMessage);
    }
    
    @Inject(method = { "renderTitle" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireIngameOverlayElementRenderEventTitlePre(final fgt $$0, final float $$1, final CallbackInfo ci) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(MinecraftUtil.obtainStackFromGraphics($$0))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderTitle" }, at = { @At("TAIL") })
    private void labyMod$fireIngameOverlayElementRenderEventTitlePost(final fgt $$0, final float $$1, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(MinecraftUtil.obtainStackFromGraphics($$0));
    }
}
