// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.ItemStackRenderer;

@Mixin({ foc.class })
@Implements({ @Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE) })
public abstract class MixinItemRenderer implements ItemStackRenderer
{
    @Shadow
    public abstract void c(final ehe p0, final cfv p1, final int p2, final int p3);
    
    @Shadow
    public abstract void a(final ehe p0, final enp p1, final cfv p2, final int p3, final int p4);
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        final cfv minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        final ehe poseStack = (ehe)stack.getProvider().getPoseStack();
        final float prevRed = RenderSystem.getShaderColor()[0];
        final float prevGreen = RenderSystem.getShaderColor()[1];
        final float prevBlue = RenderSystem.getShaderColor()[2];
        final float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        this.c(poseStack, minecraftItemStack, x, y);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            this.a(poseStack, emh.N().h, minecraftItemStack, x, y);
        }
    }
}
