// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.client.component.Component;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.metadata.MetadataExtension;

public interface NetworkPlayerInfo extends Comparable<NetworkPlayerInfo>, MetadataExtension
{
    GameProfile profile();
    
    int getCurrentPing();
    
    int getHealth();
    
    Component displayName();
    
    ScoreboardTeam getTeam();
    
    GameMode gameMode();
    
     <T> T getMinecraftInfo();
    
    PlayerSkin getSkin();
    
    default int getOrder() {
        return 0;
    }
    
    default boolean isListed() {
        return true;
    }
    
    default boolean showHat() {
        return MinecraftVersions.V1_21_3.orOlder();
    }
}
