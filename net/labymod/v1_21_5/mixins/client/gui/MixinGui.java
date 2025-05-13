// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.gui;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gfx.GFXBridge;
import java.util.function.Function;
import com.mojang.blaze3d.opengl.GlStateManager;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ftj.class })
public abstract class MixinGui
{
    private ftk labyMod$graphics;
    @Shadow
    @Final
    private fqq ak;
    @Shadow
    @Final
    private static alr f;
    @Shadow
    @Final
    private static alr e;
    @Shadow
    @Final
    private static alr d;
    @Shadow
    @Nullable
    public xg az;
    @Shadow
    private xg an;
    @Shadow
    private int ao;
    @Shadow
    private boolean ap;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final ftk p0, final int p1, final int p2, final fqg p3, final csi p4, final dak p5, final int p6);
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$renderGameOverlayPre(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.PRE, this.ak.n.X));
    }
    
    @Inject(method = { "render" }, at = { @At("TAIL") })
    public void labyMod$renderGameOverlayPost(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.POST, this.ak.n.X));
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callCrossHairPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (!cancelled) {
            return;
        }
        ci.cancel();
        if (this.ak.n.F().c() != fpx.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        GlStateManager._blendFuncSeparate(775, 769, 1, 0);
        final float strengthScale = this.ak.t.H(0.0f);
        boolean lvt_5_1_ = false;
        if (this.ak.v instanceof byf && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.ak.t.gE() > 5.0f);
            lvt_5_1_ &= this.ak.v.bJ();
        }
        final int x = graphics.a() / 2 - 8;
        final int y = graphics.b() / 2 - 7 + 16;
        if (lvt_5_1_) {
            graphics.a((Function)gry::J, MixinGui.d, x, y, 16, 16);
        }
        else if (strengthScale < 1.0f) {
            graphics.a((Function)gry::J, MixinGui.e, x, y, 16, 4);
            graphics.a((Function)gry::J, MixinGui.f, 16, 4, 0, 0, x, y, (int)(strengthScale * 17.0f), 4);
        }
        IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        gfx.restoreBlaze3DStates();
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("TAIL") })
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final ftk graphics, final fqg deltaTracker, final CallbackInfo callbackInfo) {
        if (this.ak.n.F().c() == fpx.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderCrosshair" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final gqm instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.H(value);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final gqm instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.H(value);
    }
    
    @Inject(method = { "renderItemHotbar" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPre(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        if (this.ak.n.F().c() == fpx.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Inject(method = { "renderItemHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.AFTER) })
    public void labyMod$renderHotbarAttackIndicatorPost(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        if (this.ak.n.F().c() == fpx.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Redirect(method = { "getAirBubbleYLine" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final ftj gui, final int health) {
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
    public void labyMod$renderScoreboardPre(final ftk graphics, final fgz objective, final CallbackInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "displayScoreboardSidebar" }, at = { @At("TAIL") })
    public void labyMod$renderScoreboardPost(final ftk graphics, final fgz objective, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "renderEffects" }, at = { @At("HEAD") }, cancellable = true)
    public void labymod$renderEffectsPre(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderEffects" }, at = { @At("TAIL") })
    public void labymod$renderEffectsPost(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storeGuiGraphicsInstance(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final ftk graphics, final Function<alr, gry> renderTypeMapper, final alr location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, renderTypeMapper, location, $$1, $$2, $$3, $$4);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final ftk graphics, final Function<alr, gry> renderTypeMapper, final alr location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, renderTypeMapper, location, $$1, $$2, $$3, $$4);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final ftk graphics, final Function<alr, gry> renderTypeMapper, final alr location, final int x, final int y, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        graphics.a((Function)renderTypeMapper, location, x, y, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final ftj instance, final ftk graphics, final int x, final int y, final fqg deltaTracker, final csi player, final dak itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }
    
    @Redirect(method = { "renderItemHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final ftj instance, final ftk graphics, final int x, final int y, final fqg deltaTracker, final csi player, final dak itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final ftk graphics, final int x, final int y, final fqg v, final csi player, final dak itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        this.a(graphics, x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Overwrite
    public void a(final xg message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.an = (xg)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.ao = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.ap = animatedMessage);
    }
    
    @Inject(method = { "renderTitle" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireIngameOverlayElementRenderEventTitlePre(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(MinecraftUtil.obtainStackFromGraphics(graphics))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderTitle" }, at = { @At("TAIL") })
    private void labyMod$fireIngameOverlayElementRenderEventTitlePost(final ftk graphics, final fqg deltaTracker, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
}
