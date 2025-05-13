// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import com.google.gson.Gson;

public interface MessageListener
{
    public static final Gson GSON = DefaultLabyConnectSession.GSON;
    
    void listen(final String p0);
}
