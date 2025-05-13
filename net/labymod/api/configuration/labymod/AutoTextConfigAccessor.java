// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod;

import net.labymod.api.client.chat.autotext.AutoTextEntry;
import java.util.List;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface AutoTextConfigAccessor extends ConfigAccessor
{
    List<AutoTextEntry> getEntries();
}
