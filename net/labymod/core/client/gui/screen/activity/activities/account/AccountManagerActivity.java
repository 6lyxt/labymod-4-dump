// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.account;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.main.quicklauncher.QuickLauncher;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.client.gui.screen.widget.widgets.account.AccountWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/account/account-manager.lss")
@AutoActivity
public class AccountManagerActivity extends SimpleActivity
{
    private static final ListSession<AccountWidget> ACCOUNT_LIST_SESSION;
    private VerticalListWidget<AccountWidget> listWidget;
    private ButtonWidget addButton;
    private ButtonWidget switchButton;
    private ButtonWidget deleteButton;
    private ButtonWidget shortcutButton;
    private ButtonWidget refreshButton;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget headerWidget = new DivWidget();
        headerWidget.addId("header");
        ((AbstractWidget<DivWidget>)this.document).addChild(headerWidget);
        final DivWidget containerWidget = new DivWidget();
        containerWidget.addId("account-container");
        (this.listWidget = new VerticalListWidget<AccountWidget>(AccountManagerActivity.ACCOUNT_LIST_SESSION)).addId("account-list");
        this.listWidget.setSelectCallback(value -> this.updateButtonStates());
        this.listWidget.setDoubleClickCallback(value -> {
            this.switchAccount();
            this.displayPreviousScreen();
            return;
        });
        final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        for (final Account account : accountManager.getAccounts()) {
            if (account instanceof ExternalAccount) {
                final AccountWidget accountWidget = new AccountWidget(account);
                accountWidget.addId("account");
                accountWidget.createContextMenu().with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.switch", new Component[0])).clickHandler(entry -> {
                    this.switchAccount(account);
                    return true;
                }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler(entry -> {
                    this.removeAccount(account);
                    return true;
                }).build());
                this.listWidget.addChild(accountWidget);
            }
        }
        final ScrollWidget scrollWidget = new ScrollWidget(this.listWidget);
        scrollWidget.addId("account-list-scroll");
        ((AbstractWidget<ScrollWidget>)containerWidget).addChild(scrollWidget);
        ((AbstractWidget<DivWidget>)this.document).addChild(containerWidget);
        final DivWidget footerWidget = new DivWidget();
        footerWidget.addId("footer");
        final HorizontalListWidget actionListWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)actionListWidget).addId("action-buttons");
        ((AbstractWidget<Widget>)(this.addButton = ButtonWidget.i18n("labymod.ui.button.add"))).addId("add-account");
        this.addButton.setPressable(this::addAccount);
        actionListWidget.addEntry(this.addButton);
        ((AbstractWidget<Widget>)(this.switchButton = ButtonWidget.i18n("labymod.ui.button.switch"))).addId("switch-account");
        this.switchButton.setPressable(this::switchAccount);
        actionListWidget.addEntry(this.switchButton);
        ((AbstractWidget<Widget>)(this.deleteButton = ButtonWidget.i18n("labymod.ui.button.delete"))).addId("delete-account");
        this.deleteButton.setPressable(this::removeAccount);
        actionListWidget.addEntry(this.deleteButton);
        ((AbstractWidget<Widget>)(this.shortcutButton = ButtonWidget.icon(Textures.SpriteCommon.EXPORT))).addId("shortcut-account");
        this.shortcutButton.setPressable(this::createShortcut);
        (this.refreshButton = ButtonWidget.icon(Textures.SpriteCommon.REFRESH)).setHoverComponent(Component.translatable("labymod.activity.accountManager.refresh.description", new Component[0]));
        ((AbstractWidget<Widget>)this.refreshButton).addId("refresh-sessions");
        this.refreshButton.setPressable(() -> {
            final AsyncAccountManager accountManager2 = LabyMod.getInstance().getAccountManager();
            this.refreshButton.setEnabled(false);
            accountManager2.refreshExternalSessionsAsync(account -> {
                try {
                    Thread.sleep(100L);
                }
                catch (final InterruptedException ex) {}
            }, new Runnable[] { () -> {
                    this.refreshButton.setEnabled(true);
                    ThreadSafe.executeOnRenderThread(this::reload);
                } });
            return;
        });
        actionListWidget.addEntry(this.refreshButton);
        ((AbstractWidget<HorizontalListWidget>)footerWidget).addChild(actionListWidget);
        ((AbstractWidget<DivWidget>)this.document).addChild(footerWidget);
        this.updateButtonStates();
    }
    
    private void updateButtonStates() {
        final AccountWidget selectedEntry = this.listWidget.listSession().getSelectedEntry();
        final QuickLauncher launcher = LabyMod.references().quickLauncher();
        this.addButton.setEnabled(true);
        this.switchButton.setEnabled(selectedEntry != null);
        this.shortcutButton.setEnabled(selectedEntry != null && launcher.iSupported());
        this.deleteButton.setEnabled(selectedEntry != null);
        this.shortcutButton.setHoverComponent((selectedEntry == null) ? null : Component.translatable(launcher.iSupported() ? "labymod.activity.accountManager.export.description" : "labymod.activity.accountManager.export.notSupported", new Component[0]));
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        return super.keyPressed(key, type);
    }
    
    private void addAccount() {
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new MicrosoftLoginActivity(newAccount -> this.reload()));
    }
    
    private void switchAccount() {
        final AccountWidget entry = this.listWidget.listSession().getSelectedEntry();
        if (entry == null) {
            return;
        }
        this.switchAccount(entry.account());
    }
    
    private void switchAccount(final Account account) {
        ((DefaultAbstractSessionAccessor)Laby.labyAPI().minecraft().sessionAccessor()).updateSession(account);
        this.reload();
    }
    
    private void createShortcut() {
        final AccountWidget entry = this.listWidget.listSession().getSelectedEntry();
        if (entry == null) {
            return;
        }
        final QuickLauncher launcher = LabyMod.references().quickLauncher();
        if (!launcher.iSupported()) {
            return;
        }
        launcher.createAsync(entry.account());
        this.shortcutButton.setEnabled(false);
    }
    
    private void removeAccount() {
        final AccountWidget entry = this.listWidget.listSession().getSelectedEntry();
        if (entry == null) {
            return;
        }
        this.removeAccount(entry.account());
    }
    
    private void removeAccount(final Account account) {
        final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        accountManager.getStorage().removeAccount((ExternalAccount)account);
        accountManager.saveAsync(new Runnable[0]);
        this.reload();
    }
    
    static {
        ACCOUNT_LIST_SESSION = new ListSession<AccountWidget>((a, b) -> a.account().getUUID().equals(b.account().getUUID()) && b.account().getAccessToken().equals(b.account().getAccessToken()));
    }
}
