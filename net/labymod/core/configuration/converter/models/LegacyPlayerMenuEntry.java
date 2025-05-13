// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.models;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.chat.autotext.AutoTextServerConfig;
import net.labymod.api.client.chat.autotext.AutoTextEntry;

public class LegacyPlayerMenuEntry
{
    private String displayName;
    private String command;
    private boolean sendInstantly;
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public AutoTextEntry convert() {
        return new AutoTextEntry(this.displayName, this.command, true, this.sendInstantly, new AutoTextServerConfig(false, ""), new Key[0]);
    }
}
