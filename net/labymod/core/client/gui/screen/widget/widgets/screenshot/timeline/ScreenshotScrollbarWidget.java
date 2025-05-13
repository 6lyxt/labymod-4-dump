// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.awt.Color;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ScreenshotScrollbarWidget extends SimpleWidget
{
    private final ScreenshotTimelineWidget timelineWidget;
    private long lastAttentionTime;
    private int previousAttentionAmount;
    private int attentionAmount;
    
    public ScreenshotScrollbarWidget(final ScreenshotTimelineWidget timelineWidget) {
        this.lastAttentionTime = -1L;
        this.previousAttentionAmount = 0;
        this.attentionAmount = 0;
        this.timelineWidget = timelineWidget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.draggable().set(true);
    }
    
    @Override
    public void render(final ScreenContext context) {
        final float attentionStrength = (float)MathHelper.lerp(this.attentionAmount, this.previousAttentionAmount, context.getTickDelta());
        this.opacity().set(attentionStrength / 7.0f);
        if (this.isHovered()) {
            this.updateAttention();
        }
        super.render(context);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        this.renderSectionTicks(context);
        this.renderIndicator(context);
    }
    
    @Override
    public void tick() {
        super.tick();
        this.previousAttentionAmount = this.attentionAmount;
        final long attentionDuration = TimeUtil.getMillis() - this.lastAttentionTime;
        if (attentionDuration < 1000L) {
            ++this.attentionAmount;
            if (this.attentionAmount > 7) {
                this.attentionAmount = 7;
            }
        }
        else {
            --this.attentionAmount;
            if (this.attentionAmount < 0) {
                this.attentionAmount = 0;
            }
        }
    }
    
    private void renderSectionTicks(final ScreenContext context) {
        final ScreenshotContainerWidget container = this.timelineWidget.getContainer();
        final Bounds bounds = this.bounds();
        final ScreenshotSection[] sections = this.timelineWidget.holder().browser().getSections().toArray(new ScreenshotSection[0]);
        float previousTimeY = 0.0f;
        float previousY = 0.0f;
        for (final ScreenshotSection section : sections) {
            final float containerOffset = container.getOffsetOfSection(section);
            final float scrollbarOffset = this.container2ScrollbarOffset(containerOffset, 10.0f);
            final float y = bounds.getY() + scrollbarOffset;
            if (section.isBeginningOfYear() && y - previousTimeY > 10.0f) {
                this.labyAPI.renderPipeline().componentRenderer().builder().pos((float)(int)bounds.getCenterX(), (float)(int)y).centered(true).text(String.valueOf(section.getYear())).color(NamedTextColor.GRAY.value()).scale(0.67f).render(context.stack());
                previousTimeY = y;
            }
            else if (y - previousY > 2.0f && y - previousTimeY > 10.0f) {
                this.labyAPI.renderPipeline().rectangleRenderer().pos((float)((int)bounds.getRight() - 2), (float)(int)y).size(1.0f, 1.0f).color(ColorFormat.ARGB32.pack(180, 180, 180, 80)).render(context.stack());
            }
            previousY = y;
        }
    }
    
    private void renderIndicator(final ScreenContext context) {
        final ScreenshotContainerWidget container = this.timelineWidget.getContainer();
        final Bounds bounds = this.bounds();
        final boolean attachedToMouse = this.isHovered() || this.isDragging();
        final float scrollOffset = this.timelineWidget.getScrollOffset();
        final float scrollbarOffset = MathHelper.clamp(context.mouse().getY() - bounds.getY(), 0.0f, bounds.getHeight());
        final float containerOffset = attachedToMouse ? this.scrollbar2ContainerOffset(scrollbarOffset) : scrollOffset;
        final ScreenshotSectionWidget sectionWidget = container.getSectionAtOffset(containerOffset);
        if (sectionWidget == null) {
            return;
        }
        final float targetScrollbarOffset = this.container2ScrollbarOffset(containerOffset, 1.0f);
        final float y = bounds.getY() + targetScrollbarOffset;
        final float width = sectionWidget.renderableTitleComponent().getWidth();
        final float height = 10.0f;
        final float padding = 1.0f;
        this.labyAPI.renderPipeline().rectangleRenderer().pos((float)(int)(bounds.getRight() - width - padding), (float)(int)y).size(width, 1.0f).color(Color.WHITE).render(context.stack());
        this.labyAPI.renderPipeline().rectangleRenderer().pos((float)(int)(bounds.getRight() - width - padding), (float)(int)(y - height)).size(width, height).color(ColorFormat.ARGB32.pack(0, 0, 0, 120)).render(context.stack());
        this.labyAPI.renderPipeline().componentRenderer().builder().pos((float)(int)(bounds.getRight() - width), (float)(int)(y - height)).text(sectionWidget.renderableTitleComponent()).render(context.stack());
        if (this.isHovered() && !this.isDragging()) {
            this.renderActualIndicator(context);
        }
    }
    
    private void renderActualIndicator(final ScreenContext context) {
        final Bounds bounds = this.bounds();
        final float containerOffset = this.timelineWidget.getScrollOffset();
        final float scrollbarOffset = this.container2ScrollbarOffset(containerOffset, 1.0f);
        this.labyAPI.renderPipeline().rectangleRenderer().pos(bounds.getX(), bounds.getY() + scrollbarOffset).size(bounds.getWidth(), 1.0f).color(Color.WHITE).render(context.stack());
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final float mouseOffsetY = mouse.getY() - this.bounds().getY();
        this.timelineWidget.setScrollOffset(this.scrollbar2ContainerOffset(mouseOffsetY));
        this.updateAttention();
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.isDragging()) {
            final float mouseOffsetY = mouse.getY() - this.bounds().getY();
            this.timelineWidget.setScrollOffset(this.scrollbar2ContainerOffset(mouseOffsetY));
        }
        this.updateAttention();
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    public void updateAttention() {
        final float maxScrollOffset = this.timelineWidget.getMaxScrollOffset();
        if (maxScrollOffset > 0.0f) {
            this.lastAttentionTime = TimeUtil.getMillis();
        }
    }
    
    private float container2ScrollbarOffset(final float offset) {
        return this.container2ScrollbarOffset(offset, 0.0f);
    }
    
    private float scrollbar2ContainerOffset(final float offset) {
        return this.scrollbar2ContainerOffset(offset, 0.0f);
    }
    
    private float container2ScrollbarOffset(final float offset, final float elementHeight) {
        final float maxScrollOffset = this.timelineWidget.getMaxScrollOffset();
        final float percentage = (maxScrollOffset == 0.0f) ? 0.0f : (offset / maxScrollOffset);
        return percentage * (this.bounds().getHeight() - elementHeight);
    }
    
    private float scrollbar2ContainerOffset(final float offset, final float elementHeight) {
        final float percentage = offset / (this.bounds().getHeight() - elementHeight);
        return this.timelineWidget.getMaxScrollOffset() * percentage;
    }
}
