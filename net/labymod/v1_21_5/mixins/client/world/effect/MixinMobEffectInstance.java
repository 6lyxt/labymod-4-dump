// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.world.effect;

import java.util.function.Function;
import net.labymod.v1_21_5.client.gui.GuiGraphicsAccessor;
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

@Mixin({ bwi.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinMobEffectInstance implements PotionEffect
{
    @Shadow
    private int h;
    @Shadow
    private int i;
    @Shadow
    @Final
    private jg<bwg> g;
    private hkq labyMod$iconSprite;
    private ftk labyMod$graphics;
    
    @Intrinsic
    public int mobEffect$getDuration() {
        return this.h;
    }
    
    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.i;
    }
    
    @Override
    public String getTranslationKey() {
        final Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(((bwg)this.g.a()).g());
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.key();
        }
        return "labymod.unknown";
    }
    
    @Override
    public Icon getIcon() {
        final hkq sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite((ResourceLocation)sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final hkq sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return;
        }
        final Object poseStackObject = stack.getProvider().getPoseStack();
        if (poseStackObject == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            final fqq minecraft = fqq.Q();
            this.labyMod$graphics = new ftk(minecraft, minecraft.aR().d());
        }
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((fld)poseStackObject);
        this.labyMod$graphics.a((Function)gry::H, sprite, x, y, width, height, -1);
        this.labyMod$graphics.d();
    }
    
    @Override
    public boolean hasMaxDuration() {
        return false;
    }
    
    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.h == -1;
    }
    
    private hkq labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        final alr resourceLocation = mh.d.b((Object)this.g.a());
        if (resourceLocation == null) {
            return null;
        }
        return this.labyMod$iconSprite = fqq.Q().aG().a((jg)this.g);
    }
}
