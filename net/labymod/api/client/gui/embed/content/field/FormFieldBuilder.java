// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.embed.content.field;

import net.labymod.api.client.component.Component;

public interface FormFieldBuilder
{
    FormFieldBuilder title(final Component p0);
    
    FormFieldBuilder description(final Component p0);
    
    FormFieldBuilder required(final boolean p0);
    
    FormFieldBuilder submit(final boolean p0);
    
    FormButton makeButton();
}
