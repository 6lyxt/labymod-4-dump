// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.addon.AddonLoader;
import java.util.Iterator;
import java.io.IOException;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.addon.DefaultAddonService;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.DefaultEntryRenderer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.AddonProfileActivity;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.core.flint.FlintSortBy;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.MyAddonsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.MarketplaceActivity;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.Activity;

@Links({ @Link("activity/flint/addons.lss"), @Link("activity/sidebar-activity.lss") })
@AutoActivity
public class AddonsActivity extends Activity
{
    private final FlintDownloader flintDownloader;
    private final FlintController flintController;
    private final MarketplaceActivity marketplaceActivity;
    private final MyAddonsActivity myAddonsActivity;
    private final ScreenRendererWidget screenRendererWidget;
    private final TextFieldWidget searchWidget;
    private final DropdownWidget<FlintSortBy> sortDropdownWidget;
    @Nullable
    private ButtonWidget trendingButton;
    @Nullable
    private VerticalListWidget<Widget> categoryList;
    private boolean pseudoReload;
    
    public AddonsActivity() {
        this.flintController = LabyMod.references().flintController();
        this.flintDownloader = LabyMod.references().flintDownloader();
        this.myAddonsActivity = new MyAddonsActivity(this.flintController);
        this.marketplaceActivity = new MarketplaceActivity(this.flintController);
        (this.screenRendererWidget = new ScreenRendererWidget().addId("screen-renderer")).setPreviousScreenHandler(screen -> screen instanceof AddonProfileActivity);
        this.screenRendererWidget.displayScreen(this.marketplaceActivity);
        this.screenRendererWidget.addDisplayListener(screen -> this.reload());
        (this.searchWidget = new TextFieldWidget()).placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.addId("search-widget");
        this.searchWidget.updateListener(text -> {
            final ScreenInstance screen2 = this.screenRendererWidget.getScreen();
            if (screen2 instanceof MyAddonsActivity) {
                this.myAddonsActivity.search(text);
                if (this.pseudoReload) {
                    this.pseudoReload = false;
                }
                return;
            }
            else {
                boolean reload = false;
                if (text.isEmpty()) {
                    this.marketplaceActivity.trending();
                    reload = true;
                }
                else if (!this.marketplaceActivity.isSearch(text)) {
                    this.marketplaceActivity.search(text);
                    reload = true;
                }
                else if (this.pseudoReload) {
                    reload = true;
                }
                if (reload && !(screen2 instanceof AddonProfileActivity)) {
                    if (this.pseudoReload) {
                        this.pseudoReload = false;
                    }
                    else {
                        this.reload();
                    }
                }
                if (screen2 instanceof AddonProfileActivity) {
                    this.screenRendererWidget.displayScreen(this.marketplaceActivity);
                }
                return;
            }
        });
        this.sortDropdownWidget = DropdownWidget.create(this.marketplaceActivity.getSortBy(), selected -> {
            this.marketplaceActivity.setSortBy(selected);
            this.reload();
            return;
        });
        final DefaultEntryRenderer<?> renderer = (DefaultEntryRenderer)this.sortDropdownWidget.entryRenderer();
        renderer.setTranslationKeyPrefix("labymod.addons.store.filter");
        this.sortDropdownWidget.addId("input-widget");
        this.sortDropdownWidget.addAll(FlintSortBy.getValues());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((Document)this.document).getChildren().clear();
        final String text = this.searchWidget.getText();
        String query;
        if (this.screenRendererWidget.getScreen() instanceof MyAddonsActivity) {
            query = this.myAddonsActivity.getSearchQuery();
        }
        else {
            query = this.marketplaceActivity.getSearchQuery();
        }
        if (!text.equals(query)) {
            this.pseudoReload = true;
            this.searchWidget.setText(query);
        }
        final FlexibleContentWidget container = new FlexibleContentWidget().addId("container");
        final FlexibleContentWidget header = new FlexibleContentWidget().addId("header");
        this.addTabWidget(header);
        final ButtonWidget storeButton = ButtonWidget.component(this.getScreenName(this.marketplaceActivity, false)).addId("store-button");
        this.setButtonEnabled(storeButton, this.marketplaceActivity);
        storeButton.setPressable(() -> this.openScreen(this.marketplaceActivity));
        final ButtonWidget installedButton = ButtonWidget.component(this.getScreenName(this.myAddonsActivity, false)).addId("installed-button");
        this.setButtonEnabled(installedButton, this.myAddonsActivity);
        installedButton.setPressable(() -> this.openScreen(this.myAddonsActivity));
        header.addContent(storeButton);
        header.addContent(installedButton);
        container.addContent(header);
        container.addFlexibleContent(this.screenRendererWidget);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> files) {
        for (final Path file : files) {
            final String fileName = file.getFileName().toString();
            if (!fileName.endsWith(".jar")) {
                continue;
            }
            final DefaultAddonService addonService = DefaultAddonService.getInstance();
            final AddonLoader addonLoader = addonService.addonLoader();
            final AddonPreparer addonPreparer = addonLoader.addonPreparer();
            try {
                final InstalledAddonInfo addonInfo = addonLoader.loadAddonInfo(file);
                if (addonService.getAddon(addonInfo.getNamespace()).isPresent()) {
                    continue;
                }
                addonPreparer.loadAddon(addonInfo, AddonPreparer.AddonPrepareContext.RUNTIME);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return super.fileDropped(mouse, files);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
    }
    
    private void addTabWidget(final FlexibleContentWidget widget) {
        final ScreenInstance screen = this.screenRendererWidget.getScreen();
        if (screen instanceof MyAddonsActivity) {
            final ComponentWidget component = ComponentWidget.component(this.getScreenName(screen, true));
            component.addId("title");
            widget.addContent(component);
            widget.addFlexibleContent(this.searchWidget);
            return;
        }
        widget.addContent(this.sortDropdownWidget);
        widget.addFlexibleContent(this.searchWidget);
    }
    
    private void setButtonEnabled(final ButtonWidget widget, final ScreenInstance screenInstance) {
        final ScreenInstance screen = this.screenRendererWidget.getScreen();
        boolean enabled = screen != screenInstance;
        if (screenInstance instanceof MarketplaceActivity && screen instanceof AddonProfileActivity) {
            enabled = false;
        }
        widget.setEnabled(enabled);
        widget.setActive(!enabled);
    }
    
    private boolean openScreen(final ScreenInstance screenInstance) {
        final ScreenInstance screen = this.screenRendererWidget.getScreen();
        if (screen == screenInstance) {
            return false;
        }
        this.screenRendererWidget.displayScreen(screenInstance);
        return true;
    }
    
    private Component getScreenName(final ScreenInstance screenInstance, final boolean title) {
        final String key = title ? "title" : "name";
        if (screenInstance instanceof MyAddonsActivity) {
            return Component.translatable("labymod.addons.category.myAddons." + key, new Component[0]);
        }
        return Component.translatable("labymod.addons.category.store." + key, new Component[0]);
    }
}
