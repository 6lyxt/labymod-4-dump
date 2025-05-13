// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.property.Property;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class WheelWidget extends AbstractWidget<AbstractWidget<?>>
{
    private static final ModifyReason CENTER_SEGMENT;
    private final LssProperty<Boolean> selectable;
    private final LssProperty<Float> innerRadius;
    private final LssProperty<Float> segmentDistanceDegrees;
    private final LssProperty<Integer> segmentBackgroundColor;
    private final LssProperty<Integer> segmentHighlightColor;
    private final LssProperty<Integer> segmentSelectedColor;
    private final LssProperty<Integer> segmentBorderColor;
    private final LssProperty<Integer> innerBackgroundColor;
    private final LssProperty<Integer> innerBorderColor;
    
    public WheelWidget() {
        this.selectable = new LssProperty<Boolean>(true);
        this.innerRadius = new LssProperty<Float>(0.0f);
        this.segmentDistanceDegrees = new LssProperty<Object>(2.0f).addChangeListener((type, oldValue, newValue) -> this.calculateAngles());
        this.segmentBackgroundColor = new LssProperty<Integer>(-2145773030);
        this.segmentHighlightColor = new LssProperty<Integer>(-1702854528);
        this.segmentSelectedColor = new LssProperty<Integer>(-1702854528);
        this.segmentBorderColor = new LssProperty<Integer>(Integer.MIN_VALUE);
        this.innerBackgroundColor = new LssProperty<Integer>(-2142417587);
        this.innerBorderColor = new LssProperty<Integer>(-432786380);
    }
    
    @Override
    public void applyStyleSheet(final StyleSheet styleSheet) {
        super.applyStyleSheet(styleSheet);
        this.calculateAngles();
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        final Bounds bounds = this.bounds();
        final float centerX = bounds.getCenterX();
        final float centerY = bounds.getCenterY();
        final float radius = bounds.getWidth(BoundsType.OUTER) / 2.0f;
        final float centerDistanceSquared = MathHelper.square((float)mouse.getXDouble() - centerX) + MathHelper.square((float)mouse.getYDouble() - centerY);
        final boolean segmentsHovered = this.selectable.get() && centerDistanceSquared >= MathHelper.square(this.innerRadius.get()) && centerDistanceSquared <= MathHelper.square(radius);
        final double angle = MathHelper.wrapRadians((float)Math.atan2(mouse.getXDouble() - centerX, mouse.getYDouble() - centerY));
        Segment hoveredSegment = null;
        for (final AbstractWidget<?> child : this.children) {
            if (!(child instanceof Segment)) {
                continue;
            }
            final Segment segment = (Segment)child;
            if (hoveredSegment == null && segmentsHovered && MathHelper.isAngleBetween(angle, MathHelper.wrapRadians(segment.getStartingAngle()), MathHelper.wrapRadians(segment.getEndingAngle()), 6.283185307179586)) {
                hoveredSegment = segment;
            }
            else {
                segment.setSegmentSelected(false);
            }
        }
        for (final AbstractWidget<?> child : super.children) {
            if (!(child instanceof Segment)) {
                continue;
            }
            final Segment segment = (Segment)child;
            final float startingAngle = segment.getStartingAngle();
            final float endingAngle = segment.getEndingAngle();
            if (segment == hoveredSegment) {
                segment.setSegmentSelected(true);
            }
            final float middleAngle = (startingAngle + endingAngle) / 2.0f;
            final float middleRadius = (this.innerRadius.get() + radius) / 2.0f;
            final float middleX = (float)(centerX + Math.sin(middleAngle) * middleRadius);
            final float middleY = (float)(centerY + Math.cos(middleAngle) * middleRadius);
            segment.bounds().setOuterPosition(middleX - segment.bounds().getWidth(BoundsType.OUTER) / 2.0f, middleY - segment.bounds().getHeight(BoundsType.OUTER) / 2.0f, WheelWidget.CENTER_SEGMENT);
        }
        super.renderWidget(context);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return this.getContentDiameter() + this.bounds().getHorizontalOffset(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return this.getContentDiameter() + this.bounds().getVerticalOffset(type);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        for (final AbstractWidget<?> child : this.children) {
            if (!(child instanceof Segment)) {
                continue;
            }
            final Segment segment = (Segment)child;
            if (segment.isSegmentSelected() && segment.onPress()) {
                return true;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    private float getContentDiameter() {
        if (super.children.isEmpty()) {
            return 0.0f;
        }
        final Bounds bounds = this.bounds();
        final float centerX = bounds.getCenterX();
        final float centerY = bounds.getCenterY();
        final float radius = bounds.getWidth(BoundsType.OUTER) / 2.0f;
        float width = Float.MIN_VALUE;
        float height = Float.MIN_VALUE;
        for (final AbstractWidget<?> child : super.children) {
            width = Math.max(width, child.bounds().getWidth(BoundsType.OUTER));
            height = Math.max(height, child.bounds().getHeight(BoundsType.OUTER));
        }
        final float middleRadius = (this.innerRadius.get() + radius) / 2.0f;
        final float left = centerX - middleRadius - width / 2.0f;
        final float top = centerY - middleRadius - height / 2.0f;
        final float right = centerX + middleRadius + width / 2.0f;
        final float bottom = centerY + middleRadius + height / 2.0f;
        return Math.max(top - bottom, right - left);
    }
    
    private void calculateAngles() {
        final float segmentSize = 360.0f / this.getSegmentCount();
        final float halfSegmentDistance = this.segmentDistanceDegrees().get() / 2.0f;
        final float shift = segmentSize / 2.0f * (this.getSegmentShift() + 1);
        int index = 0;
        for (final AbstractWidget<?> child : this.children) {
            if (!(child instanceof Segment)) {
                continue;
            }
            final Segment segment = (Segment)child;
            segment.setStartingAngle((float)Math.toRadians(shift + (segmentSize * index + halfSegmentDistance)));
            segment.setEndingAngle((float)Math.toRadians(shift + (segmentSize * index + segmentSize - halfSegmentDistance)));
            ++index;
        }
    }
    
    public void addSegment(final Segment segment) {
        ((AbstractWidget<Segment>)this).addChild(segment);
    }
    
    public void addSegmentInitialized(final Segment segment) {
        ((AbstractWidget<Segment>)this).addChildInitialized(segment);
        this.calculateAngles();
    }
    
    public LssProperty<Boolean> selectable() {
        return this.selectable;
    }
    
    public LssProperty<Float> innerRadius() {
        return this.innerRadius;
    }
    
    public LssProperty<Float> segmentDistanceDegrees() {
        return this.segmentDistanceDegrees;
    }
    
    public LssProperty<Integer> segmentBackgroundColor() {
        return this.segmentBackgroundColor;
    }
    
    public LssProperty<Integer> segmentHighlightColor() {
        return this.segmentHighlightColor;
    }
    
    public LssProperty<Integer> segmentSelectedColor() {
        return this.segmentSelectedColor;
    }
    
    public LssProperty<Integer> segmentBorderColor() {
        return this.segmentBorderColor;
    }
    
    public LssProperty<Integer> innerBackgroundColor() {
        return this.innerBackgroundColor;
    }
    
    public LssProperty<Integer> innerBorderColor() {
        return this.innerBorderColor;
    }
    
    public int getSegmentCount() {
        int count = 0;
        for (final AbstractWidget<?> child : super.children) {
            if (child instanceof Segment) {
                ++count;
            }
        }
        return count;
    }
    
    public int getSegmentShift() {
        return this.getSegmentCount();
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return type == AutoAlignType.POSITION && child instanceof Segment;
    }
    
    static {
        CENTER_SEGMENT = ModifyReason.of("centerSegment");
    }
    
    @AutoWidget
    public static class Segment extends AbstractWidget<Widget>
    {
        protected float startingAngle;
        protected float endingAngle;
        protected boolean selectable;
        protected boolean segmentSelected;
        protected long highlightedAt;
        private final LssProperty<Integer> segmentColor;
        
        public Segment() {
            this.selectable = true;
            this.segmentColor = new LssProperty<Integer>(0);
        }
        
        public float getStartingAngle() {
            return this.startingAngle;
        }
        
        public void setStartingAngle(final float startingAngle) {
            this.startingAngle = startingAngle;
        }
        
        public float getEndingAngle() {
            return this.endingAngle;
        }
        
        public void setEndingAngle(final float endingAngle) {
            this.endingAngle = endingAngle;
        }
        
        public boolean isSelectable() {
            return this.selectable;
        }
        
        public void setSelectable(final boolean selectable) {
            this.selectable = selectable;
        }
        
        public boolean isSegmentSelected() {
            return this.segmentSelected;
        }
        
        public void highlight() {
            this.highlightedAt = TimeUtil.getMillis();
        }
        
        public void highlightAt(final long millis) {
            this.highlightedAt = millis;
        }
        
        public void unhighlight() {
            this.highlightedAt = 0L;
        }
        
        public boolean isHighlighted() {
            return this.highlightedAt != 0L && TimeUtil.getMillis() - this.highlightedAt < 1000L;
        }
        
        public void setSegmentSelected(final boolean selected) {
            if (this.segmentSelected == selected) {
                return;
            }
            this.segmentSelected = selected;
            this.onSelectionChanged();
        }
        
        protected void onSelectionChanged() {
        }
        
        @Deprecated
        public LssProperty<Integer> segmentColor() {
            return this.segmentColor;
        }
    }
}
