// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

@Pseudo
@Mixin(targets = { "net.optifine.render.VertexBuilderDummy" })
@Implements({ @Interface(iface = BufferConsumer.class, remap = Interface.Remap.NONE, prefix = "bufferConsumer$") })
public abstract class MixinOptiFineVertexBuilderDummy implements BufferConsumer
{
    @Intrinsic
    public void bufferConsumer$vertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final float red, final float green, final float blue, final float alpha) {
        return this;
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$color(final int red, final int green, final int blue, final int alpha) {
        return super.color(red, green, blue, alpha);
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$uv(final float u, final float v) {
        return super.uv(u, v);
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$normal(final float x, final float y, final float z) {
        return super.normal(x, y, z);
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$packedOverlay(final int packedOverlay) {
        return super.packedOverlay(packedOverlay);
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$light(final int packedLight) {
        return super.light(packedLight);
    }
    
    @Intrinsic
    public BufferConsumer bufferConsumer$endVertex() {
        return this;
    }
}
