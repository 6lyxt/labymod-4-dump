// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.property;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import java.util.Iterator;
import net.labymod.api.util.function.ChangeListener;
import net.labymod.api.util.time.TimeUtil;
import java.util.Objects;
import net.labymod.api.property.PropertyConvention;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.property.Property;

public class LssProperty<T> extends Property<T>
{
    private static final String PERCENTAGE_POST_PROCESSOR = "net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor";
    private static final FrameTimer FRAME_TIMER;
    private ProcessedObject processedValue;
    private int lastUpdatedFrame;
    @ApiStatus.Experimental
    private T lastLerpedAccessedValue;
    @ApiStatus.Experimental
    private T previousLerpedAccessedValue;
    @ApiStatus.Experimental
    private long millisChanged;
    
    public LssProperty(final T value) {
        super(value);
        this.lastUpdatedFrame = -1;
        this.millisChanged = -1L;
        this.previousLerpedAccessedValue = value;
    }
    
    public LssProperty(final T value, final PropertyConvention<T> propertyConvention) {
        super(value, propertyConvention);
        this.lastUpdatedFrame = -1;
        this.millisChanged = -1L;
        this.previousLerpedAccessedValue = value;
    }
    
    @Override
    public void set(final T value) {
        this.set(value, null);
    }
    
    public void set(T value, final ProcessedObject processedValue) {
        final boolean changed = !Objects.equals(value, this.value);
        if (changed) {
            if (this.propertyConvention != null) {
                value = this.applyConvention(value);
            }
            final T oldValue = this.value;
            if (this.lastLerpedAccessedValue == null) {
                this.millisChanged = 0L;
                this.previousLerpedAccessedValue = value;
            }
            else {
                this.millisChanged = TimeUtil.getMillis();
                this.previousLerpedAccessedValue = this.lastLerpedAccessedValue;
            }
            this.value = value;
            this.processedValue = processedValue;
            this.lastUpdatedFrame = LssProperty.FRAME_TIMER.getFrame();
            for (final ChangeListener<Property<T>, T> listener : this.changeListeners) {
                listener.changed(this, oldValue, value);
            }
        }
    }
    
    public ProcessedObject getProcessedValue() {
        return this.processedValue;
    }
    
    public PostProcessor getPostProcessor() {
        return (this.processedValue != null) ? this.processedValue.postProcessor() : null;
    }
    
    public boolean isPercentage() {
        final PostProcessor postProcessor = this.getPostProcessor();
        return postProcessor != null && postProcessor.getClass().getName().equals("net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor");
    }
    
    public boolean wasUpdatedThisFrame() {
        return this.lastUpdatedFrame == Laby.references().frameTimer().getFrame();
    }
    
    @ApiStatus.Experimental
    public float getProgress(final float partialTicks, final long duration) {
        if (this.millisChanged == -1L) {
            return 1.0f;
        }
        final long millis = TimeUtil.getMillis();
        final long millisSinceChanged = millis - this.millisChanged;
        if (millisSinceChanged >= duration) {
            return 1.0f;
        }
        return (millisSinceChanged + partialTicks) / duration;
    }
    
    @ApiStatus.Experimental
    public void setLastLerpedAccessedValue(final T lastLerpedAccessedValue) {
        this.lastLerpedAccessedValue = lastLerpedAccessedValue;
    }
    
    @ApiStatus.Experimental
    public T getPreviousLerpedAccessedValue() {
        return this.previousLerpedAccessedValue;
    }
    
    static {
        FRAME_TIMER = Laby.references().frameTimer();
    }
}
