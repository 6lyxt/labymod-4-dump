// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.entity;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
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

@Mixin({ bzw.class })
public abstract class MixinRenderItem implements ItemStackRenderer
{
    @Shadow
    public float a;
    
    @Shadow
    public abstract void a(final aip p0, final int p1, final int p2);
    
    @Shadow
    public abstract void a(final bip p0, final aip p1, final int p2, final int p3, final String p4);
    
    @Shadow
    protected abstract void a(final cfy p0, final int p1, final aip p2);
    
    @Inject(method = { "renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$skipColors(final cfy model, final aip stack, final CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            this.a(model, ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, GlColorAlphaModifier.getAlpha()), stack);
            ci.cancel();
        }
    }
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        bus.D();
        bus.m();
        bus.a(770, 771, 1, 0);
        bhz.c();
        this.a += 50.0f;
        final aip minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        bus.c(1.0f, 1.0f, 1.0f, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        this.a(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        if (decorate) {
            this.a(bib.z().k, minecraftItemStack, x, y, null);
        }
        this.a -= 50.0f;
        bhz.a();
        bus.E();
        bus.l();
        gfx.restoreBlaze3DStates();
    }
}
