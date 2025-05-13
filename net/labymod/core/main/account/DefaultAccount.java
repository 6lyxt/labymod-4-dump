// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.account;

import net.labymod.api.account.Account;

@Deprecated(forRemoval = true, since = "4.2.42")
public class DefaultAccount implements Account
{
    private final String username;
    
    public DefaultAccount(final String username) {
        this.username = username;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
}
