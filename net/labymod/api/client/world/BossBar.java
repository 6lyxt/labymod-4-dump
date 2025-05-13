// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public interface BossBar
{
    @NotNull
    UUID getIdentifier();
    
    Component displayName();
    
    BossBarColor bossBarColor();
    
    BossBarOverlay bossBarOverlay();
    
    BossBarProgressHandler progressHandler();
}
