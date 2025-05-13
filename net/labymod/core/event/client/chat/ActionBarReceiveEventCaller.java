// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.chat;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.client.component.Component;

public final class ActionBarReceiveEventCaller
{
    public static ActionBarReceiveEvent callPre(final Component component, final boolean animatedMessage) {
        return call(Phase.PRE, component, animatedMessage);
    }
    
    public static void callPost(final Component component, final boolean animatedMessage) {
        call(Phase.POST, component, animatedMessage);
    }
    
    private static ActionBarReceiveEvent call(final Phase phase, final Component component, final boolean animatedMessage) {
        return Laby.fireEvent(new ActionBarReceiveEvent(phase, component, animatedMessage));
    }
}
