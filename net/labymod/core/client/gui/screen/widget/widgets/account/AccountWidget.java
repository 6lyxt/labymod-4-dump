// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.account;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.session.Session;
import net.labymod.api.Textures;
import java.util.Objects;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class AccountWidget extends AbstractWidget<Widget>
{
    private final Account account;
    private AccountSessionState previousState;
    
    public AccountWidget(final Account account) {
        this.account = account;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Icon icon = Icon.head(this.account.getUUID());
        final IconWidget avatarWidget = new IconWidget(icon);
        avatarWidget.addId("avatar");
        ((AbstractWidget<IconWidget>)this).addChild(avatarWidget);
        final ComponentWidget usernameWidget = ComponentWidget.component(Component.text(this.account.getUsername()));
        usernameWidget.addId("username");
        ((AbstractWidget<ComponentWidget>)this).addChild(usernameWidget);
        final AccountSessionState state = this.account.getSessionState();
        final ComponentWidget statusWidget = ComponentWidget.component(((BaseComponent<Component>)Component.translatable("labymod.activity.accountManager.account.state." + TextFormat.SNAKE_CASE.toLowerCamelCase(state.name()), new Component[0])).color((state == AccountSessionState.VALID) ? NamedTextColor.GREEN : ((state == AccountSessionState.VALIDATING || state == AccountSessionState.REFRESHING || state == AccountSessionState.RATE_LIMITED) ? NamedTextColor.YELLOW : NamedTextColor.RED)));
        statusWidget.addId("status");
        ((AbstractWidget<ComponentWidget>)this).addChild(statusWidget);
        final Session session = this.labyAPI.minecraft().sessionAccessor().getSession();
        final boolean isActive = session != null && session.getUniqueId().equals(this.account.getUUID()) && Objects.equals(session.getAccessToken(), this.account.getAccessToken());
        if (isActive) {
            final IconWidget activeWidget = new IconWidget(Textures.SpriteCommon.GREEN_CHECKED);
            activeWidget.addId("active");
            ((AbstractWidget<IconWidget>)this).addChild(activeWidget);
        }
        this.previousState = state;
    }
    
    @Override
    public void tick() {
        super.tick();
        final AccountSessionState state = this.account.getSessionState();
        if (this.previousState != state) {
            this.previousState = state;
            this.reInitialize();
        }
    }
    
    public Account account() {
        return this.account;
    }
}
