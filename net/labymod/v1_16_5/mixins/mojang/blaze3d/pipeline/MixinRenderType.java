// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.mojang.blaze3d.pipeline;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;

@Mixin({ eao.class })
public abstract class MixinRenderType extends ean implements Blaze3DRenderType
{
    public MixinRenderType(final String $$0, final Runnable $$1, final Runnable $$2) {
        super($$0, $$1, $$2);
    }
    
    @Shadow
    public abstract void a(final dfh p0, final int p1, final int p2, final int p3);
    
    public void apply() {
        this.a();
    }
    
    public void clear() {
        this.b();
    }
    
    public void draw(final BufferConsumer consumer, final int cameraX, final int cameraY, final int cameraZ) {
        if (consumer instanceof final dfh dfh) {
            this.a(dfh, cameraX, cameraY, cameraZ);
            return;
        }
        throw new IllegalStateException("Invalid buffer: " + String.valueOf(consumer));
    }
}
