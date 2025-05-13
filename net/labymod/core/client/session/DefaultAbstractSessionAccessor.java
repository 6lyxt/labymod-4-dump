// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.session;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.session.SessionRefresher;
import java.util.Objects;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.session.Session;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.session.SessionAccessor;

public abstract class DefaultAbstractSessionAccessor<T> implements SessionAccessor
{
    private final Task refreshProfileTask;
    
    public DefaultAbstractSessionAccessor() {
        this.refreshProfileTask = Task.builder(this::refreshProfile).build();
    }
    
    @Nullable
    @Override
    public Session getSession() {
        return this.fromGameSession(this.getGameSession());
    }
    
    @Override
    public void updateSession(final Session session) {
        final Session previousSession = this.getSession();
        this.setGameSession(this.toGameSession(session));
        Laby.labyAPI().eventBus().fire(new SessionUpdateEvent(previousSession, session));
    }
    
    public void updateSession(final Account account) {
        final Session session = this.createSession(account.getUsername(), account.getUUID(), account.getAccessToken());
        this.updateSession(session);
    }
    
    public boolean isAccount(final Account account) {
        final Session session = this.getSession();
        return session != null && (session.getUniqueId() == null || session.getUniqueId().equals(account.getUUID())) && Objects.equals(session.getAccessToken(), account.getAccessToken());
    }
    
    @Override
    public boolean isPremium() {
        final Session session = this.getSession();
        return session != null && session.isPremium();
    }
    
    public abstract T toGameSession(final Session p0);
    
    @Nullable
    public abstract Session fromGameSession(@Nullable final T p0);
    
    @Nullable
    public abstract T getGameSession();
    
    protected final void setGameSession(final T gameSession) {
        Laby.labyAPI().minecraft().setSessionRaw(gameSession);
        this.refreshProfileTask.execute();
    }
    
    @MustBeInvokedByOverriders
    protected void refreshProfile() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        minecraft.refreshRealmsClient();
        if (minecraft instanceof final SessionRefresher sessionRefresher) {
            sessionRefresher.refresh();
        }
    }
}
