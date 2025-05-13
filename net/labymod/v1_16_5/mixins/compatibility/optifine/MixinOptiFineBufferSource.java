// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Final;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.v1_16_5.client.gfx.buffer.BufferSourceAccessor;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ eag.a.class })
public abstract class MixinOptiFineBufferSource implements BufferSourceAccessor
{
    @Shadow
    protected eao lastState;
    @Shadow
    @Final
    protected Map<eao, dfh> b;
    
    @Shadow
    public abstract void a(final eao p0);
    
    @Override
    public void endLastBatch() {
        if (this.lastState == null) {
            return;
        }
        final eao renderType = this.lastState;
        if (!this.b.containsKey(renderType)) {
            this.a(renderType);
        }
        this.lastState = null;
    }
}
