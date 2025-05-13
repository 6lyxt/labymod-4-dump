// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.authlib;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.concurrent.ExecutionException;
import net.labymod.api.client.session.exceptions.AuthenticationException;
import net.labymod.api.client.session.exceptions.UserBannedException;
import net.labymod.api.client.session.exceptions.InvalidCredentialsException;
import net.labymod.api.client.session.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import net.labymod.core.client.session.DefaultSession;
import net.labymod.api.client.session.Session;
import net.labymod.api.Laby;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { YggdrasilMinecraftSessionService.class }, remap = false)
public abstract class MixinYggdrasilMinecraftSessionService
{
    @Overwrite
    public void joinServer(final GameProfile profile, final String authenticationToken, final String serverId) throws com.mojang.authlib.exceptions.AuthenticationException {
        try {
            Laby.references().minecraftAuthenticator().joinServer(new DefaultSession(profile.getName(), profile.getId(), authenticationToken, Session.Type.MOJANG), serverId, -127).get();
        }
        catch (final InterruptedException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", (Throwable)e);
        }
        catch (final ExecutionException e2) {
            final Throwable cause = e2.getCause();
            if (cause instanceof net.labymod.api.client.session.exceptions.AuthenticationUnavailableException) {
                throw new AuthenticationUnavailableException(cause.getMessage(), cause.getCause());
            }
            if (cause instanceof InsufficientPrivilegesException) {
                throw new com.mojang.authlib.exceptions.InsufficientPrivilegesException(cause.getMessage(), cause.getCause());
            }
            if (cause instanceof InvalidCredentialsException) {
                throw new com.mojang.authlib.exceptions.InvalidCredentialsException(cause.getMessage(), cause.getCause());
            }
            if (cause instanceof UserBannedException) {
                throw new com.mojang.authlib.exceptions.UserBannedException();
            }
            if (cause instanceof AuthenticationException) {
                throw new com.mojang.authlib.exceptions.AuthenticationException(cause.getMessage(), cause.getCause());
            }
            throw new AuthenticationUnavailableException("Cannot contact authentication server", cause);
        }
    }
}
