// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

public interface AttributeAnimationAccessor<T>
{
    T get();
    
    void set(final T p0);
    
    String getName();
    
    Class<T> getType();
    
    boolean canInterpolate();
    
    T getInterpolatedValue(final AttributeAnimation.Keyframe<T> p0, final AttributeAnimation.Keyframe<T> p1, final CubicBezier p2, final long p3);
}
