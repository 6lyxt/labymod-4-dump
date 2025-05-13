// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.CountryCode;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.component.format.TextColor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.user.group.Group;
import java.util.UUID;
import net.labymod.api.tag.Tag;
import net.labymod.api.tag.Taggable;
import net.labymod.api.util.Disposable;

public interface GameUser extends Disposable, Taggable
{
    public static final Tag HIDE_CAPE = Tag.of("labymod", "user/cosmetic/hide_cape");
    public static final Tag HIDE_SHIELD = Tag.of("labymod", "user/cosmetic/hide_shield");
    public static final Tag CUSTOM_ITEM = Tag.of("labymod", "user/cosmetic/custom_item");
    public static final Tag FAMILIAR = Tag.of("labymod", "user/familiar");
    public static final Tag LEGACY = Tag.of("labymod", "user/legacy");
    
    UUID getUniqueId();
    
    boolean isUsingLabyMod();
    
    boolean isLegacy();
    
    WhitelistState whitelistState();
    
    @NotNull
    Group visibleGroup();
    
    @NotNull
    TextColor displayColor();
    
    CompletableFuture<GameUser> ensureDataAvailable();
    
    @Nullable
    CountryCode getCountryCode();
    
    @Deprecated
    default Group getVisibleGroup() {
        return this.visibleGroup();
    }
    
    public enum WhitelistState
    {
        WHITELISTED, 
        NOT_WHITELISTED, 
        LOADING, 
        UNKNOWN;
        
        public boolean isWhitelisted() {
            return this == WhitelistState.WHITELISTED;
        }
        
        public boolean isLoaded() {
            return this != WhitelistState.UNKNOWN && this != WhitelistState.LOADING;
        }
        
        public static WhitelistState of(final boolean whitelisted) {
            return whitelisted ? WhitelistState.WHITELISTED : WhitelistState.NOT_WHITELISTED;
        }
    }
}
