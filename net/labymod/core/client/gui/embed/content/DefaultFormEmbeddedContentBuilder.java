// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed.content;

import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder;

public class DefaultFormEmbeddedContentBuilder implements FormEmbeddedContentBuilder
{
    private Component title;
    private Component subTitle;
    private Icon icon;
    private boolean resubmittable;
    
    @Override
    public FormEmbeddedContentBuilder title(final Component title) {
        this.title = title;
        return this;
    }
    
    @Override
    public FormEmbeddedContentBuilder subTitle(final Component subTitle) {
        this.subTitle = subTitle;
        return this;
    }
    
    @Override
    public FormEmbeddedContentBuilder icon(final Icon icon) {
        this.icon = icon;
        return this;
    }
    
    @Override
    public FormEmbeddedContentBuilder resubmittable() {
        this.resubmittable = true;
        return this;
    }
    
    public Component title() {
        return this.title;
    }
    
    public Component subTitle() {
        return this.subTitle;
    }
    
    public Icon icon() {
        return this.icon;
    }
    
    public boolean isResubmittable() {
        return this.resubmittable;
    }
    
    @Override
    public FormEmbeddedContent build() {
        return new DefaultFormEmbeddedContent(this);
    }
}
