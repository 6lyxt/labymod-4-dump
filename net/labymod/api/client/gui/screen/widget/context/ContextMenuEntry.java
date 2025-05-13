// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.context;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import java.util.function.Supplier;

public class ContextMenuEntry
{
    private final Supplier<Icon> icon;
    private final Supplier<Icon> subIcon;
    private final Supplier<Component> text;
    private final Supplier<Component> hoverComponent;
    private final ContextMenuHandler clickHandler;
    private final BooleanSupplier disabledSupplier;
    private final Supplier<ContextMenu> subMenu;
    
    private ContextMenuEntry(final Supplier<Icon> icon, final Supplier<Icon> subIcon, final Supplier<Component> text, final Supplier<Component> hoverComponent, final ContextMenuHandler clickHandler, final BooleanSupplier disabledSupplier, final Supplier<ContextMenu> subMenu) {
        this.icon = icon;
        this.subIcon = subIcon;
        this.text = text;
        this.hoverComponent = hoverComponent;
        this.clickHandler = clickHandler;
        this.disabledSupplier = disabledSupplier;
        this.subMenu = subMenu;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public Icon getIcon() {
        return (this.icon != null) ? this.icon.get() : null;
    }
    
    public Icon getSubIcon() {
        return (this.subIcon != null) ? this.subIcon.get() : null;
    }
    
    public Component getText() {
        return (this.text != null) ? this.text.get() : null;
    }
    
    public Component getHoverComponent() {
        return (this.hoverComponent != null) ? this.hoverComponent.get() : null;
    }
    
    public ContextMenuHandler clickHandler() {
        return this.clickHandler;
    }
    
    public boolean isDisabled() {
        return this.disabledSupplier != null && this.disabledSupplier.getAsBoolean();
    }
    
    public boolean hasSubMenu() {
        return this.subMenu != null;
    }
    
    public ContextMenu getSubMenu() {
        if (this.subMenu == null) {
            return null;
        }
        final ContextMenu subMenu = this.subMenu.get();
        if (subMenu == null) {
            throw new IllegalArgumentException("subMenu supplier may not return null");
        }
        return subMenu;
    }
    
    public static class Builder
    {
        private Supplier<Icon> icon;
        private Supplier<Icon> subIcon;
        private Supplier<Component> text;
        private Supplier<Component> hoverComponent;
        private ContextMenuHandler clickHandler;
        private BooleanSupplier disabled;
        private Supplier<ContextMenu> subMenu;
        
        public Builder icon(final Icon icon) {
            return this.icon(() -> icon);
        }
        
        public Builder icon(final Supplier<Icon> iconSupplier) {
            this.icon = iconSupplier;
            return this;
        }
        
        public Builder subIcon(final Icon subIcon) {
            return this.subIcon(() -> subIcon);
        }
        
        public Builder subIcon(final Supplier<Icon> subIconSupplier) {
            this.subIcon = subIconSupplier;
            return this;
        }
        
        public Builder text(final Component text) {
            return this.text(() -> text);
        }
        
        public Builder text(final Supplier<Component> textSupplier) {
            this.text = textSupplier;
            return this;
        }
        
        public Builder hoverComponent(final Component hoverComponent) {
            return this.hoverComponent(() -> hoverComponent);
        }
        
        public Builder hoverComponent(final Supplier<Component> hoverComponentSupplier) {
            this.hoverComponent = hoverComponentSupplier;
            return this;
        }
        
        @Deprecated
        public Builder clickHandler(final BiConsumer<ContextMenuEntry, Object> clickHandler) {
            return this.clickHandler(entry -> {
                clickHandler.accept(entry, entry);
                return true;
            });
        }
        
        public Builder clickHandler(final ContextMenuHandler clickHandler) {
            this.clickHandler = clickHandler;
            return this;
        }
        
        public Builder disabled() {
            return this.disabled(true);
        }
        
        public Builder disabled(final boolean disabled) {
            return this.disabled(() -> disabled);
        }
        
        public Builder disabled(final BooleanSupplier supplier) {
            this.disabled = supplier;
            return this;
        }
        
        public Builder subMenu(final Supplier<ContextMenu> subMenu) {
            this.subMenu = subMenu;
            return this;
        }
        
        public ContextMenuEntry build() {
            return new ContextMenuEntry(this.icon, this.subIcon, this.text, this.hoverComponent, this.clickHandler, this.disabled, this.subMenu);
        }
    }
}
