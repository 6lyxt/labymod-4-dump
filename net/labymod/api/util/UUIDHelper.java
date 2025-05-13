// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class UUIDHelper
{
    private UUIDHelper() {
    }
    
    @NotNull
    public static UUID createUniqueId(@NotNull final String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8));
    }
}
