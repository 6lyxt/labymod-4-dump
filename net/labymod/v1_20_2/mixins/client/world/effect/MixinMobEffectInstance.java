// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.world.effect;

import net.labymod.v1_20_2.client.gui.GuiGraphicsAccessor;
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

@Mixin({ bid.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinMobEffectInstance implements PotionEffect
{
    @Shadow
    private int l;
    @Shadow
    private int m;
    @Shadow
    @Final
    private bib k;
    private fze labyMod$iconSprite;
    private esf labyMod$graphics;
    
    @Intrinsic
    public int mobEffect$getDuration() {
        return this.l;
    }
    
    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.m;
    }
    
    @Override
    public String getTranslationKey() {
        final Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.k.e());
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.key();
        }
        return "labymod.unknown";
    }
    
    @Override
    public Icon getIcon() {
        final fze sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite((ResourceLocation)sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final fze sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return;
        }
        final Object poseStackObject = stack.getProvider().getPoseStack();
        if (poseStackObject == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            final eqv minecraft = eqv.O();
            this.labyMod$graphics = new esf(minecraft, minecraft.aO().c());
        }
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((elp)poseStackObject);
        this.labyMod$graphics.a(x, y, 0, width, height, sprite);
    }
    
    @Override
    public boolean hasMaxDuration() {
        return false;
    }
    
    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.l == -1;
    }
    
    private fze labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        final aew resourceLocation = jb.e.b((Object)this.k);
        if (resourceLocation == null) {
            return null;
        }
        return this.labyMod$iconSprite = eqv.O().aE().a(this.k);
    }
}
