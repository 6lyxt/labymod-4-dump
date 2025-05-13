// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world.effect;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.effect.PotionEffect;

@Mixin({ va.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinPotionEffect implements PotionEffect
{
    private static final nf INVENTORY_BACKGROUND;
    @Shadow
    private int c;
    @Shadow
    private int d;
    @Shadow
    @Final
    private uz b;
    
    @Shadow
    public abstract boolean g();
    
    @Intrinsic
    public int mobEffect$getDuration() {
        return this.c;
    }
    
    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.d;
    }
    
    @Override
    public String getTranslationKey() {
        return this.b.a();
    }
    
    @Override
    public Icon getIcon() {
        final int index = this.b.d();
        return Icon.sprite(Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createMinecraft("textures/gui/container/inventory.png"), index % 8 * 18, 198 + index / 8 * 18, 18, 18, 256, 256);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final uz potion = this.b;
        if (!potion.c()) {
            return;
        }
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        bus.g();
        bus.m();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bib.z().N().a(MixinPotionEffect.INVENTORY_BACKGROUND);
        final int index = potion.d();
        MinecraftUtil.drawTexturedModalRect(x, y, index % 8 * 18, 198 + index / 8 * 18, 18, 18);
        gfx.restoreBlaze3DStates();
    }
    
    @Override
    public boolean hasMaxDuration() {
        return this.g();
    }
    
    static {
        INVENTORY_BACKGROUND = new nf("textures/gui/container/inventory.png");
    }
}
