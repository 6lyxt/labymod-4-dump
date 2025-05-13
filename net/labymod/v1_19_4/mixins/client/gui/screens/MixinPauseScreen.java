// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;

@Mixin({ esy.class })
public class MixinPauseScreen implements PauseScreenAccessor
{
    @Shadow
    @Final
    private boolean D;
    
    @Override
    public boolean showPauseMenu() {
        return this.D;
    }
}
