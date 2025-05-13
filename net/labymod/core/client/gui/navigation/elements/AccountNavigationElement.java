// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.accountmanager.storage.StorageType;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.AsyncAccountManager;
import java.util.UUID;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.ManageEntry;
import net.labymod.core.main.LabyMod;
import java.util.Objects;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.DefaultAccountEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.renderer.AccountEntryRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry;
import net.labymod.api.util.UUIDHelper;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.account.AccountManagerActivity;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;

public class AccountNavigationElement extends ScreenBaseNavigationElement<DropdownWidget<?>>
{
    public AccountNavigationElement() {
        super(new AccountManagerActivity());
    }
    
    @Override
    public String getWidgetId() {
        return "account";
    }
    
    @Override
    public Icon getIcon() {
        return null;
    }
    
    @Override
    public Component getDisplayName() {
        return null;
    }
    
    @Override
    public DropdownWidget<?> createWidget(final NavigationWrapper wrapper) {
        final SessionAccessor sessionAccessor = Laby.labyAPI().minecraft().sessionAccessor();
        final Session session = sessionAccessor.getSession();
        final String username = (session == null) ? "Account" : session.getUsername();
        final UUID uniqueId = (session == null) ? UUIDHelper.createUniqueId(username) : session.getUniqueId();
        final String accessToken = (session == null) ? "" : session.getAccessToken();
        final DropdownWidget<AccountEntry> dropdownWidget = new DropdownWidget<AccountEntry>();
        dropdownWidget.addId("account-dropdown");
        dropdownWidget.setEntryRenderer(new AccountEntryRenderer());
        dropdownWidget.setSelected(DefaultAccountEntry.of(uniqueId, username, accessToken));
        dropdownWidget.setChangeListener(entry -> {
            Objects.requireNonNull(wrapper);
            entry.interact(wrapper::reload);
            return;
        });
        final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        for (final Account account : accountManager.getAccounts()) {
            if (isDisplayableAccount(account)) {
                dropdownWidget.add(DefaultAccountEntry.of(account));
            }
        }
        dropdownWidget.add(new ManageEntry());
        return dropdownWidget;
    }
    
    @Subscribe(64)
    public void onSessionUpdate(final SessionUpdateEvent event) {
        ThreadSafe.executeOnRenderThread(this::updateWidget);
    }
    
    public static boolean isDisplayableAccount(final Account account) {
        if (account.isMicrosoft()) {
            return account.getStorageType() == StorageType.EXTERNAL && account.getSessionState() != AccountSessionState.EXPIRED && account.getSessionState() != AccountSessionState.UNKNOWN && account.getSessionState() != AccountSessionState.OFFLINE;
        }
        return account.getSessionState() == AccountSessionState.OFFLINE;
    }
}
