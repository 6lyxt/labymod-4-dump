// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities.labymod.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.configuration.settings.type.SettingGroup;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.event.labymod.config.SettingWidgetInitializeEvent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingHeaderWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.settings.type.SettingHeader;
import net.labymod.api.util.KeyValue;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.type.list.ListSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import java.util.Map;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.Activity;

@Links({ @Link("tabbed.lss"), @Link(value = "activity/settings.lss", priority = -64) })
@AutoActivity
public class SettingContentActivity extends Activity
{
    private final Setting originHolder;
    private Map<String, ListSession<?>> sessions;
    private Setting currentHolder;
    private Function<Setting, Setting> screenCallback;
    private HeaderType headerType;
    private Runnable closeHandler;
    private final boolean lazy;
    
    public SettingContentActivity(final Setting holder) {
        this(holder, true);
    }
    
    public SettingContentActivity(final Setting holder, final boolean lazy) {
        if (!holder.isInitialized()) {
            throw new IllegalStateException("Setting is not initialized yet!");
        }
        this.originHolder = holder;
        this.sessions = new HashMap<String, ListSession<?>>();
        this.currentHolder = holder;
        this.headerType = HeaderType.FIXED;
        this.lazy = lazy;
    }
    
    public SettingContentActivity screenCallback(final Consumer<Setting> screenCallback) {
        this.screenCallback = (Function<Setting, Setting>)(setting -> {
            screenCallback.accept(setting);
            return setting;
        });
        return this;
    }
    
    public SettingContentActivity screenCallback(final Function<Setting, Setting> screenCallback) {
        this.screenCallback = screenCallback;
        return this;
    }
    
