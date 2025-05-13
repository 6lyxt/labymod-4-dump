// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.core.client.network.server.DefaultAbstractServerAddressResolver;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bdd.class })
public class MixinServerAddress
{
    @Overwrite
    private static String[] b(final String address) {
        final ServerAddress serverAddress = DefaultAbstractServerAddressResolver.getServerAddress(address);
        return new String[] { serverAddress.getHost(), Integer.toString(serverAddress.getPort()) };
    }
}
