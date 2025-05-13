// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.io.IOException;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.core.flint.downloader.FlintDownloader;
import java.util.Optional;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.component.Component;
import net.labymod.core.main.LabyMod;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.flint.FlintController;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class InstalledItemWidget extends StoreItemWidget
{
    private final FlintController controller;
    private final LoadedAddon loadedAddon;
    private final ResourceLocation iconResourceLocation;
    
    public InstalledItemWidget(final FlintController flintController, final LoadedAddon loadedAddon, final FlintModification modification) {
        super(null, null);
        this.loadedAddon = loadedAddon;
        this.controller = flintController;
        this.setModification(modification);
        final ResourceLocation resourceLocation = this.labyAPI.renderPipeline().resources().resourceLocationFactory().create(loadedAddon.info().getNamespace(), "textures/icon.png");
        this.iconResourceLocation = (resourceLocation.exists() ? resourceLocation : null);
    }
    
    @Override
    protected void initializeStoreItem() {
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("item-container");
        Icon icon = null;
        if (this.modification().getClass() != FlintModification.class && this.iconResourceLocation != null) {
            icon = Icon.texture(this.iconResourceLocation);
        }
        IconWidget iconWidget;
        if (icon != null) {
            iconWidget = new IconWidget(icon);
        }
        else {
            final FlintModification.Image image = this.modification().getIcon();
            iconWidget = new VariableIconWidget(Textures.DEFAULT_SERVER_ICON, (image == null) ? null : image.getIconUrl(), FlintController::getVariableBrandUrl);
            iconWidget.setCleanupOnDispose(true);
        }
        iconWidget.addId("item-icon");
        container.addContent(iconWidget);
        final FlexibleContentWidget textContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)textContainer).addId("item-text-container");
        final ComponentWidget nameWidget = ComponentWidget.text(this.modification().getName());
        nameWidget.addId("item-name");
        textContainer.addContent(nameWidget);
        String organizationName = this.loadedAddon.info().getAuthor();
        final String flintAuthor = this.modification().getAuthor();
        if (flintAuthor != null) {
            organizationName = flintAuthor;
        }
        final ComponentWidget organizationWidget = ComponentWidget.i18n("labymod.addons.store.modification.organization.name", organizationName);
        organizationWidget.addId("item-organization");
        textContainer.addContent(organizationWidget);
        if (this.modification().getClass() == FlintModification.class) {
            final FlintModification.Rating rating = this.modification().getRating();
            textContainer.addContent(new RatingWidget(rating.getRating(), rating.getCount()));
        }
        final String description = this.modification().getShortDescription();
        final ComponentWidget descriptionWidget = ComponentWidget.text(description);
        descriptionWidget.addId("item-description");
        textContainer.addContent(descriptionWidget);
        container.addFlexibleContent(textContainer);
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(container);
        final FlexibleContentWidget buttonContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)buttonContainer).addId("button-container");
        final Optional<Setting> settingOptional = this.controller.getSettings(this.loadedAddon.info().getNamespace());
        if (settingOptional.isPresent()) {
            final ButtonWidget settingsButton = new ButtonWidget();
            ((AbstractWidget<Widget>)settingsButton).addId("settings-button");
            settingsButton.icon().set(Textures.SpriteCommon.SETTINGS);
            settingsButton.setPressable(this.controller.displaySettings(settingOptional.get()));
            buttonContainer.addContent(settingsButton);
        }
        final boolean additonalAddon = DefaultAddonService.getInstance().addonLoader().isAdditionalAddon(this.loadedAddon.info());
        final ButtonWidget uninstallButton = new ButtonWidget();
        ((AbstractWidget<Widget>)uninstallButton).addId("uninstall-button");
        uninstallButton.icon().set(Textures.SpriteCommon.TRASH);
        uninstallButton.setEnabled(!additonalAddon);
        final FlintDownloader downloader = LabyMod.references().flintDownloader();
        if (additonalAddon) {
            uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.managed", new Component[0]));
        }
        else if (downloader.isScheduledForRemoval(this.modification())) {
            this.setHoverComponent(Component.translatable("labymod.addons.store.uninstalled.hover", new Component[0]));
            uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.revert.hover", new Component[0]));
        }
        else {
            this.setHoverComponent(null);
            uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.hover", new Component[0]));
        }
        uninstallButton.setPressable(this::uninstallItem);
        buttonContainer.addContent(uninstallButton);
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(buttonContainer);
        final ContextMenu contextMenu = this.createContextMenu();
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.TRASH).text(Component.translatable("labymod.ui.button.remove", new Component[0])).clickHandler((mouse, entry) -> this.uninstallItem()).build());
    }
    
    private void uninstallItem() {
        try {
            this.controller.uninstallModification(this.modification(), this::reInitialize);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
