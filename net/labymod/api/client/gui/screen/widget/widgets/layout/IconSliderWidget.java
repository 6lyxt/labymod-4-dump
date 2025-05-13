// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.sound.SoundType;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.function.IntFunction;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import java.util.List;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
@Link("widget/icon-slider.lss")
public class IconSliderWidget extends AbstractWidget<Widget>
{
    private static final ModifyReason LIST_CONTENT;
    private final List<IconWidget> icons;
    private boolean switchedThisFrame;
    private int previousIndex;
    private int index;
    private ButtonWidget previousButton;
    private ButtonWidget nextButton;
    
    public IconSliderWidget() {
        this.icons = new ArrayList<IconWidget>();
        this.addId("icon-slider");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.icons.clear();
        for (final Widget child : this.children) {
            if (child instanceof final IconWidget iconWidget) {
                iconWidget.addId("slider-entry");
                this.icons.add(iconWidget);
            }
        }
        if (this.icons.size() <= 1) {
            return;
        }
        final IconSliderNavigationWidget widget = new IconSliderNavigationWidget(this, index -> {
            final IconSliderNavigationButtonWidget navigationButton = this.createNavigationButton(index);
            navigationButton.addId("slider-navigation-entry");
            return navigationButton;
        });
        ((AbstractWidget<Widget>)widget).addId("slider-navigation-container");
        ((AbstractWidget<IconSliderNavigationWidget>)this).addChild(widget);
        ((AbstractWidget<Widget>)(this.previousButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON))).addId("slider-navigation-button", "slider-navigation-previous-button");
        this.previousButton.setPressable(() -> this.updateIndex(-1));
        this.previousButton.setEnabled(this.index != 0);
        ((AbstractWidget<ButtonWidget>)this).addChild(this.previousButton);
        ((AbstractWidget<Widget>)(this.nextButton = ButtonWidget.icon(Textures.SpriteCommon.FORWARD_BUTTON))).addId("slider-navigation-button", "slider-navigation-next-button");
        this.nextButton.setPressable(() -> this.updateIndex(1));
        this.nextButton.setEnabled(this.index != this.icons.size() - 1);
        ((AbstractWidget<ButtonWidget>)this).addChild(this.nextButton);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (!this.isVisible()) {
            return;
        }
        for (int i = 0; i < this.icons.size(); ++i) {
            if (i == this.index || i == this.previousIndex) {
                final IconWidget icon = this.icons.get(i);
                icon.render(context);
            }
        }
        for (final Widget child : this.children) {
            if (child instanceof IconWidget && this.icons.contains(child)) {
                continue;
            }
            child.render(context);
        }
        this.switchedThisFrame = false;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ARROW_LEFT || key == Key.ARROW_RIGHT) {
            this.updateIndex((key == Key.ARROW_LEFT) ? -1 : 1);
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    protected IconSliderNavigationButtonWidget createNavigationButton(final int index) {
        final IconSliderNavigationButtonWidget widget = new IconSliderNavigationButtonWidget(this, index);
        widget.setPressListener(() -> this.updateIndexPosition(index));
        return widget;
    }
    
    protected void updateIndex(final int direction) {
        this.updateIndexPosition(this.index + direction);
    }
    
    protected boolean updateIndexPosition(final int newIndex) {
        if (this.index == newIndex || newIndex < 0 || newIndex >= this.icons.size()) {
            return false;
        }
        this.previousIndex = this.index;
        this.index = newIndex;
        this.switchedThisFrame = true;
        final float width = this.bounds().getWidth();
        final float translateX = (this.index == 0) ? 0.0f : (-width * this.index);
        for (final IconWidget icon : this.icons) {
            icon.translateX().set(translateX);
        }
        this.previousButton.setEnabled(this.index != 0);
        this.nextButton.setEnabled(this.index != this.icons.size() - 1);
        return true;
    }
    
    @Override
    protected void updateContentBounds() {
        super.updateContentBounds();
        final Bounds bounds = this.bounds();
        float x = bounds.getX();
        final float y = bounds.getY();
        final float width = bounds.getWidth();
        final float height = bounds.getHeight();
        for (final IconWidget icon : this.icons) {
            final Bounds iconBounds = icon.bounds();
            iconBounds.setOuterPosition(x, y, IconSliderWidget.LIST_CONTENT);
            iconBounds.setOuterSize(width, height, IconSliderWidget.LIST_CONTENT);
            x += width;
        }
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return child instanceof IconWidget && this.icons.contains(child);
    }
    
    static {
        LIST_CONTENT = ModifyReason.of("sliderContent");
    }
    
    @AutoWidget
    public static class IconSliderNavigationWidget extends AbstractWidget<IconSliderNavigationButtonWidget>
    {
        private final IconSliderWidget slider;
        private final LssProperty<Float> spaceBetweenEntries;
        private final IntFunction<IconSliderNavigationButtonWidget> childSupplier;
        
        protected IconSliderNavigationWidget(final IconSliderWidget slider, final IntFunction<IconSliderNavigationButtonWidget> childSupplier) {
            this.spaceBetweenEntries = new LssProperty<Float>(2.0f);
            this.slider = slider;
            this.childSupplier = childSupplier;
        }
        
        @Override
        public void renderWidget(final ScreenContext context) {
            if (this.slider.switchedThisFrame) {
                for (int i = 0; i < this.children.size(); ++i) {
                    final IconSliderNavigationButtonWidget child = (IconSliderNavigationButtonWidget)this.children.get(i);
                    if (i == this.slider.index) {
                        child.addId("selected");
                    }
                    else {
                        child.removeId("selected");
                    }
                }
            }
            super.renderWidget(context);
        }
        
        @Override
        public void initialize(final Parent parent) {
            super.initialize(parent);
            for (int i = 0; i < this.slider.icons.size(); ++i) {
                final IconSliderNavigationButtonWidget child = this.childSupplier.apply(i);
                if (i == this.slider.index) {
                    child.addId("selected");
                }
                this.addChild(child);
            }
        }
        
        @Override
        protected void updateContentBounds() {
            super.updateContentBounds();
            float contentWidth = 0.0f;
            for (final IconSliderNavigationButtonWidget child : this.children) {
                contentWidth += child.bounds().getWidth(BoundsType.OUTER);
            }
            final Bounds bounds = this.bounds();
            float x = bounds.getCenterX() - contentWidth / 2.0f;
            final float y = bounds.getY();
            final float height = bounds.getHeight();
            final float spaceBetweenEntries = this.spaceBetweenEntries.get();
            for (final IconSliderNavigationButtonWidget child2 : this.children) {
                final Bounds childBounds = child2.bounds();
                float entryY = y;
                final WidgetAlignment widgetAlignment = child2.alignmentY().get();
                if (widgetAlignment == WidgetAlignment.CENTER) {
                    entryY += height / 2.0f - childBounds.getHeight(BoundsType.OUTER) / 2.0f;
                }
                else if (widgetAlignment == WidgetAlignment.BOTTOM) {
                    entryY += height - childBounds.getHeight(BoundsType.OUTER);
                }
                childBounds.setOuterPosition(x, entryY, IconSliderWidget.LIST_CONTENT);
                x += childBounds.getWidth(BoundsType.OUTER) + spaceBetweenEntries;
            }
        }
        
        @Override
        public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
            return type == AutoAlignType.POSITION;
        }
        
        @Override
        public float getContentHeight(final BoundsType type) {
            float height = 0.0f;
            for (final IconSliderNavigationButtonWidget child : this.children) {
                if (!child.isVisible()) {
                    continue;
                }
                height = Math.max(height, child.bounds().getHeight(BoundsType.OUTER));
            }
            return height + this.bounds().getVerticalOffset(type);
        }
        
        public LssProperty<Float> spaceBetweenEntries() {
            return this.spaceBetweenEntries;
        }
        
        public IconSliderWidget slider() {
            return this.slider;
        }
    }
    
    @AutoWidget
    public static class IconSliderNavigationButtonWidget extends AbstractWidget<Widget>
    {
        protected final IconSliderWidget sliderWidget;
        protected final int index;
        
        protected IconSliderNavigationButtonWidget(final IconSliderWidget sliderWidget, final int index) {
            this.sliderWidget = sliderWidget;
            this.index = index;
        }
        
        @Override
        protected SoundType getInteractionSound() {
            return SoundType.BUTTON_CLICK;
        }
        
        @Override
        protected boolean playInteractionSoundAfterHandling() {
            return true;
        }
        
        public boolean isCurrentIcon() {
            return this.sliderWidget.index == this.index;
        }
    }
}
