// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.account.entry;

import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import java.util.UUID;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.accountmanager.storage.account.Account;

public class DefaultAccountEntry implements AccountEntry
{
    private final Account account;
    
    private DefaultAccountEntry(final Account account) {
        this.account = account;
    }
    
    @Override
    public Component getComponent() {
        return Component.text(this.account.getUsername());
    }
    
    @Override
    public Icon getIcon() {
        return Icon.head(this.account.getUUID());
    }
    
    @Override
    public void interact(final Runnable callback) {
        final SessionAccessor sessionAccessor = Laby.labyAPI().minecraft().sessionAccessor();
        ((DefaultAbstractSessionAccessor)sessionAccessor).updateSession(this.account);
    }
    
    public Account account() {
        return this.account;
    }
    
    public static DefaultAccountEntry of(final Account account) {
        return new DefaultAccountEntry(account);
    }
    
    public static DefaultAccountEntry of(final UUID uniqueId, final String username, final String accessToken) {
        return of((Account)new ExternalAccount(uniqueId, username, accessToken));
    }
    
    public static AccountEntry[] of(final Account[] accounts) {
        final AccountEntry[] entries = new AccountEntry[accounts.length];
        for (int i = 0; i < accounts.length; ++i) {
            entries[i] = of(accounts[i]);
        }
        return entries;
    }
}
