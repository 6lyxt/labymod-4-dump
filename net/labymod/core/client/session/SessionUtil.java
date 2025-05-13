// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.session;

public final class SessionUtil
{
    private SessionUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }
    
    public static boolean isPremium(final String accessToken) {
        return accessToken != null && accessToken.length() > 10;
    }
}
