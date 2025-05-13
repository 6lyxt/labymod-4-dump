// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.icon.Icon;

public interface IconComponent extends BaseComponent<IconComponent>
{
    public static final int DEFAULT_SIZE = 16;
    
    @NotNull
    Icon getIcon();
    
    @NotNull
    IconComponent setIcon(@NotNull final Icon p0);
    
    @Deprecated
    default int getSize() {
        return this.getWidth();
    }
    
    int getWidth();
    
    int getHeight();
    
    @NotNull
    default IconComponent setSize(final int size) {
        return this.setWidth(size).setHeight(size);
    }
    
    IconComponent setWidth(final int p0);
    
    IconComponent setHeight(final int p0);
    
    @NotNull
    String getPlaceholder();
    
    IconComponent setPlaceholder(@NotNull final String p0);
    
    IconComponent plainCopy();
    
    default Builder toBuilder() {
        return new Builder(this);
    }
    
    public static class Builder extends Component.Builder<IconComponent, Builder>
    {
        protected Icon icon;
        protected int width;
        protected int height;
        protected String placeholder;
        
        protected Builder() {
            this.width = 16;
            this.height = 16;
            this.placeholder = "";
        }
        
        protected Builder(final IconComponent component) {
            super(component);
            this.width = 16;
            this.height = 16;
            this.placeholder = "";
            this.icon = component.getIcon();
            this.width = component.getWidth();
            this.height = component.getHeight();
            this.placeholder = component.getPlaceholder();
        }
        
        public Builder icon(final Icon icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder size(final int size) {
            this.width = size;
            this.height = size;
            return this;
        }
        
        public Builder width(final int width) {
            this.width = width;
            return this;
        }
        
        public Builder height(final int height) {
            this.height = height;
            return this;
        }
        
        public Builder placeholder(final String placeholder) {
            this.placeholder = placeholder;
            return this;
        }
        
        @Override
        public IconComponent build() {
            Objects.requireNonNull(this.icon, "Icon cannot be null!");
            return ComponentService.iconComponent(this.icon, this.isEmpty() ? null : this.buildStyle(), this.children).setWidth(this.width).setHeight(this.height).setPlaceholder(this.placeholder);
        }
    }
}
