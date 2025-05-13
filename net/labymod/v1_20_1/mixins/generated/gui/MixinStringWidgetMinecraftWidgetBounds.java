// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.generated.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.MinecraftWidgetBounds;

@Mixin({ eqk.class })
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds
{
    @Shadow
    private float a;
    
    @Override
    public int getBoundsX() {
        return super.getBoundsX() + Math.round(this.a * (super.getBoundsWidth() - enn.N().h.a((ta)this.l())));
    }
}
