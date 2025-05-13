// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ ekw.class })
public class MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    public int a;
    @Shadow
    public int b;
    @Shadow
    public int k;
    @Shadow
    public int l;
    
    @Override
    public int getBoundsX() {
        return this.a;
    }
    
    @Override
    public void setBoundsX(final int x) {
        this.a = x;
    }
    
    @Override
    public int getBoundsY() {
        return this.b;
    }
    
    @Override
    public void setBoundsY(final int y) {
        this.b = y;
    }
    
    @Override
    public int getBoundsWidth() {
        return this.k;
    }
    
    @Override
    public void setBoundsWidth(final int width) {
        this.k = width;
    }
    
    @Override
    public int getBoundsHeight() {
        return this.l;
    }
    
    @Override
    public void setBoundsHeight(final int height) {
        this.l = height;
    }
}
