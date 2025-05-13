// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.labymod.account;

import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.event.Event;

public class AccountEvent implements Event
{
    private final Account account;
    
    public AccountEvent(final Account account) {
        this.account = account;
    }
    
    public Account account() {
        return this.account;
    }
}
