// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed.content.field;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.embed.content.field.FormField;

public abstract class DefaultFormField<T> implements FormField<T>
{
    private final FormEmbeddedContent form;
    private final String id;
    private final Component title;
    private final Component description;
    private final boolean required;
    private boolean submit;
    private T value;
    
    protected DefaultFormField(final DefaultFormFieldBuilder builder) {
        this.form = builder.form();
        this.id = builder.id();
        this.title = builder.title();
        this.description = builder.description();
        this.required = builder.required();
        this.submit = builder.submit();
    }
    
    @Override
    public String id() {
        return this.id;
    }
    
    @Override
    public Component title() {
        return this.title;
    }
    
    @Override
    public Component description() {
        return this.description;
    }
    
    @Override
    public boolean isRequired() {
        return this.required;
    }
    
    @Override
    public boolean isSubmit() {
        return this.submit;
    }
    
    @Override
    public T getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final T value) {
        this.value = value;
    }
    
    @Override
    public void onSubmit(final FormEmbeddedContent.SubmitListener listener) {
        this.submit = true;
        this.form.onSubmit(submitted -> {
            if (submitted == this) {
                listener.submitted(submitted);
            }
        });
    }
    
    protected void submit() {
        if (!this.submit) {
            return;
        }
        this.form.submit(this);
    }
    
    @Override
    public final Widget createWidget() {
        final Widget widget = this.createWidgetBase();
        widget.addId(this.id);
        if (this.description != null) {
            widget.setHoverComponent(this.description);
        }
        return widget;
    }
    
    protected abstract Widget createWidgetBase();
}
