// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.session;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import java.util.UUID;
import com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.EnvironmentParser;
import com.mojang.authlib.Environment;
import java.net.Proxy;
import com.mojang.authlib.yggdrasil.ServicesKeySet;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import java.net.URL;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

public class LabyMinecraftSessionService extends YggdrasilMinecraftSessionService
{
    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL JOIN_URL;
    private final MinecraftClient client;
    
    private LabyMinecraftSessionService(final MinecraftClient client, final ServicesKeySet servicesKeySet, final Proxy proxy, final Environment environment) {
        super(servicesKeySet, proxy, environment);
        this.client = client;
    }
    
    public static LabyMinecraftSessionService create(final Proxy proxy) {
        final Environment environment = EnvironmentParser.getEnvironmentFromProperties().orElse(YggdrasilEnvironment.PROD.getEnvironment());
        final MinecraftClient client = MinecraftClient.unauthenticated(proxy);
        final URL publicKeySetUrl = HttpAuthenticationService.constantURL(environment.servicesHost() + "/publickeys");
        final ServicesKeySet servicesKeySet = YggdrasilServicesKeyInfo.get(publicKeySetUrl, client);
        return new LabyMinecraftSessionService(client, servicesKeySet, proxy, environment);
    }
    
    public void joinServer(final UUID profileId, final String authenticationToken, final String serverId) throws AuthenticationException {
        final JoinMinecraftServerRequest request = new JoinMinecraftServerRequest(authenticationToken, profileId, serverId);
        try {
            this.client.post(LabyMinecraftSessionService.JOIN_URL, (Object)request, (Class)Void.class);
        }
        catch (final MinecraftClientException e) {
            throw e.toAuthenticationException();
        }
    }
    
    static {
        JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
    }
}
