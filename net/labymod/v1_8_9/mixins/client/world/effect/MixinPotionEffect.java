// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.world.effect;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.effect.PotionEffect;

@Mixin({ pf.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinPotionEffect implements PotionEffect
{
    private static final jy INVENTORY_BACKGROUND;
    @Shadow
    private int b;
    @Shadow
    private int c;
    @Shadow
    private int d;
    
    @Shadow
    public abstract boolean h();
    
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
        final pe potion = pe.a[this.b];
        return potion.a();
    }
    
    @Override
    public Icon getIcon() {
        final pe potion = pe.a[this.b];
        final int index = potion.f();
        return Icon.sprite(Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createMinecraft("textures/gui/container/inventory.png"), index % 8 * 18, 198 + index / 8 * 18, 18, 18, 256, 256);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final pe potion = pe.a[this.b];
        if (!potion.e()) {
            return;
        }
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        bfl.f();
        bfl.l();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        ave.A().P().a(MixinPotionEffect.INVENTORY_BACKGROUND);
        final int index = potion.f();
        MinecraftUtil.drawTexturedModalRect(x, y, index % 8 * 18, 198 + index / 8 * 18, 18, 18);
        gfx.restoreBlaze3DStates();
    }
    
    @Override
    public boolean hasMaxDuration() {
        return this.h();
    }
    
    static {
        INVENTORY_BACKGROUND = new jy("textures/gui/container/inventory.png");
    }
}
