// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.main.LabyMod;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.util.Color;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.volt.callback.InsertInfo;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.renderer.EntityRendererAccessor;
import net.labymod.v1_12_2.client.gfx.texture.LightTextureAccessor;
import net.labymod.v1_12_2.client.render.lighting.LightmapState;

@Mixin({ buq.class })
public abstract class MixinEntityRenderer implements LightmapState, LightTextureAccessor, EntityRendererAccessor
{
    @Shadow
    private bib h;
    @Shadow
    private float x;
    @Shadow
    private float y;
    @Shadow
    private int m;
    @Shadow
    @Final
    private cdg H;
    @Shadow
    @Final
    private nf J;
    private ResourceLocation labyMod$textureLocation;
    private boolean labyMod$lightmapState;
    private float labyMod$cameraYaw;
    private float labyMod$cameraPitch;
    
    public MixinEntityRenderer() {
        this.labyMod$lightmapState = false;
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
    private boolean labyMod$firePreRenderHandEvent(final buq instance, final Operation<Boolean> original) {
        final RenderHandEvent event = RenderHandEventCaller.call(VersionedStackProvider.DEFAULT_STACK, Phase.PRE);
        return !event.isCancelled() && (boolean)original.call(new Object[] { instance });
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V", shift = At.Shift.AFTER) })
    private void labyMod$firePostRenderHandEvent(final int p_renderWorldPass_1_, final float p_renderWorldPass_2_, final long p_renderWorldPass_3_, final CallbackInfo ci) {
        RenderHandEventCaller.call(VersionedStackProvider.DEFAULT_STACK, Phase.POST);
    }
    
    @Insert(method = { "updateFovModifierHand" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getFovModifier()F", shift = At.Shift.AFTER), cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(final InsertInfo ci) {
        float modifier = 1.0f;
        final vg aa = this.h.aa();
        if (aa instanceof final bua var1) {
            modifier = var1.u();
        }
        final FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.x, this.y, modifier, this.m);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.x = fieldOfViewTickEvent.getFov();
        this.y = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "orientCamera" }, at = { @At("TAIL") })
    private void labyMod$postOrientCamera(final float partialTicks, final CallbackInfo ci) {
        final vg entity = this.h.aa();
        if (entity == null) {
            return;
        }
        final float eyeHeight = entity.by();
        final CameraEyeHeightEvent event = Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
        bus.c(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private vg labyMod$preOrientCamera(final bib mc) {
        final vg entity = mc.aa();
        final CameraRotationEvent event = Laby.fireEvent(new CameraRotationEvent(entity.v, entity.w));
        this.labyMod$cameraYaw = event.getYaw();
        this.labyMod$cameraPitch = event.getPitch();
        return entity;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"))
    private float labyMod$rotationYaw(final vg entity) {
        return this.labyMod$cameraYaw;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"))
    private float labyMod$rotationPitch(final vg entity) {
        return this.labyMod$cameraPitch;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"))
    private float labyMod$prevRotationYaw(final vg entity) {
        return this.labyMod$cameraYaw;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"))
    private float labyMod$prevRotationPitch(final vg entity) {
        return this.labyMod$cameraPitch;
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBox(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;IF)V"))
    public void labyMod$renderCustomOutline(final buy instance, final aed player, final bhc result, final int i, final float partialTicks) {
        if (i != 0 || result.a != bhc.a.b) {
            return;
        }
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        final amu world = player.l;
        final et blockPos = result.a();
        final RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.p(), blockPos.q(), blockPos.r());
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            gfx.restoreBlaze3DStates();
            return;
        }
        bus.m();
        bus.z();
        bus.a(false);
        final awt blockState = world.o(blockPos);
        if (blockState.a() != bcz.a && world.al().a(blockPos)) {
            final double offsetX = player.M + (player.p - player.M) * partialTicks;
            final double offsetY = player.N + (player.q - player.N) * partialTicks;
            final double offsetZ = player.O + (player.r - player.O) * partialTicks;
            final bhb boundingBox = blockState.c(world, blockPos).d(-offsetX, -offsetY, -offsetZ);
            if (event.getLineColor() != null) {
                this.labyMod$drawLines(boundingBox.g(0.0020000000949949026), event.getLineColor());
            }
            if (event.getOverlayColor() != null) {
                this.labyMod$drawOverlay(boundingBox.g(0.0010000000474974513), event.getOverlayColor());
            }
        }
        bus.a(true);
        bus.y();
        bus.l();
        gfx.restoreBlaze3DStates();
    }
    
    private void labyMod$drawLines(final bhb boundingBox, final Color color) {
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.d(2.0f);
        buy.a(boundingBox, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    private void labyMod$drawOverlay(final bhb box, final Color color) {
        final int red = color.getRed();
        final int green = color.getGreen();
        final int blue = color.getBlue();
        final int alpha = color.getAlpha();
        final bve tessellator = bve.a();
        final buk worldRenderer = tessellator.c();
        final double minX = box.a;
        final double minY = box.b;
        final double minZ = box.c;
        final double maxX = box.d;
        final double maxY = box.e;
        final double maxZ = box.f;
        worldRenderer.a(7, cdy.f);
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(minX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, minZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, maxY, maxZ).b(red, green, blue, alpha).d();
        worldRenderer.b(maxX, minY, maxZ).b(red, green, blue, alpha).d();
        tessellator.b();
    }
    
    @Inject(method = { "enableLightmap" }, at = { @At("TAIL") })
    private void labyMod$turnOnLightmap(final CallbackInfo ci) {
        this.labyMod$lightmapState = true;
        final int lightmapId = this.H.b();
        ShaderTextures.setShaderTexture(1, lightmapId);
    }
    
    @Inject(method = { "disableLightmap" }, at = { @At("TAIL") })
    private void labyMod$turnOffLightmap(final CallbackInfo ci) {
        this.labyMod$lightmapState = false;
        ShaderTextures.setShaderTexture(1, 0);
    }
    
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void redirectPlayerRotation(final bud player, final float x, final float y) {
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
    private boolean labyMod$fireHiddenOverlayRenderEvent(final bid settings) {
        final boolean hideGui = settings.av;
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
    public cds getTexture() {
        return (cds)this.H;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.J;
        }
        return this.labyMod$textureLocation;
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;DILnet/minecraft/entity/Entity;)I", ordinal = 3, shift = At.Shift.BEFORE) })
    private void renderWorldEvent(final int index, final float partialTicks, final long time, final CallbackInfo ci) {
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        RenderWorldEventCaller.call(stack, partialTicks);
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
    
    @WrapWithCondition(method = { "setupCameraTransform" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V") })
    private boolean labyMod$noViewBobbing(final buq instance, final float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get();
    }
}
