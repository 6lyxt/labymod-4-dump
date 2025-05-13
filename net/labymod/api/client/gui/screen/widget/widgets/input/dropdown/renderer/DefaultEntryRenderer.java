// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer;

import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class DefaultEntryRenderer<T> implements EntryRenderer<T>
{
    private String translationKeyPrefix;
    
    @Override
    public Widget createEntryWidget(final T entry) {
        return ComponentWidget.component(this.toComponent(entry));
    }
    
    @Override
    public float getWidth(final T entry, final float maxWidth) {
        return this.toRenderableComponent(entry, maxWidth).getWidth();
    }
    
    @Override
    public float getHeight(final T entry, final float maxWidth) {
        return this.toRenderableComponent(entry, maxWidth).getHeight();
    }
    
    private Component toComponent(final T entry) {
        if (this.translationKeyPrefix != null) {
            final String translation = I18n.getTranslation(this.translationKeyPrefix + "." + TextFormat.SNAKE_CASE.toLowerCamelCase(entry.toString()), new Object[0]);
            if (translation != null) {
                return Component.text(translation);
            }
        }
        return Component.text(entry.toString());
    }
    
    private RenderableComponent toRenderableComponent(final T entry, final float maxWidth) {
        return RenderableComponent.builder().maxWidth(maxWidth).disableCache().format(this.toComponent(entry));
    }
    
    public void setTranslationKeyPrefix(final String translationKeyPrefix) {
        this.translationKeyPrefix = translationKeyPrefix;
    }
    
    public String getTranslationKeyPrefix() {
        return this.translationKeyPrefix;
    }
}
