// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.gui;

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
import net.labymod.v1_20_2.client.util.MinecraftUtil;
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

@Mixin({ ese.class })
public abstract class MixinGui
{
    private esf labyMod$graphics;
    @Shadow
    @Final
    private eqv W;
    @Shadow
    private int aw;
    @Shadow
    private int av;
    @Shadow
    @Final
    private static aew e;
    @Shadow
    @Final
    private static aew d;
    @Shadow
    @Final
    private static aew c;
    @Shadow
    @Nullable
    public tl am;
    @Shadow
    private tl aa;
    @Shadow
    private int ab;
    @Shadow
    private boolean ac;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final esf p0, final int p1, final int p2, final float p3, final cbu p4, final cjf p5, final int p6);
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$renderGameOverlayPre(final esf guiGraphics, final float $$1, final CallbackInfo ci) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)guiGraphics.c()).stack(), Phase.PRE, false));
    }
    
    @Insert(method = { "render" }, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(final esf graphics, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)graphics.c()).stack(), Phase.POST, false));
    }
    
    @Inject(method = { "renderCrosshair" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final esf graphics, final CallbackInfo ci) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callCrossHairPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (!cancelled) {
            return;
        }
        ci.cancel();
        if (this.W.m.A().c() != eqf.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final float strengthScale = this.W.s.B(0.0f);
        boolean lvt_5_1_ = false;
        if (this.W.u instanceof bjg && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.W.s.gk() > 5.0f);
            lvt_5_1_ &= this.W.u.bv();
        }
        final int x = this.av / 2 - 8;
        final int y = this.aw / 2 - 7 + 16;
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
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final esf graphics, final InsertInfo callbackInfo) {
        if (this.W.m.A().c() == eqf.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderCrosshair" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final fng instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.B(value);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final fng instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics));
        if (cancelled) {
            return 1.0f;
        }
        return instance.B(value);
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPre(final float lvt_1_1_, final esf graphics, final CallbackInfo ci) {
        if (this.W.m.A().c() == eqf.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final float lvt_1_1_, final esf graphics, final CallbackInfo ci) {
        if (this.W.m.A().c() == eqf.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(MinecraftUtil.obtainStackFromGraphics(graphics));
        }
    }
    
    @Redirect(method = { "renderPlayerHealth" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final ese gui, final int health) {
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
    public void labyMod$renderScoreboardPre(final esf graphics, final eij objective, final InsertInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "displayScoreboardSidebar" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final esf graphics, final eij objective, final InsertInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Insert(method = { "renderEffects" }, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(final esf graphics, final InsertInfo insertInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            insertInfo.cancel();
        }
    }
    
    @Insert(method = { "renderEffects" }, at = @At("TAIL"))
    public void labymod$renderEffectsPost(final esf graphics, final InsertInfo insertInfo) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storeGuiGraphicsInstance(final esf graphics, final float $$1, final CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final esf graphics, final aew location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final esf graphics, final aew location, final int $$1, final int $$2, final int $$3, final int $$4) {
        this.labyMod$renderOffhandHotbarSlot(graphics, location, $$1, $$2, $$3, $$4);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final esf graphics, final aew location, final int x, final int y, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        graphics.a(location, x, y, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final ese gui, final esf graphics, final int x, final int y, final float v, final cbu player, final cjf itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, v, player, itemStack, i);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final ese gui, final esf graphics, final int x, final int y, final float v, final cbu player, final cjf itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(graphics, x, y, v, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final esf graphics, final int x, final int y, final float v, final cbu player, final cjf itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(MinecraftUtil.obtainStackFromGraphics(graphics));
        if (cancelled) {
            return;
        }
        this.a(graphics, x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(MinecraftUtil.obtainStackFromGraphics(graphics));
    }
    
    @Overwrite
    public void a(final tl message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.aa = (tl)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.ab = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.ac = animatedMessage);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(final eqz options) {
        if (!Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get()) {
            return options.Z;
        }
        return options.Z && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private tl labyMod$fireIngameOverlayElementRenderEventTitle(final ese instance) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(MinecraftUtil.obtainStackFromGraphics(this.labyMod$graphics))) {
            return null;
        }
        return this.am;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE) })
    private void labyMod$fireIngameOverlayElementRenderEventTitle(final esf $$0, final float $$1, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(MinecraftUtil.obtainStackFromGraphics($$0));
    }
}
