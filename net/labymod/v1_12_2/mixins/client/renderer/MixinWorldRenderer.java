// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import java.nio.ByteBuffer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.renderer.WorldRendererAccessor;

@Mixin({ buk.class })
public abstract class MixinWorldRenderer implements WorldRendererAccessor
{
    @Shadow
    private boolean o;
    @Shadow
    private ByteBuffer b;
    @Shadow
    private cea n;
    @Shadow
    private int f;
    @Shadow
    private int h;
    
    @Shadow
    protected abstract void k();
    
    @Override
    public ByteBuffer getBuffer() {
        return this.b;
    }
    
    @Override
    public int getNextElementPosition() {
        return this.f * this.n.g() + this.n.d(this.h);
    }
    
    @Override
    public boolean building() {
        return this.o;
    }
    
    @Override
    public void updateVertexFormatIndex() {
        this.k();
    }
}
