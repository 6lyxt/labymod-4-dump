// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.labymod.account;

import net.labymod.accountmanager.storage.account.Account;

public class AccountRefreshEvent extends AccountEvent
{
    public AccountRefreshEvent(final Account account) {
        super(account);
    }
    
    public String getNewAccessToken() {
        return this.account().getAccessToken();
    }
}
