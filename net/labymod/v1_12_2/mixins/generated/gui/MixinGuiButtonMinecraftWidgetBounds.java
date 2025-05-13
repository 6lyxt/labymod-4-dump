// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ bja.class })
public class MixinGuiButtonMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    public int h;
    @Shadow
    public int i;
    @Shadow
    public int f;
    @Shadow
    public int g;
    
    @Override
    public int getBoundsX() {
        return this.h;
    }
    
    @Override
    public void setBoundsX(final int x) {
        this.h = x;
    }
    
    @Override
    public int getBoundsY() {
        return this.i;
    }
    
    @Override
    public void setBoundsY(final int y) {
        this.i = y;
    }
    
    @Override
    public int getBoundsWidth() {
        return this.f;
    }
    
    @Override
    public void setBoundsWidth(final int width) {
        this.f = width;
    }
    
    @Override
    public int getBoundsHeight() {
        return this.g;
    }
    
    @Override
    public void setBoundsHeight(final int height) {
        this.g = height;
    }
}
