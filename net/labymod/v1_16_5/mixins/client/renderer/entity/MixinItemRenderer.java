// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.entity;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import net.labymod.api.util.math.MathHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.ItemStackRenderer;

@Mixin({ efo.class })
@Implements({ @Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE) })
public abstract class MixinItemRenderer implements ItemStackRenderer
{
    @Shadow
    public abstract void a(final dku p0, final bmb p1, final int p2, final int p3);
    
    @Shadow
    public abstract void c(final bmb p0, final int p1, final int p2);
    
    @Override
    public void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate, final float alpha) {
        final bmb minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix((b)MathHelper.mapper().toMatrix4f(stack.getProvider().getPosition()));
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        this.c(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (decorate) {
            this.a(djz.C().g, minecraftItemStack, x, y);
        }
        RenderSystem.popMatrix();
    }
}
