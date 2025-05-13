// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.mojang.blaze3d.pipeline;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;

@Mixin({ fio.class })
public abstract class MixinRenderType extends fin implements Blaze3DRenderType
{
    public MixinRenderType(final String $$0, final Runnable $$1, final Runnable $$2) {
        super($$0, $$1, $$2);
    }
    
    @Shadow
    public abstract void a(final egz p0, final int p1, final int p2, final int p3);
    
    public void apply() {
        this.a();
    }
    
    public void clear() {
        this.b();
    }
    
    public void draw(final BufferConsumer consumer, final int cameraX, final int cameraY, final int cameraZ) {
        if (consumer instanceof final egz bufferBuilder) {
            this.a(bufferBuilder, cameraX, cameraY, cameraZ);
            return;
        }
        throw new IllegalStateException("Invalid buffer: " + String.valueOf(consumer));
    }
}
