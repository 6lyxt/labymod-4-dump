// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.global;

import java.util.ArrayList;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PublicServerListService
{
    ArrayList<PublicServerData> getServers();
}
