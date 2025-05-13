// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed.content.field;

import net.labymod.api.client.gui.embed.content.field.FormField;
import net.labymod.api.client.gui.embed.content.field.FormButton;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.component.Component;
import net.labymod.core.client.gui.embed.content.DefaultFormEmbeddedContent;
import net.labymod.api.client.gui.embed.content.field.FormFieldBuilder;

public class DefaultFormFieldBuilder implements FormFieldBuilder
{
    private final DefaultFormEmbeddedContent form;
    private final String id;
    private Component title;
    private Component description;
    private boolean required;
    private boolean submit;
    
    public DefaultFormFieldBuilder(final DefaultFormEmbeddedContent form, final String id) {
        this.form = form;
        this.id = id;
    }
    
    @Override
    public FormFieldBuilder title(final Component title) {
        this.title = title;
        return this;
    }
    
    @Override
    public FormFieldBuilder description(final Component description) {
        this.description = description;
        return this;
    }
    
    @Override
    public FormFieldBuilder required(final boolean required) {
        this.required = required;
        return this;
    }
    
    @Override
    public FormFieldBuilder submit(final boolean submit) {
        this.submit = submit;
        return this;
    }
    
    public FormEmbeddedContent form() {
        return this.form;
    }
    
    public String id() {
        return this.id;
    }
    
    public Component title() {
        return this.title;
    }
    
    public Component description() {
        return this.description;
    }
    
    public boolean required() {
        return this.required;
    }
    
    public boolean submit() {
        return this.submit;
    }
    
    @Override
    public FormButton makeButton() {
        return this.addField(new DefaultFormButton(this));
    }
    
    private <T extends FormField<?>> T addField(final T t) {
        this.form.addField(t);
        return t;
    }
}
