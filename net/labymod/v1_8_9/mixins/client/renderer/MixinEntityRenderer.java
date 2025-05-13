// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.main.LabyMod;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import org.lwjgl.opengl.GL11;
import net.labymod.api.util.Color;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.volt.callback.InsertInfo;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.renderer.EntityRendererAccessor;
import net.labymod.v1_8_9.client.gfx.texture.LightTextureAccessor;
import net.labymod.v1_8_9.client.render.lighting.LightmapState;

@Mixin({ bfk.class })
public abstract class MixinEntityRenderer implements LightmapState, LightTextureAccessor, EntityRendererAccessor
{
    @Shadow
    private ave h;
    @Shadow
    private float x;
    @Shadow
    private float y;
    @Shadow
    private int m;
    @Shadow
    @Final
    private blz G;
    @Shadow
    @Final
    private jy I;
    private ResourceLocation labyMod$textureLocation;
    private boolean labyMod$lightmapState;
    private float labyMod$cameraYaw;
    private float labyMod$cameraPitch;
    
    public MixinEntityRenderer() {
        this.labyMod$lightmapState = false;
    }
    
    @Override
    public float getCameraYaw() {
        return this.labyMod$cameraYaw;
    }
    
    @Override
    public void setCameraYaw(final float yaw) {
        this.labyMod$cameraYaw = yaw;
    }
    
    @Override
    public float getCameraPitch() {
        return this.labyMod$cameraPitch;
    }
    
    @Override
    public void setCameraPitch(final float pitch) {
        this.labyMod$cameraPitch = pitch;
    }
    
