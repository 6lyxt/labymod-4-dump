// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.overlay;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.Stack;

public final class IngameOverlayElementRenderEventCaller
{
    public static boolean callAttackIndicatorPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.ATTACK_INDICATOR);
    }
    
    public static void callAttackIndicatorPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.ATTACK_INDICATOR);
    }
    
    public static boolean callBossBarPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.BOSS_BAR);
    }
    
    public static void callBossBarPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.BOSS_BAR);
    }
    
    public static boolean callCrossHairPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR);
    }
    
    public static void callCrossHairPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR);
    }
    
    public static boolean callOffhandTexturePre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE);
    }
    
    public static void callOffhandTexturePost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE);
    }
    
    public static boolean callOffhandItemPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM);
    }
    
    public static void callOffhandItemPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM);
    }
    
    public static boolean callPotionEffectsPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.POTION_EFFECTS);
    }
    
    public static void callPotionEffectsPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.POTION_EFFECTS);
    }
    
    public static boolean callTitlePre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.TITLE);
    }
    
    public static void callTitlePost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.TITLE);
    }
    
    public static boolean callScoreboardPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.SCOREBOARD);
    }
    
    public static void callScoreboardPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.SCOREBOARD);
    }
    
    public static boolean callActionBarPre(final Stack stack) {
        return call(stack, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.ACTION_BAR);
    }
    
    public static void callActionBarPost(final Stack stack) {
        call(stack, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.ACTION_BAR);
    }
    
    private static boolean call(final Stack stack, final Phase phase, final IngameOverlayElementRenderEvent.OverlayElementType type) {
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        final IngameOverlayElementRenderEvent event = Laby.fireEvent(new IngameOverlayElementRenderEvent(type, stack, phase));
        gfx.restoreBlaze3DStates();
        return event.isCancelled();
    }
}
