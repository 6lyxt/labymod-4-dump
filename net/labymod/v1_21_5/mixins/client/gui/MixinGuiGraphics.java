// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.gui;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.gui.GuiGraphicsAccessor;

@Mixin({ ftk.class })
public class MixinGuiGraphics implements GuiGraphicsAccessor
{
    @Mutable
    @Shadow
    @Final
    private fld e;
    
    @Override
    public void setPose(final fld pose) {
        this.e = pose;
    }
}
