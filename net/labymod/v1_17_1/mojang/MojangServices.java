// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mojang;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.labymod.api.util.logging.Logging;

public final class MojangServices
{
    private static final Logging LOGGER;
    
    private MojangServices() {
    }
    
    public static SocialInteractionsService createSocialInteractions(final MinecraftSessionService service, final dwd user) {
        if (service instanceof final YggdrasilMinecraftSessionService sessionService) {
            final YggdrasilAuthenticationService authenticationService = sessionService.getAuthenticationService();
            return createSocialInteractions(authenticationService, user);
        }
        MojangServices.LOGGER.error("Failed to verify authentication. (MinecraftSessionService is not a YggdrasilMinecraftSessionService)", new Object[0]);
        return (SocialInteractionsService)new OfflineSocialInteractions();
    }
    
    public static SocialInteractionsService createSocialInteractions(final YggdrasilAuthenticationService service, final dwd user) {
        try {
            return (SocialInteractionsService)service.createSocialInteractionsService(user.d());
        }
        catch (final AuthenticationException exception) {
            MojangServices.LOGGER.error("Failed to verify authentication", (Throwable)exception);
            return (SocialInteractionsService)new OfflineSocialInteractions();
        }
    }
    
    static {
        LOGGER = Logging.create(MojangServices.class);
    }
}
