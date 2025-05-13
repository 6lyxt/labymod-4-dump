// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ eac.class })
public class MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    public int l;
    @Shadow
    public int m;
    @Shadow
    public int j;
    @Shadow
    public int k;
    
    @Override
    public int getBoundsX() {
        return this.l;
    }
    
    @Override
    public void setBoundsX(final int x) {
        this.l = x;
    }
    
    @Override
    public int getBoundsY() {
        return this.m;
    }
    
    @Override
    public void setBoundsY(final int y) {
        this.m = y;
    }
    
    @Override
    public int getBoundsWidth() {
        return this.j;
    }
    
    @Override
    public void setBoundsWidth(final int width) {
        this.j = width;
    }
    
    @Override
    public int getBoundsHeight() {
        return this.k;
    }
    
    @Override
    public void setBoundsHeight(final int height) {
        this.k = height;
    }
}
