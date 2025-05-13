// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.v1_20_6.client.gui.GuiGraphicsAccessor;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.ItemStackRenderer;

@Mixin({ gjx.class })
@Implements({ @Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE) })
public abstract class MixinItemRenderer implements ItemStackRenderer
{
    private fgt labyMod$graphics;
    
    @Shadow
    public abstract void a(final cur p0, final cuo p1, final boolean p2, final faa p3, final gdq p4, final int p5, final int p6, final grc p7);
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new fgt(ffh.Q(), ffh.Q().aQ().c());
        }
        final cur minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((faa)stack.getProvider().getPoseStack());
        final float prevRed = RenderSystem.getShaderColor()[0];
        final float prevGreen = RenderSystem.getShaderColor()[1];
        final float prevBlue = RenderSystem.getShaderColor()[2];
        final float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        this.labyMod$graphics.b(minecraftItemStack, x, y);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            this.labyMod$graphics.a(ffh.Q().h, minecraftItemStack, x, y);
        }
    }
}
