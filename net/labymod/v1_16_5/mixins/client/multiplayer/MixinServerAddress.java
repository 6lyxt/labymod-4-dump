// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.core.client.network.server.DefaultAbstractServerAddressResolver;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dwy.class })
public abstract class MixinServerAddress
{
    @Overwrite
    private static Pair<String, Integer> b(final String address) {
        final ServerAddress serverAddress = DefaultAbstractServerAddressResolver.getServerAddress(address);
        return (Pair<String, Integer>)Pair.of((Object)serverAddress.getHost(), (Object)serverAddress.getPort());
    }
}
