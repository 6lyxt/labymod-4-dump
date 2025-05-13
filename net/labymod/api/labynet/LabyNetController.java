// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet;

import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.labynet.models.NameHistory;
import java.util.List;
import java.util.UUID;
import net.labymod.api.labynet.models.ServerManifest;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.labynet.models.ServerGroup;
import java.util.Optional;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LabyNetController
{
    void loadServerData();
    
    Optional<ServerGroup> getCurrentServer();
    
    Optional<ServerGroup> getServerByName(final String p0);
    
    Optional<ServerGroup> getServerByIp(final String p0);
    
    Optional<ServerGroup> getServerByIp(final ServerAddress p0);
    
    void getOrLoadManifest(final ServerGroup p0, final ResultCallback<ServerManifest> p1);
    
    void loadNameHistory(final UUID p0, final ResultCallback<List<NameHistory>> p1);
    
    void loadNameHistory(final String p0, final ResultCallback<List<NameHistory>> p1);
    
    void loadNameByUniqueId(final UUID p0, final ResultCallback<String> p1);
    
    Result<String> loadNameByUniqueIdSync(final UUID p0);
    
    void loadUniqueIdByName(final String p0, final ResultCallback<UUID> p1);
    
    Result<UUID> loadUniqueIdByNameSync(final String p0);
    
    void loadServiceData(final ServiceDataType p0, final ResultCallback<ServiceStatus> p1);
    
    void loadSocials(final UUID p0, final ResultCallback<List<SocialMediaEntry>> p1);
}
