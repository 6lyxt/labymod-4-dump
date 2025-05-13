// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.Objects;

public interface TextComponent extends BaseComponent<TextComponent>
{
    default Builder builder() {
        return new Builder();
    }
    
    String getText();
    
    TextComponent plainCopy();
    
    TextComponent text(final String p0);
    
    @Deprecated
    default String content() {
        return this.getText();
    }
    
    default Builder toBuilder() {
        return new Builder(this);
    }
    
    public static class Builder extends Component.Builder<TextComponent, Builder>
    {
        protected String text;
        
        protected Builder() {
            this.text = "";
        }
        
        protected Builder(final TextComponent component) {
            super(component);
            this.text = "";
            this.text = component.getText();
        }
        
        public Builder text(final String text) {
            this.text = text;
            return this;
        }
        
        @Deprecated
        public Builder content(final String content) {
            return this.text(content);
        }
        
        @Override
        public TextComponent build() {
            Objects.requireNonNull(this.text, "Text cannot be null!");
            return ComponentService.textComponent(this.text, this.isEmpty() ? null : this.buildStyle(), this.children);
        }
    }
}
