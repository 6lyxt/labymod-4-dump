// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

public interface Interpolatable<I extends Interpolatable<I>>
{
    I interpolate(final CubicBezier p0, final I p1, final long p2, final long p3, final long p4);
}
