// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import java.util.Iterator;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.List;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;

@AutoWidget
public class SectionedListWidget<T extends ListWidget<?>, I> extends SessionedListWidget<Widget>
{
    private static final ModifyReason ENTRY_BOUNDS;
    private final LssProperty<Float> spaceBetweenEntries;
    private Runnable onFocusedSectionChanged;
    private I focusedSection;
    private boolean manualTarget;
    private I manualTargetIdentifier;
    private long lastManualTargetChanged;
    
    public SectionedListWidget() {
        this.spaceBetweenEntries = new LssProperty<Float>(1.0f);
        this.manualTarget = false;
        this.manualTargetIdentifier = null;
        this.lastManualTargetChanged = 0L;
        this.translateY().addChangeListener(newValue -> this.updateVisibility(this, this.parent));
    }
    
    @Override
    protected void updateContentBounds() {
        super.updateContentBounds();
        this.updateChildren(true);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return this.updateChildren(false) + this.bounds().getVerticalOffset(type);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return this.bounds().getWidth(type);
    }
    
    @Override
    public void updateVisibility(final ListWidget<?> widget, final Parent parent) {
        super.updateVisibility(widget, parent);
        final long timePassedSinceManualTargetChanged = TimeUtil.getMillis() - this.lastManualTargetChanged;
        if (this.manualTarget || timePassedSinceManualTargetChanged < 100L) {
            this.manualTarget = false;
            return;
        }
        final float offset = -widget.getTranslateY();
        SectionWidget<I> section = null;
        final List<?> widgetChildren = widget.getChildren();
        for (int i = widgetChildren.size() - 1; i >= 0; --i) {
            final Widget childWidget = (Widget)widgetChildren.get(i);
            if (childWidget instanceof SectionWidget) {
                final SectionWidget<?> sectionWidget = (SectionWidget<?>)childWidget;
                final Bounds bounds = sectionWidget.bounds();
                if (offset > bounds.getY() - 20.0f) {
                    section = (SectionWidget)childWidget;
                    break;
                }
            }
        }
        if (section == null) {
            this.focusedSection = null;
        }
        else {
            this.updateSection(section.getIdentifier());
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.manualTargetIdentifier != null) {
            this.setFocusedSection(this.manualTargetIdentifier, false);
        }
    }
    
    private void updateSection(final I newSection) {
        if (Objects.equals(this.focusedSection, newSection)) {
            return;
        }
        this.focusedSection = newSection;
        if (this.onFocusedSectionChanged != null) {
            this.onFocusedSectionChanged.run();
        }
        if (!this.children.isEmpty()) {
            final Widget lastWidget = this.children.get(this.children.size() - 1);
            final float requiredOverflow = this.parent.bounds().getHeight() - lastWidget.bounds().getHeight() - 40.0f;
            this.marginBottom().set(Math.max(requiredOverflow, 0.0f));
        }
    }
    
    public SectionedListWidget<T, I> onFocusedSectionChanged(final Runnable runnable) {
        this.onFocusedSectionChanged = runnable;
        return this;
    }
    
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public void addSection(@NotNull final Component component, @NotNull final I identifier, @NotNull final T widget) {
        if (widget.getChildren().isEmpty()) {
            return;
        }
        this.addChild(new SectionWidget<Object>(component, identifier));
        this.addChild(widget);
    }
    
    public void forEachSection(final BiConsumer<Component, T> consumer) {
        for (final Widget child : this.children) {
            if (!(child instanceof SectionWidget)) {
                continue;
            }
            final SectionWidget<T> section = (SectionWidget<T>)child;
            consumer.accept(section.getComponent(), section.getIdentifier());
        }
    }
    
    public I getFocusedSection() {
        return this.focusedSection;
    }
    
    public void setFocusedSection(final I identifier, final boolean retry) {
        this.manualTarget = true;
        this.lastManualTargetChanged = TimeUtil.getMillis();
        SectionWidget<I> section = null;
        for (final Widget child : this.children) {
            if (!(child instanceof SectionWidget)) {
                continue;
            }
            final SectionWidget<I> currentSection = (SectionWidget<I>)child;
            if (Objects.equals(currentSection.getIdentifier(), identifier)) {
                section = currentSection;
                break;
            }
        }
        if (section == null) {
            if (retry) {
                this.manualTargetIdentifier = identifier;
            }
            return;
        }
        final ReasonableMutableRectangle rectangle = this.getParent().bounds().rectangle(BoundsType.MIDDLE);
        final boolean lowerHalf = this.session.getScrollPositionY() > this.session.maxScrollPositionY() / 2.0f;
        float target;
        if (lowerHalf) {
            target = section.translationY;
        }
        else {
            target = section.translationY;
        }
        this.manualTargetIdentifier = null;
        this.session.setScrollPositionY(target);
        this.updateSection(section.getIdentifier());
    }
    
