// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

import java.lang.reflect.Type;
import net.labymod.api.util.PrimitiveHelper;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;

public class PropertyValueAccessorAttributeAnimationAccessor<T> implements AttributeAnimationAccessor<T>
{
    private final Widget widget;
    private final PropertyValueAccessor accessor;
    private final String attributeName;
    
    public PropertyValueAccessorAttributeAnimationAccessor(final Widget widget, final PropertyValueAccessor accessor, final String attributeName) {
        this.widget = widget;
        this.accessor = accessor;
        this.attributeName = attributeName;
    }
    
    @Override
    public T get() {
        return this.accessor.get(this.widget);
    }
    
    @Override
    public void set(final T value) {
        this.accessor.set(this.widget, value);
    }
    
    @Override
    public String getName() {
        return this.attributeName;
    }
    
    @Override
    public Class<T> getType() {
        return this.accessor.type();
    }
    
    @Override
    public boolean canInterpolate() {
        return this.get() instanceof Number;
    }
    
    @Override
    public T getInterpolatedValue(final AttributeAnimation.Keyframe<T> from, final AttributeAnimation.Keyframe<T> to, final CubicBezier interpolator, final long timestamp) {
        return (T)PrimitiveHelper.convertToTarget(interpolator.interpolate(((Number)from.getValue()).doubleValue(), ((Number)to.getValue()).doubleValue(), from.getTimestamp(), to.getTimestamp(), timestamp), this.getType());
    }
}
