// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.network.chat.contents;

import net.labymod.api.client.component.Component;

public interface KeybindContentsAccessor
{
    void setKeybind(final String p0);
    
    Component resolveKeybind();
}
