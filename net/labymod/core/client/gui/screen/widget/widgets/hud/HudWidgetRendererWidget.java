// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.position.VerticalHudWidgetAlignment;
import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.widget.widgets.hud.ScaledRectangle;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.bounds.area.RectangleArea;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.core.client.render.font.text.TextUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.bounds.Rectangle;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.util.bounds.area.AreaRectangle;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class HudWidgetRendererWidget extends AbstractWidget<HudWidgetWidget> implements HudWidgetRendererAccessor
{
    private static final ModifyReason HUD_WIDGET_RENDERER;
    protected final AreaRectangle area;
    protected final HudWidgetRegistry registry;
    private boolean debugEnabled;
    
    public HudWidgetRendererWidget() {
        this.area = new AreaRectangle();
        this.registry = Laby.references().hudWidgetRegistry();
    }
    
    public void addHudWidget(final HudWidget<?> hudWidget) {
        this.addChildInitialized(new HudWidgetWidget(hudWidget, this));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final HudWidget<?> hudWidget : this.registry.values()) {
            this.addChild(new HudWidgetWidget(hudWidget, this));
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.area.set(this.bounds());
        this.updateHudWidgets();
    }
    
    @Override
    public void render(final ScreenContext context) {
        this.debugEnabled = this.labyAPI.minecraft().options().isDebugEnabled();
        super.render(context);
    }
    
    public void updateHudWidgets() {
        for (final HudWidgetWidget widget : this.children) {
            final HudWidget<?> hudWidget = widget.hudWidget();
            if (hudWidget.getParent() != null) {
                continue;
            }
            if (!hudWidget.isEnabled()) {
                continue;
            }
            if (!this.canUpdateHudWidget(hudWidget)) {
                continue;
            }
            this.updateHudWidget(hudWidget);
        }
    }
    
    public void updateHudWidget(final HudWidget<?> hudWidget) {
        if (!hudWidget.isEnabled()) {
            throw new IllegalStateException("Cannot update hud widget that is not enabled");
        }
        if (hudWidget.getParent() != null) {
            throw new IllegalStateException("The hud widget has to be the first hud widget in the chain");
        }
        TextUtil.pushAndApplyAttributes();
        final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfig();
        final RectangleAreaPosition identifier = config.areaIdentifier();
        final RectangleArea rectangle = this.area.getArea(identifier);
        final FloatVector2 position = rectangle.relativeToAbsolute(config.getX(), config.getY());
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final HudWidgetAnchor anchor = HudWidgetAnchor.of(hudWidget);
        final ScaledRectangle bounds = widget.scaledBounds();
        final HudSize size = widget.size();
        final HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone == null) {
            hudWidget.updateAnchor(anchor);
            bounds.setPosition((float)(int)(position.getX() - anchor.getShiftX(size)), (float)(int)(position.getY() - anchor.getShiftY(size)), HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
        }
        else {
            this.updateHudWidgetInDropzone(widget);
        }
        hudWidget.updateSize(widget, this.isEditor(), widget.size());
        widget.updateSizeOfWidget();
        this.updateOutOfBounds(hudWidget);
        this.updateHudWidgetChildrenOf(hudWidget);
        TextUtil.popRenderAttributes();
    }
    
    public void updateHudWidgetChildrenOf(final HudWidget<?> parentHudWidget) {
        final HudWidget<?> hudWidget = parentHudWidget.getNextVisibleChild(this.isEditor());
        if (hudWidget == null) {
            return;
        }
        final HudWidget<?> firstHudWidget = hudWidget.firstWidget();
        final HudWidgetConfig firstWidgetConfig = (HudWidgetConfig)firstHudWidget.getConfig();
        final HorizontalHudWidgetAlignment alignment = firstWidgetConfig.horizontalAlignment().get();
        final HudWidgetWidget parentWidget = this.getWidget(parentHudWidget);
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final ScaledRectangle parentBounds = parentWidget.scaledBounds();
        final RectangleArea originRectangleArea = this.getArea(firstHudWidget);
        HudWidgetAnchor anchor = parentHudWidget.anchor();
        if (originRectangleArea != null) {
            anchor = HudWidgetAnchor.of(originRectangleArea.position(), alignment);
            hudWidget.updateAnchor(anchor);
        }
        final float anchorX = parentBounds.getX() + anchor.getShiftX(parentWidget.size());
        final float anchorY = (parentHudWidget.isVisibleInGame() || this.isEditor()) ? parentBounds.getBottom() : parentBounds.getTop();
        final ScaledRectangle bounds = widget.scaledBounds();
        final HudSize size = widget.size();
        bounds.setPosition(anchorX - anchor.getShiftX(size), anchorY, HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
        widget.updateSizeOfWidget();
        this.updateHudWidgetChildrenOf(hudWidget);
    }
    
    public void updateHudWidgetsInDropzone() {
        final HudWidget<?> draggingHudWidget = this.getDraggingHudWidget();
        for (final HudWidgetWidget widget : this.children) {
            if (widget.hudWidget() == draggingHudWidget) {
                continue;
            }
            this.updateHudWidgetInDropzone(widget);
        }
    }
    
    public void updateHudWidgetInDropzone(final HudWidgetWidget widget) {
        final HudWidget<?> hudWidget = widget.hudWidget();
        final HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone == null || !hudWidget.isEnabled()) {
            return;
        }
        final HudSize size = widget.size();
        final ScaledRectangle bounds = widget.scaledBounds();
        hudWidget.updateAnchor(dropzone.getAnchor());
        final float x = dropzone.getX(this, size);
        final float y = dropzone.getY(this, size);
        bounds.setPosition((float)(int)x, (float)(int)y, HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
    }
    
    private void updateOutOfBounds(final HudWidget<?> hudWidget) {
        final HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone != null) {
            return;
        }
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final ScaledRectangle rectangle = widget.scaledBounds();
        if (rectangle.getRight() > this.area.getRight()) {
            rectangle.setX(this.area.getRight() - rectangle.getWidth(), HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
        }
        if (rectangle.getBottom() > this.area.getBottom()) {
            rectangle.setY(this.area.getBottom() - rectangle.getHeight(), HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
        }
        if (rectangle.getLeft() < this.area.getLeft()) {
            rectangle.setX(this.area.getLeft(), HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
        }
        if (rectangle.getTop() < this.area.getTop()) {
            rectangle.setY(this.area.getTop(), HudWidgetRendererWidget.HUD_WIDGET_RENDERER);
        }
    }
    
    public void reinitializeHudWidget(final HudWidget<?> hudWidget, final String reason) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        widget.update(reason);
        this.updateHudWidget(hudWidget.firstWidget());
        hudWidget.onUpdate();
        if (hudWidget.getAttachedDropzone() != null) {
            this.updateHudWidgetsInDropzone();
        }
    }
    
    protected RectangleArea getArea(final HudWidget<?> hudWidget) {
        final HudWidgetWidget widget = this.getWidget(hudWidget);
        final ScaledRectangle rectangle = widget.scaledBounds();
        final float x = rectangle.getX() + hudWidget.anchor().getShiftX(widget.size());
        final float y = rectangle.getY();
        return this.getArea(hudWidget, x, y);
    }
    
    protected RectangleArea getArea(final HudWidget<?> hudWidget, float x, float y) {
        final VerticalHudWidgetAlignment verticalAlignment = ((HudWidgetConfig)hudWidget.getConfig()).verticalOrientation().get();
        switch (verticalAlignment) {
            case TOP: {
                y = this.area.getTop();
                break;
            }
            case CENTER: {
                y = this.area.getCenterY();
                break;
            }
            case BOTTOM: {
                y = this.area.getBottom();
                break;
            }
        }
        final HorizontalHudWidgetAlignment horizontalAlignment = ((HudWidgetConfig)hudWidget.getConfig()).horizontalOrientation().get();
        switch (horizontalAlignment) {
            case LEFT: {
                x = this.area.getLeft();
                break;
            }
            case CENTER: {
                x = this.area.getCenterX();
                break;
            }
            case RIGHT: {
                x = this.area.getRight();
                break;
            }
        }
        return this.area.getArea(x, y);
    }
    
    @Override
    public boolean canUpdateHudWidget(final HudWidget<?> hudWidget) {
        return true;
    }
    
    @NotNull
    @Override
    public HudWidgetWidget getWidget(@NotNull final HudWidget<?> hudWidget) {
        for (final HudWidgetWidget child : this.children) {
            if (child.hudWidget().equals(hudWidget)) {
                return child;
            }
        }
        throw new IllegalStateException("No widget found for " + hudWidget.getId());
    }
    
    @Override
    public void onVisibilityChanged(final HudWidget<?> hudWidget) {
        final HudWidget<?> firstWidget = hudWidget.firstWidget();
        if (firstWidget.isEnabled() && this.canUpdateHudWidget(firstWidget)) {
            this.updateHudWidget(firstWidget);
        }
        if (((HudWidgetConfig)firstWidget.getConfig()).getDropzoneId() != null) {
            this.updateHudWidgetsInDropzonesOf(firstWidget);
        }
    }
    
    private void updateHudWidgetsInDropzonesOf(final HudWidget<?> hudWidget) {
        for (final HudWidgetDropzone dropzone : hudWidget.getDropzones()) {
            final HudWidget<?> hudWidgetInDropzone = this.getHudWidgetInDropzone(dropzone);
            if (hudWidgetInDropzone != null) {
                if (hudWidgetInDropzone != hudWidget) {
                    this.updateHudWidget(hudWidgetInDropzone);
                }
            }
        }
    }
    
    @Override
    public HudWidget<?> getHudWidgetInDropzone(final String dropzoneId) {
        for (final HudWidgetWidget widget : this.children) {
            final HudWidget<?> hudWidget = widget.hudWidget();
            if (hudWidget.isEnabled()) {
                if (!this.canUpdateHudWidget(hudWidget)) {
                    continue;
                }
                final String id = ((HudWidgetConfig)hudWidget.getConfig()).getDropzoneId();
                if (id != null && id.equals(dropzoneId)) {
                    return hudWidget;
                }
                continue;
            }
        }
        return null;
    }
    
    @Override
    public void onSizeChanged(final HudWidget<?> hudWidget) {
        final HudWidget<?> firstWidget = hudWidget.firstWidget();
        if (firstWidget.isEnabled()) {
            if (this.canUpdateHudWidget(firstWidget)) {
                this.updateHudWidget(firstWidget);
                if (hudWidget.anchor().isRight()) {
                    this.getWidget(hudWidget).skipInterpolation();
                }
            }
            else {
                final HudWidgetWidget widget = this.getWidget(hudWidget);
                widget.updateSizeOfWidget();
            }
        }
        this.updateHudWidgetsInDropzone();
    }
    
    @Override
    public Rectangle getArea() {
        return this.area;
    }
    
    @Override
    public boolean isEditor() {
        return false;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }
    
    @Override
    public boolean isVisible() {
        final boolean editor = this.isEditor();
        if (!editor && !this.labyAPI.minecraft().isIngame()) {
            return false;
        }
        if (editor) {
            return true;
        }
        for (final HudWidgetWidget child : this.getChildren()) {
            final HudWidget<?> hudWidget = child.hudWidget();
            if (hudWidget.isEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        HUD_WIDGET_RENDERER = ModifyReason.of("hudWidgetRenderer");
    }
}
