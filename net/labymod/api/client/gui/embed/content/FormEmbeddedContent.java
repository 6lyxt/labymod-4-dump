// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.embed.content;

import net.labymod.api.client.gui.embed.content.field.FormFieldBuilder;
import net.labymod.api.client.gui.embed.content.field.FormField;
import java.util.List;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public interface FormEmbeddedContent extends EmbeddedContent
{
    Component title();
    
    Component subTitle();
    
    Icon icon();
    
    List<FormField<?>> fields();
    
     <T> FormField<T> getField(final String p0);
    
    FormFieldBuilder addField(final String p0);
    
    FormEmbeddedContent onSubmit(final SubmitListener p0);
    
    void submit(final FormField<?> p0);
    
    boolean wasSubmitted();
    
    public interface SubmitListener
    {
        void submitted(final FormField<?> p0);
    }
}
