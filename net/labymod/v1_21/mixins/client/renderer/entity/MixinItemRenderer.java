// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.v1_21.client.gui.GuiGraphicsAccessor;
import net.labymod.v1_21.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.ItemStackRenderer;

@Mixin({ glh.class })
@Implements({ @Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE) })
public abstract class MixinItemRenderer implements ItemStackRenderer
{
    private fhz labyMod$graphics;
    
    @Shadow
    public abstract void a(final cuq p0, final cun p1, final boolean p2, final fbi p3, final gez p4, final int p5, final int p6, final gsm p7);
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new fhz(fgo.Q(), fgo.Q().aO().c());
        }
        final cuq minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((fbi)stack.getProvider().getPoseStack());
        final float prevRed = RenderSystem.getShaderColor()[0];
        final float prevGreen = RenderSystem.getShaderColor()[1];
        final float prevBlue = RenderSystem.getShaderColor()[2];
        final float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        this.labyMod$graphics.b(minecraftItemStack, x, y);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            this.labyMod$graphics.a(fgo.Q().h, minecraftItemStack, x, y);
        }
    }
}
