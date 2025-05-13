// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.popup;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Locale;
import org.jetbrains.annotations.Nullable;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;

public class SimpleAdvancedPopup extends AdvancedPopup
{
    protected Component title;
    protected Component description;
    protected Consumer<VerticalListWidget<Widget>> widgetFunction;
    protected List<SimplePopupButton> buttons;
    
    protected SimpleAdvancedPopup() {
        this(null, null, null, null);
    }
    
    protected SimpleAdvancedPopup(final Component title, final Component description, final Consumer<VerticalListWidget<Widget>> widgetFunction, final List<SimplePopupButton> buttons) {
        this.title = title;
        this.description = description;
        this.widgetFunction = widgetFunction;
        if (buttons == null) {
            this.buttons = null;
        }
        else {
            this.buttons = List.copyOf((Collection<? extends SimplePopupButton>)buttons);
            for (final SimplePopupButton button : this.buttons) {
                button.setAttachedPopup(this);
            }
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public Widget initialize() {
        final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
        this.initializeComponents(container);
        this.initializeCustomWidgets(container);
        this.initializeButtons(container);
        return container;
    }
    
    protected void initializeComponents(final VerticalListWidget<Widget> container) {
        if (this.title != null) {
            container.addChild(ComponentWidget.component(this.title).addId("popup-title"));
        }
        if (this.description != null) {
            container.addChild(ComponentWidget.component(this.description).addId("popup-description"));
        }
    }
    
    protected void initializeCustomWidgets(final VerticalListWidget<Widget> container) {
        if (this.widgetFunction != null) {
            this.widgetFunction.accept(container);
        }
    }
    
    protected void initializeButtons(final VerticalListWidget<Widget> container) {
        if (this.buttons == null) {
            return;
        }
        final HorizontalListWidget buttonContainer = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonContainer).addId("popup-button-container");
        for (final SimplePopupButton button : this.buttons) {
            final ButtonWidget buttonWidget = this.createButton(button);
            if (buttonWidget != null) {
                buttonContainer.addEntry(buttonWidget);
            }
        }
        if (!buttonContainer.getChildren().isEmpty()) {
            container.addChild(buttonContainer);
        }
    }
    
    protected ButtonWidget createButton(final SimplePopupButton button) {
        final ButtonWidget buttonWidget = ButtonWidget.component(button.text());
        ((AbstractWidget<Widget>)buttonWidget).addId("popup-button");
        if (button.getIdentifier() != null) {
            ((AbstractWidget<Widget>)buttonWidget).addId("popup-button-" + button.getIdentifier());
        }
        buttonWidget.setEnabled(button.isEnabled());
        buttonWidget.setActive(!button.isEnabled());
        buttonWidget.setPressable(() -> {
            if (button.getClickListener() != null) {
                button.getClickListener().accept(button);
            }
            this.close();
            return;
        });
        final ButtonWidget obj = buttonWidget;
        Objects.requireNonNull(obj);
        button.setEnabledConsumer(obj::setEnabled);
        return buttonWidget;
    }
    
    public static class Builder
    {
        protected Component title;
        protected Component description;
        protected Consumer<VerticalListWidget<Widget>> widgetFunction;
        protected List<SimplePopupButton> buttons;
        
        protected Builder() {
        }
        
        @NotNull
        public Builder title(final Component title) {
            this.title = title;
            return this;
        }
        
        @NotNull
        public Builder description(final Component description) {
            this.description = description;
            return this;
        }
        
        @NotNull
        public Builder widgets(final Consumer<VerticalListWidget<Widget>> widgetFunction) {
            this.widgetFunction = widgetFunction;
            return this;
        }
        
        @NotNull
        public Builder widget(final Supplier<Widget> widgetSupplier) {
            return this.widgets(container -> container.addChild(widgetSupplier.get().addId("popup-custom-content")));
        }
        
        @NotNull
        public Builder addButton(final SimplePopupButton button) {
            if (this.buttons == null) {
                this.buttons = new ArrayList<SimplePopupButton>();
            }
            this.buttons.add(button);
            return this;
        }
        
        @NotNull
        public Builder addButtons(final List<SimplePopupButton> buttons) {
            if (this.buttons == null) {
                this.buttons = new ArrayList<SimplePopupButton>();
            }
            this.buttons.addAll(buttons);
            return this;
        }
        
        @NotNull
        public Builder addButtons(final SimplePopupButton... buttons) {
            if (this.buttons == null) {
                this.buttons = new ArrayList<SimplePopupButton>();
            }
            this.buttons.addAll(Arrays.asList(buttons));
            return this;
        }
        
        @NotNull
        public SimpleAdvancedPopup build() {
            return new SimpleAdvancedPopup(this.title, this.description, this.widgetFunction, this.buttons);
        }
    }
    
    public static class SimplePopupButton
    {
        private final String identifier;
        private final Component text;
        private final Consumer<SimplePopupButton> clickListener;
        private SimpleAdvancedPopup attachedPopup;
        private boolean enabled;
        private BooleanConsumer enabledConsumer;
        
        protected SimplePopupButton(@Nullable final String identifier, @NotNull final Component text, @Nullable final Consumer<SimplePopupButton> clickListener) {
            Objects.requireNonNull(text, "text");
            this.identifier = ((identifier == null) ? null : identifier.toLowerCase(Locale.ROOT));
            this.text = text;
            this.clickListener = clickListener;
            this.enabled = true;
        }
        
        @NotNull
        public static SimplePopupButton create(@NotNull final Component text) {
            return new SimplePopupButton(null, text, null);
        }
        
        @NotNull
        public static SimplePopupButton create(@NotNull final Component text, @Nullable final Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton(null, text, clickListener);
        }
        
        @NotNull
        public static SimplePopupButton create(@Nullable final String identifier, @NotNull final Component text, @Nullable final Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton(identifier, text, clickListener);
        }
        
        @NotNull
        public static SimplePopupButton create(@Nullable final String identifier, @NotNull final Component text) {
            return new SimplePopupButton(identifier, text, null);
        }
        
        @NotNull
        public static SimplePopupButton confirm(@Nullable final Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton("confirm", Component.translatable("labymod.ui.button.confirm", new Component[0]), clickListener);
        }
        
        @NotNull
        public static SimplePopupButton confirm() {
            return confirm(null);
        }
        
        @NotNull
        public static SimplePopupButton cancel(@Nullable final Consumer<SimplePopupButton> clickListener) {
            return new SimplePopupButton("cancel", Component.translatable("labymod.ui.button.cancel", new Component[0]), clickListener);
        }
        
        @NotNull
        public static SimplePopupButton cancel() {
            return cancel(null);
        }
        
        @NotNull
        public SimplePopupButton enabled(final boolean enabled) {
            if (this.enabled == enabled) {
                return this;
            }
            this.enabled = enabled;
            if (this.enabledConsumer != null) {
                this.enabledConsumer.accept(enabled);
            }
            return this;
        }
        
        @Nullable
        public String getIdentifier() {
            return this.identifier;
        }
        
        @NotNull
        public Component text() {
            return this.text;
        }
        
        @Nullable
        public Consumer<SimplePopupButton> getClickListener() {
            return this.clickListener;
        }
        
        public boolean isEnabled() {
            return this.enabled;
        }
        
        @NotNull
        public SimplePopupButton enable() {
            return this.enabled(true);
        }
        
        @NotNull
        public SimplePopupButton disable() {
            return this.enabled(false);
        }
        
        void setEnabledConsumer(final BooleanConsumer enabledConsumer) {
            this.enabledConsumer = enabledConsumer;
        }
        
        void setAttachedPopup(final SimpleAdvancedPopup attachedPopup) {
            if (this.attachedPopup != null) {
                throw new IllegalStateException("This SimplePopupButton is already attached to a popup! Please do not reuse it.");
            }
            this.attachedPopup = attachedPopup;
        }
    }
}
