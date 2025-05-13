// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.gui.GuiScreenOptionsSoundsAccessor;

@Mixin({ blo.class })
public abstract class MixinGuiScreenOptionsSounds implements GuiScreenOptionsSoundsAccessor
{
    @Shadow
    protected abstract String a(final qg p0);
    
    @Override
    public String labymod$getSoundVolume(final qg category) {
        return this.a(category);
    }
}
