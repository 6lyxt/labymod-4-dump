// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.world.effect;

import com.mojang.blaze3d.systems.RenderSystem;
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

@Mixin({ axe.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinMobEffectInstance implements PotionEffect
{
    @Shadow
    private int c;
    @Shadow
    private int d;
    @Shadow
    @Final
    private axc b;
    private fay labyMod$iconSprite;
    
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
        final Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.b.d());
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.key();
        }
        return "labymod.unknown";
    }
    
    @Override
    public Icon getIcon() {
        final fay sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        final yt location = sprite.m().g();
        return Icon.sprite((ResourceLocation)location, sprite.d(), sprite.e(), 18, 18, 256, 128);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final fay sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return;
        }
        final Object poseStackObject = stack.getProvider().getPoseStack();
        if (poseStackObject == null) {
            return;
        }
        RenderSystem.setShaderTexture(0, sprite.m().g());
        dzr.a((dtm)poseStackObject, x, y, 0, width, height, sprite);
    }
    
    @Override
    public boolean hasMaxDuration() {
        return this.h();
    }
    
    private fay labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        final yt resourceLocation = hb.T.b((Object)this.b);
        if (resourceLocation == null) {
            return null;
        }
        return this.labyMod$iconSprite = dyr.D().au().a(this.b);
    }
}
