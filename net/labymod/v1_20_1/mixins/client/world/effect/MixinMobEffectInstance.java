// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.world.effect;

import net.labymod.v1_20_1.client.gui.GuiGraphicsAccessor;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.effect.PotionEffect;

@Mixin({ bfa.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinMobEffectInstance implements PotionEffect
{
    @Shadow
    private int d;
    @Shadow
    private int e;
    @Shadow
    @Final
    private bey c;
    private fuv labyMod$iconSprite;
    private eox labyMod$graphics;
    
    @Intrinsic
    public int mobEffect$getDuration() {
        return this.d;
    }
    
    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.e;
    }
    
    @Override
    public String getTranslationKey() {
        final Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.c.e());
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.key();
        }
        return "labymod.unknown";
    }
    
    @Override
    public Icon getIcon() {
        final fuv sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite((ResourceLocation)sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final fuv sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return;
        }
        final Object poseStackObject = stack.getProvider().getPoseStack();
        if (poseStackObject == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            final enn minecraft = enn.N();
            this.labyMod$graphics = new eox(minecraft, minecraft.aN().c());
        }
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((eij)poseStackObject);
        this.labyMod$graphics.a(x, y, 0, width, height, sprite);
    }
    
    @Override
    public boolean hasMaxDuration() {
        return false;
    }
    
    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.d == -1;
    }
    
    private fuv labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        final acq resourceLocation = jb.e.b((Object)this.c);
        if (resourceLocation == null) {
            return null;
        }
        return this.labyMod$iconSprite = enn.N().aF().a(this.c);
    }
}
