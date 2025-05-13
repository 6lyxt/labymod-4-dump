// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_12_2.client.renderer.EntityRendererAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import net.labymod.core.main.animation.old.animations.HitboxOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.item.ItemUtil;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.EntityRenderDispatcher;

@Mixin({ bzf.class })
public abstract class MixinRenderManager implements EntityRenderDispatcher
{
    @Shadow
    protected abstract void a(final vg p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    @Shadow
    public abstract boolean b();
    
    @Shadow
    public abstract void b(final boolean p0);
    
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
        final boolean flip = bib.z().t.aw != 2;
        bus.h();
        bus.G();
        bhz.b();
        final bzf renderManager = bib.z().ac();
        renderManager.a(flip ? 0.0f : 180.0f);
        renderManager.a(false);
        renderManager.a((vg)entity, 0.0, 0.0, 0.0, 0.0f, partialTicks, false);
        renderManager.a(true);
        bus.H();
        bhz.a();
        bus.E();
        bus.g(cii.r);
        bus.z();
        bus.g(cii.q);
        ItemUtil.resetSkipFlyingPets();
    }
    
    @Redirect(method = { "renderEntity" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderDebugBoundingBox(Lnet/minecraft/entity/Entity;DDDFF)V"))
    private void labyMod$renderOldHitbox(final bzf renderManager, final vg entity, final double x, final double y, final double z, final float p_renderDebugBoundingBox_8_, final float partialTicks) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HitboxOldAnimation animation = oldAnimationRegistry.get("hitbox");
        if (animation == null) {
            this.a(entity, x, y, z, p_renderDebugBoundingBox_8_, partialTicks);
            return;
        }
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        bus.a(false);
        bus.z();
        bus.g();
        bus.r();
        bus.l();
        final HitboxOldAnimation hitbox = animation;
        hitbox.renderHitbox(x, y, z, VersionedStackProvider.DEFAULT_STACK, (Entity)entity, partialTicks);
        bus.y();
        bus.f();
        bus.q();
        bus.l();
        bus.a(true);
        gfx.restoreBlaze3DStates();
    }
    
    @Inject(method = { "setPlayerViewY" }, at = { @At("TAIL") })
    public void labyMod$setPlayerViewY(final float playerViewY, final CallbackInfo ci) {
        final EntityRendererAccessor accessor = (EntityRendererAccessor)bib.z().o;
        accessor.setCameraYaw(playerViewY);
    }
}
