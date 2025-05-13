// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.session;

import net.labymod.api.client.session.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.GameProfile;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.session.Session;
import javax.inject.Inject;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.util.UUID;
import java.net.Proxy;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.session.DefaultsAbstractMinecraftAuthenticator;

@Singleton
@Implements(MinecraftAuthenticator.class)
public class VersionedMinecraftAuthenticator extends DefaultsAbstractMinecraftAuthenticator
{
    private final LabyMinecraftSessionService sessionService;
    
    @Inject
    public VersionedMinecraftAuthenticator() {
        final MinecraftSessionService sessionService = ave.A().aa();
        this.sessionService = new LabyMinecraftSessionService((sessionService instanceof YggdrasilAuthenticationService) ? sessionService : new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()));
    }
    
    @Override
    public CompletableFuture<Boolean> joinServer(final Session session, final String serverId, final int priority) {
        return this.queueTask(priority, future -> {
            boolean result = false;
            try {
                this.sessionService.joinServer(new GameProfile(session.getUniqueId(), session.getUsername()), session.getAccessToken(), serverId);
                result = true;
            }
            catch (final com.mojang.authlib.exceptions.AuthenticationException e) {
                if (e instanceof AuthenticationUnavailableException) {
                    future.completeExceptionally(new net.labymod.api.client.session.exceptions.AuthenticationUnavailableException(e.getMessage(), e.getCause()));
                }
                else if (e instanceof InvalidCredentialsException) {
                    future.completeExceptionally(new net.labymod.api.client.session.exceptions.InvalidCredentialsException(e.getMessage(), e.getCause()));
                }
                else {
                    future.completeExceptionally(new AuthenticationException(e.getMessage(), e.getCause()));
                }
            }
            finally {
                if (!future.isDone()) {
                    future.complete(result);
                }
            }
        });
    }
}
