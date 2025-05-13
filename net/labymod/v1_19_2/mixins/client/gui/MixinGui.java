// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.gui;

import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.GFXBridge;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.volt.callback.InsertInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ehb.class })
public abstract class MixinGui
{
    private eaq labyMod$stack;
    @Shadow
    @Final
    private efu v;
    @Shadow
    private int V;
    @Shadow
    private int U;
    @Shadow
    @Nullable
    public rq L;
    @Shadow
    private rq z;
    @Shadow
    private int A;
    @Shadow
    private boolean B;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final int p0, final int p1, final float p2, final buc p3, final cax p4, final int p5);
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V" }, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(final eaq stack, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)stack).stack(), Phase.PRE, false));
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V" }, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(final eaq stack, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)stack).stack(), Phase.POST, false));
    }
    
    @Inject(method = { "renderCrosshair(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final eaq stack, final CallbackInfo ci) {
        final boolean cancelledCrossHair = IngameOverlayElementRenderEventCaller.callCrossHairPre(((VanillaStackAccessor)stack).stack());
        if (!cancelledCrossHair) {
            return;
        }
        ci.cancel();
        if (this.v.m.w().c() != efe.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(((VanillaStackAccessor)stack).stack());
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final float strengthScale = this.v.t.v(0.0f);
        boolean lvt_5_1_ = false;
        if (this.v.v instanceof bcc && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.v.t.fU() > 5.0f);
            lvt_5_1_ &= this.v.v.bo();
        }
        final int x = this.U / 2 - 8;
        final int y = this.V / 2 - 7 + 16;
        if (lvt_5_1_) {
            ((ehb)this).b(stack, x, y, 68, 94, 16, 16);
        }
        else if (strengthScale < 1.0f) {
            ((ehb)this).b(stack, x, y, 36, 94, 16, 4);
            ((ehb)this).b(stack, x, y, 52, 94, (int)(strengthScale * 17.0f), 4);
        }
        IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(((VanillaStackAccessor)stack).stack());
        gfx.restoreBlaze3DStates();
    }
    
    @Insert(method = { "renderCrosshair(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At("TAIL"))
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final eaq stack, final InsertInfo callbackInfo) {
        if (this.v.m.w().c() == efe.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(((VanillaStackAccessor)stack).stack());
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(((VanillaStackAccessor)stack).stack());
    }
    
    @Redirect(method = { "renderCrosshair(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final eyw instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(((VanillaStackAccessor)this.labyMod$stack).stack());
        if (cancelled) {
            return 1.0f;
        }
        return instance.v(value);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final eyw instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(((VanillaStackAccessor)this.labyMod$stack).stack());
        if (cancelled) {
            return 1.0f;
        }
        return instance.v(value);
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final float lvt_1_1_, final eaq stack, final CallbackInfo ci) {
        if (this.v.m.w().c() == efe.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(((VanillaStackAccessor)stack).stack());
        }
    }
    
    @Redirect(method = { "renderPlayerHealth" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final ehb gui, final int health) {
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
    
    @Insert(method = { "displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(final eaq stack, final dxl objective, final InsertInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(((VanillaStackAccessor)stack).stack());
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final eaq stack, final dxl objective, final InsertInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(((VanillaStackAccessor)stack).stack());
    }
    
    @Insert(method = { "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(final eaq stack, final InsertInfo insertInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(((VanillaStackAccessor)stack).stack());
        if (cancelled) {
            insertInfo.cancel();
        }
    }
    
    @Insert(method = { "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At("TAIL"))
    public void labymod$renderEffectsPost(final eaq stack, final InsertInfo insertInfo) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(((VanillaStackAccessor)stack).stack());
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storePoseStackInstance(final eaq poseStack, final float $$1, final CallbackInfo ci) {
        this.labyMod$stack = poseStack;
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final ehb gui, final eaq stack, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        this.labyMod$renderOffhandHotbarSlot(gui, stack, x, y, u, v, uWidth, vHeight);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final ehb gui, final eaq stack, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        this.labyMod$renderOffhandHotbarSlot(gui, stack, x, y, u, v, uWidth, vHeight);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final ehb gui, final eaq stack, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(((VanillaStackAccessor)stack).stack());
        if (cancelled) {
            return;
        }
        gui.b(stack, x, y, u, v, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(((VanillaStackAccessor)stack).stack());
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final ehb gui, final int x, final int y, final float v, final buc player, final cax itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(x, y, v, player, itemStack, i);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final ehb gui, final int x, final int y, final float v, final buc player, final cax itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(x, y, v, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final int x, final int y, final float v, final buc player, final cax itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(((VanillaStackAccessor)this.labyMod$stack).stack());
        if (cancelled) {
            return;
        }
        this.a(x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(((VanillaStackAccessor)this.labyMod$stack).stack());
    }
    
    @Overwrite
    public void a(final rq message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.z = (rq)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.A = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.B = animatedMessage);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(final efy options) {
        if (!Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get()) {
            return options.aa;
        }
        return options.aa && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private rq labyMod$fireIngameOverlayElementRenderEventTitle(final ehb instance) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(((VanillaStackAccessor)this.labyMod$stack).stack())) {
            return null;
        }
        return this.L;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.BEFORE) })
    private void labyMod$fireIngameOverlayElementRenderEventTitle(final eaq matrixStackIn, final float partialTicks, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(((VanillaStackAccessor)matrixStackIn).stack());
    }
}
