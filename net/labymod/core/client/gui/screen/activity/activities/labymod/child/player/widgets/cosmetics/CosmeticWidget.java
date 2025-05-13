// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.notification.Notification;
import net.labymod.core.labymodnet.models.ChangeResponse;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.util.Debounce;
import net.labymod.core.main.user.shop.item.metadata.util.ItemMetadataUtil;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Collections;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.CosmeticsActivity;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

@AutoWidget
public class CosmeticWidget extends FlexibleContentWidget
{
    private static final LabyModNetService LABY_MOD_NET_SERVICE;
    private static final String DEBOUNCE_ID = "cosmetic-toggle-debounce";
    private static final String INVALID_OPTIONS_TRANSLATION_KEY = "labymod.activity.customization.cosmetics.invalidOptions";
    private static final String DASHBOARD_ONLY_TRANSLATION_KEY = "labymod.activity.customization.cosmetics.dashboardOnly";
    private final WidgetOptionService widgetOptionService;
    private final CosmeticsActivity cosmeticsActivity;
    private final Cosmetic cosmetic;
    private final AbstractItem item;
    @Nullable
    private final Consumer<Cosmetic> cosmeticUpdateListener;
    private final CosmeticWidgetSettingsListener listener;
    private ItemTextureListener itemTextureListener;
    private SwitchWidget switchWidget;
    private CosmeticSettingsWidget cosmeticSettingsWidget;
    private boolean waitingForResponse;
    private boolean invalidCosmeticData;
    private List<WidgetOption> options;
    
