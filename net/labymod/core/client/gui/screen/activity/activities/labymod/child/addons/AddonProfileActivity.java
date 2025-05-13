// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.notification.Notification;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.util.TextFormat;
import java.util.Locale;
import net.labymod.core.flint.downloader.DownloadState;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.downloader.FlintDownloadTask;
import java.io.IOException;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import java.text.SimpleDateFormat;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.Textures;
import net.labymod.core.client.gui.screen.widget.widgets.store.GradientWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import java.util.Optional;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.core.main.LabyMod;
import net.labymod.core.addon.DefaultAddonService;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.client.gui.screen.widget.widgets.store.profile.ProfileInfoWidget;
import net.labymod.core.flint.FlintController;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/flint/profile.lss")
@AutoActivity
public class AddonProfileActivity extends Activity
{
    private final Internationalization internationalization;
    private final FlintController controller;
    private final Activity fallback;
    private final String undefined;
    private final AddonInstallController installController;
    private final ProfileInfoWidget infoWidget;
    private final boolean hasUnsupportedDependencies;
    private FlintModification modification;
    private boolean loadingModification;
    private ScrollWidget scrollWidget;
    
    public AddonProfileActivity(final Activity fallback, final FlintController flintController, final FlintModification modification) {
        this.fallback = fallback;
        this.modification = modification;
        this.installController = new AddonInstallController(modification, this::reload);
        this.controller = flintController;
        this.hasUnsupportedDependencies = StoreItemWidget.hasUnsupportedDependencies(modification);
        this.internationalization = Laby.references().internationalization();
        this.undefined = this.internationalization.getRawTranslation("labymod.misc.undefined");
        this.infoWidget = new ProfileInfoWidget(modification).addId("profile-info-container");
    }
    
