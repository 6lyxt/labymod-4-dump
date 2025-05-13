// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class AttributeAnimation<T>
{
    private final AttributeAnimationAccessor<T> accessor;
    private final Keyframe<T> defaultKeyframe;
    private final List<Keyframe<T>> keyframes;
    private Runnable finishHandler;
    
    public AttributeAnimation(final AttributeAnimationAccessor<T> accessor) {
        this.accessor = accessor;
        this.defaultKeyframe = new Keyframe<T>(accessor.get(), 0L, CubicBezier.EASE_IN_OUT);
        this.keyframes = new ArrayList<Keyframe<T>>();
    }
    
    public Keyframe<T> get(final long timestamp) {
        final Keyframe<T> keyframe = this.getKeyframe(timestamp);
        if (keyframe != null) {
            return keyframe;
        }
        Keyframe<T> from = this.defaultKeyframe;
        for (final Keyframe<T> current : this.keyframes) {
            if (current.getTimestamp() >= timestamp) {
                return this.computeResult(from, current, timestamp);
            }
            from = current;
        }
        if (this.finishHandler != null && this.isLast(from)) {
            this.finishHandler.run();
            this.finishHandler = null;
        }
        return from;
    }
    
    public Keyframe<T> getKeyframe(final long timestamp) {
        for (final Keyframe<T> keyframe : this.keyframes) {
            if (keyframe.getTimestamp() == timestamp) {
                return keyframe;
            }
        }
        return null;
    }
    
    public Keyframe<T> getLast() {
        if (this.keyframes.isEmpty()) {
            return null;
        }
        return this.keyframes.get(this.keyframes.size() - 1);
    }
    
    public boolean isLast(final Keyframe<T> keyframe) {
        return this.getLast() == keyframe;
    }
    
    private Keyframe<T> computeResult(final Keyframe<T> from, final Keyframe<T> to, final long timestamp) {
        if (!this.accessor.canInterpolate() || to.getInterpolator() == null) {
            return from;
        }
        if (from.getValue() == to.getValue() || timestamp > to.getTimestamp()) {
            return to;
        }
        return new Keyframe<T>(this.accessor.getInterpolatedValue(from, to, to.getInterpolator(), timestamp), timestamp, to.getInterpolator());
    }
    
    public Keyframe<T> first() {
        return new Keyframe<T>(this.keyframes.isEmpty() ? this.defaultKeyframe : this.keyframes.get(0));
    }
    
    public Keyframe<T> last() {
        return new Keyframe<T>(this.keyframes.isEmpty() ? this.defaultKeyframe : this.keyframes.get(this.keyframes.size() - 1));
    }
    
    public AttributeAnimation<T> addKeyframe(final T value, final long timestamp) {
        return this.addKeyframe(value, timestamp, CubicBezier.EASE_IN_OUT);
    }
    
    public AttributeAnimation<T> addKeyframe(final T value, final long timestamp, final CubicBezier interpolator) {
        return this.addKeyframe(new Keyframe<T>(value, timestamp, interpolator));
    }
    
    public AttributeAnimation<T> addKeyframe(final Keyframe<T> keyframe) {
        this.keyframes.add(keyframe);
        return this;
    }
    
    public float getLength() {
        return this.keyframes.isEmpty() ? 0.0f : ((float)this.keyframes.get(this.keyframes.size() - 1).getTimestamp());
    }
    
    public AttributeAnimationAccessor<T> getAccessor() {
        return this.accessor;
    }
    
    public void setFinishHandler(final Runnable finishHandler) {
        this.finishHandler = finishHandler;
    }
    
    public static class Keyframe<T>
    {
        private final T value;
        private final long timestamp;
        private final CubicBezier interpolator;
        
        public Keyframe(final T value, final long timestamp, final CubicBezier interpolator) {
            this.value = value;
            this.timestamp = timestamp;
            this.interpolator = interpolator;
        }
        
        public Keyframe(final Keyframe<T> keyframe) {
            this(keyframe.getValue(), keyframe.getTimestamp(), keyframe.getInterpolator());
        }
        
        public T getValue() {
            return this.value;
        }
        
        public long getTimestamp() {
            return this.timestamp;
        }
        
        public CubicBezier getInterpolator() {
            return this.interpolator;
        }
    }
}
