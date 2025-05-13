// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui;

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

@Mixin({ enq.class })
public abstract class MixinGui
{
    private ehe labyMod$stack;
    @Shadow
    @Final
    private emh w;
    @Shadow
    private int W;
    @Shadow
    private int V;
    @Shadow
    @Nullable
    public tj M;
    @Shadow
    private tj A;
    @Shadow
    private int B;
    @Shadow
    private boolean C;
    
    @Shadow
    protected abstract int a(final int p0);
    
    @Shadow
    protected abstract void a(final ehe p0, final int p1, final int p2, final float p3, final bym p4, final cfv p5, final int p6);
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V" }, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(final ehe stack, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)stack).stack(), Phase.PRE, false));
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V" }, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(final ehe stack, final float partialTicks, final InsertInfo callback) {
        Laby.fireEvent(new IngameOverlayRenderEvent(((VanillaStackAccessor)stack).stack(), Phase.POST, false));
    }
    
    @Inject(method = { "renderCrosshair(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$renderCrosshairPre(final ehe stack, final CallbackInfo ci) {
        final boolean cancelledCrossHair = IngameOverlayElementRenderEventCaller.callCrossHairPre(((VanillaStackAccessor)stack).stack());
        if (!cancelledCrossHair) {
            return;
        }
        ci.cancel();
        if (this.w.m.z().c() != els.b) {
            return;
        }
        final boolean cancelledAttackIndicator = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(((VanillaStackAccessor)stack).stack());
        if (cancelledAttackIndicator) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final float strengthScale = this.w.t.z(0.0f);
        boolean lvt_5_1_ = false;
        if (this.w.v instanceof bfx && strengthScale >= 1.0f) {
            lvt_5_1_ = (this.w.t.gc() > 5.0f);
            lvt_5_1_ &= this.w.v.bq();
        }
        final int x = this.V / 2 - 8;
        final int y = this.W / 2 - 7 + 16;
        if (lvt_5_1_) {
            enr.c(stack, x, y, 68, 94, 16, 16);
        }
        else if (strengthScale < 1.0f) {
            enr.c(stack, x, y, 36, 94, 16, 4);
            enr.c(stack, x, y, 52, 94, (int)(strengthScale * 17.0f), 4);
        }
        IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(((VanillaStackAccessor)stack).stack());
        gfx.restoreBlaze3DStates();
    }
    
    @Insert(method = { "renderCrosshair(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At("TAIL"))
    public void labyMod$renderCrosshairAndAttackIndicatorPost(final ehe stack, final InsertInfo callbackInfo) {
        if (this.w.m.z().c() == els.b) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(((VanillaStackAccessor)stack).stack());
        }
        IngameOverlayElementRenderEventCaller.callCrossHairPost(((VanillaStackAccessor)stack).stack());
    }
    
    @Redirect(method = { "renderCrosshair(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(final fhk instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(((VanillaStackAccessor)this.labyMod$stack).stack());
        if (cancelled) {
            return 1.0f;
        }
        return instance.z(value);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(final fhk instance, float value) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(((VanillaStackAccessor)this.labyMod$stack).stack());
        if (cancelled) {
            return 1.0f;
        }
        return instance.z(value);
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.BEFORE) })
    public void labyMod$renderHotbarAttackIndicatorPost(final float lvt_1_1_, final ehe stack, final CallbackInfo ci) {
        if (this.w.m.z().c() == els.c) {
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(((VanillaStackAccessor)stack).stack());
        }
    }
    
    @Redirect(method = { "renderPlayerHealth" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(final enq gui, final int health) {
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
    public void labyMod$renderScoreboardPre(final ehe stack, final edz objective, final InsertInfo callbackInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callScoreboardPre(((VanillaStackAccessor)stack).stack());
        if (cancelled) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V" }, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(final ehe stack, final edz objective, final InsertInfo ci) {
        IngameOverlayElementRenderEventCaller.callScoreboardPost(((VanillaStackAccessor)stack).stack());
    }
    
    @Insert(method = { "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(final ehe stack, final InsertInfo insertInfo) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callPotionEffectsPre(((VanillaStackAccessor)stack).stack());
        if (cancelled) {
            insertInfo.cancel();
        }
    }
    
    @Insert(method = { "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = @At("TAIL"))
    public void labymod$renderEffectsPost(final ehe stack, final InsertInfo insertInfo) {
        IngameOverlayElementRenderEventCaller.callPotionEffectsPost(((VanillaStackAccessor)stack).stack());
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$storePoseStackInstance(final ehe poseStack, final float $$1, final CallbackInfo ci) {
        this.labyMod$stack = poseStack;
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(final ehe stack, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        this.labyMod$renderOffhandHotbarSlot(stack, x, y, u, v, uWidth, vHeight);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(final ehe stack, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        this.labyMod$renderOffhandHotbarSlot(stack, x, y, u, v, uWidth, vHeight);
    }
    
    private void labyMod$renderOffhandHotbarSlot(final ehe stack, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandTexturePre(((VanillaStackAccessor)stack).stack());
        if (cancelled) {
            return;
        }
        enr.c(stack, x, y, u, v, uWidth, vHeight);
        IngameOverlayElementRenderEventCaller.callOffhandTexturePost(((VanillaStackAccessor)stack).stack());
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lcom/mojang/blaze3d/vertex/PoseStack;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(final enq gui, final ehe poseStack, final int x, final int y, final float v, final bym player, final cfv itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(poseStack, x, y, v, player, itemStack, i);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lcom/mojang/blaze3d/vertex/PoseStack;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(final enq gui, final ehe poseStack, final int x, final int y, final float v, final bym player, final cfv itemStack, final int i) {
        this.labyMod$renderOffhandHotbarItem(poseStack, x, y, v, player, itemStack, i);
    }
    
    private void labyMod$renderOffhandHotbarItem(final ehe poseStack, final int x, final int y, final float v, final bym player, final cfv itemStack, final int i) {
        final boolean cancelled = IngameOverlayElementRenderEventCaller.callOffhandItemPre(((VanillaStackAccessor)poseStack).stack());
        if (cancelled) {
            return;
        }
        this.a(poseStack, x, y, v, player, itemStack, i);
        IngameOverlayElementRenderEventCaller.callOffhandItemPost(((VanillaStackAccessor)poseStack).stack());
    }
    
    @Overwrite
    public void a(final tj message, final boolean animatedMessage) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        final Component mapped = mapper.fromMinecraftComponent(message);
        final ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        final Component modifiedMessage = event.getMessage();
        this.A = (tj)((modifiedMessage == mapped) ? message : mapper.toMinecraftComponent(modifiedMessage));
        this.B = 60;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, this.C = animatedMessage);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(final eml options) {
        if (!Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get()) {
            return options.Z;
        }
        return options.Z && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private tj labyMod$fireIngameOverlayElementRenderEventTitle(final enq instance) {
        if (IngameOverlayElementRenderEventCaller.callTitlePre(((VanillaStackAccessor)this.labyMod$stack).stack())) {
            return null;
        }
        return this.M;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.BEFORE) })
    private void labyMod$fireIngameOverlayElementRenderEventTitle(final ehe matrixStackIn, final float partialTicks, final CallbackInfo ci) {
        IngameOverlayElementRenderEventCaller.callTitlePost(((VanillaStackAccessor)matrixStackIn).stack());
    }
}
