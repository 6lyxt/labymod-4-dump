// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.chat.config;

import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.metadata.MetadataExtension;

public class TemporaryChatTabConfig extends RootChatTabConfig implements MetadataExtension
{
    private transient Metadata metadata;
    private Icon icon;
    
    public TemporaryChatTabConfig(final int index, final Type type, final GeneralChatTabConfig config) {
        super(index, type, config);
        this.metadata = Metadata.create();
    }
    
    public Icon icon() {
        return this.icon;
    }
    
    public void icon(final Icon icon) {
        this.icon = icon;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
}
