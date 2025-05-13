// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.Final;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Optional;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.gfx.buffer.BufferSourceAccessor;

@Mixin({ eag.a.class })
public abstract class MixinBufferSource implements BufferSourceAccessor
{
    @Shadow
    protected Optional<eao> c;
    @Shadow
    @Final
    protected Map<eao, dfh> b;
    
    @Shadow
    public abstract void a(final eao p0);
    
    @Override
    public void endLastBatch() {
        if (!this.c.isPresent()) {
            return;
        }
        final eao renderType = this.c.get();
        if (!this.b.containsKey(renderType)) {
            this.a(renderType);
        }
        this.c = Optional.empty();
    }
}
