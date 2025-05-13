// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.account;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.TextFormat;
import net.labymod.accountmanager.authentication.microsoft.MicrosoftAuthState;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.accountmanager.storage.loader.external.ExternalAccountStorage;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.accountmanager.authentication.AsyncAccountAuthentication;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import java.io.IOException;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.account.AccountManagerController;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.accountmanager.authentication.microsoft.oauth.OAuthServer;
import net.labymod.api.client.component.Component;
import net.labymod.accountmanager.storage.account.Account;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/account/microsoft-login.lss")
@AutoActivity
public class MicrosoftLoginActivity extends SimpleActivity
{
    protected final Consumer<Account> callback;
    private Component lastStatusMessage;
    private OAuthServer server;
    
    public MicrosoftLoginActivity(final Consumer<Account> callback) {
        this.callback = callback;
        this.lastStatusMessage = Component.translatable("labymod.activity.accountManager.login.state.open", Style.style(NamedTextColor.GREEN));
        final MicrosoftLoginActivity scope = this;
        try {
            this.server = new OAuthServer(AccountManagerController.AZURE);
            this.labyAPI.minecraft().chatExecutor().openUrl(this.server.getUrl().toString());
            this.server.listenForCodeAsync(code -> {
                if (code == null) {
                    scope.updateStatusMessage(Component.translatable("labymod.activity.accountManager.login.state.denied", Style.style(NamedTextColor.YELLOW)));
                }
                else {
                    scope.authUsingCode(code);
                }
            });
        }
        catch (final Exception exception) {
            scope.updateStatusMessage(Component.text(exception.getMessage(), NamedTextColor.RED));
            MicrosoftLoginActivity.LOGGER.warn("Error while listening for the microsoft account code", new Object[0]);
            exception.printStackTrace();
        }
    }
    
    private void updateStatusMessage(final Component message) {
        this.lastStatusMessage = message;
        this.labyAPI.minecraft().executeOnRenderThread(this::reload);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
        container.addId("microsoft-login-container");
        final HorizontalListWidget logo = new HorizontalListWidget();
        ((AbstractWidget<Widget>)logo).addId("microsoft-logo");
        final IconWidget iconWidget = new IconWidget(Textures.SpriteAccountManager.MICROSOFT_LOGO);
        iconWidget.addId("microsoft-icon");
        logo.addEntry(iconWidget);
        final ComponentWidget nameWidget = ComponentWidget.component(Component.text("Microsoft"));
        nameWidget.addId("microsoft-text");
        logo.addEntry(nameWidget);
        container.addChild(logo);
        final ComponentWidget statusWidget = ComponentWidget.component(this.lastStatusMessage);
        statusWidget.addId("microsoft-status");
        container.addChild(statusWidget);
        final DivWidget buttonWrapper = new DivWidget();
        buttonWrapper.addId("button-wrapper");
        final ButtonWidget copyButton = ButtonWidget.icon(Textures.SpriteCommon.COPY, () -> {
            if (this.server != null) {
                try {
                    this.labyAPI.minecraft().setClipboard(this.server.getUrl().toString());
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            return;
        });
        ((AbstractWidget<Widget>)copyButton).addId("button-copy");
        copyButton.setHoverComponent(Component.translatable("labymod.activity.accountManager.login.copy", new Component[0]));
        ((AbstractWidget<ButtonWidget>)buttonWrapper).addChild(copyButton);
        final ButtonWidget buttonCancel = ButtonWidget.i18n("labymod.ui.button.cancel", this::displayPreviousScreen);
        ((AbstractWidget<Widget>)buttonCancel).addId("button-cancel");
        ((AbstractWidget<ButtonWidget>)buttonWrapper).addChild(buttonCancel);
        final ButtonWidget offlineButton = ButtonWidget.icon(Textures.SpriteCommon.DISCONNECT, () -> {
            final TextFieldWidget textFieldWidget = new TextFieldWidget();
            textFieldWidget.maximalLength(19);
            final PopupWidget widget = PopupWidget.builder().title(Component.translatable("labymod.activity.accountManager.login.offline.title", new Component[0])).text(Component.translatable("labymod.activity.accountManager.login.offline.prompt", new Component[0])).confirmComponent(Component.translatable("labymod.ui.button.add", new Component[0])).widgetSupplier(() -> {
                textFieldWidget.placeholder(Component.translatable("labymod.ui.textfield.username", new Component[0]));
                return textFieldWidget;
            }).confirmCallback(() -> {
                final String username = textFieldWidget.getText();
                this.submitUsername(username);
                return;
            }).build();
            widget.displayInOverlay();
            return;
        });
        ((AbstractWidget<Widget>)offlineButton).addId("button-offline");
        offlineButton.setHoverComponent(Component.translatable("labymod.activity.accountManager.login.offline.tooltip", new Component[0]));
        ((AbstractWidget<ButtonWidget>)buttonWrapper).addChild(offlineButton);
        container.addChild(buttonWrapper);
        ((AbstractWidget<VerticalListWidget<Widget>>)this.document).addChild(container);
    }
    
    @Override
    public void onCloseScreen() {
        if (this.server != null) {
            this.server.close();
        }
        super.onCloseScreen();
    }
    
    private void authUsingCode(final String code) {
        final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        final ExternalAccountStorage externalStorage = accountManager.getStorage();
        final AsyncAccountAuthentication auth = (AsyncAccountAuthentication)externalStorage.getAuthentication();
        auth.setStateCallback(state -> {
            final String stateId = TextFormat.SNAKE_CASE.toCamelCase(state.name(), true);
            final Component component = Component.translatable("labymod.activity.accountManager.login.state." + stateId, new Component[0]);
            this.updateStatusMessage(component.style(Style.style(NamedTextColor.YELLOW)));
        });
        auth.authenticateMicrosoftAsync(code, account -> {
            try {
                this.submitAccount(account);
            }
            catch (final Exception e) {
                MicrosoftLoginActivity.LOGGER.warn("Error while submitting microsoft account", new Object[0]);
                this.updateStatusMessage(Component.text(e.getMessage(), NamedTextColor.RED));
            }
        }, e -> {
            MicrosoftLoginActivity.LOGGER.warn("Error while authenticating microsoft account", new Object[0]);
            e.printStackTrace();
            this.updateStatusMessage(Component.text(e.getMessage(), NamedTextColor.RED));
        });
    }
    
    private void submitUsername(final String username) {
        Laby.references().labyNetController().loadUniqueIdByName(username, result -> {
            final UUID uniqueId = (UUID)result.getOrDefault(UUID.randomUUID());
            final ExternalAccount account = new ExternalAccount(uniqueId, username, "0");
            account.setSessionState(AccountSessionState.OFFLINE);
            this.submitAccount(account);
        });
    }
    
    protected void submitAccount(final ExternalAccount account) {
        final DefaultAbstractSessionAccessor<?> accessor = (DefaultAbstractSessionAccessor<?>)this.labyAPI.minecraft().sessionAccessor();
        final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        accountManager.getStorage().addAccount(account);
        accountManager.saveAsync(new Runnable[0]);
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            accessor.updateSession((Account)account);
            this.displayPreviousScreen();
            this.callback.accept((Account)account);
        });
    }
}
