// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.player;

import net.labymod.api.client.resources.CompletableResourceLocation;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;

public interface PlayerSkinAccessor
{
    Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod4$getPendingCompletables();
}
