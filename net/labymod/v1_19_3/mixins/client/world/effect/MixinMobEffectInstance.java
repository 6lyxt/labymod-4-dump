// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.world.effect;

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

@Mixin({ bdk.class })
@Implements({ @Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE) })
public abstract class MixinMobEffectInstance implements PotionEffect
{
    @Shadow
    private int c;
    @Shadow
    private int d;
    @Shadow
    @Final
    private bdi b;
    private fol labyMod$iconSprite;
    
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
        final Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.b.e());
        if (component instanceof final TranslatableComponent translatableComponent) {
            return translatableComponent.key();
        }
        return "labymod.unknown";
    }
    
    @Override
    public Icon getIcon() {
        final fol sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        final acf atlas = sprite.i();
        return Icon.sprite((ResourceLocation)atlas, sprite.a(), sprite.b(), 18, 18, 256, 128);
    }
    
    @Override
    public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
        final fol sprite = this.labyMod$cacheIcon();
        if (sprite == null) {
            return;
        }
        final Object poseStackObject = stack.getProvider().getPoseStack();
        if (poseStackObject == null) {
            return;
        }
        RenderSystem.setShaderTexture(0, sprite.i());
        eko.a((eed)poseStackObject, x, y, 0, width, height, sprite);
    }
    
    @Override
    public boolean hasMaxDuration() {
        return false;
    }
    
    private fol labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        final acf resourceLocation = iw.e.b((Object)this.b);
        if (resourceLocation == null) {
            return null;
        }
        return this.labyMod$iconSprite = ejf.N().aF().a(this.b);
    }
}
