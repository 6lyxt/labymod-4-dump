// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.post;

import net.labymod.api.event.Event;

record PostProcessingScreenEvent(Phase phase, float partialTicks) implements Event {
    public enum Phase
    {
        BEFORE_HAND, 
        WORLD;
    }
}
