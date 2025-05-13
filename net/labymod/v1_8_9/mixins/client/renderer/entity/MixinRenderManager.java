// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.renderer.EntityRendererAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import net.labymod.core.main.animation.old.animations.HitboxOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.item.ItemUtil;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.EntityRenderDispatcher;

@Mixin({ biu.class })
public abstract class MixinRenderManager implements EntityRenderDispatcher
{
    @Shadow
    protected abstract void b(final pk p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    @Shadow
    public abstract void b(final boolean p0);
    
    @Shadow
    public abstract boolean b();
    
    @Override
    public boolean isRenderDebugHitBoxes() {
        return this.b();
    }
    
    @Override
    public void setRenderDebugHitBoxes(final boolean renderDebugHitBoxes) {
        this.b(renderDebugHitBoxes);
    }
    
    @Override
    public void renderInScreen(final Stack stack, final Entity entity, final float partialTicks) {
        ItemUtil.skipFlyingPets();
        final boolean flip = ave.A().t.aB != 2;
        bfl.g();
        bfl.E();
        avc.b();
        final biu renderManager = ave.A().af();
        renderManager.a(flip ? 0.0f : 180.0f);
        renderManager.a(false);
        renderManager.a((pk)entity, 0.0, 0.0, 0.0, 0.0f, partialTicks, false);
        renderManager.a(true);
        bfl.F();
        avc.a();
        bfl.C();
        bfl.g(bqs.r);
        bfl.x();
        bfl.g(bqs.q);
        ItemUtil.resetSkipFlyingPets();
    }
    
    @Redirect(method = { "doRenderEntity" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderDebugBoundingBox(Lnet/minecraft/entity/Entity;DDDFF)V"))
    private void labyMod$renderOldHitbox(final biu renderManager, final pk entity, final double x, final double y, final double z, final float p_renderDebugBoundingBox_8_, final float partialTicks) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HitboxOldAnimation animation = oldAnimationRegistry.get("hitbox");
        if (animation == null) {
            this.b(entity, x, y, z, p_renderDebugBoundingBox_8_, partialTicks);
            return;
        }
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        bfl.a(false);
        bfl.x();
        bfl.f();
        bfl.p();
        bfl.k();
        final HitboxOldAnimation hitbox = animation;
        hitbox.renderHitbox(x, y, z, VersionedStackProvider.DEFAULT_STACK, (Entity)entity, partialTicks);
        bfl.w();
        bfl.e();
        bfl.o();
        bfl.k();
        bfl.a(true);
        gfx.restoreBlaze3DStates();
    }
    
    @Inject(method = { "setPlayerViewY" }, at = { @At("TAIL") })
    public void labyMod$setPlayerViewY(final float playerViewY, final CallbackInfo ci) {
        final EntityRendererAccessor accessor = (EntityRendererAccessor)ave.A().o;
        accessor.setCameraYaw(playerViewY);
    }
}