    public SettingContentActivity closeHandler(final Runnable closeHandler) {
        this.closeHandler = closeHandler;
        return this;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("content", "header-" + this.headerType.name().toLowerCase(Locale.ROOT));
        HorizontalListWidget header = new HorizontalListWidget();
        if (this.headerType != HeaderType.NONE) {
            header = new HorizontalListWidget();
            ((AbstractWidget<Widget>)header).addId("setting-header");
            final boolean hasParent = this.currentHolder.hasParent() && this.currentHolder.parent().isHoldable();
            if (this.closeHandler != null || hasParent || (!this.isOriginHolder() && this.isOriginFiltered())) {
                final ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
                ((AbstractWidget<Widget>)backButton).addId("back-button");
                if (this.currentHolder == this.originHolder) {
                    ((AbstractWidget<Widget>)backButton).addId("back-button-origin");
                }
                if (this.currentHolder.hasParent() && this.currentHolder.parent() instanceof ListSetting) {
                    ((AbstractWidget<Widget>)backButton).addId("back-button-list-entry");
                }
                backButton.setPressable(() -> {
                    if (hasParent) {
                        this.currentHolder = this.currentHolder.parent();
                    }
                    else {
                        this.currentHolder = this.originHolder;
                        if (this.closeHandler != null) {
                            this.closeHandler.run();
                        }
                    }
                    if (this.screenCallback != null) {
                        this.currentHolder = this.screenCallback.apply(this.currentHolder);
                    }
                    if (this.currentHolder != null) {
                        this.reload();
                    }
                    return;
                });
                header.addEntry(backButton);
            }
            final Icon icon = this.currentHolder.getIcon();
            if (icon != null) {
                final IconWidget iconWidget = new IconWidget(icon);
                iconWidget.addId("parent-icon");
                header.addEntry(iconWidget);
            }
            final Component titleComponent = this.currentHolder.displayName();
            if (titleComponent != null) {
                final ComponentWidget title = ComponentWidget.component(titleComponent);
                title.addId("title");
                header.addEntry(title);
            }
            if (this.currentHolder instanceof ListSetting) {
                final ButtonWidget addButton = ButtonWidget.icon(Textures.SpriteCommon.SMALL_ADD_WITH_SHADOW);
                ((AbstractWidget<Widget>)addButton).addId("add-button");
                addButton.setPressable(() -> {
                    final ListSetting list2 = (ListSetting)this.currentHolder;
                    this.currentHolder = list2.createNew();
                    if (this.screenCallback != null) {
                        this.currentHolder = this.screenCallback.apply(this.currentHolder);
                    }
                    if (this.currentHolder != null) {
                        this.reload();
                    }
                    return;
                });
                header.addEntry(addButton).alignment().set(HorizontalAlignment.RIGHT);
            }
            if (this.headerType == HeaderType.FIXED || (this.headerType == HeaderType.FIXED_IN_CHILDREN && this.currentHolder.hasParent() && (this.currentHolder.parent().isHoldable() || this.currentHolder.parent() instanceof ListSetting))) {
                container.addContent(header);
            }
        }
        final ListSession<?> session = this.sessions.computeIfAbsent(this.currentHolder.getPath(), function -> new ListSession());
        if (this.currentHolder.isElement()) {
            final SettingElement settingElement = this.currentHolder.asElement();
            final SettingHandler handler = settingElement.handler();
            Activity activity = null;
            if (settingElement.getWidgets() != null && settingElement.getWidgets()[0] instanceof SettingActivitySupplier) {
                activity = ((SettingActivitySupplier)settingElement.getWidgets()[0]).activity(settingElement);
            }
            else if (handler != null && handler.opensActivity(settingElement)) {
                activity = handler.activity(settingElement);
            }
            if (activity != null) {
                final ScreenRendererWidget screenRendererWidget = new ScreenRendererWidget();
                screenRendererWidget.addId("addon-activity-renderer");
                screenRendererWidget.displayScreen(activity);
                container.addFlexibleContent(screenRendererWidget);
                ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
                return;
            }
        }
        final VerticalListWidget<Widget> settingsList = new VerticalListWidget<Widget>();
        settingsList.addId("list");
        if (this.headerType == HeaderType.SCROLL || (this.headerType == HeaderType.SCROLL_IN_CHILDREN && this.currentHolder.hasParent() && (this.currentHolder.parent().isHoldable() || this.currentHolder.parent() instanceof ListSetting))) {
            settingsList.addChild(header);
        }
        final List<Widget> list = new ArrayList<Widget>();
        final Setting parentSetting = this.currentHolder;
        for (KeyValue<Setting> element : this.currentHolder.getElements()) {
            final Setting setting = element.getValue();
            if (setting.isElement() && !setting.hasAdvancedButton()) {
                final SettingElement settingElement2 = setting.asElement();
                if (!settingElement2.hasControlButton() && settingElement2.getWidgets() == null) {
                    continue;
                }
            }
            if (setting instanceof final SettingHeader settingHeader) {
                final List<Component> rows = settingHeader.getRows();
                for (int i = 0; i < rows.size(); ++i) {
                    final Component row = rows.get(i);
                    final SettingHeaderWidget settingHeaderWidget = new SettingHeaderWidget(row, settingHeader.pressable());
                    if (rows.size() > 1) {
                        if (i == 0) {
                            settingHeaderWidget.addId("header-first");
                        }
                        if (i == rows.size() - 1) {
                            settingHeaderWidget.addId("header-last");
                        }
                    }
                    else {
                        settingHeaderWidget.addId("header-single");
                    }
                    list.add(settingHeaderWidget.addId("header-" + (settingHeader.isCenter() ? "center" : "left")));
                }
            }
            else {
                list.add(new SettingWidget(setting, this.lazy, layer -> {
                    this.currentHolder = ((layer != null) ? layer : parentSetting);
                    if (this.screenCallback != null) {
                        this.currentHolder = this.screenCallback.apply(this.currentHolder);
                    }
                    if (this.currentHolder != null) {
                        this.reload();
                    }
                    return;
                }));
            }
        }
        this.labyAPI.eventBus().fire(new SettingWidgetInitializeEvent((ParentScreen)this.getParent(), this.currentHolder, list));
        for (final Widget settingWidget : list) {
            settingsList.addChild(settingWidget);
        }
        final ScrollWidget scrollWidget = new ScrollWidget(settingsList, session);
        scrollWidget.addId("scroll");
        if (this.headerType == HeaderType.FIXED) {
            scrollWidget.addId("header-spacing");
        }
        container.addFlexibleContent(scrollWidget);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    @Override
    public boolean displayPreviousScreen() {
        return false;
    }
    
    public void setHeaderType(final HeaderType headerType) {
        this.headerType = headerType;
    }
    
    public void setSessions(final Map<String, ListSession<?>> sessions) {
        this.sessions = sessions;
    }
    
    private boolean isOriginHolder() {
        return this.currentHolder == this.originHolder;
    }
    
    public Setting getCurrentHolder() {
        return this.currentHolder;
    }
    
    public Setting getOriginHolder() {
        return this.originHolder;
    }
    
    private boolean isOriginFiltered() {
        return this.originHolder instanceof SettingGroup && ((SettingGroup)this.originHolder).isFiltered();
    }
    
    public enum HeaderType
    {
        NONE, 
        SCROLL, 
        FIXED, 
        SCROLL_IN_CHILDREN, 
        FIXED_IN_CHILDREN;
    }
}