    @Inject(method = { "orientCamera(F)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;", shift = At.Shift.BEFORE) })
    private void labyMod$preCameraSetup(final float partialTicks, final CallbackInfo ci) {
        this.labyMod$fireCameraSetupEvent(Phase.PRE);
    }
    
    @Inject(method = { "orientCamera(F)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.BEFORE, ordinal = 4) })
    private void labyMod$postCameraSetup(final float partialTicks, final CallbackInfo ci) {
        this.labyMod$fireCameraSetupEvent(Phase.POST);
    }
    
    private void labyMod$fireCameraSetupEvent(final Phase phase) {
        Laby.fireEvent(new CameraSetupEvent(VersionedStackProvider.DEFAULT_STACK, phase));
    }
    
    @WrapOperation(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z") })
    private boolean labyMod$firePreRenderHandEvent(final bfk instance, final Operation<Boolean> original) {
        final RenderHandEvent event = RenderHandEventCaller.call(VersionedStackProvider.DEFAULT_STACK, Phase.PRE);
        return !event.isCancelled() && (boolean)original.call(new Object[] { instance });
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderWorldDirections(F)V", shift = At.Shift.AFTER) })
    private void labyMod$firePostRenderHandEvent(final int p_renderWorldPass_1_, final float p_renderWorldPass_2_, final long p_renderWorldPass_3_, final CallbackInfo ci) {
        RenderHandEventCaller.call(VersionedStackProvider.DEFAULT_STACK, Phase.POST);
    }
    
    @Insert(method = { "updateFovModifierHand" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getFovModifier()F", shift = At.Shift.AFTER), cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(final InsertInfo ci) {
        float modifier = 1.0f;
        final pk ac = this.h.ac();
        if (ac instanceof final bet var1) {
            modifier = var1.o();
        }
        final FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.x, this.y, modifier, this.m);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.x = fieldOfViewTickEvent.getFov();
        this.y = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private pk labyMod$preOrientCamera(final ave mc) {
        final pk entity = mc.ac();
        final CameraRotationEvent event = Laby.fireEvent(new CameraRotationEvent(entity.y, entity.z));
        this.labyMod$cameraYaw = event.getYaw();
        this.labyMod$cameraPitch = event.getPitch();
        return entity;
    }
    
    @Inject(method = { "orientCamera" }, at = { @At("TAIL") })
    private void labyMod$postOrientCamera(final float partialTicks, final CallbackInfo ci) {
        final pk entity = this.h.ac();
        if (entity == null) {
            return;
        }
        final float eyeHeight = entity.aS();
        final CameraEyeHeightEvent event = Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
        bfl.b(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
    }
    
    @Redirect(method = { "renderWorldDirections" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    private float labyMod$modifiedEyeHeight(final pk entity) {
        return Laby.fireEvent(new CameraEyeHeightEvent(Laby.labyAPI().minecraft().getPartialTicks(), entity.aS())).getEyeHeight();
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"))
    private float labyMod$rotationYaw(final pk entity) {
        return this.labyMod$cameraYaw;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"))
    private float labyMod$rotationPitch(final pk entity) {
        return this.labyMod$cameraPitch;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"))
    private float labyMod$prevRotationYaw(final pk entity) {
        return this.labyMod$cameraYaw;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"))
    private float labyMod$prevRotationPitch(final pk entity) {
        return this.labyMod$cameraPitch;
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBox(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/MovingObjectPosition;IF)V"))
    public void labyMod$renderCustomOutline(final bfr instance, final wn player, final auh position, final int i, final float partialTicks) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        final adm theWorld = player.o;
        if (i == 0 && position.a == auh.a.b) {
            final cj blockPos = position.a();
            final RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.n(), blockPos.o(), blockPos.p());
            Laby.fireEvent(event);
            if (event.isCancelled()) {
                return;
            }
            bfl.l();
            bfl.x();
            bfl.a(false);
            final afh block = theWorld.p(blockPos).c();
            if (block.t() != arm.a && theWorld.af().a(blockPos)) {
                block.a((adq)theWorld, blockPos);
                final double offsetX = player.P + (player.s - player.P) * partialTicks;
                final double offsetY = player.Q + (player.t - player.Q) * partialTicks;
                final double offsetZ = player.R + (player.u - player.R) * partialTicks;
                final aug boundingBox = block.b(theWorld, blockPos).c(-offsetX, -offsetY, -offsetZ);
                if (event.getLineColor() != null) {
                    this.drawLines(boundingBox.b(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026), event.getLineColor());
                }
                if (event.getOverlayColor() != null) {
                    this.drawOverlay(boundingBox.b(0.0010000000474974513, 0.0010000000474974513, 0.0010000000474974513), event.getOverlayColor());
                }
            }
            bfl.a(true);
            bfl.w();
            bfl.k();
        }
        gfx.restoreBlaze3DStates();
    }
    
    private void drawLines(final aug boundingBox, final Color color) {
        bfl.a(770, 771, 1, 0);
        bfl.c(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glLineWidth(2.0f);
        bfr.a(boundingBox);
    }
    
    private void drawOverlay(final aug boundingBox, final Color color) {
        bfl.c(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        final bfx tessellator = bfx.a();
        final bfd worldRenderer = tessellator.c();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.f).d();
        worldRenderer.b(boundingBox.a, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.a, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.c).d();
        worldRenderer.b(boundingBox.d, boundingBox.e, boundingBox.f).d();
        worldRenderer.b(boundingBox.d, boundingBox.b, boundingBox.f).d();
        tessellator.b();
    }
    
    @Inject(method = { "enableLightmap" }, at = { @At("TAIL") })
    private void labyMod$turnOnLightmap(final CallbackInfo ci) {
        this.labyMod$lightmapState = true;
        final int lightmapId = this.G.b();
        ShaderTextures.setShaderTexture(1, lightmapId);
    }
    
    @Inject(method = { "disableLightmap" }, at = { @At("TAIL") })
    private void labyMod$turnOffLightmap(final CallbackInfo ci) {
        this.labyMod$lightmapState = false;
        ShaderTextures.setShaderTexture(1, 0);
    }
    
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"))
    private void redirectPlayerRotation(final bew player, final float x, final float y) {
        final ClientPlayerTurnEvent event = Laby.fireEvent(new ClientPlayerTurnEvent((ClientPlayer)player, x / 8.0f, -y / 8.0f));
        if (event.isCancelled()) {
            return;
        }
        player.c((float)event.getX() * 8.0f, (float)(-event.getY()) * 8.0f);
    }
    
    @Override
    public boolean isEnabled() {
        return this.labyMod$lightmapState;
    }
    
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/settings/GameSettings;hideGUI:Z"))
    private boolean labyMod$fireHiddenOverlayRenderEvent(final avh settings) {
        final boolean hideGui = settings.aA;
        if (hideGui && this.h.m == null) {
            final Stack stack = VersionedStackProvider.DEFAULT_STACK;
            final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
            gfx.storeBlaze3DStates();
            Laby.fireEvent(new IngameOverlayRenderEvent(stack, Phase.PRE, true));
            Laby.fireEvent(new IngameOverlayRenderEvent(stack, Phase.POST, true));
            gfx.restoreBlaze3DStates();
        }
        return hideGui;
    }
    
    @Override
    public bmk getTexture() {
        return (bmk)this.G;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.I;
        }
        return this.labyMod$textureLocation;
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I", ordinal = 3, shift = At.Shift.BEFORE) })
    private void renderWorldEvent(final int index, final float partialTicks, final long time, final CallbackInfo ci) {
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        RenderWorldEventCaller.call(stack, partialTicks);
    }
    
    @WrapWithCondition(method = { "setupCameraTransform" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V") })
    private boolean labyMod$noViewBobbing(final bfk instance, final float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get();
    }
}
