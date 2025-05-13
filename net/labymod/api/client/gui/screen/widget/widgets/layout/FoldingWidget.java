// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.property.Property;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;

@AutoWidget
public class FoldingWidget extends ListWidget<Widget>
{
    private static final ModifyReason LIST_CONTENT;
    private final LssProperty<Float> contentOffset;
    private final LssProperty<Boolean> previewExpand;
    private final Widget previewWidget;
    private final Widget contentWidget;
    private IconWidget unfoldWidget;
    private FlexibleContentWidget previewWrapper;
    private boolean expanded;
    private boolean alwaysWrapPreview;
    
    @Deprecated
    public FoldingWidget(final Widget previewWidget, final Widget contentWidget, final boolean expanded) {
        this.contentOffset = new LssProperty<Float>(0.0f);
        this.previewExpand = new LssProperty<Boolean>(false);
        this.alwaysWrapPreview = false;
        this.previewWidget = previewWidget;
        this.contentWidget = contentWidget;
        this.expanded = expanded;
        this.previewWidget.addId("folding-preview");
        this.contentWidget.addId("folding-content");
        this.translateY().addChangeListener((property, oldValue, newValue) -> {
            if (this.parent != null && !(this.parent instanceof ListWidget)) {
                this.updateVisibility(this, this.parent);
            }
        });
    }
    
    @Deprecated
    public FoldingWidget(final Widget previewWidget, final Widget contentWidget) {
        this(previewWidget, contentWidget, false);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget previewWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)previewWrapper).addId("preview-wrapper");
        previewWrapper.addFlexibleContent(this.previewWidget);
        final DivWidget unfoldWrapper = new DivWidget();
        unfoldWrapper.addId("unfold-wrapper");
        unfoldWrapper.setPressable(() -> {
            this.labyAPI.minecraft().sounds().playButtonPress();
            this.setExpanded(!this.expanded);
            return;
        });
        (this.unfoldWidget = new IconWidget(this.unfoldIcon())).addId("unfold-widget");
        this.unfoldWidget.setAttributeState(AttributeState.ENABLED, true);
        ((AbstractWidget<IconWidget>)unfoldWrapper).addChild(this.unfoldWidget);
        previewWrapper.addContent(unfoldWrapper);
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(this.previewWrapper = previewWrapper);
        if (this.expanded) {
            this.addChild(this.contentWidget);
        }
        this.setAttributeState(AttributeState.ACTIVE, this.expanded);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.contentWidget.setVisible(this.expanded);
        super.renderWidget(context);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton != MouseButton.LEFT || !this.previewExpand.get() || !this.previewWrapper.bounds().isInRectangle(BoundsType.BORDER, mouse)) {
            return false;
        }
        this.labyAPI.minecraft().sounds().playButtonPress();
        this.setExpanded(!this.expanded);
        return true;
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.update();
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return this.bounds().getWidth(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        float height = 0.0f;
        if (this.previewWrapper != null) {
            height += this.previewWrapper.bounds().getHeight(type);
        }
        if (this.expanded) {
            height += this.contentWidget.bounds().getHeight(BoundsType.OUTER);
        }
        return height + this.bounds().getVerticalOffset(type);
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return type != AutoAlignType.HEIGHT && (child == this.previewWrapper || child == this.contentWidget);
    }
    
    protected Icon unfoldIcon() {
        return this.expanded ? Textures.SpriteCommon.SMALL_UP_SHADOW : Textures.SpriteCommon.SMALL_DOWN_SHADOW;
    }
    
    protected void update() {
        if (this.previewWrapper == null) {
            return;
        }
        final Bounds bounds = this.bounds();
        final float x = bounds.getX();
        final float y = bounds.getY();
        final float width = bounds.getWidth();
        final ReasonableMutableRectangle preview = this.previewWrapper.bounds().rectangle(BoundsType.OUTER);
        preview.setPosition(x, y, FoldingWidget.LIST_CONTENT);
        preview.setWidth(width, FoldingWidget.LIST_CONTENT);
        float height = preview.getHeight();
        if (this.expanded) {
            final float contentOffset = this.contentOffset.get();
            final ReasonableMutableRectangle content = this.contentWidget.bounds().rectangle(BoundsType.OUTER);
            content.setPosition(x + contentOffset, y + height, FoldingWidget.LIST_CONTENT);
            content.setWidth(width - contentOffset, FoldingWidget.LIST_CONTENT);
            height += content.getHeight();
        }
        bounds.setHeight(height, FoldingWidget.LIST_CONTENT);
    }
    
    public LssProperty<Float> contentOffset() {
        return this.contentOffset;
    }
    
    public LssProperty<Boolean> previewExpand() {
        return this.previewExpand;
    }
    
    public Widget contentWidget() {
        return this.contentWidget;
    }
    
    public Widget previewWidget() {
        return this.previewWidget;
    }
    
    public FoldingWidget alwaysWrapPreview(final boolean alwaysWrapPreview) {
        this.alwaysWrapPreview = alwaysWrapPreview;
        return this;
    }
    
    public boolean isAlwaysWrappingPreview() {
        return this.alwaysWrapPreview;
    }
    
    public boolean isExpanded() {
        return this.expanded;
    }
    
    public void setExpanded(final boolean expanded) {
        if (expanded) {
            final Widget firstChildIf = this.findFirstChildIf(widget -> widget == this.contentWidget);
            if (firstChildIf == null) {
                this.addChildInitialized(this.contentWidget);
                this.contentWidget.setTranslateY(this.getTranslateY());
            }
        }
        this.expanded = expanded;
        this.setAttributeState(AttributeState.ACTIVE, expanded);
        if (this.unfoldWidget != null) {
            this.unfoldWidget.icon().set(this.unfoldIcon());
        }
        this.update();
        ((Widget)this.parent).updateBounds();
        ((Widget)this.parent.getParent()).updateBounds();
        if (this.parent instanceof ListWidget) {
            final ListWidget<?> listWidget = (ListWidget<?>)this.parent;
            listWidget.updateVisibility(listWidget, listWidget.getParent());
        }
    }
    
    static {
        LIST_CONTENT = ModifyReason.of("listContent");
    }
}
