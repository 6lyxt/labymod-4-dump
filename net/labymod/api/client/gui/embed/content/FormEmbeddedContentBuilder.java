// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.embed.content;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public interface FormEmbeddedContentBuilder
{
    FormEmbeddedContentBuilder title(final Component p0);
    
    FormEmbeddedContentBuilder subTitle(final Component p0);
    
    FormEmbeddedContentBuilder icon(final Icon p0);
    
    FormEmbeddedContentBuilder resubmittable();
    
    FormEmbeddedContent build();
}
