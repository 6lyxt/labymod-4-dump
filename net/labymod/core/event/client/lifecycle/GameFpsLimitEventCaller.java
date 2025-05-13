// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.lifecycle;

import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.api.event.client.lifecycle.GameFpsLimitEvent;

public final class GameFpsLimitEventCaller
{
    private static final GameFpsLimitEvent GAME_FPS_LIMIT_EVENT;
    
    public static void callEvent(final InsertInfoReturnable<Integer> iir) {
        GameFpsLimitEventCaller.GAME_FPS_LIMIT_EVENT.setFramerateLimit(-1);
        Laby.fireEvent(GameFpsLimitEventCaller.GAME_FPS_LIMIT_EVENT);
        final int limit = GameFpsLimitEventCaller.GAME_FPS_LIMIT_EVENT.getFramerateLimit();
        if (limit == -1) {
            return;
        }
        iir.setReturnValue(limit);
    }
    
    static {
        GAME_FPS_LIMIT_EVENT = new GameFpsLimitEvent();
    }
}
