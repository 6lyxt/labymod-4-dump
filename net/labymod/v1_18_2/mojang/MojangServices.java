// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mojang;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.labymod.api.util.logging.Logging;

public final class MojangServices
{
    private static final Logging LOGGER;
    
    private MojangServices() {
    }
    
    public static UserApiService createSocialInteractions(final MinecraftSessionService service, final dzh user) {
        if (service instanceof final YggdrasilMinecraftSessionService sessionService) {
            final YggdrasilAuthenticationService authenticationService = sessionService.getAuthenticationService();
            return createSocialInteractions(authenticationService, user);
        }
        MojangServices.LOGGER.error("Failed to verify authentication. (MinecraftSessionService is not a YggdrasilMinecraftSessionService)", new Object[0]);
        return UserApiService.OFFLINE;
    }
    
    public static UserApiService createSocialInteractions(final YggdrasilAuthenticationService service, final dzh user) {
        try {
            return service.createUserApiService(user.d());
        }
        catch (final AuthenticationException exception) {
            MojangServices.LOGGER.error("Failed to verify authentication", (Throwable)exception);
            return UserApiService.OFFLINE;
        }
    }
    
    static {
        LOGGER = Logging.create(MojangServices.class);
    }
}
