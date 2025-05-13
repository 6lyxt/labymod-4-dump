// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import java.util.Locale;
import java.util.Iterator;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Laby;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;

public class Animation
{
    private final PropertyRegistry propertyRegistry;
    private final Widget widget;
    private final Map<String, AttributeAnimation<?>> attributeAnimations;
    private long animationStartMillis;
    
    public Animation(final Widget widget) {
        this.attributeAnimations = new ConcurrentHashMap<String, AttributeAnimation<?>>();
        this.propertyRegistry = Laby.references().propertyRegistry();
        this.widget = widget;
    }
    
    public void start() {
        this.animationStartMillis = 0L;
    }
    
    public void remove(final String attributeName) {
        final AttributeAnimation attributeAnimation = this.attributeAnimations.remove(attributeName);
        if (attributeAnimation != null) {
            final AttributeAnimation.Keyframe keyframe = attributeAnimation.getLast();
            if (keyframe != null) {
                attributeAnimation.getAccessor().set(keyframe.getValue());
            }
        }
    }
    
    public void apply(final float partialTicks) {
        if (this.attributeAnimations.isEmpty()) {
            return;
        }
        final long animationStartMillis = (long)(this.animationStartMillis + TimeUtil.convertDeltaToMilliseconds(partialTicks));
        this.animationStartMillis = animationStartMillis;
        final long progress = animationStartMillis;
        for (final AttributeAnimation attributeAnimation : this.attributeAnimations.values()) {
            final AttributeAnimation.Keyframe<?> keyframe = attributeAnimation.get(progress);
            final Object value = keyframe.getValue();
            attributeAnimation.getAccessor().set(value);
        }
    }
    
    public Map<String, AttributeAnimation<?>> getAttributeAnimations() {
        return this.attributeAnimations;
    }
    
    public void reset() {
        this.attributeAnimations.clear();
    }
    
    public <T> AttributeAnimation<T> getOrCreate(final String attributeName, final Class<T> attributeType) {
        AttributeAnimation<?> attributeAnimation = this.attributeAnimations.get(attributeName);
        if (attributeAnimation != null) {
            final Class<?> type = attributeAnimation.getAccessor().getType();
            if (!type.equals(attributeType)) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Invalid type supplied (Accessor: %s, Attribute: %s)", type.getName(), attributeType.getName()));
            }
            return (AttributeAnimation<T>)attributeAnimation;
        }
        else {
            final PropertyValueAccessor<?, ?, ?> accessor = this.propertyRegistry.getValueAccessor(this.widget, attributeName);
            if (accessor == null) {
                throw new IllegalArgumentException("Invalid attribute supplied");
            }
            attributeAnimation = new AttributeAnimation<Object>(new PropertyValueAccessorAttributeAnimationAccessor<Object>(this.widget, accessor, attributeName));
            this.attributeAnimations.put(attributeName, attributeAnimation);
            return (AttributeAnimation<T>)attributeAnimation;
        }
    }
    
    public <T> AttributeAnimation<T> createCustom(final String attributeName, final AttributeAnimationAccessor<T> field) {
        final AttributeAnimation<T> attributeAnimation = new AttributeAnimation<T>(field);
        this.attributeAnimations.put(attributeName, attributeAnimation);
        return attributeAnimation;
    }
}
