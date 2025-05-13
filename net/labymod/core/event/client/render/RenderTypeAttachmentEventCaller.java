// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;

public final class RenderTypeAttachmentEventCaller
{
    public static boolean firePre(final String name, final RenderTypeAttachmentEvent.State state) {
        return Laby.fireEvent(new RenderTypeAttachmentEvent(name, state, Phase.PRE)).isCancelled();
    }
    
    public static void firePost(final String name, final RenderTypeAttachmentEvent.State state) {
        Laby.fireEvent(new RenderTypeAttachmentEvent(name, state, Phase.POST));
    }
}