    public static void collectUninstalledDependencies(final FlintModification modification, final List<FlintModification> dependencies, final boolean optional) {
        modification.forEachAddonDependency(Laby.labyAPI().labyModLoader().version(), dependency -> {
            if (dependency.isOptional() == optional) {
                final String namespace = dependency.getNamespace();
                if (!CollectionHelper.anyMatch(dependencies, mod -> mod.getNamespace().equals(namespace))) {
                    if (!DefaultAddonService.getInstance().getAddon(namespace).isPresent()) {
                        final FlintIndex.IndexFilter filter = LabyMod.references().flintController().getFlintIndex().filter();
                        final Optional<FlintModification> optionalMod = filter.namespace(namespace);
                        if (!optionalMod.isEmpty()) {
                            final FlintModification dependencyModification = optionalMod.get();
                            if (!dependencyModification.hasMeta(AddonMeta.HIDDEN)) {
                                dependencies.add(dependencyModification);
                                collectUninstalledDependencies(dependencyModification, dependencies, optional);
                            }
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void onOpenScreen() {
        ((Document)this.document).playAnimation("fade-in");
        super.onOpenScreen();
    }
    
    @Override
    public void onCloseScreen() {
        ((Document)this.document).playAnimation("fade-out");
        super.onCloseScreen();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.loadModification();
        final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
        container.addId("container");
        final DivWidget detailsContainer = new DivWidget();
        detailsContainer.addId("details-container");
        final FlintModification.Image thumbnail = this.modification.getThumbnail();
        if (thumbnail != null) {
            detailsContainer.addId("details-with-thumbnail");
            final IconWidget thumbnailWidget = new VariableIconWidget(thumbnail.getIconUrl(), FlintController::getVariableBrandUrl);
            thumbnailWidget.addId("thumbnail");
            thumbnailWidget.setCleanupOnDispose(true);
            ((AbstractWidget<IconWidget>)detailsContainer).addChild(thumbnailWidget);
        }
        else {
            detailsContainer.addId("details-no-thumbnail");
        }
        final GradientWidget thumbnailGradient = new GradientWidget();
        thumbnailGradient.addId("thumbnail");
        ((AbstractWidget<GradientWidget>)detailsContainer).addChild(thumbnailGradient);
        final FlintModification.Image image = this.modification.getIcon();
        final IconWidget icon = new VariableIconWidget(Textures.DEFAULT_SERVER_ICON, (image == null) ? null : image.getIconUrl(), FlintController::getVariableBrandUrl);
        icon.addId("icon");
        icon.setCleanupOnDispose(true);
        ((AbstractWidget<IconWidget>)detailsContainer).addChild(icon);
        final VerticalListWidget<Widget> infoContainer = new VerticalListWidget<Widget>();
        infoContainer.addId("info-container");
        final String authorName = this.modification.getAuthor();
        final String organizationName = (authorName == null) ? this.internationalization.getRawTranslation("labymod.addons.store.organization.loading") : authorName;
        final ComponentWidget author = ComponentWidget.i18n("labymod.addons.store.modification.organization.name", organizationName);
        author.addId("author");
        infoContainer.addChild(author);
        final ComponentWidget name = ComponentWidget.text(this.modification.getName());
        name.addId("name");
        infoContainer.addChild(name);
        final ComponentWidget shortDescription = ComponentWidget.text(this.modification.getShortDescription());
        shortDescription.addId("short-description");
        infoContainer.addChild(shortDescription);
        final FlintModification.Rating rating = this.modification.getRating();
        final RatingWidget ratingWidget = new RatingWidget(rating.getRating(), rating.getCount());
        infoContainer.addChild(ratingWidget);
        ((AbstractWidget<VerticalListWidget<Widget>>)detailsContainer).addChild(infoContainer);
        container.addChild(detailsContainer);
        final HorizontalListWidget misc = new HorizontalListWidget();
        ((AbstractWidget<Widget>)misc).addId("misc-container");
        final ResourceLocation resource = Textures.SpriteFlint.TEXTURE;
        misc.addEntry(new MiscWidget(Icon.sprite32(resource, 0, 1), "labymod.addons.store.profile.downloads", this.modification.getDownloadsString()).addId("downloads"));
        String lastUpdateString;
        if (this.modification.getLastUpdate() == 0L) {
            lastUpdateString = null;
        }
        else {
            try {
                final SimpleDateFormat dateFormat = new SimpleDateFormat(this.internationalization.getRawTranslation("labymod.addons.store.profile.lastUpdate.format"));
                lastUpdateString = dateFormat.format(this.modification.getLastUpdate());
            }
            catch (final IllegalArgumentException e) {
                lastUpdateString = this.undefined;
            }
        }
        misc.addEntry(new MiscWidget(Icon.sprite32(resource, 1, 1), "labymod.addons.store.profile.lastUpdate.name", lastUpdateString).addId("last-update"));
        misc.addEntry(new MiscWidget(Icon.sprite32(resource, 2, 1), "labymod.addons.store.profile.versions", this.modification.getVersionString()).addId("versions"));
        container.addChild(misc);
        container.addChild(this.infoWidget);
        this.scrollWidget = new ScrollWidget(container);
        final DivWidget scrollContainerWidget = new DivWidget().addId("scroll-container");
        ((AbstractWidget<ScrollWidget>)scrollContainerWidget).addChild(this.scrollWidget);
        ((AbstractWidget<DivWidget>)this.document()).addChild(scrollContainerWidget);
        final ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON, () -> this.fallback.displayScreen(this.fallback));
        ((AbstractWidget<Widget>)backButton).addId("back-button");
        final DivWidget topContainer = new DivWidget().addId("top-container");
        ((AbstractWidget<ButtonWidget>)topContainer).addChild(backButton);
        topContainer.addChild(this.getManageWidget());
        ((AbstractWidget<DivWidget>)this.document()).addChild(topContainer);
    }
    
    @Override
    public void tick() {
        final float overflowHeight = this.scrollWidget.getOverflowHeight();
        final float height = overflowHeight / 255.0f;
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        int alpha = MathHelper.ceil(this.scrollWidget.getBottomLeftSpace() * height * window.getScale());
        alpha = MathHelper.clamp(alpha, 0, 100);
        this.setVariable("--addon-profile-scroll-color", ColorFormat.ARGB32.pack(0, 0, 0, alpha));
        final int blur = alpha / 8;
        this.setVariable("--addon-profile-blur-radius", blur);
        super.tick();
        this.installController.tick();
    }
    
    private Widget getManageWidget() {
        final FlintDownloadTask downloadTask = this.installController.downloadTask;
        if (this.controller.isInstalled(this.modification) && (downloadTask == null || downloadTask.isFinished())) {
            this.installController.unsetInstallButton();
            final HorizontalListWidget manageContainer = new HorizontalListWidget();
            ((AbstractWidget<Widget>)manageContainer).addId("manage-container");
            final Optional<Setting> settingOptional = this.controller.getSettings(this.modification);
            if (settingOptional.isPresent()) {
                final ButtonWidget settingsButton = new ButtonWidget();
                ((AbstractWidget<Widget>)settingsButton).addId("settings-button");
                settingsButton.icon().set(Textures.SpriteCommon.SETTINGS);
                settingsButton.setPressable(this.controller.displaySettings(settingOptional.get()));
                manageContainer.addEntry(settingsButton);
            }
            final DefaultAddonService instance = DefaultAddonService.getInstance();
            final boolean additonalAddon = instance.addonLoader().isAdditionalAddon(instance.getAddonInfo(this.modification.getNamespace()));
            final ButtonWidget uninstallButton = new ButtonWidget();
            ((AbstractWidget<Widget>)uninstallButton).addId("uninstall-button");
            uninstallButton.icon().set(Textures.SpriteCommon.TRASH);
            uninstallButton.setEnabled(!additonalAddon);
            final FlintDownloader downloader = LabyMod.references().flintDownloader();
            if (additonalAddon) {
                uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.managed", new Component[0]));
            }
            else if (downloader.isScheduledForRemoval(this.modification)) {
                uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.revert.hover", new Component[0]));
            }
            else {
                uninstallButton.setHoverComponent(Component.translatable("labymod.addons.store.installed.uninstall.hover", new Component[0]));
            }
            uninstallButton.setPressable(() -> {
                try {
                    this.installController.unsetDownloadTask();
                    this.controller.uninstallModification(this.modification, this::reload);
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
                return;
            });
            manageContainer.addEntry(uninstallButton);
            return manageContainer;
        }
        if (!this.modification.isBuildCompatible() || !this.modification.isCompatible() || this.hasUnsupportedDependencies) {
            this.installController.unsetInstallButton();
            final ButtonWidget incompatibleButton = ButtonWidget.i18n("labymod.addons.store.profile.incompatible.name");
            ((AbstractWidget<Widget>)incompatibleButton).addId("incompatible-button");
            incompatibleButton.setEnabled(false);
            incompatibleButton.setHoverComponent(StoreItemWidget.getIncompatibleComponent(this.modification, this.modification.isCompatible()));
            return incompatibleButton;
        }
        return this.installController.createInstallButton();
    }
    
    protected void loadModification() {
        if (this.loadingModification || this.modification == null) {
            return;
        }
        if (!(this.modification instanceof FlintIndex.FlintIndexModification)) {
            return;
        }
        final FlintModification mod = this.modification.loadModification(result -> {
            if (!result.isPresent() || !(this.modification instanceof FlintIndex.FlintIndexModification)) {
                return;
            }
            else {
                this.modification = (FlintModification)result.get();
                this.loadingModification = false;
                this.labyAPI.minecraft().executeOnRenderThread(this::reload);
                return;
            }
        });
        if (mod != null) {
            this.modification = mod;
        }
        else {
            this.loadingModification = true;
        }
    }
    
    public enum DependencyPopupAction
    {
        INSTALL, 
        SKIP;
    }
    
    public static class AddonInstallController
    {
        private final FlintModification modification;
        private final Runnable reload;
        private FlintDownloadTask downloadTask;
        private ButtonWidget installButton;
        
        public AddonInstallController(final FlintModification modification, final Runnable reload) {
            this.modification = modification;
            this.reload = reload;
            this.downloadTask = LabyMod.references().flintController().getDownloadTask(modification).orElse(null);
        }
        
        public void tick() {
            if (this.installButton == null || this.downloadTask == null || !this.installButton.isVisible()) {
                return;
            }
            final double percentage = this.downloadTask.getPercentage();
            Component component;
            if (this.downloadTask.state() == DownloadState.DOWNLOADING && percentage != -1.0) {
                this.installButton.setVariable("--download-percentage", percentage);
                component = Component.text(String.format(Locale.ROOT, "%.0f", percentage));
            }
            else {
                final String state = this.downloadTask.state().toString();
                component = Component.translatable("labymod.addons.store.profile." + state, new Component[0]);
                ((AbstractWidget<Widget>)this.installButton).addId(TextFormat.CAMEL_CASE.toDashCase(state));
            }
            this.installButton.updateComponent(component);
            this.installButton.reInitialize();
        }
        
        public void install() {
            final List<FlintModification> requiredDependencies = new ArrayList<FlintModification>();
            final List<FlintModification> optionalDependencies = new ArrayList<FlintModification>();
            this.collectDependencies(requiredDependencies, optionalDependencies);
            final FlintController flintController = LabyMod.references().flintController();
            final FlintIndex.IndexFilter filter = flintController.getFlintIndex().filter();
            FlintModification modLoader = null;
            if (filter.isHidden(this.modification)) {
                modLoader = this.modification;
            }
            else {
                for (final FlintModification requiredDependency : requiredDependencies) {
                    if (filter.isHidden(requiredDependency)) {
                        modLoader = requiredDependency;
                        break;
                    }
                }
            }
            if (modLoader == null) {
                this.startInstallProcess(requiredDependencies, optionalDependencies, new ArrayList<String>());
                return;
            }
            final boolean isMainAddon = modLoader.getNamespace().equals(this.modification.getNamespace());
            final FlintModification finalModLoader = modLoader;
            flintController.setModLoader(modLoader.getNamespace(), true, isMainAddon, result -> {
                if (isMainAddon) {
                    if (result == FlintController.SetModLoaderResult.SUCCESS) {
                        this.downloadTask = FlintDownloadTask.dummy(finalModLoader, DownloadState.REQUIRES_RESTART);
                        LabyMod.references().flintDownloader().setDownloadTask(finalModLoader, this.downloadTask);
                        this.reload.run();
                        this.installButton.setEnabled(true);
                    }
                }
                else if (result == FlintController.SetModLoaderResult.CONTINUE || result == FlintController.SetModLoaderResult.SUCCESS) {
                    final List<String> skippedDependencies = new ArrayList<String>();
                    if (result == FlintController.SetModLoaderResult.SUCCESS) {
                        skippedDependencies.add(finalModLoader.getNamespace());
                        requiredDependencies.remove(finalModLoader);
                    }
                    this.startInstallProcess(requiredDependencies, optionalDependencies, skippedDependencies);
                }
            });
        }
        
        public void unsetDownloadTask() {
            this.downloadTask = null;
        }
        
        public void unsetInstallButton() {
            this.installButton = null;
        }
        
        public ButtonWidget createInstallButton() {
            ((AbstractWidget<Widget>)(this.installButton = new ButtonWidget())).addId("install-button");
            if (this.downloadTask != null) {
                final DownloadState state = this.downloadTask.state();
                this.installButton.setEnabled(state == DownloadState.REQUIRES_RESTART && Laby.references().launcherService().hasGameSessionId());
                ((AbstractWidget<Widget>)this.installButton).addId(TextFormat.CAMEL_CASE.toDashCase(state.toString()));
                this.installButton.updateComponent(Component.translatable("labymod.addons.store.profile." + String.valueOf(state), new Component[0]));
                this.installButton.setPressable(this::openRestartPopup);
            }
            else {
                if (!this.modification.isCompatible()) {
                    this.installButton.updateComponent(Component.translatable("labymod.addons.store.profile.download", new Component[0]));
                }
                else {
                    this.installButton.updateComponent(Component.translatable("labymod.addons.store.profile.install", new Component[0]));
                }
                this.installButton.setPressable(this::install);
            }
            return this.installButton;
        }
        
        private void collectDependencies(final List<FlintModification> requiredDependencies, final List<FlintModification> optionalDependencies) {
            AddonProfileActivity.collectUninstalledDependencies(this.modification, requiredDependencies, false);
            AddonProfileActivity.collectUninstalledDependencies(this.modification, optionalDependencies, true);
        }
        
        private void startInstallProcess(final List<FlintModification> requiredDependencies, final List<FlintModification> optionalDependencies, final List<String> skippedDependencies) {
            final boolean required = !requiredDependencies.isEmpty();
            List<FlintModification> dependencies;
            if (!requiredDependencies.isEmpty()) {
                dependencies = requiredDependencies;
            }
            else {
                if (optionalDependencies.isEmpty()) {
                    this.install(skippedDependencies);
                    return;
                }
                dependencies = optionalDependencies;
            }
            final FlintModification dependency = dependencies.remove(0);
            this.openDependencyPopup(dependency, required, action -> {
                if (action == DependencyPopupAction.SKIP) {
                    if (required) {
                        return;
                    }
                    else if (!skippedDependencies.contains(dependency.getNamespace())) {
                        skippedDependencies.add(dependency.getNamespace());
                    }
                }
                this.startInstallProcess(requiredDependencies, optionalDependencies, skippedDependencies);
            });
        }
        
        private void install(final List<String> skippedDependencies) {
            this.installButton.setEnabled(false);
            this.downloadTask = LabyMod.references().flintController().downloadModification(this.modification, skippedDependencies, downloadTask -> {
                this.installButton.setEnabled(false);
                if (downloadTask != null && downloadTask.state() == DownloadState.REQUIRES_RESTART && Laby.references().launcherService().hasGameSessionId()) {
                    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                        this.reload.run();
                        this.installButton.setEnabled(true);
                        this.openRestartPopup();
                        return;
                    });
                }
                if (downloadTask == null || downloadTask.isFinished()) {
                    this.downloadTask = null;
                    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                        this.reload.run();
                        final LegacyConfigConverter converter = Laby.references().legacyConfigConverter();
                        final String namespace = this.modification.getNamespace();
                        if (converter.hasStuffToConvert(namespace)) {
                            final TranslatableComponent title = Component.translatable("labymod.legacyconverter.convertSettings", Component.text(this.modification.getName()));
                            ConfirmActivity.confirm(title, Component.translatable("labymod.legacyconverter.yes", new Component[0]), Component.translatable("labymod.legacyconverter.no", new Component[0]), confirmed -> {
                                if (confirmed != null && confirmed) {
                                    converter.convert(namespace);
                                }
                                converter.setConversionAsked(namespace);
                            });
                        }
                    });
                }
            });
        }
        
        private void openRestartPopup() {
            PopupWidget.builder().title(Component.translatable("labymod.addons.store.profile.restart.title", Component.text(this.modification.getName()))).text(Component.translatable("labymod.addons.store.profile.restart.description", Component.text(this.modification.getName()))).confirmComponent(Component.translatable("labymod.addons.store.profile.restart.now", new Component[0])).cancelComponent(Component.translatable("labymod.addons.store.profile.restart.later", new Component[0])).confirmCallback(() -> {
                try {
                    Laby.references().launcherService().restart();
                }
                catch (final IllegalArgumentException e) {
                    final Notification notification = Notification.builder().title(Component.translatable("labymod.addons.store.profile.restart.error.title", new Component[0])).text(Component.text(e.getMessage())).build();
                    Laby.references().notificationController().push(notification);
                }
            }).cancelCallback(Pressable.NOOP).build().displayInOverlay();
        }
        
        private void openDependencyPopup(final FlintModification dependency, final boolean required, final Consumer<DependencyPopupAction> actionConsumer) {
            String translationKeyPrefix;
            Component skipComponent;
            if (required) {
                translationKeyPrefix = "labymod.addons.store.profile.dependency.required.";
                skipComponent = Component.translatable("labymod.ui.button.cancel", new Component[0]);
            }
            else {
                translationKeyPrefix = "labymod.addons.store.profile.dependency.optional.";
                skipComponent = Component.translatable("labymod.addons.store.profile.dependency.optional.skip", new Component[0]);
            }
            SimpleAdvancedPopup.builder().title(Component.translatable(translationKeyPrefix + "title", Component.text(this.modification.getName()), Component.text(dependency.getName()))).description(Component.translatable(translationKeyPrefix + "description", Component.text(this.modification.getName()), Component.text(dependency.getName()))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(skipComponent, button -> actionConsumer.accept(DependencyPopupAction.SKIP))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.addons.store.profile.install", new Component[0]), button -> actionConsumer.accept(DependencyPopupAction.INSTALL))).build().displayInOverlay();
        }
    }
    
    @AutoWidget
    public class MiscWidget extends HorizontalListWidget
    {
        private final Icon icon;
        private final Component title;
        private final String value;
        
        protected MiscWidget(final AddonProfileActivity this$0, final Icon icon, final String titleKey, final String value) {
            final String title = Laby.references().internationalization().getRawTranslation(titleKey);
            this.icon = icon;
            this.title = Component.text(title.toUpperCase(Locale.ENGLISH));
            if (value == null) {
                this.value = this$0.undefined;
            }
            else {
                this.value = value;
            }
        }
        
        @Override
        public void initialize(final Parent parent) {
            super.initialize(parent);
            final IconWidget iconWidget = new IconWidget(this.icon);
            iconWidget.addId("misc-icon");
            this.addEntry(iconWidget);
            final VerticalListWidget<ComponentWidget> textContainer = new VerticalListWidget<ComponentWidget>();
            textContainer.addId("text-container");
            final ComponentWidget titleComponent = ComponentWidget.component(this.title);
            titleComponent.addId("misc-title");
            textContainer.addChild(titleComponent);
            final ComponentWidget valueComponent = ComponentWidget.text(this.value);
            valueComponent.addId("misc-value");
            textContainer.addChild(valueComponent);
            this.addEntry(textContainer);
        }
    }
}
