// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.util.buffersource;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface BufferSourceGui
{
    void blit(final Object p0, final Object p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void blit(final Object p0, final Object p1, final int p2, final int p3, final int p4, final float p5, final float p6, final int p7, final int p8, final int p9, final int p10);
    
    void blit(final Object p0, final Object p1, final int p2, final int p3, final int p4, final int p5, final float p6, final float p7, final int p8, final int p9, final int p10, final int p11);
    
    void blit(final Object p0, final Object p1, final int p2, final int p3, final float p4, final float p5, final int p6, final int p7, final int p8, final int p9);
}
