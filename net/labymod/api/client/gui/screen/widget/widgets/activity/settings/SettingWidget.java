// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.revision.Revision;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.type.list.ListSettingEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.user.permission.ClientPermission;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.Iterator;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.function.Consumer;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

@AutoWidget
public class SettingWidget extends FlexibleContentWidget
{
    private final Setting setting;
    private final Consumer<Setting> advancedCallback;
    private boolean lastRenderEnabled;
    
    public SettingWidget(final Setting setting, final boolean lazy) {
        this(setting, lazy, null);
    }
    
    public SettingWidget(final Setting setting, final boolean lazy, final Consumer<Setting> advancedCallback) {
        this.setting = setting;
        this.advancedCallback = advancedCallback;
        this.lazy = ((!this.setting.isElement() || !this.setting.asElement().isExtended()) && lazy);
    }
    
    public Setting setting() {
        return this.setting;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final boolean enabled = this.setting.isEnabled();
        if (this.lastRenderEnabled != enabled) {
            this.lastRenderEnabled = enabled;
            this.setAttributeState(AttributeState.ENABLED, enabled);
        }
        super.renderWidget(context);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean settingEnabled = this.setting.isEnabled();
        final boolean tempPreventStateUpdate = this.preventStateUpdate;
        this.preventStateUpdate = true;
        this.lastRenderEnabled = settingEnabled;
        this.setAttributeState(AttributeState.ENABLED, settingEnabled);
        this.preventStateUpdate = tempPreventStateUpdate;
        final Widget[] inputWidgets = (Widget[])(this.setting.isElement() ? this.setting.asElement().getWidgets() : null);
        final DivWidget statusIndicator = new DivWidget();
        statusIndicator.addId("status-indicator");
        this.addContent(statusIndicator);
        final FlexibleContentWidget content = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)content).addId("setting-content");
        final Component description = this.setting.getDescription();
        if (description != null) {
            content.setHoverComponent(description, 300.0f);
        }
        this.initializeInfoWidgets(content);
        this.initializeInteractionWidget(content, inputWidgets, statusIndicator);
        if (inputWidgets != null && this.setting.asElement().isExtended()) {
            final VerticalListWidget<Widget> extended = (VerticalListWidget<Widget>)new VerticalListWidget().addId("extended-content");
            extended.addChild(content);
            for (final Widget inputWidget : inputWidgets) {
                if (!(inputWidget instanceof AddonActivityWidget)) {
                    inputWidget.reset();
                    inputWidget.addId("extended-input-widget");
                    extended.addChild(inputWidget);
                }
            }
            this.addFlexibleContent(extended);
        }
        else {
            this.addFlexibleContent(content);
        }
        if (this.setting.isElement()) {
            final SettingElement settingElement = this.setting.asElement();
            final SettingAccessor accessor = settingElement.getAccessor();
            if (settingElement.getResetListener() != null || (accessor != null && accessor.property() != null)) {
                final ContextMenu contextMenu = this.createContextMenu();
                contextMenu.with(ContextMenuEntry.builder().icon(Textures.SpriteCommon.TRASH).text(Component.translatable("labymod.ui.button.reset", new Component[0])).disabled(new BooleanSupplier(this) {
                    @Override
                    public boolean getAsBoolean() {
                        boolean resettable = settingElement.isResettable();
                        if (!resettable) {
                            for (final Setting childSetting : settingElement.values()) {
                                if (childSetting.isResettable()) {
                                    resettable = true;
                                    break;
                                }
                            }
                        }
                        return !resettable;
                    }
                }).clickHandler(new BiConsumer<ContextMenuEntry, Object>() {
                    @Override
                    public void accept(final ContextMenuEntry entry, final Object widget) {
                        SettingWidget.this.resetSetting(settingElement);
                    }
                }).build());
                final ClientPermission permission = this.getPermission();
                if (permission != null && !permission.isDefaultEnabled() && !permission.isEnabled() && this.labyAPI.minecraft().isIngame() && this.setting.canForceEnable()) {
                    final String forceEnableKey = "labymod.ui.settings.serverFeatures.forceEnable.%s";
                    contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.SHIELD).text(Component.translatable(String.format(Locale.ROOT, forceEnableKey, "entry"), new Component[0])).clickHandler(entry -> {
                        PopupWidget.builder().title(Component.translatable(String.format(Locale.ROOT, forceEnableKey, "title"), new Component[0])).text(Component.translatable(String.format(Locale.ROOT, forceEnableKey, "warning"), new Component[0])).confirmCallback(() -> {
                            permission.setEnabled(true);
                            this.reInitialize();
                            return;
                        }).build().displayInOverlay();
                        return true;
                    }).build());
                }
            }
        }
    }
    
    private void resetSetting(final SettingElement setting) {
        setting.reset();
        this.callActionListeners(true);
        for (final Setting childSetting : setting.values()) {
            if (childSetting.isElement()) {
                this.resetSetting(childSetting.asElement());
            }
        }
    }
    
    private void initializeInfoWidgets(final FlexibleContentWidget header) {
        SettingOverlayInfo settingOverlayInfo = null;
        final ClientPermission permission = this.getPermission();
        if (permission != null) {
            ((AbstractWidget<Widget>)this).addId("permission-required");
            final boolean permissionDefaultEnabled = permission.isDefaultEnabled();
            final boolean permissionEnabled = !this.labyAPI.serverController().isConnected() || permission.isEnabled();
            if ((!permissionDefaultEnabled && !this.labyAPI.minecraft().isIngame()) || !permissionEnabled) {
                settingOverlayInfo = permission.overlayInfo();
            }
        }
        if (settingOverlayInfo == null && this.setting.isElement()) {
            settingOverlayInfo = this.setting.asElement().getOverlayInfo();
        }
        if (settingOverlayInfo != null) {
            final boolean active = settingOverlayInfo.isActive();
            if (active || settingOverlayInfo.isExclamationMarkAlwaysVisible()) {
                final IconWidget widget = new IconWidget(null);
                widget.addId("permission-warning");
                if (!active) {
                    widget.setHoverComponent(settingOverlayInfo.component());
                }
                header.addContent(widget);
            }
            if (active) {
                ((AbstractWidget<Widget>)this).addId("disabled");
                final DivWidget permissionDisabled = new DivWidget();
                permissionDisabled.addId("permission-disabled");
                permissionDisabled.setPressable(new Pressable(this) {
                    @Override
                    public void press() {
                    }
                });
                this.addContent(permissionDisabled);
                header.setHoverComponent(settingOverlayInfo.component());
            }
        }
        final Icon icon = this.setting.getIcon();
        if (icon != null) {
            final IconWidget widget = new IconWidget(icon);
            widget.addId("setting-icon");
            header.addContent(widget);
        }
        final ComponentWidget displayName = ComponentWidget.component(this.setting.displayName());
        displayName.addId("display-name");
        header.addFlexibleContent(displayName);
    }
    
    @Override
    public boolean isVisible() {
        return super.isVisible() && (!this.setting.isElement() || this.setting.asElement().isVisible());
    }
    
    private ClientPermission getPermission() {
        if (!this.setting.isElement()) {
            return null;
        }
        final String requiredPermission = this.setting.asElement().getRequiredPermission();
        return (requiredPermission == null) ? null : this.labyAPI.permissionRegistry().getPermission(requiredPermission);
    }
    
    private void initializeInteractionWidget(final FlexibleContentWidget header, final Widget[] inputWidgets, final Widget indicator) {
        final SettingHandler handler = this.setting.handler();
        SwitchWidget advancedSwitch = null;
        SwitchWidget indicatorSwitch = null;
        if (inputWidgets != null) {
            for (final Widget inputWidget : inputWidgets) {
                if (inputWidget instanceof SettingActivitySupplier) {
                    inputWidget.setPressable(new Pressable() {
                        @Override
                        public void press() {
                            SettingWidget.this.advancedCallback.accept(SettingWidget.this.setting);
                        }
                    });
                }
            }
        }
        if (this.setting.isElement()) {
            final Revision revision = this.setting.asElement().getRevision();
            if (revision != null && revision.isRelevant()) {
                final IconWidget newBadge = new IconWidget(Textures.SpriteCommon.NEW);
                newBadge.addId("new-badge");
                newBadge.setHoverComponent(Component.translatable("labymod.misc.introduced", new Component[0]).color(NamedTextColor.BLUE).argument(((BaseComponent<Component>)Component.text(revision.getDisplayName()).color(NamedTextColor.WHITE)).decorate(TextDecoration.BOLD)));
                header.addContent(newBadge);
            }
        }
        if (this.setting.hasAdvancedButton() || (inputWidgets != null && inputWidgets[0] instanceof AddonActivityWidget) || (handler != null && handler.opensActivity(this.setting))) {
            ButtonWidget advancedButton = null;
            if (inputWidgets != null) {
                final Widget widget = inputWidgets[0];
                if (widget instanceof final AddonActivityWidget addonActivityWidget) {
                    final String text = addonActivityWidget.getText();
                    if (text != null) {
                        advancedButton = ButtonWidget.text(text);
                    }
                }
            }
            if (advancedButton == null) {
                advancedButton = ButtonWidget.advancedSettings();
            }
            ((AbstractWidget<Widget>)advancedButton).addId("advanced-button");
            advancedButton.setPressable(new Pressable() {
                @Override
                public void press() {
                    SettingWidget.this.advancedCallback.accept(SettingWidget.this.setting);
                }
            });
            header.addContent(advancedButton);
            if (this.setting.hasControlButton()) {
                final Setting setting = this.setting;
                if (setting instanceof final SettingElement settingElement) {
                    final Widget[] widgets = settingElement.getWidgets();
                    if (widgets != null && widgets.length > 0 && widgets[0] instanceof SwitchWidget) {
                        advancedSwitch = (SwitchWidget)widgets[0];
                    }
                }
            }
        }
        if (this.setting instanceof ListSettingEntry) {
            final ButtonWidget advancedButton = ButtonWidget.deleteButton();
            ((AbstractWidget<Widget>)advancedButton).addId("delete-button");
            advancedButton.setPressable(new Pressable() {
                @Override
                public void press() {
                    ((ListSettingEntry)SettingWidget.this.setting).remove();
                    SettingWidget.this.advancedCallback.accept(null);
                }
            });
            header.addContent(advancedButton);
        }
        if (inputWidgets != null && !this.setting.asElement().isExtended()) {
            final FlexibleContentWidget div = new FlexibleContentWidget();
            ((AbstractWidget<Widget>)div).addId("input-wrapper");
            for (int i = inputWidgets.length - 1; i >= 0; --i) {
                final Widget inputWidget2 = inputWidgets[i];
                if (indicatorSwitch == null && inputWidget2 instanceof SwitchWidget) {
                    indicatorSwitch = (SwitchWidget)inputWidget2;
                }
                if (!(inputWidget2 instanceof AddonActivityWidget)) {
                    inputWidget2.reset();
                    inputWidget2.addId("input-widget");
                    if (i == 0) {
                        div.addContent(inputWidget2);
                    }
                    else {
                        div.addFlexibleContent(inputWidget2);
                    }
                }
            }
            if (!div.getChildren().isEmpty()) {
                header.addContent(div);
            }
        }
        final SwitchWidget switchWidget = (indicatorSwitch != null) ? indicatorSwitch : advancedSwitch;
        if (switchWidget != null) {
            indicator.addId(switchWidget.getValue() ? "status-enabled" : "status-disabled");
            switchWidget.setActionListener("indicator", new Runnable(this) {
                @Override
                public void run() {
                    indicator.replaceId(switchWidget.getValue() ? "status-disabled" : "status-enabled", switchWidget.getValue() ? "status-enabled" : "status-disabled");
                }
            });
        }
    }
}
