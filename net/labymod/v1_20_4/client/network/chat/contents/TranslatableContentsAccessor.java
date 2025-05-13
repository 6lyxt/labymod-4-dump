// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.network.chat.contents;

import net.labymod.api.client.component.Component;
import java.util.List;

public interface TranslatableContentsAccessor
{
    List<Component> getLabyArguments();
    
    String getKey();
    
    String getFallback();
}
