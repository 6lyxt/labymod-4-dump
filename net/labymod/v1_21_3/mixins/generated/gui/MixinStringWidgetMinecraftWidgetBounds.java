// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ fpn.class })
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    private float a;
    
    @Override
    public int getBoundsX() {
        return super.getBoundsX() + Math.round(this.a * (super.getBoundsWidth() - fmg.Q().h.a((ya)this.z())));
    }
}
