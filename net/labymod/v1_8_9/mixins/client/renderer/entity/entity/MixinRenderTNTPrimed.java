// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity.entity;

import net.labymod.api.client.options.Perspective;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.Laby;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bkf.class })
public abstract class MixinRenderTNTPrimed extends biv<vj>
{
    protected MixinRenderTNTPrimed(final biu lvt_1_1_) {
        super(lvt_1_1_);
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V" }, at = { @At("TAIL") })
    public void render(final vj entity, final double x, final double y, final double z, final float lvt_8_1_, final float distance, final CallbackInfo callbackInfo) {
        if (this.b((pk)entity)) {
            return;
        }
        final double distanceSq = entity.h(this.b.c);
        if (distanceSq < distance * distance) {
            return;
        }
        final float scale = 0.02666667f;
        bfl.E();
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        bfl.b(x, y + entity.K + 0.5, z);
        bfl.b(-this.b.e, 0.0f, 1.0f, 0.0f);
        bfl.b(this.labyMod$getPlayerViewX(), 1.0f, 0.0f, 0.0f);
        bfl.a(-scale, -scale, scale);
        final LabyAPI labyAPI = Laby.labyAPI();
        final GFXBridge gfx = labyAPI.gfxRenderPipeline().gfx();
        Laby.references().renderEnvironmentContext().setPackedLight(MinecraftUtil.getPackedLight(entity));
        gfx.storeBlaze3DStates();
        bfl.f();
        bfl.a(false);
        bfl.i();
        labyAPI.tagRegistry().render(VersionedStackProvider.DEFAULT_STACK, (Entity)entity, 0.0f, TagType.MAIN_TAG);
        gfx.restoreBlaze3DStates();
        bfl.F();
    }
    
    private float labyMod$getPlayerViewX() {
        return (Laby.labyAPI().minecraft().options().perspective() == Perspective.THIRD_PERSON_FRONT) ? (-this.b.f) : this.b.f;
    }
}
