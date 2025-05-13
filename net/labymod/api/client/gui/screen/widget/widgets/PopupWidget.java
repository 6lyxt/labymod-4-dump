// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@Deprecated
@AutoWidget
@Link("activity/popup.lss")
public class PopupWidget extends SimpleWidget
{
    private final Component title;
    private final Component text;
    private final Icon icon;
    private final Component confirmComponent;
    private final Component cancelComponent;
    private final boolean closable;
    private final Pressable confirmationCallback;
    private final Pressable cancelCallback;
    private final Supplier<Widget> widgetSupplier;
    private WidgetReference overlayReference;
    private ComponentWidget titleComponent;
    private ComponentWidget textComponent;
    private IconWidget iconWidget;
    private ButtonWidget confirmButton;
    private ButtonWidget cancelButton;
    private DivWidget wrapper;
    
    public PopupWidget(final Component title, final Component text, final Icon icon, final Supplier<Widget> widgetSupplier, final Component confirmComponent, final Component cancelComponent, final boolean closable, final Pressable confirmationCallback, final Pressable cancelCallback) {
        this.title = title;
        this.text = text;
        this.icon = icon;
        this.widgetSupplier = widgetSupplier;
        this.confirmComponent = confirmComponent;
        this.cancelComponent = cancelComponent;
        this.closable = closable;
        this.confirmationCallback = confirmationCallback;
        this.cancelCallback = cancelCallback;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.wrapper = new DivWidget()).addId("label-wrapper");
        final VerticalListWidget<Widget> labelContainer = new VerticalListWidget<Widget>();
        labelContainer.addId("label-container");
        labelContainer.addChild(this.titleComponent = ComponentWidget.component(this.title).addId("popup-title"));
        if (this.text != null) {
            labelContainer.addChild(this.textComponent = ComponentWidget.component(this.text).addId("popup-text"));
        }
        if (this.icon != null) {
            final DivWidget iconContainer = new DivWidget();
            iconContainer.addId("icon-container");
            ((AbstractWidget<IconWidget>)iconContainer).addChild(this.iconWidget = new IconWidget(this.icon));
            labelContainer.addChild(iconContainer);
        }
        if (this.widgetSupplier != null) {
            labelContainer.addChild(this.widgetSupplier.get()).addId("popup-content");
        }
        final HorizontalListWidget buttonContainer = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonContainer).addId("button-container");
        if (this.confirmationCallback != null) {
            ((AbstractWidget<Widget>)(this.confirmButton = ButtonWidget.component(this.confirmComponent, () -> {
                this.confirmationCallback.press();
                this.close();
                return;
            }))).addId("confirm");
            buttonContainer.addEntry(this.confirmButton);
        }
        if (this.cancelCallback != null) {
            ((AbstractWidget<Widget>)(this.cancelButton = ButtonWidget.component(this.cancelComponent, () -> {
                this.cancelCallback.press();
                this.close();
                return;
            }))).addId("cancel");
            buttonContainer.addEntry(this.cancelButton);
        }
        labelContainer.addChild(buttonContainer);
        ((AbstractWidget<VerticalListWidget<Widget>>)this.wrapper).addChild(labelContainer);
        ((AbstractWidget<DivWidget>)this).addChild(this.wrapper);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (this.overlayReference == null || this.wrapper == null || this.wrapper.bounds().isInRectangle(BoundsType.OUTER, mouse)) {
            return false;
        }
        if (this.closable) {
            this.close();
        }
        return true;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ESCAPE) {
            this.close();
            return true;
        }
        if (key == Key.ENTER) {
            this.confirmationCallback.press();
            this.close();
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public boolean isHovered() {
        if (this.wrapper != null) {
            final MutableMouse mouse = this.labyAPI.minecraft().mouse();
            return this.wrapper.bounds().isInRectangle(mouse);
        }
        return super.isHovered();
    }
    
    public void displayInOverlay() {
        this.displayInOverlay(new ArrayList<StyleSheet>());
    }
    
    @Override
    public WidgetReference displayInOverlay(final List<StyleSheet> styles, final Widget widget) {
        this.overlayReference = super.displayInOverlay(styles, widget);
        if (this.overlayReference == null) {
            throw new IllegalStateException("Widget overlay not initialized yet");
        }
        this.overlayReference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        this.overlayReference.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
        return this.overlayReference;
    }
    
    public void close() {
        if (this.overlayReference != null && this.overlayReference.isAlive()) {
            this.overlayReference.remove();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.close();
    }
    
    @NotNull
    public Component title() {
        return this.title;
    }
    
    @NotNull
    public Component confirmComponent() {
        return this.confirmComponent;
    }
    
    @NotNull
    public Component cancelComponent() {
        return this.cancelComponent;
    }
    
    public void setTitle(final Component title) {
        this.titleComponent.setComponent(title);
    }
    
    public void setText(final Component text) {
        this.textComponent.setComponent(text);
    }
    
    public void setIcon(final Icon icon) {
        this.iconWidget.icon().set(icon);
    }
    
    public void setConfirmComponent(final Component confirmComponent) {
        this.confirmButton.updateComponent(confirmComponent);
    }
    
    public void setCancelComponent(final Component cancelComponent) {
        this.cancelButton.updateComponent(cancelComponent);
    }
    
    public static class Builder
    {
        private Component title;
        private Component confirmComponent;
        private Component cancelComponent;
        private Pressable confirmationCallback;
        private Pressable cancelCallback;
        private Supplier<Widget> widgetSupplier;
        private Component text;
        private boolean closable;
        private Icon icon;
        
        public Builder() {
            this.closable = true;
        }
        
        public Builder confirmComponent(final Component component) {
            this.confirmComponent = component;
            return this;
        }
        
        public Builder title(final Component title) {
            this.title = title;
            return this;
        }
        
        public Builder text(final Component text) {
            this.text = text;
            return this;
        }
        
        public Builder icon(final Icon icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder cancelComponent(final Component cancelComponent) {
            this.cancelComponent = cancelComponent;
            return this;
        }
        
        public Builder confirmCallback(final Pressable confirmationCallback) {
            this.confirmationCallback = confirmationCallback;
            return this;
        }
        
        public Builder cancelCallback(final Pressable cancelCallback) {
            this.cancelCallback = cancelCallback;
            return this;
        }
        
        public Builder widgetSupplier(final Supplier<Widget> widgetSupplier) {
            this.widgetSupplier = widgetSupplier;
            return this;
        }
        
        public Builder notClosable() {
            this.closable = false;
            return this;
        }
        
        public PopupWidget build() {
            if (this.confirmComponent == null) {
                this.confirmComponent = Component.translatable("labymod.ui.button.confirm", new Component[0]);
            }
            if (this.cancelComponent == null) {
                this.cancelComponent = Component.translatable("labymod.ui.button.cancel", new Component[0]);
            }
            return new PopupWidget(this.title, this.text, this.icon, this.widgetSupplier, this.confirmComponent, this.cancelComponent, this.closable, this.confirmationCallback, this.cancelCallback);
        }
    }
}
