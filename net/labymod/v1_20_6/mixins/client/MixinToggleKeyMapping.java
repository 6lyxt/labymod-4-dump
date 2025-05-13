// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.BooleanSupplier;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.ToggleInputMapping;

@Mixin({ ffu.class })
public abstract class MixinToggleKeyMapping extends MixinKeyMapping implements ToggleInputMapping
{
    @Shadow
    @Final
    private BooleanSupplier h;
    
    @Override
    public boolean needsToggle() {
        return this.h.getAsBoolean();
    }
    
    @Override
    public void forcePress() {
        this.updateDown(true);
    }
    
    @Override
    public void forceUnpress() {
        this.resetClickCount();
        this.updateDown(false);
    }
    
    @Override
    public boolean toggle() {
        if (!this.needsToggle()) {
            return false;
        }
        this.updateDown(!this.input$isDown());
        return true;
    }
}
