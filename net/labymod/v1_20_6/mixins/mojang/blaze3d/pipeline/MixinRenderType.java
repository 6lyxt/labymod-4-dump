// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.mojang.blaze3d.pipeline;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;

@Mixin({ gdy.class })
public abstract class MixinRenderType extends gdx implements Blaze3DRenderType
{
    @Shadow
    public abstract void a(final ezv p0, final fai p1);
    
    public MixinRenderType(final String $$0, final Runnable $$1, final Runnable $$2) {
        super($$0, $$1, $$2);
    }
    
    public void apply() {
        this.a();
    }
    
    public void clear() {
        this.b();
    }
    
    public void draw(final BufferConsumer consumer, final int cameraX, final int cameraY, final int cameraZ) {
        if (consumer instanceof final ezv bufferBuilder) {
            this.a(bufferBuilder, fai.a((float)cameraX, (float)cameraY, (float)cameraZ));
            return;
        }
        throw new IllegalStateException("Invalid buffer: " + String.valueOf(consumer));
    }
}
