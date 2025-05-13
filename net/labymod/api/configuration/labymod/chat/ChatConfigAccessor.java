// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat;

import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import java.util.List;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface ChatConfigAccessor extends ConfigAccessor
{
    List<ChatWindowConfig> getWindows();
}
