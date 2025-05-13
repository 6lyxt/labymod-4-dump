// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ epf.class })
public abstract class MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    public int c;
    @Shadow
    public int d;
    @Shadow
    public int o;
    @Shadow
    public int p;
    
    @Shadow
    public abstract sw l();
    
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
        return this.o;
    }
    
    @Override
    public void setBoundsWidth(final int width) {
        this.o = width;
    }
    
    @Override
    public int getBoundsHeight() {
        return this.p;
    }
    
    @Override
    public void setBoundsHeight(final int height) {
        this.p = height;
    }
}
