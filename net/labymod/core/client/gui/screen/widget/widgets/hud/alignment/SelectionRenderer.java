// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.DefaultRectangle;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.util.math.MathHelper;
import java.awt.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.Locale;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import java.util.List;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;

public class SelectionRenderer
{
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 1.5f;
    private final HudWidgetInteractionWidget interactionWidget;
    private final LabyAPI labyAPI;
    private HandleType hoveredHandle;
    private HandleType draggingHandle;
    private boolean dragging;
    private float mouseClickedX;
    private float mouseClickedY;
    private final List<HudWidget<?>> selected;
    private final List<Session> sessions;
    private HudWidget<?> lastSelectedHudWidget;
    private final EditButton editButton;
    
    public SelectionRenderer(final HudWidgetInteractionWidget interactionWidget) {
        this.selected = new ArrayList<HudWidget<?>>();
        this.sessions = new ArrayList<Session>();
        this.lastSelectedHudWidget = null;
        this.editButton = new EditButton(this);
        this.interactionWidget = interactionWidget;
        this.labyAPI = Laby.labyAPI();
    }
    
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        if (this.selected.isEmpty() || this.isMoving()) {
            return;
        }
        final MutableRectangle entireRectangle = this.getEntireRectangle(1);
        if (entireRectangle == null) {
            return;
        }
        this.renderSelectionBox(stack, mouse, (float)(int)entireRectangle.getLeft(), (float)(int)entireRectangle.getTop(), (float)(int)entireRectangle.getRight(), (float)(int)entireRectangle.getBottom());
        if (this.isSingleSelection()) {
            if (this.dragging) {
                if (this.draggingHandle.isCorner()) {
                    final HudWidgetWidget widget = this.interactionWidget.getWidget(this.lastSelectedHudWidget);
                    final boolean isOnRightSideOfScreen = entireRectangle.getCenterX() > this.interactionWidget.bounds().getCenterX() + 5.0f;
                    final float snappingScale = this.getSnappingScale();
                    final float scale = widget.size().getScale();
                    final String scaleString = String.format(Locale.ROOT, "%.2fx", scale);
                    final boolean ignoreSnapping = this.interactionWidget.shouldIgnoreAlignment();
                    int color = NamedTextColor.YELLOW.getValue();
                    if (scale == snappingScale && !ignoreSnapping) {
                        color = NamedTextColor.AQUA.getValue();
                    }
                    if (scale == 1.0f && !ignoreSnapping) {
                        color = NamedTextColor.GREEN.getValue();
                    }
                    if (scale == 0.5f || scale == 1.5f) {
                        color = NamedTextColor.RED.getValue();
                    }
                    final float fontSize = 0.67f;
                    final RenderableComponent renderableComponent = RenderableComponent.of(Component.text(scaleString));
                    this.labyAPI.renderPipeline().componentRenderer().builder().pos(isOnRightSideOfScreen ? (entireRectangle.getLeft() - renderableComponent.getWidth() * fontSize - 1.0f) : (entireRectangle.getRight() + 1.0f), entireRectangle.getCenterY() - fontSize * 4.0f).text(renderableComponent).color(color).scale(fontSize).render(stack);
                }
            }
            else {
                this.editButton.render(stack, mouse, tickDelta);
            }
        }
    }
    
    private void renderSelectionBox(final Stack stack, final MutableMouse mouse, final float left, final float top, final float right, final float bottom) {
        final BatchRectangleRenderer renderer = this.labyAPI.renderPipeline().rectangleRenderer().beginBatch(stack);
        final float progress = TimeUtil.getMillis() % 1000L / 250.0f;
        renderer.pos(left, top, right, bottom).color(ColorFormat.ARGB32.pack(120, 120, 120, 30)).outline(Color.BLACK.getRGB(), Color.WHITE.getRGB(), 0.25f).build();
        this.renderSelectionLine(renderer, top, bottom, left, false, 1.0f - progress);
        this.renderSelectionLine(renderer, left, right, top, true, progress + 0.5f);
        this.renderSelectionLine(renderer, top, bottom, right, false, progress + 0.5f);
        this.renderSelectionLine(renderer, left, right, bottom, true, 1.0f - progress);
        this.hoveredHandle = null;
        if (this.isSingleSelection()) {
            this.renderHandle(renderer, mouse, HandleType.TOP_LEFT, left, top);
            this.renderHandle(renderer, mouse, HandleType.TOP_RIGHT, right, top);
            this.renderHandle(renderer, mouse, HandleType.BOTTOM_LEFT, left, bottom);
            this.renderHandle(renderer, mouse, HandleType.BOTTOM_RIGHT, right, bottom);
            if (this.lastSelectedHudWidget.isResizeable()) {
                this.renderHandle(renderer, mouse, HandleType.LEFT, left, top + (bottom - top) / 2.0f);
                this.renderHandle(renderer, mouse, HandleType.RIGHT, right, top + (bottom - top) / 2.0f);
                this.renderHandle(renderer, mouse, HandleType.TOP, left + (right - left) / 2.0f, top);
                this.renderHandle(renderer, mouse, HandleType.BOTTOM, left + (right - left) / 2.0f, bottom);
            }
        }
        renderer.upload();
    }
    
    private void renderHandle(final BatchRectangleRenderer renderer, final MutableMouse mouse, final HandleType type, final float x, final float y) {
        if (!this.interactionWidget.bounds().isInRectangle(x, y)) {
            return;
        }
        if (type == HandleType.TOP || type == HandleType.BOTTOM) {
            final HudWidget<?> hudWidget = this.getLastSelectedHudWidget();
            final HudWidget<?> target = (type == HandleType.TOP) ? hudWidget.getParent() : hudWidget.getChild();
            if (target != null) {
                return;
            }
        }
        final boolean hover = mouse.isInside(x - 2.0f, y - 2.0f, 4.0, 4.0) || (this.dragging && this.draggingHandle == type);
        renderer.pos(x - 1.0f, y - 1.0f, x + 1.0f, y + 1.0f).color(ColorFormat.ARGB32.pack(hover ? 240 : 0, hover ? 240 : 0, hover ? 240 : 0, hover ? 200 : 120)).outline(Color.WHITE.getRGB(), Color.WHITE.getRGB(), 0.25f).build();
        if (hover) {
            this.hoveredHandle = type;
        }
    }
    
    private void renderSelectionLine(final BatchRectangleRenderer renderer, final float from, final float to, final float level, final boolean horizontal, final float progress) {
        final int lineLength = 2;
        final float thickness = 0.25f;
        final float offset = progress % 1.0f * (lineLength * 2);
        final int color = Color.BLACK.getRGB();
        for (float position = from + offset - lineLength; position < to; position += lineLength * 2) {
            final float clamped1 = MathHelper.clamp(position, from, to);
            final float clamped2 = MathHelper.clamp(position + lineLength, from, to);
            if (horizontal) {
                renderer.pos(clamped1, level - thickness, clamped2, level + thickness).color(color).build();
            }
            else {
                renderer.pos(level - thickness, clamped1, level + thickness, clamped2).color(color).build();
            }
        }
    }
    
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.hoveredHandle != null) {
            this.dragging = true;
            this.draggingHandle = this.hoveredHandle;
            this.mouseClickedX = (float)mouse.getX();
            this.mouseClickedY = (float)mouse.getY();
            this.sessions.clear();
            for (final HudWidget<?> hudWidget : this.selected) {
                this.sessions.add(new Session(this.interactionWidget.getWidget(hudWidget)));
            }
            return true;
        }
        return this.editButton.mouseClicked(mouse, mouseButton);
    }
    
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.dragging) {
            final float mouseOffsetX = mouse.getX() - this.mouseClickedX;
            final float mouseOffsetY = mouse.getY() - this.mouseClickedY;
            if (this.draggingHandle.isCorner()) {
                final float snappingScale = this.getSnappingScale();
                for (final Session session : this.sessions) {
                    session.setScaleFromOffset(this.interactionWidget, this.draggingHandle, mouseOffsetX, snappingScale);
                }
            }
            else {
                final float mouseOffset = (this.draggingHandle == HandleType.TOP || this.draggingHandle == HandleType.BOTTOM) ? mouseOffsetY : mouseOffsetX;
                for (final Session session : this.sessions) {
                    session.setSizeFromOffset(this.interactionWidget, this.draggingHandle, mouseOffset);
                }
            }
            this.interactionWidget.updateHudWidgetsInDropzone();
            return true;
        }
        return false;
    }
    
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.dragging) {
            this.dragging = false;
            this.hoveredHandle = null;
            for (final Session session : this.sessions) {
                session.dispose();
                this.interactionWidget.saveHudWidget(session.hudWidget());
            }
            this.sessions.clear();
            return true;
        }
        return false;
    }
    
    public void onTick() {
        this.editButton.onTick();
    }
    
    public void select(final HudWidget<?> hudWidget) {
        if (this.selected.contains(hudWidget)) {
            return;
        }
        this.selected.add(hudWidget);
        this.lastSelectedHudWidget = hudWidget;
    }
    
    public void unselect(final HudWidget<?> hudWidget) {
        this.selected.remove(hudWidget);
    }
    
    public void unselectAll() {
        this.selected.clear();
    }
    
    public boolean isSelected(final HudWidget<?> hudWidget) {
        return this.selected.contains(hudWidget);
    }
    
    public boolean isSingleSelection() {
        return this.selected.size() == 1;
    }
    
    public List<HudWidget<?>> getSelected() {
        return this.selected;
    }
    
    private boolean isMoving() {
        return this.interactionWidget.getDraggingHudWidget() != null;
    }
    
    public HudWidget<?> getLastSelectedHudWidget() {
        return this.lastSelectedHudWidget;
    }
    
    public HudWidgetInteractionWidget interactionWidget() {
        return this.interactionWidget;
    }
    
    MutableRectangle getEntireRectangle(final int padding) {
        DefaultRectangle entireRectangle = null;
        for (final HudWidget<?> hudWidget : this.selected) {
            final HudWidgetWidget widget = this.interactionWidget.getWidget(hudWidget);
            if (entireRectangle == null) {
                entireRectangle = new DefaultRectangle(widget.scaledBounds());
            }
            else {
                entireRectangle.extend(widget.scaledBounds());
            }
        }
        return (entireRectangle == null) ? null : entireRectangle.expand((float)padding);
    }
    
    private float getSnappingScale() {
        if (this.sessions.size() != 1) {
            return 1.0f;
        }
        final Session session = this.sessions.get(0);
        final HudWidget<?> hudWidget = session.hudWidget();
        final HudWidget<?> parent = hudWidget.getParent();
        if (parent != null) {
            final HudWidgetWidget parentWidget = this.interactionWidget.getWidget(parent);
            return parentWidget.size().getScale();
        }
        final HudWidget<?> child = hudWidget.getChild();
        if (child != null) {
            final HudWidgetWidget childWidget = this.interactionWidget.getWidget(child);
            return childWidget.size().getScale();
        }
        return 1.0f;
    }
    
    public boolean canUpdateHudWidget(final HudWidget<?> hudWidget) {
        for (final Session session : this.sessions) {
            if (session.hudWidget() == hudWidget && this.draggingHandle.isEdge()) {
                return false;
            }
        }
        return true;
    }
    
    enum HandleType
    {
        TOP_LEFT, 
        TOP_RIGHT, 
        BOTTOM_LEFT, 
        BOTTOM_RIGHT, 
        TOP, 
        RIGHT, 
        BOTTOM, 
        LEFT;
        
        public boolean isLeftCorner() {
            return this == HandleType.TOP_LEFT || this == HandleType.BOTTOM_LEFT;
        }
        
        public boolean isRightCorner() {
            return this == HandleType.TOP_RIGHT || this == HandleType.BOTTOM_RIGHT;
        }
        
        public boolean isTopCorner() {
            return this == HandleType.TOP_LEFT || this == HandleType.TOP_RIGHT;
        }
        
        public boolean isBottomCorner() {
            return this == HandleType.BOTTOM_LEFT || this == HandleType.BOTTOM_RIGHT;
        }
        
        public boolean isCorner() {
            return this == HandleType.TOP_LEFT || this == HandleType.TOP_RIGHT || this == HandleType.BOTTOM_LEFT || this == HandleType.BOTTOM_RIGHT;
        }
        
        public boolean isEdge() {
            return this == HandleType.TOP || this == HandleType.RIGHT || this == HandleType.BOTTOM || this == HandleType.LEFT;
        }
    }
    
    static class Session
    {
        private final HudWidget<?> hudWidget;
        private final Rectangle previousRectangle;
        private final float previousScale;
        private final HudWidgetAnchor previousAnchor;
        
        public Session(final HudWidgetWidget widget) {
            this.hudWidget = widget.hudWidget();
            this.previousScale = widget.size().getScale();
            this.previousRectangle = widget.scaledBounds().copy();
            this.previousAnchor = this.hudWidget.anchor();
        }
        
        public void setScaleFromOffset(final HudWidgetInteractionWidget interactionWidget, final HandleType type, final float mouseOffset, final float snappingScale) {
            final float fromLeft = this.previousRectangle.getLeft();
            final float fromTop = this.previousRectangle.getTop();
            final float fromRight = this.previousRectangle.getRight();
            final float fromBottom = this.previousRectangle.getBottom();
            float scale = type.isLeftCorner() ? ((fromRight - (fromLeft + mouseOffset)) / (fromRight - fromLeft) * this.previousScale) : ((fromRight + mouseOffset - fromLeft) / (fromRight - fromLeft) * this.previousScale);
            final boolean ignoreSnapping = interactionWidget.shouldIgnoreAlignment();
            final float differenceToOther = Math.abs(scale - snappingScale);
            if (differenceToOther < 0.04f && !ignoreSnapping) {
                scale = snappingScale;
            }
            final float differenceToDefault = Math.abs(scale - 1.0f);
            if (differenceToDefault < 0.04f && !ignoreSnapping) {
                scale = 1.0f;
            }
            scale = MathHelper.clamp(scale, 0.5f, 1.5f);
            final HudWidgetWidget widget = interactionWidget.getWidget(this.hudWidget);
            final HudSize size = widget.size();
            final HudWidgetDropzone dropzone = this.hudWidget.getAttachedDropzone();
            if (dropzone == null) {
                ((HudWidgetConfig)this.hudWidget.getConfig()).scale().set(scale);
                final boolean isCentered = this.previousAnchor.isCenter();
                interactionWidget.updatePosition(this.hudWidget, (float)Math.round((type.isRightCorner() && !isCentered) ? fromLeft : (fromLeft + (fromRight - fromLeft - size.getActualWidth() * scale) * (isCentered ? 0.5f : 1.0f))), (float)Math.round(type.isBottomCorner() ? fromTop : (fromTop + (fromBottom - fromTop) - size.getActualHeight() * scale)));
            }
            else {
                for (final HudWidgetDropzone otherZone : this.hudWidget.getDropzones()) {
                    final HudWidget<?> hudWidget = interactionWidget.getHudWidgetInDropzone(otherZone);
                    if (hudWidget != null) {
                        ((HudWidgetConfig)hudWidget.getConfig()).scale().set(scale);
                        interactionWidget.updateHudWidget(hudWidget);
                    }
                }
            }
            if (this.hudWidget.getParent() != null) {
                interactionWidget.updateHudWidget(this.hudWidget.firstWidget());
            }
            widget.updateSizeOfWidget();
            widget.skipInterpolation();
        }
        
        public void setSizeFromOffset(final HudWidgetInteractionWidget interactionWidget, final HandleType type, float mouseOffset) {
            if (!this.hudWidget.isResizeable()) {
                return;
            }
            final HudWidgetConfig config = (HudWidgetConfig)this.hudWidget.getConfig();
            final ResizeableHudWidget.ResizeableHudWidgetConfig resizeable = (ResizeableHudWidget.ResizeableHudWidgetConfig)config;
            final HudWidgetWidget widget = interactionWidget.getWidget(this.hudWidget);
            final float scale = config.scale().get();
            float width = resizeable.width().get() * scale;
            float height = resizeable.height().get() * scale;
            final float previousWidth = this.previousRectangle.getWidth();
            final float previousHeight = this.previousRectangle.getHeight();
            final boolean isCentered = this.previousAnchor.isCenter();
            final float minWidth = resizeable.getMinWidth() * scale;
            final float minHeight = resizeable.getMinHeight() * scale;
            final float maxWidth = resizeable.getMaxWidth() * scale;
            final float maxHeight = resizeable.getMaxHeight() * scale;
            switch (type.ordinal()) {
                case 7: {
                    if (previousWidth - mouseOffset * (isCentered ? 2 : 1) < minWidth) {
                        mouseOffset = (previousWidth - minWidth) / (isCentered ? 2 : 1);
                    }
                    if (previousWidth - mouseOffset * (isCentered ? 2 : 1) > maxWidth) {
                        mouseOffset = (previousWidth - maxWidth) / (isCentered ? 2 : 1);
                    }
                    width = previousWidth - mouseOffset * (isCentered ? 2 : 1);
                    interactionWidget.updatePosition(this.hudWidget, this.previousRectangle.getLeft() + mouseOffset, this.previousRectangle.getTop());
                    break;
                }
                case 5: {
                    if (previousWidth + mouseOffset * (isCentered ? 2 : 1) < minWidth) {
                        mouseOffset = (minWidth - previousWidth) / (isCentered ? 2 : 1);
                    }
                    if (previousWidth + mouseOffset * (isCentered ? 2 : 1) > maxWidth) {
                        mouseOffset = (maxWidth - previousWidth) / (isCentered ? 2 : 1);
                    }
                    width = previousWidth + mouseOffset * (isCentered ? 2 : 1);
                    if (isCentered) {
                        interactionWidget.updatePosition(this.hudWidget, this.previousRectangle.getLeft() - mouseOffset, this.previousRectangle.getTop());
                        break;
                    }
                    break;
                }
                case 4: {
                    if (previousHeight - mouseOffset < minHeight) {
                        mouseOffset = previousHeight - minHeight;
                    }
                    if (previousHeight - mouseOffset > maxHeight) {
                        mouseOffset = previousHeight - maxHeight;
                    }
                    height = previousHeight - mouseOffset;
                    interactionWidget.updatePosition(this.hudWidget, this.previousRectangle.getLeft(), this.previousRectangle.getTop() + mouseOffset);
                    break;
                }
                case 6: {
                    if (previousHeight + mouseOffset < minHeight) {
                        mouseOffset = minHeight - previousHeight;
                    }
                    if (previousHeight + mouseOffset > maxHeight) {
                        mouseOffset = maxHeight - previousHeight;
                    }
                    height = previousHeight + mouseOffset;
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
                }
            }
            width /= scale;
            height /= scale;
            widget.size().set(width, height);
            ((ResizeableHudWidget)this.hudWidget).setSize(width, height);
            widget.updateSizeOfWidget();
            widget.skipInterpolation();
            if (this.hudWidget.getParent() != null) {
                interactionWidget.updateHudWidget(this.hudWidget.firstWidget());
            }
        }
        
        public void dispose() {
        }
        
        public HudWidget<?> hudWidget() {
            return this.hudWidget;
        }
    }
}
