// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.embed.content;

import java.util.Map;
import net.labymod.api.client.gui.embed.content.field.FormField;

public interface FormResult
{
    FormEmbeddedContent form();
    
    FormField<?> submitted();
    
    Map<String, Object> fieldValues();
}
