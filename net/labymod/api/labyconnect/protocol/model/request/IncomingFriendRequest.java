// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.request;

import net.labymod.api.labyconnect.protocol.model.User;

public interface IncomingFriendRequest extends User
{
    void accept();
    
    void decline();
}
