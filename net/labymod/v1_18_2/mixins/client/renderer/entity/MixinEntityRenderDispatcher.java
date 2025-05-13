// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer.entity;

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
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.EntityRenderDispatcher;

@Mixin({ evi.class })
public abstract class MixinEntityRenderDispatcher implements EntityRenderDispatcher
{
    private static final g FRONT_CAMERA;
    
    @Shadow
    abstract <E extends axk> void a(final E p0, final double p1, final double p2, final double p3, final float p4, final float p5, final dtm p6, final eqs p7, final int p8);
    
    @Shadow
    public abstract void a(final boolean p0);
    
    @Shadow
    public abstract void a(final g p0);
    
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
        dsi.c();
        this.a(false);
        this.a(MixinEntityRenderDispatcher.FRONT_CAMERA);
        this.a((axk)entity, 0.0, 0.0, 0.0, 0.0f, partialTicks, (dtm)stack.getProvider().getPoseStack(), (eqs)dyr.D().aD().b(), 15728880);
        dsi.b();
        ItemUtil.resetSkipFlyingPets();
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    private void labyMod$fireEntityRenderPre(final axk param0, final double param1, final double param2, final double param3, final float param4, final float param5, final dtm param6, final eqs param7, final int param8, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)param0, Phase.PRE));
    }
    
    @Inject(method = { "render" }, at = { @At("TAIL") })
    private void labyMod$fireEntityRenderPost(final axk param0, final double param1, final double param2, final double param3, final float param4, final float param5, final dtm param6, final eqs param7, final int param8, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)param0, Phase.POST));
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;F)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$customAndOldHitbox(final axk entity, final double x, final double y, final double z, final float param4, final float partialTicks, final dtm poseStack, final eqs bufferSource, final int param8, final CallbackInfo ci) {
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
        FRONT_CAMERA = g.a;
    }
}