    protected float updateChildren(final boolean setBounds) {
        final float spaceBetweenEntries = this.spaceBetweenEntries.get();
        final Bounds bounds = this.bounds();
        final float width = bounds.getWidth();
        final float x = bounds.getX();
        final float y = bounds.getY();
        float height = 0.0f;
        SectionWidget<?> currentSection = null;
        for (final Widget child : this.children) {
            if (!child.isVisible()) {
                continue;
            }
            final Bounds childBounds = child.bounds();
            if (setBounds) {
                childBounds.setOuterPosition(x + childBounds.getHorizontalOffset(BoundsType.OUTER), y + height + childBounds.getVerticalOffset(BoundsType.OUTER), SectionedListWidget.ENTRY_BOUNDS);
                childBounds.setOuterWidth(width, SectionedListWidget.ENTRY_BOUNDS);
            }
            if (child instanceof SectionWidget) {
                final SectionWidget<?> sectionWidget = (SectionWidget<?>)child;
                if (currentSection != null && currentSection.sectionHeight != 0.0f) {
                    final SectionWidget<?> sectionWidget2 = currentSection;
                    sectionWidget2.sectionHeight -= spaceBetweenEntries;
                }
                sectionWidget.translationY = height;
                sectionWidget.sectionHeight = 0.0f;
                currentSection = sectionWidget;
            }
            height += childBounds.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
            if (currentSection == null) {
                continue;
            }
            final SectionWidget<?> sectionWidget3 = currentSection;
            sectionWidget3.sectionHeight += childBounds.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
        }
        if (height != 0.0f) {
            height -= spaceBetweenEntries;
        }
        return height;
    }
    
    static {
        ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    }
    
    @AutoWidget
    public static class SectionWidget<T> extends AbstractWidget<Widget>
    {
        private final Component component;
        private final LssProperty<Float> lineHeight;
        private final LssProperty<Integer> lineColor;
        private final LssProperty<Integer> textColor;
        private final T identifier;
        private float translationY;
        private float sectionHeight;
        
        private SectionWidget(@NotNull final Component component, @NotNull final T identifier) {
            this.lineHeight = new LssProperty<Float>(1.0f);
            this.lineColor = new LssProperty<Integer>(-1);
            this.textColor = new LssProperty<Integer>(-1);
            Objects.requireNonNull(identifier);
            this.component = component;
            this.identifier = identifier;
        }
        
        @Override
        public void renderWidget(final ScreenContext context) {
            super.renderWidget(context);
            final RenderableComponent renderableComponent = RenderableComponent.of(this.component);
            final RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
            final Bounds bounds = this.bounds();
            final float lineWidth = bounds.getWidth() / 2.0f - renderableComponent.getWidth() / 2.0f;
            final RectangleRenderer rectangleRenderer = renderPipeline.rectangleRenderer();
            final float lineHeight = this.lineHeight.get();
            final int lineColor = this.lineColor.get();
            final BatchRectangleRenderer batchRectangleRenderer = rectangleRenderer.beginBatch(context.stack());
            batchRectangleRenderer.pos(bounds.getX(), bounds.getCenterY()).size(lineWidth - 5.0f, lineHeight).color(lineColor).build();
            batchRectangleRenderer.pos(bounds.getMaxX() - lineWidth + 3.0f, bounds.getCenterY()).size(lineWidth - 3.0f, lineHeight).color(lineColor).build();
            batchRectangleRenderer.upload();
            renderPipeline.componentRenderer().builder().text(renderableComponent).pos(bounds.getCenterX(), bounds.getCenterY() - renderableComponent.getHeight() / 2.0f + 1.0f).centered(true).color(this.textColor.get()).render(context.stack());
        }
        
        public Component getComponent() {
            return this.component;
        }
        
        public T getIdentifier() {
            return this.identifier;
        }
        
        public LssProperty<Float> lineHeight() {
            return this.lineHeight;
        }
        
        public LssProperty<Integer> lineColor() {
            return this.lineColor;
        }
        
        public LssProperty<Integer> textColor() {
            return this.textColor;
        }
    }
}
