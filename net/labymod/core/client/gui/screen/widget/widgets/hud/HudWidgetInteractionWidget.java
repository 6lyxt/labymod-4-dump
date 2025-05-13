// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.event.client.gui.hud.HudWidgetMovedEvent;
import net.labymod.api.event.client.gui.hud.HudWidgetDestroyedEvent;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.event.client.gui.hud.HudWidgetCreatedEvent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.hud.ScaledRectangle;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.util.bounds.area.RectangleArea;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.ChainAlignmentSide;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.AlignmentLine;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import java.util.Iterator;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border.VerticalAlignmentLine;
import net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border.HorizontalAlignmentLine;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.SelectionRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border.RangeAlignmentLine;
import java.util.List;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class HudWidgetInteractionWidget extends HudWidgetRendererWidget
{
    private static final ModifyReason HUD_WIDGET_INTERACTION;
    private static final int BORDER_ALIGNMENT_RANGE = 4;
    private static final int BORDER_ALIGNMENT_MARGIN = 2;
    private static final int ALIGNMENT_HOTKEY_INFORMATION_TICKS = 7;
    private final List<RangeAlignmentLine> alignmentLines;
    private final SelectionRenderer selectionRenderer;
    private final Bounds wrapperBounds;
    private final WidgetsEditorActivity editor;
    private HudWidget<?> draggingHudWidget;
    private boolean hasDraggingHudWidgetMoved;
    private boolean isCreatingHudWidget;
    private boolean mouseWasInRenderer;
    private float draggingOffsetX;
    private float draggingOffsetY;
    private boolean mouseOverWindow;
    private float lastMouseX;
    private float lastMouseY;
    private long renderTimePassed;
    private Consumer<HudWidget<?>> openSettingsListener;
    private int prevAlignmentHotkeyInformationOpacity;
    private int alignmentHotkeyInformationOpacity;
    
    public HudWidgetInteractionWidget(final Bounds wrapperBounds, final WidgetsEditorActivity editor) {
        this.alignmentLines = new ArrayList<RangeAlignmentLine>();
        this.selectionRenderer = new SelectionRenderer(this);
        this.draggingOffsetX = 0.0f;
        this.draggingOffsetY = 0.0f;
        this.openSettingsListener = null;
        this.wrapperBounds = wrapperBounds;
        this.editor = editor;
        this.alignmentLines.add(HorizontalAlignmentLine.border(4.0f, () -> HorizontalAlignmentLine.HorizontalCoordinates.of(this.area.getTop() + 2.0f, this.area.getLeft() + 2.0f, this.area.getRight() - 2.0f)));
        this.alignmentLines.add(HorizontalAlignmentLine.border(4.0f, () -> HorizontalAlignmentLine.HorizontalCoordinates.of(this.area.getBottom() - 2.0f, this.area.getLeft() + 2.0f, this.area.getRight() - 2.0f)));
        this.alignmentLines.add(VerticalAlignmentLine.border(4.0f, () -> VerticalAlignmentLine.VerticalCoordinates.of(this.area.getLeft() + 2.0f, this.area.getTop() + 2.0f, this.area.getBottom() - 2.0f)));
        this.alignmentLines.add(VerticalAlignmentLine.border(4.0f, () -> VerticalAlignmentLine.VerticalCoordinates.of(this.area.getRight() - 2.0f, this.area.getTop() + 2.0f, this.area.getBottom() - 2.0f)));
        this.alignmentLines.add(VerticalAlignmentLine.center(4.0f, () -> VerticalAlignmentLine.VerticalCoordinates.of((float)(int)this.area.getCenterX(), this.area.getTop(), this.area.getBottom())));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.renderTimePassed = 0L;
        for (final HudWidgetWidget child : this.children) {
            final ContextMenu contextMenu = child.createContextMenu();
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.settings", new Component[0])).clickHandler(entry -> {
                this.openSettings(child.hudWidget());
                return true;
            }).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.remove", new Component[0])).clickHandler(entry -> {
                this.destroyHudWidget(child.hudWidget(), false);
                return true;
            }).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.reset", new Component[0])).clickHandler(entry -> {
                this.resetHudWidget(child.hudWidget());
                return true;
            }).build());
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.hudWidgetEditor.context.resetScale", new Component[0])).clickHandler(entry -> {
                final HudWidget<?> hudWidget = child.hudWidget();
                final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfig();
                config.scale().reset();
                if (hudWidget.isResizeable()) {
                    final ResizeableHudWidget.ResizeableHudWidgetConfig resizeable = (ResizeableHudWidget.ResizeableHudWidgetConfig)config;
                    resizeable.width().reset();
                    resizeable.height().reset();
                }
                hudWidget.updateSize(child, true, child.size());
                child.updateSizeOfWidget();
                this.updateHudWidgetChildrenOf(hudWidget);
                this.saveHudWidget(hudWidget);
                this.updateHudWidget(hudWidget.firstWidget());
                this.updateHudWidgetsInDropzone();
                return true;
            }).build());
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        for (final HudWidgetWidget widget : this.children) {
            widget.skipInterpolation();
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final Stack stack = context.stack();
        final MutableMouse mouse = context.mouse();
        final float tickDelta = context.getTickDelta();
        this.renderTimePassed += (long)TimeUtil.convertDeltaToMilliseconds(tickDelta);
        for (final HudWidgetWidget widget : this.children) {
            final HudWidget<?> hudWidget = widget.hudWidget();
            final HudWidgetDropzone[] dropzones2;
            final HudWidgetDropzone[] dropzones = dropzones2 = hudWidget.getDropzones();
            for (final HudWidgetDropzone dropzone : dropzones2) {
                dropzone.render(stack, tickDelta, this, widget.size());
            }
        }
        super.renderWidget(context);
        final Rectangle chain = (this.draggingHudWidget == null) ? null : this.getChainRectangle(this.draggingHudWidget);
        for (final AlignmentLine alignmentLine : this.alignmentLines) {
            alignmentLine.render(stack, chain, tickDelta);
        }
        this.selectionRenderer.render(stack, mouse, tickDelta);
        final boolean ctrlPressed = this.shouldIgnoreAlignment();
        if (this.draggingHudWidget != null && chain != null && this.hasDraggingHudWidgetMoved && !ctrlPressed) {
            final HudWidgetWidget widget2 = this.getWidget(this.draggingHudWidget);
            if (this.getTargetDropzone(widget2) == null) {
                final RectangleArea area = this.getArea(this.draggingHudWidget);
                if (area != null) {
                    this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, area, ColorFormat.ARGB32.pack(16777215, 20));
                    final RectangleAreaPosition.PositionUnit[] units = area.position().getPositionUnits().toArray(new RectangleAreaPosition.PositionUnit[0]);
                    boolean hasChainAlignment = false;
                    for (final HudWidgetWidget child : this.children) {
                        if (child.getAlignmentVisibility() != ChainAlignmentSide.NONE) {
                            hasChainAlignment = true;
                            break;
                        }
                    }
                    if (!hasChainAlignment) {
                        final float padding = 2.0f;
                        final float fontSize = 0.67f;
                        final int color = ColorFormat.ARGB32.pack(16776992, 100);
                        final RectangleAreaPosition.PositionUnit vertical = units[0];
                        final float x1 = chain.getCenterX() - 0.5f + widget2.getInterpolationOffsetX();
                        final float x2 = chain.getCenterX() + 0.5f + widget2.getInterpolationOffsetX();
                        if (vertical != RectangleAreaPosition.PositionUnit.BOTTOM) {
                            final float y1 = (vertical == RectangleAreaPosition.PositionUnit.MIDDLE) ? area.getCenterY() : area.getTop();
                            final float y2 = chain.getTop() + widget2.getInterpolationOffsetY() - padding;
                            if (y1 + fontSize * 9.0f < y2) {
                                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, x1, y1, x2, y2, color);
                                this.labyAPI.renderPipeline().textRenderer().pos(x1 + padding, y1 + (y2 - y1 - fontSize * 9.0f) / 2.0f).color(color).text((int)(y2 - y1) + "px").scale(fontSize).render(stack);
                            }
                        }
                        if (vertical != RectangleAreaPosition.PositionUnit.TOP) {
                            final float y1 = chain.getBottom() + widget2.getInterpolationOffsetY() + padding;
                            final float y2 = (vertical == RectangleAreaPosition.PositionUnit.MIDDLE) ? area.getCenterY() : area.getBottom();
                            if (y1 + fontSize * 9.0f < y2) {
                                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, x1, y1, x2, y2, color);
                                this.labyAPI.renderPipeline().textRenderer().pos(x1 + padding, y1 + (y2 - y1 - fontSize * 9.0f) / 2.0f).color(color).text((int)(y2 - y1) + "px").scale(fontSize).render(stack);
                            }
                        }
                        final RectangleAreaPosition.PositionUnit horizontal = units[1];
                        final float y3 = chain.getCenterY() - 0.5f + widget2.getInterpolationOffsetY();
                        final float y4 = chain.getCenterY() + 0.5f + widget2.getInterpolationOffsetY();
                        if (horizontal != RectangleAreaPosition.PositionUnit.RIGHT) {
                            final float x3 = (horizontal == RectangleAreaPosition.PositionUnit.CENTER) ? area.getCenterX() : area.getLeft();
                            final float x4 = chain.getLeft() + widget2.getInterpolationOffsetX() - padding;
                            if (x3 + 12.0f < x4) {
                                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, x3, y3, x4, y4, color);
                                this.labyAPI.renderPipeline().textRenderer().pos(x3 + (x4 - x3 - 12.0f) / 2.0f, y3 + padding).color(color).text((int)(x4 - x3) + "px").scale(fontSize).render(stack);
                            }
                        }
                        if (horizontal != RectangleAreaPosition.PositionUnit.LEFT) {
                            final float x3 = chain.getRight() + widget2.getInterpolationOffsetX() + padding;
                            final float x4 = (horizontal == RectangleAreaPosition.PositionUnit.CENTER) ? area.getCenterX() : area.getRight();
                            if (x3 + 12.0f < x4) {
                                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, x3, y3, x4, y4, color);
                                this.labyAPI.renderPipeline().textRenderer().pos(x3 + (x4 - x3 - 12.0f) / 2.0f, y3 + padding).color(color).text((int)(x4 - x3) + "px").scale(fontSize).render(stack);
                            }
                        }
                    }
                }
            }
        }
        if (this.alignmentHotkeyInformationOpacity > 0) {
            final float opacity = MathHelper.lerp((float)this.alignmentHotkeyInformationOpacity, (float)this.prevAlignmentHotkeyInformationOpacity) / 7.0f;
            this.labyAPI.renderPipeline().componentRenderer().builder().text(Component.translatable("labymod.hudWidgetEditor.alignmentHotkeyInformation", new Component[0]).color(NamedTextColor.WHITE).argument(((BaseComponent<Component>)Component.text(OperatingSystem.isOSX() ? "CMD" : "CTRL")).color(NamedTextColor.YELLOW))).centered(true).scale(0.75f).useFloatingPointPosition(true).pos(this.area.getCenterX(), this.area.getBottom() - 65.0f).color(ColorFormat.ARGB32.pack(16777215, (int)(opacity * 255.0f))).render(stack);
        }
        this.lastMouseX = (float)mouse.getX();
        this.lastMouseY = (float)mouse.getY();
    }
    
    @Override
    public void tick() {
        super.tick();
        this.selectionRenderer.onTick();
        final boolean ctrlPressed = this.shouldIgnoreAlignment();
        final boolean prevIgnoreAlignment;
        boolean ignoreAlignment = prevIgnoreAlignment = (ctrlPressed || this.mouseOverWindow || !this.hasDraggingHudWidgetMoved);
        ignoreAlignment = this.tickDropZones(ignoreAlignment);
        ignoreAlignment = this.tickChainAttaching(ignoreAlignment);
        ignoreAlignment = this.tickBorderAlignment(ignoreAlignment);
        final boolean snappingVisible = ignoreAlignment != prevIgnoreAlignment && !prevIgnoreAlignment;
        this.tickAlignmentHotkeyInformation(snappingVisible);
    }
    
    private boolean tickDropZones(boolean ignoreAlignment) {
        boolean isOverlapping = false;
        for (final HudWidgetWidget widget : this.children) {
            final HudWidget<?> hudWidget = widget.hudWidget();
            final HudWidgetDropzone[] dropzones = hudWidget.getDropzones();
            final boolean isDraggingHudWidget = hudWidget == this.draggingHudWidget;
            for (final HudWidgetDropzone dropzone : dropzones) {
                final boolean visible = !ignoreAlignment && isDraggingHudWidget && !this.isDropzoneInUse(dropzone);
                dropzone.tick(visible, this.getTargetDropzone(widget) == dropzone);
                if (visible && dropzone.isOverlapping(this, widget)) {
                    isOverlapping = true;
                }
            }
        }
        if (isOverlapping) {
            ignoreAlignment = true;
        }
        return ignoreAlignment;
    }
    
    private boolean tickChainAttaching(final boolean ignoreAlignment) {
        for (final HudWidgetWidget widget : this.children) {
            widget.setAlignmentVisibility(ChainAlignmentSide.NONE);
        }
        if (!ignoreAlignment && this.draggingHudWidget != null) {
            final HudWidgetWidget widget2 = this.getWidget(this.draggingHudWidget);
            final HudWidgetWidget targetAt = this.getTargetAt(widget2.scaledBounds());
            if (targetAt != null) {
                final HudWidgetDropzone attachedDropzone = targetAt.hudWidget().getAttachedDropzone();
                if (attachedDropzone == null) {
                    final ChainAlignmentSide side = this.getTargetChainAlignmentSide(widget2, targetAt);
                    targetAt.setAlignmentVisibility(side);
                    return true;
                }
            }
        }
        return ignoreAlignment;
    }
    
    private boolean tickBorderAlignment(final boolean ignoreAlignment) {
        boolean ignoreNextAlignment = ignoreAlignment;
        final Rectangle chain = this.getDraggingChain();
        for (final RangeAlignmentLine alignmentLine : this.alignmentLines) {
            final boolean visible = !ignoreAlignment && alignmentLine.isInRange(chain);
            alignmentLine.tick(visible);
            if (visible) {
                ignoreNextAlignment = true;
            }
        }
        return ignoreNextAlignment;
    }
    
    private void tickAlignmentHotkeyInformation(final boolean snappingVisible) {
        this.prevAlignmentHotkeyInformationOpacity = this.alignmentHotkeyInformationOpacity;
        if (snappingVisible) {
            if (this.alignmentHotkeyInformationOpacity < 7) {
                ++this.alignmentHotkeyInformationOpacity;
            }
        }
        else if (this.alignmentHotkeyInformationOpacity > 0) {
            --this.alignmentHotkeyInformationOpacity;
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.selectionRenderer.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton == MouseButton.LEFT) {
            this.hasDraggingHudWidgetMoved = false;
            this.mouseWasInRenderer = mouse.isInside(this.area);
            for (final HudWidgetWidget widget : this.children) {
                if (widget.isHovered()) {
                    this.updateDraggingState(widget.hudWidget(), true);
                    final HudWidget<?> hudWidget = widget.hudWidget();
                    this.draggingHudWidget = hudWidget;
                    final ScaledRectangle bounds = widget.scaledBounds();
                    this.draggingOffsetX = (float)(mouse.getXDouble() - bounds.getX());
                    this.draggingOffsetY = (float)(mouse.getYDouble() - bounds.getY());
                    this.updateHudWidgetsInDropzone();
                    return super.mouseClicked(mouse, mouseButton);
                }
            }
            this.selectionRenderer.unselectAll();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (mouse.isInside(this.area)) {
            this.mouseWasInRenderer = true;
        }
        if (this.selectionRenderer.mouseDragged(mouse, button, deltaX, deltaY)) {
            return true;
        }
        final HudWidget<?> hudWidget = this.draggingHudWidget;
        if (hudWidget != null) {
            if (!this.hasDraggingHudWidgetMoved) {
                this.hasDraggingHudWidgetMoved = true;
                if (hudWidget.getParent() != null) {
                    hudWidget.updateParent(null);
                    this.saveHudWidget(hudWidget);
                }
            }
            this.updatePosition(hudWidget, (float)mouse.getXDouble() - this.draggingOffsetX, (float)mouse.getYDouble() - this.draggingOffsetY);
            return false;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    public void updatePosition(final HudWidget<?> hudWidget, final float x, final float y) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final ScaledRectangle rectangle = widget.scaledBounds();
        rectangle.setPosition(x, y, HudWidgetInteractionWidget.HUD_WIDGET_INTERACTION);
        final boolean canDropHudWidget = this.editor.window().isHudWidgetList();
        this.updateOutOfBounds(hudWidget, false, canDropHudWidget);
        final RectangleArea rectangleArea = this.getArea(hudWidget);
        if (rectangleArea != null) {
            final HudWidgetAnchor anchor = HudWidgetAnchor.of(rectangleArea.position(), ((HudWidgetConfig)hudWidget.getConfig()).horizontalAlignment().get());
            hudWidget.updateAnchor(anchor);
        }
        widget.updateSizeOfWidget();
        this.updateHudWidgetChildrenOf(hudWidget);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.selectionRenderer.mouseReleased(mouse, mouseButton)) {
            return true;
        }
        if (this.draggingHudWidget != null) {
            final HudWidgetWidget widget = this.getWidget(this.draggingHudWidget);
            final ScaledRectangle bounds = widget.scaledBounds();
            final HudWidget<?> prevParent = this.draggingHudWidget.getParent();
            boolean ignoreAlignment = this.shouldIgnoreAlignment();
            final HudWidgetConfig config = (HudWidgetConfig)this.draggingHudWidget.getConfig();
            final boolean placedInsideOfRenderer = widget.scaledBounds().isOverlapping(this.area);
            boolean hasAttached = false;
            this.updateDraggingState(this.draggingHudWidget, false);
            if (this.hasDraggingHudWidgetMoved) {
                final boolean wasInDropzone = config.getDropzoneId() != null;
                config.setDropzoneId(null);
                if (wasInDropzone) {
                    this.updateHudWidgetsInDropzone();
                }
                if (!ignoreAlignment) {
                    final HudWidgetDropzone dropzone = this.getTargetDropzone(widget);
                    if (dropzone != null) {
                        config.setDropzoneId(dropzone.getId());
                        ignoreAlignment = true;
                        hasAttached = true;
                        this.selectionRenderer.unselectAll();
                    }
                }
                final HudWidgetWidget target = this.getTargetAt(widget.scaledBounds());
                if (target == null || ignoreAlignment || target.hudWidget().getAttachedDropzone() != null) {
                    if (prevParent != null) {
                        this.draggingHudWidget.updateParent(null);
                    }
                    this.updateOutOfBounds(this.draggingHudWidget, !ignoreAlignment, false);
                    this.updateHudWidgetChildrenOf(this.draggingHudWidget);
                    this.saveHudWidget(this.draggingHudWidget);
                }
                else if (target.hudWidget().getAttachedDropzone() == null) {
                    final ChainAlignmentSide side = this.getTargetChainAlignmentSide(widget, target);
                    final HudWidget<?> targetHudWidget = target.hudWidget();
                    if (prevParent != targetHudWidget) {
                        if (side == ChainAlignmentSide.TOP) {
                            this.draggingHudWidget.updateParent(targetHudWidget.getParent());
                            targetHudWidget.updateParent(this.draggingHudWidget.lastWidget());
                            if (this.draggingHudWidget.getParent() == null) {
                                final HudWidgetAnchor anchor = targetHudWidget.anchor();
                                final ScaledRectangle targetBounds = target.scaledBounds();
                                final float anchorX = targetBounds.getX() + anchor.getShiftX(target.size());
                                bounds.setPosition(anchorX - anchor.getShiftX(widget.size()), targetBounds.getY(), HudWidgetInteractionWidget.HUD_WIDGET_INTERACTION);
                            }
                        }
                        else {
                            final HudWidget<?> child = targetHudWidget.getChild();
                            if (child != null) {
                                child.updateParent(this.draggingHudWidget.lastWidget());
                            }
                            this.draggingHudWidget.updateParent(targetHudWidget);
                        }
                        final HudWidget<?> firstHudWidget = targetHudWidget.firstWidget();
                        this.updateOutOfBounds(firstHudWidget, false, false);
                        this.saveHudWidget(firstHudWidget);
                        this.updateHudWidget(firstHudWidget);
                        hasAttached = true;
                    }
                }
            }
            if (hasAttached) {
                Laby.references().soundService().play(SoundType.HUD_ATTACH, 0.15f, 0.5f);
            }
            if (!this.selectionRenderer.isSelected(this.draggingHudWidget)) {
                if (!this.labyAPI.minecraft().isKeyPressed(Key.L_SHIFT) && !KeyHandler.isLeftControlDown()) {
                    this.selectionRenderer.unselectAll();
                }
                if (!this.hasDraggingHudWidgetMoved) {
                    this.selectionRenderer.select(this.draggingHudWidget);
                }
            }
            if (this.isCreatingHudWidget) {
                if (placedInsideOfRenderer) {
                    if (!this.mouseWasInRenderer) {
                        this.destroyHudWidget(this.draggingHudWidget, true);
                    }
                }
                else if (this.hasDraggingHudWidgetMoved) {
                    this.destroyHudWidget(this.draggingHudWidget, true);
                }
                else {
                    this.resetHudWidget(this.draggingHudWidget);
                    this.selectionRenderer.unselectAll();
                }
            }
            else if (this.editor.window().isHudWidgetList() && this.editor.window().isHudWidgetOnTrashArea()) {
                this.destroyHudWidget(this.draggingHudWidget, true);
            }
            this.setStencil(false);
            this.isCreatingHudWidget = false;
            this.draggingHudWidget = null;
            this.updateHudWidgetsInDropzone();
            return true;
        }
        return super.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.DELETE) {
            final HudWidget[] array;
            final HudWidget<?>[] toDestroy = array = this.selectionRenderer.getSelected().toArray(new HudWidget[0]);
            for (final HudWidget<?> hudWidget : array) {
                this.destroyHudWidget(hudWidget, false);
            }
            this.selectionRenderer.unselectAll();
        }
        if ((key == Key.ARROW_UP || key == Key.ARROW_DOWN) && !this.selectionRenderer.getSelected().isEmpty()) {
            if (key == Key.ARROW_UP) {
                HudWidget<?> hudWidget2 = null;
                for (final HudWidget<?> widget : this.selectionRenderer.getSelected()) {
                    final HudWidget<?> parent = widget.getParent();
                    if (parent != null) {
                        if (this.selectionRenderer.isSelected(parent)) {
                            continue;
                        }
                        hudWidget2 = widget;
                        break;
                    }
                }
                final HudWidget<?> parent2 = (hudWidget2 == null) ? null : hudWidget2.getParent();
                if (parent2 != null) {
                    if (!this.labyAPI.minecraft().isKeyPressed(Key.L_SHIFT)) {
                        this.selectionRenderer.unselect(hudWidget2);
                    }
                    this.selectionRenderer.select(parent2);
                }
            }
            if (key == Key.ARROW_DOWN) {
                HudWidget<?> hudWidget2 = null;
                for (final HudWidget<?> widget : this.selectionRenderer.getSelected()) {
                    final HudWidget<?> child = widget.getChild();
                    if (child != null) {
                        if (this.selectionRenderer.isSelected(child)) {
                            continue;
                        }
                        hudWidget2 = widget;
                        break;
                    }
                }
                final HudWidget<?> child2 = (hudWidget2 == null) ? null : hudWidget2.getChild();
                if (child2 != null) {
                    if (!this.labyAPI.minecraft().isKeyPressed(Key.L_SHIFT)) {
                        this.selectionRenderer.unselect(hudWidget2);
                    }
                    this.selectionRenderer.select(child2);
                }
            }
        }
        if (this.shouldIgnoreAlignment() && key == Key.A) {
            for (final HudWidget<?> hudWidget3 : this.registry.values()) {
                if (!hudWidget3.isEnabled()) {
                    continue;
                }
                this.selectionRenderer.select(hudWidget3);
            }
        }
        if (key == Key.ESCAPE && !this.selectionRenderer.getSelected().isEmpty()) {
            this.selectionRenderer.unselectAll();
            return true;
        }
        if (key == Key.L_CONTROL || key == Key.L_WIN) {
            this.updateHudWidgetsInDropzone();
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        if (key == Key.L_CONTROL || key == Key.L_WIN) {
            this.updateHudWidgetsInDropzone();
        }
        return super.keyReleased(key, type);
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return super.shouldHandleEscape() || !this.selectionRenderer.getSelected().isEmpty();
    }
    
    @Override
    public boolean shouldIgnoreAlignment() {
        return KeyHandler.isLeftControlDown();
    }
    
    public void createHudWidget(final HudWidget<?> hudWidget) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        if (hudWidget.canPreInitialize()) {
            hudWidget.initialize(widget);
        }
        ((HudWidgetConfig)hudWidget.getConfig()).setEnabled(true);
        hudWidget.reloadConfig();
        this.updateDraggingState(hudWidget, true);
        this.draggingHudWidget = hudWidget;
        this.hasDraggingHudWidgetMoved = false;
        this.isCreatingHudWidget = true;
        this.mouseWasInRenderer = false;
        final HudSize size = widget.size();
        this.draggingOffsetX = size.getScaledWidth() / 2.0f;
        this.draggingOffsetY = 4.0f;
        this.updatePosition(hudWidget, this.lastMouseX - this.draggingOffsetX, this.lastMouseY - this.draggingOffsetY);
        this.saveHudWidget(hudWidget);
        widget.skipInterpolation();
        Laby.fireEvent(new HudWidgetCreatedEvent(hudWidget));
        this.setStencil(true);
        this.updateHudWidgetsInDropzone();
    }
    
    public void resetHudWidget(final HudWidget<?> hudWidget) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final HudSize size = widget.size();
        final Bounds editorBounds = this.bounds();
        final MutableRectangle defaultRect = Rectangle.relative(editorBounds.getX() + 2.0f, editorBounds.getY() + 2.0f, size.getActualWidth(), size.getActualHeight());
        final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfig();
        config.setDropzoneId(null);
        config.reset();
        config.scale().reset();
        hudWidget.updateSize(widget, true, widget.size());
        final HudWidget<?> parent = hudWidget.getParent();
        if (parent == null) {
            final HudWidget<?> child = hudWidget.getChild();
            if (child != null) {
                child.updateParent(null);
                final ScaledRectangle rectangle = widget.scaledBounds();
                this.updatePosition(child, rectangle.getX(), rectangle.getY());
                this.saveHudWidget(child);
            }
        }
        else {
            hudWidget.updateParent(null);
            final HudWidget<?> child = hudWidget.getChild();
            if (child != null) {
                child.updateParent(parent);
            }
            this.updateHudWidget(parent.firstWidget());
        }
        config.setEnabled(false);
        final HudWidgetWidget targetWidget = this.getTargetAt(defaultRect);
        config.setEnabled(true);
        if (targetWidget == null || targetWidget.hudWidget() == hudWidget) {
            this.updatePosition(hudWidget, defaultRect.getX(), defaultRect.getY());
        }
        else {
            final HudWidget<?> lastWidget = targetWidget.hudWidget().lastWidget();
            hudWidget.updateParent(lastWidget);
            this.updateHudWidget(hudWidget.firstWidget());
        }
        this.saveHudWidget(hudWidget);
        this.updateHudWidgetsInDropzone();
    }
    
    public void destroyHudWidget(@NotNull HudWidget<?> hudWidget, final boolean destroyChildren) {
        while (hudWidget != null) {
            final HudWidget<?> prevParent = hudWidget.getParent();
            ((HudWidgetConfig)hudWidget.getConfig()).setEnabled(false);
            hudWidget.updateParent(null);
            this.saveHudWidget(hudWidget);
            Laby.fireEvent(new HudWidgetDestroyedEvent(hudWidget));
            if (!destroyChildren) {
                final HudWidget<?> child = hudWidget.getChild();
                if (child != null) {
                    child.updateParent(prevParent);
                    if (prevParent == null) {
                        final HudWidgetWidget widget = this.getWidget(hudWidget);
                        final ScaledRectangle rectangle = widget.scaledBounds();
                        this.updatePosition(child, rectangle.getX(), rectangle.getY());
                    }
                    else {
                        this.updateHudWidget(prevParent.firstWidget());
                    }
                    this.saveHudWidget(child);
                    break;
                }
                break;
            }
            else {
                hudWidget = hudWidget.getChild();
            }
        }
        this.draggingHudWidget = null;
        this.selectionRenderer.unselectAll();
        if (this.editor.window().canDropHudWidget()) {
            Laby.references().soundService().play(SoundType.HUD_TRASH, 0.15f);
        }
    }
    
    private void updateOutOfBounds(final HudWidget<?> hudWidget, final boolean canAttachToAlignmentLines, final boolean extendToWrapperBounds) {
        final HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone != null) {
            return;
        }
        final MutableRectangle chain = (MutableRectangle)this.getChainRectangle(hudWidget);
        chain.shiftToBounds((Rectangle)(extendToWrapperBounds ? this.wrapperBounds : this.area));
        if (canAttachToAlignmentLines) {
            for (final RangeAlignmentLine alignmentLine : this.alignmentLines) {
                alignmentLine.align(chain);
            }
        }
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final float offsetX = hudWidget.anchor().getGapX(chain.getWidth(), widget.size().getScaledWidth());
        final float x = chain.getX() + offsetX;
        final float y = chain.getY();
        widget.scaledBounds().setPosition(x, y, HudWidgetInteractionWidget.HUD_WIDGET_INTERACTION);
    }
    
    public Rectangle getChainRectangle(final HudWidget<?> hudWidget) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final ScaledRectangle bounds = widget.scaledBounds();
        final float x = bounds.getX() + hudWidget.anchor().getShiftX(widget.size());
        final float y = bounds.getY();
        final HudSize size = widget.size();
        float width = size.getScaledWidth();
        float height = size.getScaledHeight();
        for (HudWidget<?> child = hudWidget.getChild(); child != null; child = child.getChild()) {
            final HudWidgetWidget childWidget = this.getWidget(child);
            final HudSize childSize = childWidget.size();
            width = Math.max(childSize.getScaledWidth(), width);
            height += childSize.getScaledHeight();
        }
        return Rectangle.relative(x - hudWidget.anchor().getShiftX(width), y, width, height);
    }
    
    public void saveHudWidget(final HudWidget<?> hudWidget) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfig();
        final HudWidgetAnchor anchor = HudWidgetAnchor.of(hudWidget);
        final ScaledRectangle bounds = widget.scaledBounds();
        final HudSize size = widget.size();
        if (!this.isCreatingHudWidget) {
            this.updateOutOfBounds(hudWidget, false, false);
        }
        final float x = bounds.getX();
        final float y = bounds.getY();
        final RectangleArea identifier = this.getArea(hudWidget);
        if (identifier == null) {
            return;
        }
        final FloatVector2 relative = identifier.absoluteToRelative(x + anchor.getShiftX(size), y + anchor.getShiftY(size));
        config.setAreaIdentifier(identifier.position());
        final HudWidgetAnchor newAnchor = HudWidgetAnchor.of(hudWidget);
        if (anchor != newAnchor) {
            relative.add(newAnchor.getShiftX(size) - anchor.getShiftX(size), newAnchor.getShiftY(size) - anchor.getShiftY(size));
        }
        config.setX(relative.getX());
        config.setY(relative.getY());
        config.scale().set(size.getScale());
        if (hudWidget.isResizeable()) {
            final ResizeableHudWidget.ResizeableHudWidgetConfig resizeableConfig = (ResizeableHudWidget.ResizeableHudWidgetConfig)config;
            resizeableConfig.width().set(size.getActualWidth());
            resizeableConfig.height().set(size.getActualHeight());
        }
        final HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone == null) {
            hudWidget.updateAnchor(newAnchor);
        }
        else {
            this.updateHudWidgetInDropzone(widget);
        }
        if (hudWidget.isEnabled()) {
            Laby.fireEvent(new HudWidgetMovedEvent(hudWidget));
        }
        for (HudWidget<?> child = hudWidget.getChild(); child != null; child = child.getChild()) {
            child.updateAnchor(newAnchor);
        }
        this.registry.saveConfig();
    }
    
    @Override
    public void reinitializeHudWidget(final HudWidget<?> hudWidget, final String reason) {
        super.reinitializeHudWidget(hudWidget, reason);
        final HudWidget<?> target = hudWidget.firstWidget();
        this.updateOutOfBounds(target, false, false);
        this.updateHudWidgetChildrenOf(target);
    }
    
    @Override
    public boolean isEditor() {
        return true;
    }
    
    @Override
    public void onSizeChanged(final HudWidget<?> hudWidget) {
        super.onSizeChanged(hudWidget);
    }
    
    @Override
    public boolean canUpdateHudWidget(final HudWidget<?> hudWidget) {
        return super.canUpdateHudWidget(hudWidget) && this.selectionRenderer.canUpdateHudWidget(hudWidget) && hudWidget != this.draggingHudWidget;
    }
    
    public boolean hasDraggingHudWidgetMoved() {
        return this.hasDraggingHudWidgetMoved;
    }
    
    private void updateDraggingState(final HudWidget<?> hudWidget, final boolean state) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        widget.setDragging(state);
        final HudWidget<?> child = hudWidget.getChild();
        if (child != null) {
            this.updateDraggingState(child, state);
        }
    }
    
    private HudWidgetWidget getTargetAt(final Rectangle rectangle) {
        float nearestDistance = Float.MAX_VALUE;
        HudWidgetWidget nearestWidget = null;
        for (final HudWidgetWidget widget : this.children) {
            final HudWidget<?> hudWidget = widget.hudWidget();
            if (hudWidget == this.draggingHudWidget) {
                continue;
            }
            if (hudWidget.firstWidget() == this.draggingHudWidget) {
                continue;
            }
            if (!hudWidget.isEnabled()) {
                continue;
            }
            if (!hudWidget.isHolderEnabled()) {
                continue;
            }
            final Rectangle targetRect = widget.scaledBounds();
            final float distanceRectangle = targetRect.distanceSquared(rectangle);
            if (distanceRectangle > 25.0f) {
                continue;
            }
            final float distanceEdges = Math.min(Math.min(Math.abs(targetRect.getTop() - rectangle.getBottom()), Math.abs(targetRect.getBottom() - rectangle.getTop())), Math.min(Math.abs(targetRect.getTop() - rectangle.getTop()), Math.abs(targetRect.getBottom() - rectangle.getBottom())));
            if (distanceEdges >= nearestDistance) {
                continue;
            }
            nearestDistance = distanceEdges;
            nearestWidget = widget;
        }
        return nearestWidget;
    }
    
    private ChainAlignmentSide getTargetChainAlignmentSide(final HudWidgetWidget widget, final HudWidgetWidget destination) {
        final ScaledRectangle widgetRect = widget.scaledBounds();
        final ScaledRectangle destinationRect = destination.scaledBounds();
        final float distanceToTop = Math.min(Math.abs(destinationRect.getTop() - widgetRect.getBottom()), Math.abs(destinationRect.getTop() - widgetRect.getTop()));
        final float distanceToBottom = Math.min(Math.abs(destinationRect.getBottom() - widgetRect.getBottom()), Math.abs(destinationRect.getBottom() - widgetRect.getTop()));
        return (distanceToBottom > distanceToTop) ? ChainAlignmentSide.TOP : ChainAlignmentSide.BOTTOM;
    }
    
    private HudWidgetDropzone getTargetDropzone(final HudWidgetWidget widget) {
        final HudWidget<?> hudWidget = widget.hudWidget();
        final HudWidgetDropzone[] dropzones = hudWidget.getDropzones();
        HudWidgetDropzone target = null;
        for (final HudWidgetDropzone dropzone : dropzones) {
            if (dropzone.isOverlapping(this, widget) && !this.isDropzoneInUse(dropzone)) {
                target = dropzone;
                break;
            }
        }
        for (final HudWidgetDropzone dropzone : dropzones) {
            if (dropzone.isInside(this, widget) && !this.isDropzoneInUse(dropzone)) {
                return dropzone;
            }
        }
        return target;
    }
    
    @Override
    protected RectangleArea getArea(final HudWidget<?> hudWidget, final float x, final float y) {
        final MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        if (hudWidget == this.draggingHudWidget) {
            final RectangleArea area = super.getArea(hudWidget, (float)mouse.getX(), (float)mouse.getY());
            if (area != null) {
                return area;
            }
        }
        return super.getArea(hudWidget, x, y);
    }
    
    private Rectangle getDraggingChain() {
        return (this.draggingHudWidget == null) ? null : this.getChainRectangle(this.draggingHudWidget);
    }
    
    @Override
    public HudWidget<?> getDraggingHudWidget() {
        return this.draggingHudWidget;
    }
    
    public SelectionRenderer selectionRenderer() {
        return this.selectionRenderer;
    }
    
    public void setMouseOverWindow(final boolean hovered) {
        this.mouseOverWindow = hovered;
    }
    
    public void setOpenSettingsListener(final Consumer<HudWidget<?>> listener) {
        this.openSettingsListener = listener;
    }
    
    @Override
    public void openSettings(final HudWidget<?> hudWidget) {
        if (this.openSettingsListener != null) {
            this.openSettingsListener.accept(hudWidget);
        }
    }
    
    public long getRenderTimePassed() {
        return this.renderTimePassed;
    }
    
    static {
        HUD_WIDGET_INTERACTION = ModifyReason.of("hudWidgetInteraction");
    }
}
