// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import net.labymod.api.util.version.VersionMultiRange;
import net.labymod.api.Laby;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.util.Lazy;
import net.labymod.api.models.version.VersionCompatibility;

public final class ChatData
{
    public static final String CHAT_TRUST_VERSIONS = "1.19<*";
    private static final VersionCompatibility CHAT_TRUST_COMPATIBILITY;
    private static final Lazy<LabyModLoader> LAZY_LOADER;
    
    private ChatData() {
    }
    
    public static boolean isChatTrustEnabled() {
        return ChatData.CHAT_TRUST_COMPATIBILITY.isCompatible(ChatData.LAZY_LOADER.get().version());
    }
    
    static {
        CHAT_TRUST_COMPATIBILITY = new VersionMultiRange("1.19<*");
        LAZY_LOADER = Lazy.of(() -> Laby.labyAPI().labyModLoader());
    }
}
