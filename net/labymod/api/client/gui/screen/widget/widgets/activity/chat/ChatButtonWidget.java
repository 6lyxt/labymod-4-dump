// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.chat;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Locale;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.service.Identifiable;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ChatButtonWidget extends AbstractWidget<Widget> implements Identifiable
{
    private final Supplier<ChatInputTabActivity<?>> supplier;
    private final String id;
    private final Widget widget;
    private final Component defaultHoverComponent;
    private Component currentHoverComponent;
    @Nullable
    private String permissionId;
    private boolean allowed;
    @Nullable
    private ConfigProperty<Boolean> enabledProperty;
    
    private ChatButtonWidget(final String id, final Widget widget, final Supplier<ChatInputTabActivity<?>> supplier) {
        this.allowed = true;
        this.supplier = supplier;
        this.widget = widget;
        this.id = id;
        this.defaultHoverComponent = Component.translatable(String.format(Locale.ROOT, "labymod.chatInput.tab.%s.name", this.id), new Component[0]);
        this.currentHoverComponent = this.defaultHoverComponent;
        this.addId("chat-button");
        this.addId(id);
        widget.addId("chat-button-entry");
    }
    
    public static ChatButtonWidget icon(final String id, final Icon icon, final Supplier<ChatInputTabActivity<?>> supplier) {
        return new ChatButtonWidget(id, new IconWidget(icon), supplier);
    }
    
    public static ChatButtonWidget component(final String id, final Component component, final Supplier<ChatInputTabActivity<?>> supplier) {
        return new ChatButtonWidget(id, ComponentWidget.component(component), supplier);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.addChild(this.widget);
        this.setHoverComponent(this.currentHoverComponent);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        if (!this.isVisible() || this.opacity().get() <= 0.0f || !this.isHoverComponentRendered()) {
            return;
        }
        final ReasonableMutableRectangle rectangle = this.bounds().rectangle(BoundsType.OUTER);
        Laby.references().tooltipService().renderFixedTooltip(context.stack(), Point.fixed((int)rectangle.getCenterX(), (int)rectangle.getY()), this.getHoverComponent(), true);
    }
    
    @Override
    public void setHoverComponent(final Component component) {
        super.setHoverComponent(component);
        this.currentHoverComponent = component;
    }
    
    @Override
    public String getUniqueId() {
        return this.id;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    public ScreenInstance createScreen() {
        return this.supplier.get();
    }
    
    public boolean isEnabled() {
        return this.allowed;
    }
    
    public ChatButtonWidget setEnabled(final boolean enabled) {
        this.allowed = enabled;
        if (enabled) {
            this.setHoverComponent(this.defaultHoverComponent);
        }
        else {
            this.setHoverComponent(Component.translatable("labymod.chatInput.permission.description", NamedTextColor.RED, this.defaultHoverComponent.copy()));
        }
        return this;
    }
    
    public ChatButtonWidget setPermissionId(@Nullable final String permissionId) {
        this.permissionId = permissionId;
        return this;
    }
    
    @Nullable
    public String getPermissionId() {
        return this.permissionId;
    }
    
    public ConfigProperty<Boolean> getProperty() {
        return this.enabledProperty;
    }
    
    public ChatButtonWidget property(final ConfigProperty<Boolean> enabledProperty) {
        this.enabledProperty = enabledProperty;
        return this;
    }
}
