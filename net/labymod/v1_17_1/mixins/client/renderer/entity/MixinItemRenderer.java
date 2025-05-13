// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.renderer.entity;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.v1_17_1.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.ItemStackRenderer;

@Mixin({ esv.class })
@Implements({ @Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE) })
public abstract class MixinItemRenderer implements ItemStackRenderer
{
    @Shadow
    public abstract void a(final dwl p0, final bqq p1, final int p2, final int p3);
    
    @Shadow
    public abstract void c(final bqq p0, final int p1, final int p2);
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        final bqq minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        final dql minecraftModelViewStack = RenderSystem.getModelViewStack();
        final Stack modelViewStack = ((VanillaStackAccessor)minecraftModelViewStack).stack();
        modelViewStack.push();
        modelViewStack.multiply(stack.getProvider().getPosition());
        RenderSystem.applyModelViewMatrix();
        final float prevRed = RenderSystem.getShaderColor()[0];
        final float prevGreen = RenderSystem.getShaderColor()[1];
        final float prevBlue = RenderSystem.getShaderColor()[2];
        final float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        this.c(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            this.a(dvp.C().h, minecraftItemStack, x, y);
        }
        modelViewStack.pop();
        RenderSystem.applyModelViewMatrix();
    }
}
