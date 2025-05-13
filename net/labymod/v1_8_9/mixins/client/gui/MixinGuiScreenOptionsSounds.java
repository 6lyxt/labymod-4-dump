// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.gui.GuiScreenOptionsSoundsAccessor;

@Mixin({ axz.class })
public abstract class MixinGuiScreenOptionsSounds implements GuiScreenOptionsSoundsAccessor
{
    @Shadow
    protected abstract String a(final bpg p0);
    
    @Override
    public String labymod$getSoundVolume(final bpg category) {
        return this.a(category);
    }
}