    public CosmeticWidget(final CosmeticsActivity cosmeticsActivity, final WidgetOptionService widgetOptionService, final Cosmetic cosmetic, final AbstractItem item, @Nullable final Consumer<Cosmetic> cosmeticUpdateListener) {
        this.options = Collections.emptyList();
        this.cosmetic = cosmetic;
        this.item = item;
        this.widgetOptionService = widgetOptionService;
        this.cosmeticsActivity = cosmeticsActivity;
        this.cosmeticUpdateListener = cosmeticUpdateListener;
        this.listener = new CosmeticWidgetSettingsListener(this);
        this.lazy = true;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.setCosmeticActive(this.cosmetic.isEnabled());
        final int id = this.cosmetic.getItemId();
        final boolean dashboardOnly = id == 32 || id == 36 || id == 0 || id == 22;
        if (this.options.isEmpty()) {
            this.options = this.widgetOptionService.getOptions(this.cosmetic, () -> {
                if (dashboardOnly) {
                    return;
                }
                else {
                    this.invalidCosmeticData = true;
                    return;
                }
            });
        }
        final DivWidget iconWrapper = new DivWidget();
        iconWrapper.addId("icon-wrapper");
        final IconWidget icon = new IconWidget(this.cosmetic.icon());
        icon.addId("icon");
        icon.setCleanupOnDispose(true);
        ((AbstractWidget<IconWidget>)iconWrapper).addChild(icon);
        final boolean invalidCosmeticData = dashboardOnly || this.hasInvalidCosmeticData();
        final IconWidget wrappedIcon = new IconWidget(invalidCosmeticData ? Textures.SpriteCommon.EXCLAMATION_MARK_DARK : Textures.SpriteCommon.SETTINGS);
        wrappedIcon.addId(invalidCosmeticData ? "error-icon" : "settings-icon");
        if (invalidCosmeticData) {
            Component component;
            if (dashboardOnly) {
                component = Component.translatable("labymod.activity.customization.cosmetics.dashboardOnly", new Component[0]);
            }
            else {
                component = Component.translatable("labymod.activity.customization.cosmetics.invalidOptions", new Component[0]);
            }
            wrappedIcon.setHoverComponent(component);
            wrappedIcon.setVisible(true);
        }
        ((AbstractWidget<IconWidget>)iconWrapper).addChild(wrappedIcon);
        this.addFlexibleContent(iconWrapper);
        final FlexibleContentWidget infoWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)infoWrapper).addId("info-wrapper");
        final ComponentWidget name = ComponentWidget.text(this.cosmetic.getName());
        name.addId("name");
        infoWrapper.addFlexibleContent(name);
        (this.switchWidget = SwitchWidget.text("", "", this::updateCosmeticEnabled)).setValue(this.cosmetic.isEnabled());
        this.switchWidget.addId("switch");
        infoWrapper.addContent(this.switchWidget);
        this.addContent(infoWrapper);
    }
    
    public void setCosmeticActive(final boolean active) {
        this.cosmetic.setEnabled(active);
        if (this.switchWidget != null && this.switchWidget.getValue() != active) {
            this.switchWidget.setValue(active);
            this.switchWidget.refreshActionTime();
        }
        if (this.cosmeticSettingsWidget != null) {
            this.cosmeticSettingsWidget.setCosmeticEnabled(active);
        }
        if (active) {
            ((AbstractWidget<Widget>)this).addId("active");
        }
        else {
            this.removeId("active");
        }
        if (this.cosmeticsActivity.getOpenSettingsId() == this.cosmetic.getItemId()) {
            if (active) {
                this.cosmeticsActivity.updateModelFocus(this.item, this.cosmetic);
            }
            else {
                this.cosmeticsActivity.resetModelFocus();
            }
        }
        final GameUser user = Laby.references().gameUserService().clientGameUser();
        LabyMod.references().shopItemLayer().resetAnimations(user, true);
    }
    
    public void updateCosmeticEnabled(final boolean enabled) {
        this.updateCosmeticEnabled(enabled, false);
    }
    
    public void updateCosmeticEnabled(final boolean enabled, final boolean skipListener) {
        this.setCosmeticActive(enabled);
        this.waitingForResponse = true;
        if (!skipListener && this.cosmeticUpdateListener != null) {
            this.cosmeticUpdateListener.accept(this.cosmetic);
        }
        if (enabled) {
            this.displayCosmeticOptions(false);
        }
        ItemMetadataUtil.updateGameUser(this.cosmetic, this.itemTextureListener);
        Debounce.of("cosmetic-toggle-debounce" + this.cosmetic.getId(), 1000L, () -> CosmeticWidget.LABY_MOD_NET_SERVICE.toggleCosmetic(this.cosmetic, enabled, changeResponse -> this.labyAPI.minecraft().executeOnRenderThread(() -> this.handleToggleResponse(changeResponse))));
    }
    
    public void onCloseSettings(final CosmeticSettingsWidget cosmeticSettingsWidget) {
        this.setAttributeState(AttributeState.ACTIVE, false);
        this.cosmeticSettingsWidget = null;
    }
    
    public void onOpenSettings(final CosmeticSettingsWidget cosmeticSettingsWidget) {
        this.setAttributeState(AttributeState.ACTIVE, true);
        this.cosmeticSettingsWidget = cosmeticSettingsWidget;
    }
    
    @Override
    public boolean onPress() {
        if (this.hasInvalidCosmeticData()) {
            return false;
        }
        this.displayCosmeticOptions(true);
        return true;
    }
    
    private void displayCosmeticOptions(final boolean playSound) {
        if (playSound) {
            this.labyAPI.minecraft().sounds().playButtonPress();
        }
        this.cosmeticsActivity.updateModelFocus(this.item, this.cosmetic);
        final CosmeticSettingsWidget openSettings = this.cosmeticsActivity.getOpenSettings();
        if (openSettings != null && openSettings.cosmetic().getId() == this.cosmetic.getId()) {
            return;
        }
        this.cosmeticsActivity.displayOptions(new CosmeticSettingsWidget(this.cosmetic, this.options, this.listener));
    }
    
    private void handleToggleResponse(final ChangeResponse changeResponseResult) {
        this.waitingForResponse = false;
        if (changeResponseResult == null || !changeResponseResult.isDone()) {
            this.labyAPI.notificationController().push(Notification.builder().title(Component.translatable("labymod.misc.error", new Component[0])).text(Component.translatable("labymod.activity.customization.cosmetics.updateFailed", new Component[0])).duration(3000L).build());
        }
    }
    
    public boolean isWaitingForResponse() {
        return this.waitingForResponse;
    }
    
    public boolean hasInvalidCosmeticData() {
        return this.invalidCosmeticData;
    }
    
    public CosmeticsActivity cosmeticsActivity() {
        return this.cosmeticsActivity;
    }
    
    public Cosmetic cosmetic() {
        return this.cosmetic;
    }
    
    public AbstractItem item() {
        return this.item;
    }
    
    public void setItemTextureListener(final ItemTextureListener itemTextureListener) {
        this.itemTextureListener = itemTextureListener;
    }
    
    public void updateModelFocus(final Cosmetic cosmetic) {
        final CosmeticsActivity activity = this.cosmeticsActivity();
        if (activity.getOpenSettingsId() == cosmetic.getItemId()) {
            activity.updateModelFocus(this.item(), cosmetic);
        }
    }
    
    static {
        LABY_MOD_NET_SERVICE = LabyMod.references().labyModNetService();
    }
    
    record CosmeticWidgetSettingsListener(CosmeticWidget widget) implements CosmeticSettingsWidget.CosmeticSettingsListener {
        @Override
        public void onOpenSettings(final CosmeticSettingsWidget widget) {
            this.widget.onOpenSettings(widget);
        }
        
        @Override
        public void onCloseSettings(final CosmeticSettingsWidget widget) {
            this.widget.onCloseSettings(widget);
        }
        
        @Nullable
        @Override
        public Switchable onSwitch() {
            final CosmeticWidget widget = this.widget;
            Objects.requireNonNull(widget);
            return widget::updateCosmeticEnabled;
        }
        
        @Override
        public void onDataUpdate(final Cosmetic cosmetic) {
            ItemMetadataUtil.updateGameUser(cosmetic, null);
            this.widget.updateModelFocus(cosmetic);
        }
        
        @Override
        public boolean shouldSendRemoteRequest() {
            return true;
        }
    }
}
