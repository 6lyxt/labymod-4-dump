// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.post;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.post.PostProcessingScreenEvent;

public final class PostProcessingScreenEventCaller
{
    public static void callBeforeHand(final float partialTicks) {
        call(PostProcessingScreenEvent.Phase.BEFORE_HAND, partialTicks);
    }
    
    public static void callWorld(final float partialTicks) {
        call(PostProcessingScreenEvent.Phase.WORLD, partialTicks);
    }
    
    private static void call(final PostProcessingScreenEvent.Phase phase, final float partialTicks) {
        Laby.fireEvent(new PostProcessingScreenEvent(phase, partialTicks));
    }
}
