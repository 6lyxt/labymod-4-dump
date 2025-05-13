// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.thirdparty;

import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ThirdPartyService
{
    void initialize();
    
    DiscordApp discord();
}
