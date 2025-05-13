// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.renderer.entity;

import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
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

@Mixin({ fio.class })
public abstract class MixinEntityRenderDispatcher implements EntityRenderDispatcher
{
    private static final Quaternionf FRONT_CAMERA;
    
    @Shadow
    abstract <E extends bdr> void a(final E p0, final double p1, final double p2, final double p3, final float p4, final float p5, final eed p6, final fdv p7, final int p8);
    
    @Shadow
    public abstract void a(final boolean p0);
    
    @Shadow
    public abstract void a(final Quaternionf p0);
    
    @Shadow
    public abstract boolean a();
    
    @Shadow
    public abstract void b(final boolean p0);
    
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
        ecz.c();
        this.a(false);
        this.a(MixinEntityRenderDispatcher.FRONT_CAMERA);
        this.a((bdr)entity, 0.0, 0.0, 0.0, 0.0f, partialTicks, (eed)stack.getProvider().getPoseStack(), (fdv)ejf.N().aO().b(), 15728880);
        ecz.b();
        ItemUtil.resetSkipFlyingPets();
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$fireEntityRenderPre(final bdr param0, final double param1, final double param2, final double param3, final float param4, final float param5, final eed param6, final fdv param7, final int param8, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)param0, Phase.PRE));
    }
    
    @Inject(method = { "render" }, at = { @At("TAIL") })
    private void labyMod$fireEntityRenderPost(final bdr param0, final double param1, final double param2, final double param3, final float param4, final float param5, final eed param6, final fdv param7, final int param8, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)param0, Phase.POST));
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;F)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$customAndOldHitbox(final bdr entity, final double x, final double y, final double z, final float param4, final float partialTicks, final eed poseStack, final fdv bufferSource, final int param8, final CallbackInfo ci) {
        ci.cancel();
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HitboxOldAnimation animation = oldAnimationRegistry.get("hitbox");
        if (animation != null) {
            animation.renderHitbox(x, y, z, ((VanillaStackAccessor)poseStack).stack(bufferSource), (Entity)entity, partialTicks);
        }
        if (ci.isCancelled()) {
            poseStack.b();
        }
    }
    
    static {
        FRONT_CAMERA = new Quaternionf();
    }
}
