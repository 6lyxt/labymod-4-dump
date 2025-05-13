// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.entity;

import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import net.labymod.core.main.animation.old.animations.HitboxOldAnimation;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.core.main.user.shop.item.ItemUtil;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.EntityRenderDispatcher;

@Mixin({ gxt.class })
public abstract class MixinEntityRenderDispatcher implements EntityRenderDispatcher
{
    private static final Quaternionf FRONT_CAMERA;
    
    @Shadow
    public abstract void a(final boolean p0);
    
    @Shadow
    public abstract void a(final Quaternionf p0);
    
    @Shadow
    public abstract boolean a();
    
    @Shadow
    public abstract void b(final boolean p0);
    
    @Shadow
    public abstract void a(final bxe p0, final double p1, final double p2, final double p3, final float p4, final fld p5, final grn p6, final int p7);
    
    @Override
    public boolean isRenderDebugHitBoxes() {
        return this.a();
    }
    
    @Override
    public void setRenderDebugHitBoxes(final boolean renderDebugHitBoxes) {
        this.b(renderDebugHitBoxes);
    }
    
    @Override
    public void renderInScreen(final Stack stack, final Entity entity, final float partialTicks) {
        ItemUtil.skipFlyingPets();
        fkb.e();
        this.a(false);
        this.a(MixinEntityRenderDispatcher.FRONT_CAMERA);
        this.a((bxe)entity, 0.0, 0.0, 0.0, partialTicks, (fld)stack.getProvider().getPoseStack(), (grn)fqq.Q().aR().c(), 15728880);
        fkb.d();
        ItemUtil.resetSkipFlyingPets();
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V" }, at = { @At("HEAD") })
    private <E extends bxe, S extends hec> void labyMod$fireEntityRenderPre(final bxe entity, final double x, final double y, final double z, final float partialTicks, final fld poseStack, final grn bufferSource, final int $$7, final gxu<? super E, S> $$8, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)entity, Phase.PRE));
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V" }, at = { @At("TAIL") })
    private <E extends bxe, S extends hec> void labyMod$fireEntityRenderPost(final bxe entity, final double x, final double y, final double z, final float partialTicks, final fld poseStack, final grn bufferSource, final int $$7, final gxu<? super E, S> $$8, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)entity, Phase.POST));
    }
    
    @Inject(method = { "render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitboxes(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/client/renderer/entity/state/HitboxesRenderState;Lnet/minecraft/client/renderer/MultiBufferSource;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private <E extends bxe, S extends hec> void labyMod$customAndOldHitbox(final hec renderState, final double x, final double y, final double z, final fld poseStack, final grn bufferSource, final int $$6, final gxu<?, S> $$7, final CallbackInfo ci) {
        ci.cancel();
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HitboxOldAnimation animation = oldAnimationRegistry.get("hitbox");
        if (animation != null) {
            final EntityRenderStateAccessor<bxe> accessor = CastUtil.cast(renderState);
            animation.renderHitbox(x, y, z, ((VanillaStackAccessor)poseStack).stack(bufferSource), CastUtil.cast(accessor.labyMod$getEntity()), Laby.labyAPI().minecraft().getPartialTicks());
        }
        if (ci.isCancelled()) {
            poseStack.b();
        }
    }
    
    static {
        FRONT_CAMERA = new Quaternionf();
    }
}
