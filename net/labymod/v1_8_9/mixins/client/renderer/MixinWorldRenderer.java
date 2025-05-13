// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import java.nio.ByteBuffer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.renderer.WorldRendererAccessor;

@Mixin({ bfd.class })
public abstract class MixinWorldRenderer implements WorldRendererAccessor
{
    @Shadow
    private boolean n;
    @Shadow
    private ByteBuffer a;
    @Shadow
    private bmu m;
    @Shadow
    private int e;
    @Shadow
    private int g;
    
    @Shadow
    protected abstract void k();
    
    @Override
    public ByteBuffer getBuffer() {
        return this.a;
    }
    
    @Override
    public int getNextElementPosition() {
        return this.e * this.m.g() + this.m.d(this.g);
    }
    
    @Override
    public boolean building() {
        return this.n;
    }
    
    @Override
    public void updateVertexFormatIndex() {
        this.k();
    }
}
