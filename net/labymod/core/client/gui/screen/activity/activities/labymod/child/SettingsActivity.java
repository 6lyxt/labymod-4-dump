// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.CategoryWidget;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.component.Component;
import java.util.Collection;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.configuration.settings.type.SettingGroup;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.HrWidget;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity;

@Link("activity/settings.lss")
@AutoActivity
public class SettingsActivity extends AbstractSidebarActivity
{
    private final AbstractSettingRegistry registry;
    private Setting defaultSetting;
    @Nullable
    private Setting selectedSetting;
    private Setting previousSetting;
    private Widget temporarySettingWidget;
    private Setting lastFilter;
    
    public SettingsActivity(final AbstractSettingRegistry registry) {
        this.registry = registry;
        this.updateScreen();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.screenRendererWidget.getScreen() == null) {
            this.updateScreen();
        }
    }
    
    @Override
    public void onCategoryListInitialize(final VerticalListWidget<Widget> categoryList) {
        final boolean searching = !this.searchWidget.getText().trim().isEmpty();
        final List<Setting> addonCategories = new ArrayList<Setting>();
        this.registry.forEach(category -> {
            if (category instanceof RootSettingRegistry && ((RootSettingRegistry)category).isAddon()) {
                final String namespace = ((RootSettingRegistry)category).getNamespace();
                this.labyAPI.addonService().getAddon(namespace).ifPresent(addon -> addonCategories.add(category));
                return;
            }
            else {
                if (this.defaultSetting == null) {
                    this.defaultSetting = category;
                }
                if (this.selectedSetting == null) {
                    this.selectedSetting = category;
                    this.previousSetting = category;
                    this.updateScreen();
                }
                categoryList.addChild(this.createCategory(category, searching));
                return;
            }
        });
        if (addonCategories.isEmpty()) {
            return;
        }
        final HrWidget addonHrWidget = new HrWidget().addId("addon");
        categoryList.addChild(addonHrWidget);
        categoryList.addChild(ComponentWidget.i18n("labymod.activity.settings.addons.name").addId("sub-category"));
        addonCategories.sort((a, b) -> {
            final String aName = I18n.translate(a.getTranslationKey() + ".name", new Object[0]);
            final String bName = I18n.translate(b.getTranslationKey() + ".name", new Object[0]);
            return aName.compareTo(bName);
        });
        for (final Setting addonCategory : addonCategories) {
            categoryList.addChild(this.createCategory(addonCategory, searching));
        }
    }
    
    @Override
    protected void initializeSidebarFooter(final DivWidget widget) {
        super.initializeSidebarFooter(widget);
    }
    
    @Subscribe
    public void laby(final LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.PLAY) {
            this.labyAPI.minecraft().executeOnRenderThread(this::reload);
        }
    }
    
    @Override
    public void onSearchUpdateListener(final String searchContent) {
        if (this.updateScreen()) {
            this.reload();
        }
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        Laby.fireEvent(new ConfigurationSaveEvent());
    }
    
    private void resetSearch() {
        if (!(this.selectedSetting instanceof SettingGroup)) {
            this.selectedSetting = ((this.selectedSetting == null) ? this.defaultSetting : this.selectedSetting);
            this.displayScreen(this.selectedSetting.createActivityLazy());
            return;
        }
        final Setting previousSetting = this.previousSetting;
        if (previousSetting instanceof SettingGroup) {
            this.selectedSetting = this.defaultSetting;
            this.displayScreen(this.selectedSetting.createActivityLazy());
            return;
        }
        this.selectedSetting = previousSetting;
        this.displayScreen(previousSetting.createActivityLazy());
    }
    
    private boolean updateScreen() {
        final String text = this.searchWidget.getText().trim();
        if (text.isEmpty()) {
            this.searchWidget.setEditable(true);
            if (this.selectedSetting == null) {
                return true;
            }
            if (this.temporarySettingWidget != null) {
                this.screenRendererWidget.removeChild(this.temporarySettingWidget);
                this.temporarySettingWidget = null;
            }
            this.resetSearch();
            return true;
        }
        else {
            if (text.length() < 2) {
                this.displayScreen((Activity)null);
                if (this.temporarySettingWidget != null) {
                    this.screenRendererWidget.removeChild(this.temporarySettingWidget);
                }
                this.temporarySettingWidget = this.createEmptySetting(text, "tooShort");
                this.screenRendererWidget.addChildInitialized(this.temporarySettingWidget);
                return true;
            }
            final List<Setting> collectedSettings = this.registry.collect(setting -> this.filterSettings(text, setting));
            final List<Setting> settings = Lists.newDistinctArrayList(collectedSettings, true);
            if (settings.isEmpty()) {
                this.displayScreen((Activity)null);
                if (this.temporarySettingWidget != null) {
                    this.screenRendererWidget.removeChild(this.temporarySettingWidget);
                }
                this.temporarySettingWidget = this.createEmptySetting(text, "noResults");
                this.screenRendererWidget.addChildInitialized(this.temporarySettingWidget);
                return false;
            }
            if (this.temporarySettingWidget != null) {
                this.screenRendererWidget.removeChild(this.temporarySettingWidget);
                this.temporarySettingWidget = null;
            }
            final SettingGroup group = SettingGroup.named(Component.text(text)).of(settings).filtered(true);
            if (!(this.selectedSetting instanceof SettingGroup)) {
                this.previousSetting = this.selectedSetting;
            }
            this.selectedSetting = group;
            final SettingContentActivity activity = group.createActivityLazy();
            activity.screenCallback(setting -> {
                if (setting == group) {
                    this.lastFilter = null;
                    this.searchWidget.setEditable(true);
                    return group;
                }
                else {
                    final Setting parent = setting.parent();
                    if (this.isLabyModRootSetting(parent) && this.lastFilter != null && !this.isLabyModRootSetting(this.lastFilter.parent())) {
                        this.lastFilter = null;
                        this.searchWidget.setEditable(true);
                        return group;
                    }
                    else {
                        if (this.lastFilter == null && !this.isLabyModRootSetting(setting)) {
                            this.lastFilter = setting;
                            this.searchWidget.setEditable(false);
                        }
                        else if (this.lastFilter != null && setting == this.lastFilter.parent()) {
                            this.lastFilter = null;
                            this.searchWidget.setEditable(true);
                            return group;
                        }
                        this.searchWidget.setEditable(false);
                        return setting;
                    }
                }
            });
            this.displayScreen(activity);
            return true;
        }
    }
    
    private boolean isLabyModRootSetting(final Setting setting) {
        if (!(setting instanceof RootSettingRegistry)) {
            return false;
        }
        final RootSettingRegistry rootSettingRegistry = (RootSettingRegistry)setting;
        return rootSettingRegistry.getNamespace().equals("labymod");
    }
    
    private boolean filterSettings(final String searchTerm, final Setting setting) {
        if (!setting.isElement()) {
            return false;
        }
        if (CharSequences.containsLowercase(I18n.translate(setting.getTranslationKey() + ".name", new Object[0]), searchTerm)) {
            return true;
        }
        for (final String s : setting.getSearchTags()) {
            if (CharSequences.containsLowercase(s, searchTerm)) {
                return true;
            }
        }
        return CharSequences.containsLowercase(I18n.translate(setting.getTranslationKey() + ".description", new Object[0]), searchTerm);
    }
    
    private CategoryWidget createCategory(final Setting setting, final boolean searching) {
        final CategoryWidget widget = new CategoryWidget(setting);
        widget.setEnabled(this.selectedSetting != setting || searching);
        widget.setActive(this.selectedSetting == setting && !searching);
        widget.setPressable(() -> {
            this.setSelectedSetting(setting);
            this.reload();
            return;
        });
        return widget;
    }
    
    public void setSelectedSetting(final Setting setting) {
        this.selectedSetting = setting;
        this.lastFilter = null;
        this.searchWidget.setEditable(true);
        this.searchWidget.setText("");
        this.searchWidget.setFocused(false);
        this.updateScreen();
    }
    
    private Widget createEmptySetting(final String query, final String translationKey) {
        final FlexibleContentWidget content = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)content).addId("content");
        final HorizontalListWidget header = new HorizontalListWidget();
        ((AbstractWidget<Widget>)header).addId("setting-header");
        final ComponentWidget title = ComponentWidget.text(query);
        title.addId("title");
        header.addEntry(title);
        content.addContent(header);
        final DivWidget infoWrapper = new DivWidget();
        infoWrapper.addId("info-wrapper");
        final ComponentWidget info = ComponentWidget.component(Component.translatable("labymod.activity.settings.search." + translationKey, new Component[0]));
        info.addId("info");
        ((AbstractWidget<ComponentWidget>)infoWrapper).addChild(info);
        content.addFlexibleContent(infoWrapper);
        return content;
    }
}
