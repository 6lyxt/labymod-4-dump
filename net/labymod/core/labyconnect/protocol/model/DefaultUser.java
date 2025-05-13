// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model;

import net.labymod.api.user.GameUser;
import java.util.Objects;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import java.util.UUID;
import net.labymod.api.labyconnect.protocol.model.User;

public class DefaultUser implements User
{
    protected final UUID uniqueId;
    protected final String name;
    private boolean labyPlusOverride;
    @NotNull
    private UserStatus userStatus;
    
    public DefaultUser(final UUID uniqueId, final String name) {
        this.userStatus = UserStatus.OFFLINE;
        this.uniqueId = uniqueId;
        this.name = name;
    }
    
    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean isSelf() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        return session != null && session.self().getUniqueId().equals(this.uniqueId);
    }
    
    @NotNull
    @Override
    public UserStatus userStatus() {
        return this.userStatus;
    }
    
    @Override
    public void updateStatus(@NotNull final UserStatus userStatus) {
        Objects.requireNonNull(userStatus);
        this.userStatus = userStatus;
    }
    
    @Override
    public GameUser gameUser() {
        return Laby.labyAPI().gameUserService().gameUser(this.uniqueId);
    }
    
    @Override
    public boolean isLabyPlus() {
        return this.labyPlusOverride || this.gameUser().visibleGroup().isLabyModPlus();
    }
    
    public void setLabyPlusOverride(final boolean labyPlusOverride) {
        if (!this.isSelf()) {
            throw new IllegalStateException("Only the self user can override the LabyPlus status!");
        }
        this.labyPlusOverride = labyPlusOverride;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof User && ((User)obj).getUniqueId().equals(this.uniqueId);
    }
}
