// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.client.gui.GuiGraphicsAccessor;

@Mixin({ esf.class })
public class MixinGuiGraphics implements GuiGraphicsAccessor
{
    @Mutable
    @Shadow
    @Final
    private elp e;
    
    @Override
    public void setPose(final elp pose) {
        this.e = pose;
    }
}
