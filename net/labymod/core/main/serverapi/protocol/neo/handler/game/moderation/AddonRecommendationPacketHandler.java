// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.AddonProfileActivity;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import java.util.Optional;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import java.util.ArrayList;
import net.labymod.core.addon.DefaultAddonService;
import java.util.Iterator;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.LabyAPI;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.game.moderation.AddonRecommendationResponsePacket;
import net.labymod.serverapi.core.model.moderation.RecommendedAddon;
import java.util.List;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonRecommendationPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class AddonRecommendationPacketHandler implements PacketHandler<AddonRecommendationPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final AddonRecommendationPacket packet) {
        if (packet.getAddons().isEmpty()) {
            return;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final ServerData currentServerData = labyAPI.serverController().getCurrentServerData();
        if (currentServerData == null) {
            return;
        }
        final OtherConfig otherConfig = LabyConfigProvider.INSTANCE.get().other();
        final List<String> strings = otherConfig.ignoredAddonRecommendations().get();
        final AddonRecommendationResponsePacket responsePacket = this.sendResponse(packet, true);
        boolean showScreen = true;
        String configKey;
        if (responsePacket.isAllInstalled()) {
            showScreen = false;
            configKey = null;
        }
        else {
            configKey = this.generateKeyFromServer(currentServerData, packet);
            showScreen = !strings.contains(configKey);
            if (!showScreen) {
                for (final String missingAddon : responsePacket.getMissingAddons()) {
                    for (final RecommendedAddon addon : packet.getAddons()) {
                        if (addon.isRequired() && addon.getNamespace().equals(missingAddon)) {
                            showScreen = true;
                            break;
                        }
                    }
                }
            }
        }
        if (!showScreen || configKey == null) {
            Laby.references().labyModProtocolService().sendLabyModPacket((Packet)new AddonRecommendationResponsePacket(packet, false, responsePacket.isAllInstalled(), responsePacket.getMissingAddons(), responsePacket.getInstalledAddons()));
            return;
        }
        new AddonRecommendationPopup(packet, currentServerData, strings.contains(configKey), (canceled, ignoreState) -> {
            if (!canceled && ignoreState != AddonRecommendationIgnoreState.UNSET) {
                if (ignoreState == AddonRecommendationIgnoreState.IGNORED) {
                    if (!strings.contains(configKey)) {
                        strings.add(configKey);
                    }
                }
                else {
                    strings.remove(configKey);
                }
                otherConfig.ignoredAddonRecommendations().set(strings);
                LabyConfigProvider.INSTANCE.save();
            }
            this.sendResponse(packet, false);
        }).displayAsActivity();
    }
    
    private String generateKeyFromServer(final ServerData serverData, final AddonRecommendationPacket packet) {
        final StringBuilder addonString = new StringBuilder();
        for (final RecommendedAddon addon : packet.getAddons()) {
            if (!addonString.isEmpty()) {
                addonString.append('#');
            }
            if (addon.isRequired()) {
                addonString.append('!');
            }
            addonString.append(addon.getNamespace());
        }
        return serverData.actualAddress().toString() + "#" + String.valueOf(addonString);
    }
    
    private AddonRecommendationResponsePacket sendResponse(final AddonRecommendationPacket packet, final boolean initial) {
        final DefaultAddonService instance = DefaultAddonService.getInstance();
        final List<String> installedAddons = new ArrayList<String>();
        final List<String> missingAddons = new ArrayList<String>();
        for (final RecommendedAddon addon : packet.getAddons()) {
            if (instance.getOptionalAddon(addon.getNamespace()).isPresent()) {
                installedAddons.add(addon.getNamespace());
            }
            else {
                missingAddons.add(addon.getNamespace());
            }
        }
        final AddonRecommendationResponsePacket responsePacket = new AddonRecommendationResponsePacket(packet, initial, missingAddons.isEmpty(), (List)missingAddons, (List)installedAddons);
        Laby.references().labyModProtocolService().sendLabyModPacket((Packet)responsePacket);
        return responsePacket;
    }
    
    public enum AddonRecommendationIgnoreState
    {
        UNSET, 
        IGNORED, 
        NOT_IGNORED;
    }
    
    @Links({ @Link("activity/overlay/addon-recommendation.lss"), @Link("activity/flint/addon-item.lss") })
    public static class AddonRecommendationPopup extends SimpleAdvancedPopup
    {
        private static final String TRANSLATION_PREFIX = "labymod.activity.addonRecommendation.";
        private final AddonRecommendationPacket packet;
        private final ServerData serverData;
        private final AddonRecommendationCloseListener closeListener;
        private final SimplePopupButton doneButton;
        private final boolean hasRequired;
        private final boolean hasRecommended;
        private AddonRecommendationIgnoreState ignoreState;
        private boolean calledCloseListener;
        
        public AddonRecommendationPopup(final AddonRecommendationPacket packet, final ServerData serverData, final boolean ignored, final AddonRecommendationCloseListener closeListener) {
            this.calledCloseListener = false;
            this.packet = packet;
            this.serverData = serverData;
            this.closeListener = closeListener;
            boolean hasRequired = false;
            boolean hasRecommended = false;
            for (final RecommendedAddon addon : packet.getAddons()) {
                if (addon.isRequired()) {
                    hasRequired = true;
                }
                else {
                    hasRecommended = true;
                }
            }
            this.hasRequired = hasRequired;
            if (!(this.hasRecommended = hasRecommended)) {
                this.ignoreState = AddonRecommendationIgnoreState.UNSET;
            }
            else {
                this.ignoreState = (ignored ? AddonRecommendationIgnoreState.IGNORED : AddonRecommendationIgnoreState.NOT_IGNORED);
            }
            String translationKey;
            if (this.hasRequired && this.hasRecommended) {
                translationKey = "title";
            }
            else if (this.hasRequired) {
                translationKey = "titleRequired";
            }
            else {
                translationKey = "titleRecommend";
            }
            this.title = Component.translatable("labymod.activity.addonRecommendation." + translationKey, NamedTextColor.GREEN, Component.text(this.serverData.address().toString(), NamedTextColor.YELLOW));
            (this.buttons = new ArrayList<SimplePopupButton>()).add(SimplePopupButton.cancel());
            this.doneButton = SimplePopupButton.create("done", Component.translatable("labymod.ui.button.done", new Component[0]), button -> {
                if (!this.calledCloseListener) {
                    this.calledCloseListener = true;
                    this.closeListener.onClose(false, this.ignoreState);
                }
                return;
            }).enabled(this.allRequiredInstalled());
            this.buttons.add(this.doneButton);
        }
        
        @Override
        protected void onClose() {
            super.onClose();
            if (this.calledCloseListener) {
                return;
            }
            this.calledCloseListener = true;
            this.closeListener.onClose(true, this.ignoreState);
        }
        
        @Override
        protected void tick() {
            super.tick();
            if (this.doneButton == null) {
                return;
            }
            this.doneButton.enabled(this.allRequiredInstalled());
        }
        
        private boolean allRequiredInstalled() {
            final DefaultAddonService instance = DefaultAddonService.getInstance();
            for (final RecommendedAddon addon : this.packet.getAddons()) {
                if (addon.isRequired() && instance.getOptionalAddon(addon.getNamespace()).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        protected void initializeCustomWidgets(final VerticalListWidget<Widget> container) {
            super.initializeCustomWidgets(container);
            final VerticalListWidget<Widget> addonList = new VerticalListWidget<Widget>();
            addonList.addId("addon-list");
            final FlintIndex.IndexFilter filter = LabyMod.references().flintController().getFlintIndex().filter();
            for (final RecommendedAddon addon : this.packet.getAddons()) {
                final Optional<FlintModification> optionalModification = filter.namespace(addon.getNamespace());
                if (optionalModification.isEmpty()) {
                    continue;
                }
                addonList.addChild(new AddonRecommendationItemWidget(optionalModification.get(), addon.isRequired()));
            }
            container.addChild(new ScrollWidget(addonList));
            if (this.hasRecommended) {
                final FlexibleContentWidget ignoreContainer = new FlexibleContentWidget();
                ((AbstractWidget<Widget>)ignoreContainer).addId("ignore-container");
                final ComponentWidget ignoreComponentwidget = ComponentWidget.i18n("labymod.activity.addonRecommendation.ignore", NamedTextColor.GRAY, this.serverData.address().toString());
                ignoreContainer.addFlexibleContent(ignoreComponentwidget);
                final CheckBoxWidget ignoreWidget = new CheckBoxWidget();
                ignoreWidget.setState((this.ignoreState == AddonRecommendationIgnoreState.IGNORED) ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
                ignoreWidget.setPressable(() -> this.ignoreState = ((ignoreWidget.state() == CheckBoxWidget.State.CHECKED) ? AddonRecommendationIgnoreState.IGNORED : AddonRecommendationIgnoreState.NOT_IGNORED));
                ignoreContainer.addContent(ignoreWidget);
                container.addChild(ignoreContainer);
            }
        }
    }
    
    @AutoWidget
    public static class AddonRecommendationItemWidget extends StoreItemWidget
    {
        private final AddonProfileActivity.AddonInstallController installController;
        private final boolean required;
        
        public AddonRecommendationItemWidget(final FlintModification modification, final boolean required) {
            super(modification, null);
            this.required = required;
            this.installController = new AddonProfileActivity.AddonInstallController(modification, this::reInitialize);
            this.addId("addon-item");
        }
        
        @Override
        protected void initializeStoreItem() {
            super.initializeStoreItem();
            final DivWidget requiredWrapper = new DivWidget();
            requiredWrapper.addId("required-wrapper");
            if (!this.modification().isInstalled()) {
                requiredWrapper.addId("not-installed");
            }
            if (this.required) {
                final IconWidget requiredBadge = new IconWidget(Textures.SpriteCommon.EXCLAMATION_MARK_LIGHT);
                requiredBadge.addId("required-badge");
                requiredBadge.setHoverComponent(Component.translatable("labymod.activity.addonRecommendation.required", new Component[0]));
                ((AbstractWidget<IconWidget>)requiredWrapper).addChild(requiredBadge);
            }
            ((AbstractWidget<DivWidget>)this).addChild(requiredWrapper);
            if (this.modification().isInstalled()) {
                return;
            }
            ButtonWidget installButton;
            if (this.isIncompatible()) {
                this.installController.unsetInstallButton();
                installButton = ButtonWidget.text("Install");
                ((AbstractWidget<Widget>)installButton).addId("install-button");
                if (this.isIncompatible()) {
                    installButton.setEnabled(false);
                    installButton.setHoverComponent(this.getIncompatibleComponent());
                }
            }
            else {
                installButton = this.installController.createInstallButton();
            }
            ((AbstractWidget<ButtonWidget>)this).addChild(installButton);
        }
        
        @Override
        public void tick() {
            super.tick();
            this.installController.tick();
        }
    }
    
    public interface AddonRecommendationCloseListener
    {
        void onClose(final boolean p0, final AddonRecommendationIgnoreState p1);
    }
}
