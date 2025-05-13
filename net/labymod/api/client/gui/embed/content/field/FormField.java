// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.embed.content.field;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.component.Component;

public interface FormField<T>
{
    String id();
    
    Component title();
    
    Component description();
    
    boolean isRequired();
    
    boolean isSubmit();
    
    T getValue();
    
    void setValue(final T p0);
    
    boolean isValid(final T p0);
    
    void onSubmit(final FormEmbeddedContent.SubmitListener p0);
    
    Widget createWidget();
}
