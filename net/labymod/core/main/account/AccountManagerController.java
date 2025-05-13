// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.account;

import net.labymod.accountmanager.authentication.microsoft.oauth.OAuthServer;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import net.labymod.api.account.Account;
import java.util.List;
import net.labymod.accountmanager.storage.loader.external.ExternalAccountStorage;
import net.labymod.api.client.session.Session;
import net.labymod.api.Laby;
import net.labymod.core.event.labymod.account.AccountRefreshEvent;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import java.util.concurrent.atomic.AtomicBoolean;
import net.labymod.core.main.LabyMod;
import java.util.concurrent.TimeUnit;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.authentication.microsoft.azure.Azure;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.account.AccountService;

@Singleton
@Implements(AccountService.class)
public class AccountManagerController implements AccountService
{
    private static final Logging LOGGER;
    private static final String CLIENT_ID = "27843883-6e3b-42cb-9e51-4f55a700601e";
    private static final String SCOPE = "XboxLive.signin%20offline_access";
    public static final Azure AZURE;
    private AsyncAccountManager accountManager;
    private final Task updateTask;
    private final DefaultAbstractSessionAccessor<?> sessionAccessor;
    
    public AccountManagerController() {
        this.updateTask = Task.builder(() -> {
            try {
                this.onUpdate();
            }
            catch (final Exception exception) {
                exception.printStackTrace();
            }
            return;
        }).delay(1L, TimeUnit.MINUTES).repeat(1L, TimeUnit.MINUTES).build();
        this.sessionAccessor = (DefaultAbstractSessionAccessor)LabyMod.references().sessionAccessor();
    }
    
    public void initialize(final AsyncAccountManager accountManager) {
        this.accountManager = accountManager;
        AccountManagerController.LOGGER.info("Loading accounts...", new Object[0]);
        this.accountManager.loadAsync(AccountManagerController.AZURE, new Runnable[] { () -> {
                AccountManagerController.LOGGER.info("Loaded all accounts. Refreshing external sessions...", new Object[0]);
                this.accountManager.refreshExternalSessions();
                AccountManagerController.LOGGER.info("External sessions refreshed.", new Object[0]);
                try {
                    this.accountManager.save();
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
                this.schedule();
            } });
    }
    
    private void schedule() {
        if (this.updateTask != null) {
            this.updateTask.execute();
        }
    }
    
    private void onUpdate() throws Exception {
        final Session activeSession = this.sessionAccessor.getSession();
        final ExternalAccountStorage storage = this.accountManager.getStorage();
        if (storage.hasFileChanged()) {
            storage.reload();
            storage.refreshAccounts(account -> {});
        }
        final AtomicBoolean changed = new AtomicBoolean(false);
        storage.refreshJustExpiredAccounts(account -> {
            if (account.getSessionState() != AccountSessionState.VALID) {
                return;
            }
            else {
                final boolean isCurrentSession = activeSession != null && account.getUUID().equals(activeSession.getUniqueId());
                if (isCurrentSession) {
                    this.sessionAccessor.updateSession(account);
                }
                Laby.fireEvent(new AccountRefreshEvent(account));
                changed.set(true);
                return;
            }
        });
        if (changed.get()) {
            this.accountManager.save();
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    @NotNull
    @Override
    public List<Account> getAccounts() {
        if (this.accountManager == null) {
            return Collections.emptyList();
        }
        final List<Account> accounts = new ArrayList<Account>();
        for (final net.labymod.accountmanager.storage.account.Account account : this.accountManager.getAccounts()) {
            accounts.add(new DefaultAccount(account.getUsername()));
        }
        return accounts;
    }
    
    static {
        LOGGER = Logging.getLogger();
        AZURE = new Azure("27843883-6e3b-42cb-9e51-4f55a700601e", OAuthServer.REDIRECT_URL, "XboxLive.signin%20offline_access");
    }
}
