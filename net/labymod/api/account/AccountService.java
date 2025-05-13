// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.account;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AccountService
{
    @Deprecated(forRemoval = true, since = "4.2.42")
    @NotNull
    List<Account> getAccounts();
}
