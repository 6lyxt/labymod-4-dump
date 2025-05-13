// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ enz.class })
public abstract class MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    public int c;
    @Shadow
    public int d;
    @Shadow
    public int s;
    @Shadow
    public int t;
    
    @Shadow
    public abstract tj k();
    
    @Override
    public int getBoundsX() {
        return this.c;
    }
    
    @Override
    public void setBoundsX(final int x) {
        this.c = x;
    }
    
    @Override
    public int getBoundsY() {
        return this.d;
    }
    
    @Override
    public void setBoundsY(final int y) {
        this.d = y;
    }
    
    @Override
    public int getBoundsWidth() {
        return this.s;
    }
    
    @Override
    public void setBoundsWidth(final int width) {
        this.s = width;
    }
    
    @Override
    public int getBoundsHeight() {
        return this.t;
    }
    
    @Override
    public void setBoundsHeight(final int height) {
        this.t = height;
    }
}
