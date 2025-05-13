// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed.content;

import net.labymod.core.client.gui.embed.content.field.DefaultFormFieldBuilder;
import net.labymod.api.client.gui.embed.content.field.FormFieldBuilder;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.ArrayList;
import net.labymod.api.client.gui.embed.content.field.FormField;
import java.util.List;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;

@Link("widget/form.lss")
public class DefaultFormEmbeddedContent extends DefaultEmbeddedContent implements FormEmbeddedContent
{
    private final Component title;
    private final Component subTitle;
    private final Icon icon;
    private final boolean resubmittable;
    private final List<FormField<?>> fields;
    private final List<SubmitListener> submitListeners;
    private boolean submitted;
    
    public DefaultFormEmbeddedContent(final DefaultFormEmbeddedContentBuilder builder) {
        this.fields = new ArrayList<FormField<?>>();
        this.submitListeners = new ArrayList<SubmitListener>();
        this.title = builder.title();
        this.subTitle = builder.subTitle();
        this.icon = builder.icon();
        this.resubmittable = builder.isResubmittable();
    }
    
    @Override
    protected Widget createWidgetBase() {
        final FlexibleContentWidget contentWrapper = new FlexibleContentWidget().addId("content-wrapper");
        if (this.icon() != null) {
            contentWrapper.addContent(new IconWidget(this.icon())).addId("form-icon");
        }
        final FlexibleContentWidget form = new FlexibleContentWidget().addId("form");
        contentWrapper.addFlexibleContent(form);
        if (this.title() != null) {
            form.addContent(ComponentWidget.component(this.title())).addId("title");
        }
        if (this.subTitle() != null) {
            form.addContent(ComponentWidget.component(this.subTitle())).addId("sub-title");
        }
        final HorizontalListWidget fields = new HorizontalListWidget().addId("fields");
        form.addContent(fields);
        for (final FormField<?> field : this.fields()) {
            fields.addEntry(field.createWidget()).addId("field");
        }
        return contentWrapper;
    }
    
    @Override
    public Component title() {
        return this.title;
    }
    
    @Override
    public Component subTitle() {
        return this.subTitle;
    }
    
    @Override
    public Icon icon() {
        return this.icon;
    }
    
    @Override
    public List<FormField<?>> fields() {
        return this.fields;
    }
    
    @Override
    public <T> FormField<T> getField(final String id) {
        for (final FormField<?> field : this.fields) {
            if (field.id().equals(id)) {
                return (FormField<T>)field;
            }
        }
        return null;
    }
    
    @Override
    public FormFieldBuilder addField(final String id) {
        if (this.getField(id) != null) {
            throw new IllegalArgumentException("Field already present: " + id);
        }
        return new DefaultFormFieldBuilder(this, id);
    }
    
    public void addField(final FormField<?> field) {
        this.fields.add(field);
    }
    
    @Override
    public FormEmbeddedContent onSubmit(final SubmitListener listener) {
        this.submitListeners.add(listener);
        return this;
    }
    
    @Override
    public void submit(final FormField<?> submitted) {
        if (!this.resubmittable && this.submitted) {
            return;
        }
        for (final FormField field : this.fields) {
            if (field.isRequired() && !field.isValid(field.getValue())) {
                return;
            }
        }
        this.submitted = true;
        for (final SubmitListener listener : this.submitListeners) {
            listener.submitted(submitted);
        }
    }
    
    @Override
    public boolean wasSubmitted() {
        return this.submitted;
    }
}
