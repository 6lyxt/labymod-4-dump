// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.animation;

import java.lang.reflect.Type;
import net.labymod.api.util.PrimitiveHelper;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;

public class AttributePatchAnimationAccessor implements AttributeAnimationAccessor<AttributePatch>
{
    private final String name;
    private final Widget widget;
    private final AttributePatch firstPatch;
    private AttributePatch currentPatch;
    
    public AttributePatchAnimationAccessor(final String name, final Widget widget, final AttributePatch firstPatch) {
        this.name = name;
        this.widget = widget;
        this.firstPatch = firstPatch;
        this.currentPatch = this.firstPatch;
    }
    
    @Override
    public AttributePatch get() {
        return this.currentPatch;
    }
    
    @Override
    public void set(final AttributePatch value) {
        (this.currentPatch = value).patch(this.widget);
        this.widget.handleAttributes();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Class<AttributePatch> getType() {
        return AttributePatch.class;
    }
    
    @Override
    public boolean canInterpolate() {
        final AttributePatch patch = this.get();
        if (!(patch instanceof PropertyAttributePatch)) {
            return false;
        }
        final ProcessedObject object = ((PropertyAttributePatch)patch).objects()[0];
        final Object value = object.value();
        return value instanceof Number || value instanceof Interpolatable;
    }
    
    @Override
    public AttributePatch getInterpolatedValue(final AttributeAnimation.Keyframe<AttributePatch> from, final AttributeAnimation.Keyframe<AttributePatch> to, final CubicBezier interpolator, final long timestamp) {
        if (!(from.getValue() instanceof PropertyAttributePatch) || !(to.getValue() instanceof PropertyAttributePatch)) {
            return to.getValue();
        }
        final PropertyAttributePatch fromValue = from.getValue();
        final PropertyAttributePatch toValue = to.getValue();
        final int size = Math.min(fromValue.objects().length, toValue.objects().length);
        final ProcessedObject[] toProcessedObjects = new ProcessedObject[size];
        for (int i = 0; i < size; ++i) {
            final ProcessedObject fromValueProcessedObject = fromValue.objects()[0];
            final Object fromValueObject = fromValueProcessedObject.value();
            if (fromValueObject instanceof final Number n) {
                final Number interpolated = interpolator.interpolate(n.doubleValue(), ((Number)toValue.objects()[0].value()).doubleValue(), from.getTimestamp(), to.getTimestamp(), timestamp);
                toProcessedObjects[i] = new ProcessedObject(fromValueProcessedObject.postProcessor(), String.valueOf(interpolated), PrimitiveHelper.convertToTarget(interpolated, fromValueObject.getClass()));
            }
            else {
                final ProcessedObject toProcessedObject = toValue.objects()[0];
                toProcessedObjects[i] = new ProcessedObject(fromValueProcessedObject.postProcessor(), toProcessedObject.rawValue(), ((Interpolatable)toProcessedObject.value()).interpolate(interpolator, (Object)fromValueObject, from.getTimestamp(), to.getTimestamp(), timestamp));
            }
        }
        try {
            return new PropertyAttributePatch(fromValue.forwarder(), fromValue.getType(), fromValue.instruction(), fromValue.element(), () -> toProcessedObjects);
        }
        catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
