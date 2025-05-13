// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.ItemStackRenderer;

@Mixin({ bjh.class })
public abstract class MixinRenderItem implements ItemStackRenderer
{
    @Shadow
    public float a;
    
    @Shadow
    public abstract void a(final zx p0, final int p1, final int p2);
    
    @Shadow
    public abstract void a(final avn p0, final zx p1, final int p2, final int p3, final String p4);
    
    @Shadow
    protected abstract void a(final boq p0, final int p1, final zx p2);
    
    @Inject(method = { "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$skipColors(final boq model, final zx stack, final CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            this.a(model, ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, GlColorAlphaModifier.getAlpha()), stack);
            ci.cancel();
        }
    }
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        bfl.B();
        bfl.l();
        bfl.a(770, 771, 1, 0);
        avc.c();
        this.a += 50.0f;
        final zx minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        bfl.c(1.0f, 1.0f, 1.0f, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        this.a(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        if (decorate) {
            this.a(ave.A().k, minecraftItemStack, x, y, null);
        }
        this.a -= 50.0f;
        avc.a();
        bfl.C();
        bfl.k();
        gfx.restoreBlaze3DStates();
    }
}
