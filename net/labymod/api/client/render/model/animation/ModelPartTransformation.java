// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.Comparator;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.MathHelper;
import java.util.ArrayList;
import net.labymod.api.metadata.Metadata;
import java.util.List;
import net.labymod.api.metadata.MetadataExtension;

public class ModelPartTransformation implements MetadataExtension
{
    private final TransformationType type;
    private final Keyframe defaultKeyframe;
    private final List<Keyframe> keyframes;
    private long length;
    private Keyframe first;
    private Keyframe last;
    private Metadata metadata;
    
    public ModelPartTransformation(final TransformationType type, final float defaultX, final float defaultY, final float defaultZ) {
        this.type = type;
        this.defaultKeyframe = new Keyframe(0L, false, defaultX, defaultY, defaultZ);
        this.keyframes = new ArrayList<Keyframe>();
        this.length = 0L;
        this.first = this.defaultKeyframe;
        this.last = this.defaultKeyframe;
        this.metadata = Metadata.create();
    }
    
    private static float calculateTransformation(final float before, final float from, final float to, final float after, final float progress, final float duration, final boolean smooth) {
        if (from == to || duration == 0.0f || progress > duration) {
            return to;
        }
        if (smooth) {
            final float progressPercentage = progress / duration;
            return MathHelper.catmullrom(progressPercentage, before, from, to, after);
        }
        return from - (from - to) / duration * progress;
    }
    
    public FloatVector3 get(final long timestamp) {
        Keyframe previous = this.defaultKeyframe;
        for (int index = 0; index < this.keyframes.size(); ++index) {
            final Keyframe current = this.keyframes.get(index);
            if (current.getTimestamp() == timestamp) {
                return current;
            }
            if (current.getTimestamp() >= timestamp) {
                final boolean smooth = current.isSmooth();
                final long progress = timestamp - previous.getTimestamp();
                final long duration = current.getTimestamp() - previous.getTimestamp();
                final FloatVector3 before = this.keyframes.get(Math.max(index - 2, 0));
                final FloatVector3 after = this.keyframes.get(Math.min(index + 1, this.keyframes.size() - 1));
                return new FloatVector3(calculateTransformation(before.getX(), previous.getX(), current.getX(), after.getX(), (float)progress, (float)duration, smooth), calculateTransformation(before.getY(), previous.getY(), current.getY(), after.getY(), (float)progress, (float)duration, smooth), calculateTransformation(before.getZ(), previous.getZ(), current.getZ(), after.getZ(), (float)progress, (float)duration, smooth));
            }
            previous = current;
        }
        return previous;
    }
    
    public boolean hasKeyframe(final long timestamp) {
        return !this.keyframes.isEmpty() && timestamp >= 0L && timestamp <= this.getLength();
    }
    
    public TransformationType type() {
        return this.type;
    }
    
    public Keyframe first() {
        return this.first;
    }
    
    public Keyframe last() {
        return this.last;
    }
    
    public Keyframe previous(final Keyframe keyframe) {
        final int index = this.keyframes.indexOf(keyframe) - 1;
        return (index < 0) ? this.defaultKeyframe : this.keyframes.get(index);
    }
    
    public void addKeyframe(final Keyframe keyframe) {
        this.keyframes.add(keyframe);
        this.keyframes.sort(Comparator.comparingLong(Keyframe::getTimestamp));
        this.first = this.keyframes.get(0);
        this.last = this.keyframes.get(this.keyframes.size() - 1);
        this.length = this.last.getTimestamp();
    }
    
    public void filterKeyframes(final Predicate<Keyframe> predicate) {
        this.keyframes.removeIf(keyframe -> !predicate.test(keyframe));
        this.first = (this.keyframes.isEmpty() ? this.defaultKeyframe : this.keyframes.get(0));
        this.last = (this.keyframes.isEmpty() ? this.defaultKeyframe : this.keyframes.get(this.keyframes.size() - 1));
        this.length = this.last.getTimestamp();
    }
    
    public long getLength() {
        return this.length;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    public ModelPartTransformation copy() {
        final ModelPartTransformation copy = new ModelPartTransformation(this.type, this.defaultKeyframe.getX(), this.defaultKeyframe.getY(), this.defaultKeyframe.getZ());
        for (final Keyframe keyframe : this.keyframes) {
            copy.addKeyframe(new Keyframe(keyframe.getTimestamp(), keyframe.isSmooth(), keyframe));
        }
        return copy;
    }
    
    public static class Keyframe extends FloatVector3
    {
        private final long timestamp;
        private final boolean smooth;
        
        public Keyframe(final long timestamp, final boolean smooth, final float x, final float y, final float z) {
            super(x, y, z);
            this.timestamp = timestamp;
            this.smooth = smooth;
        }
        
        public Keyframe(final long timestamp, final boolean smooth, final FloatVector3 transformation) {
            super(transformation);
            this.timestamp = timestamp;
            this.smooth = smooth;
        }
        
        public Keyframe(final Keyframe keyframe) {
            this(keyframe.getTimestamp(), keyframe.isSmooth(), keyframe);
        }
        
        public long getTimestamp() {
            return this.timestamp;
        }
        
        public boolean isSmooth() {
            return this.smooth;
        }
    }
}
