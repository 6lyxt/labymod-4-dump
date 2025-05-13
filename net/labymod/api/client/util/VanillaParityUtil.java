// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.util;

import net.labymod.api.loader.MinecraftVersions;

public final class VanillaParityUtil
{
    public static float Z_SEPARATION;
    public static float BOSS_BAR_Z_INDEX;
    public static float SCOREBOARD_SIDEBAR_Z_INDEX;
    public static float ACTION_BAR_Z_INDEX;
    public static float TITLE_OVERLAY_Z_INDEX;
    public static boolean NEEDS_Z_SEPARATION;
    
    private VanillaParityUtil() {
    }
    
    public static float getBossBarZLevel() {
        return VanillaParityUtil.NEEDS_Z_SEPARATION ? VanillaParityUtil.BOSS_BAR_Z_INDEX : 0.0f;
    }
    
    public static float getScoreboardSidebarZLevel() {
        return VanillaParityUtil.NEEDS_Z_SEPARATION ? VanillaParityUtil.SCOREBOARD_SIDEBAR_Z_INDEX : 0.0f;
    }
    
    public static float getActionBarZLevel() {
        return VanillaParityUtil.NEEDS_Z_SEPARATION ? VanillaParityUtil.ACTION_BAR_Z_INDEX : 0.0f;
    }
    
    public static float getTitlesOverlayZLevel() {
        return VanillaParityUtil.NEEDS_Z_SEPARATION ? VanillaParityUtil.TITLE_OVERLAY_Z_INDEX : 0.0f;
    }
    
    static {
        VanillaParityUtil.Z_SEPARATION = 200.0f;
        VanillaParityUtil.BOSS_BAR_Z_INDEX = VanillaParityUtil.Z_SEPARATION * 5.0f;
        VanillaParityUtil.SCOREBOARD_SIDEBAR_Z_INDEX = VanillaParityUtil.Z_SEPARATION * 10.0f;
        VanillaParityUtil.ACTION_BAR_Z_INDEX = VanillaParityUtil.Z_SEPARATION * 11.0f;
        VanillaParityUtil.TITLE_OVERLAY_Z_INDEX = VanillaParityUtil.Z_SEPARATION * 12.0f;
        VanillaParityUtil.NEEDS_Z_SEPARATION = MinecraftVersions.V1_20_5.orNewer();
    }
}
