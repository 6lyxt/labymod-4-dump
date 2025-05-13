// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.generated.gui;

import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ avw.class })
public class MixinGuiTextFieldMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    public int a;
    @Shadow
    public int f;
    @Shadow
    @Final
    @Mutable
    public int i;
    @Shadow
    @Final
    @Mutable
    public int j;
    
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
        return this.f;
    }
    
    @Override
    public void setBoundsY(final int y) {
        this.f = y;
    }
    
    @Override
    public int getBoundsWidth() {
        return this.i;
    }
    
    @Override
    public void setBoundsWidth(final int width) {
        this.i = width;
    }
    
    @Override
    public int getBoundsHeight() {
        return this.j;
    }
    
    @Override
    public void setBoundsHeight(final int height) {
        this.j = height;
    }
}
