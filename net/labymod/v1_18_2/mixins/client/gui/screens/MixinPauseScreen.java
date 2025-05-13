// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;

@Mixin({ edr.class })
public class MixinPauseScreen implements PauseScreenAccessor
{
    @Shadow
    @Final
    private boolean n;
    
    @Override
    public boolean showPauseMenu() {
        return this.n;
    }
}
