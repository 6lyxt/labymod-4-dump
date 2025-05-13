// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.window;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.configuration.settings.type.AbstractSetting;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget.HudWindowActivity;
import java.util.Locale;
import net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.SelectionRenderer;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget.ListHudWindowActivity;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import java.util.Objects;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class HudWidgetWindowWidget extends SimpleWidget
{
    private static final RootSettingRegistry DUMMY_REGISTRY;
    private static final SettingElement DUMMY_SETTING;
    private final WidgetsEditorActivity editor;
    private final ScreenRendererWidget contentRendererWidget;
    private ComponentWidget title;
    private TextFieldWidget searchFieldWidget;
    private ButtonWidget globalButton;
    
    public HudWidgetWindowWidget(final WidgetsEditorActivity editor) {
        this.editor = editor;
        (this.contentRendererWidget = new ScreenRendererWidget()).addId("window-content");
        this.contentRendererWidget.addPostDisplayListener(screenInstance -> {
            if (this.searchFieldWidget != null) {
                this.searchFieldWidget.setEditable(this.isHudWidgetList());
            }
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final HorizontalListWidget header = new HorizontalListWidget();
        ((AbstractWidget<Widget>)header).addId("window-header");
        (this.title = ComponentWidget.component(Component.translatable("labymod.hudWidgetEditor.window.widgets.name", new Component[0]))).addId("title");
        header.addEntry(this.title);
        final ButtonWidget fullscreenButton = ButtonWidget.icon(this.editor.isFullScreen() ? Textures.SpriteWidgetEditor.MINIMIZE : Textures.SpriteWidgetEditor.MAXIMIZE);
        ((AbstractWidget<Widget>)fullscreenButton).addId("fullscreen-button");
        fullscreenButton.setHoverComponent(Component.translatable("labymod.hudWidgetEditor.button.fullscreen." + (this.editor.isFullScreen() ? "minimize" : "maximize"), new Component[0]));
        final ButtonWidget buttonWidget = fullscreenButton;
        final WidgetsEditorActivity editor = this.editor;
        Objects.requireNonNull(editor);
        buttonWidget.setPressable(editor::toggleFullscreen);
        header.addEntry(fullscreenButton);
        ((AbstractWidget<Widget>)(this.globalButton = ButtonWidget.icon(Textures.SpriteCommon.SETTINGS))).addId("open-global-settings-button");
        this.globalButton.setHoverComponent(Component.translatable("labymod.hudWidgetEditor.window.globalWidget.name", new Component[0]));
        this.globalButton.setPressable(this::displayGlobalSettings);
        header.addEntry(this.globalButton);
        this.updateGlobalButton();
        (this.searchFieldWidget = new TextFieldWidget()).addId("search-field");
        this.searchFieldWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchFieldWidget.setEditable(this.isHudWidgetList());
        this.searchFieldWidget.maximalLength(128);
        this.searchFieldWidget.setActionListener(() -> {
            if (this.isHudWidgetList()) {
                this.contentRendererWidget.reInitialize();
            }
            else {
                this.displayList();
            }
            return;
        });
        header.addEntry(this.searchFieldWidget);
        ((AbstractWidget<HorizontalListWidget>)this).addChild(header);
        final DivWidget stencilWrapper = new DivWidget();
        stencilWrapper.addId("content-wrapper");
        ((AbstractWidget<ScreenRendererWidget>)stencilWrapper).addChild(this.contentRendererWidget);
        ((AbstractWidget<DivWidget>)this).addChild(stencilWrapper);
        if (this.contentRendererWidget.getScreen() == null) {
            this.displayList();
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.title != null) {
            this.title.setVisible(this.bounds().getWidth() > 170.0f);
        }
    }
    
    private void updateGlobalButton() {
        final ScreenInstance screenInstance = this.contentRendererWidget.getScreen();
        if (screenInstance instanceof final SettingContentActivity contentActivity) {
            final RootSettingRegistry globalSetting = Laby.references().hudWidgetRegistry().globalHudWidgetSettingRegistry();
            boolean isGlobalSetting = false;
            for (Setting setting = contentActivity.getCurrentHolder(); setting.hasParent(); setting = setting.parent()) {
                if (globalSetting == setting) {
                    isGlobalSetting = true;
                    break;
                }
            }
            this.globalButton.setEnabled(!isGlobalSetting);
        }
        else {
            this.globalButton.setEnabled(true);
        }
    }
    
    public void displayList() {
        this.displayActivity(new ListHudWindowActivity(this));
    }
    
    public void displayGlobalSettings() {
        final RootSettingRegistry rootSettingRegistry = Laby.references().hudWidgetRegistry().globalHudWidgetSettingRegistry();
        Setting parent = HudWidgetWindowWidget.DUMMY_REGISTRY;
        final ScreenInstance screen = this.contentRendererWidget.getScreen();
        if (screen instanceof final SettingContentActivity activity) {
            Setting currentHolder = activity.getCurrentHolder();
            if (currentHolder == rootSettingRegistry) {
                return;
            }
            if (currentHolder.getId().equals("useGlobal") && currentHolder.hasParent()) {
                currentHolder = currentHolder.parent();
            }
            if (currentHolder != null) {
                parent = currentHolder;
            }
        }
        rootSettingRegistry.setParent(parent);
        this.displaySetting(rootSettingRegistry);
    }
    
    public void displaySettings(final HudWidget<?> hudWidget) {
        final RootSettingRegistry settingRegistry = this.createSettingRegistry(hudWidget);
        settingRegistry.initialize();
        this.displaySetting(settingRegistry);
        final SelectionRenderer selection = this.editor().renderer().selectionRenderer();
        selection.unselectAll();
        selection.select(hudWidget);
    }
    
    private void displaySetting(final RootSettingRegistry settingRegistry) {
        final SettingContentActivity activity = settingRegistry.createActivity();
        activity.addInitializeRunnable(() -> activity.addStyle("activity/hudwidget/window/hud-widget-settings.lss"));
        activity.screenCallback(setting -> {
            if (setting.getId().equals("useGlobal")) {
                this.displayGlobalSettings();
                return null;
            }
            else if (setting == HudWidgetWindowWidget.DUMMY_REGISTRY) {
                this.displayList();
                return null;
            }
            else {
                return setting;
            }
        });
        this.displayActivity(activity);
    }
    
    public void displayActivity(final Activity activity) {
        this.contentRendererWidget.displayScreen(activity);
        this.updateGlobalButton();
        this.searchFieldWidget.setEditable(this.isHudWidgetList());
    }
    
    private RootSettingRegistry createSettingRegistry(final HudWidget<?> hudWidget) {
        final String namespace = Laby.labyAPI().getNamespace(hudWidget);
        final String id = hudWidget.getId();
        final HudWidgetSettingRegistry setting = new HudWidgetSettingRegistry(namespace, id);
        setting.setDisplayName(Component.translatable("labymod.hudWidgetEditor.window.editWidget.name", hudWidget.displayName()));
        setting.translationId(String.format(Locale.ROOT, "hudWidget.%s", id));
        setting.setParent(HudWidgetWindowWidget.DUMMY_REGISTRY);
        setting.addSettings(hudWidget.getSettings());
        return setting;
    }
    
    public WidgetsEditorActivity editor() {
        return this.editor;
    }
    
    public String getSearchQuery() {
        return this.searchFieldWidget.getText();
    }
    
    public boolean isHudWidgetList() {
        final ScreenInstance screen = this.contentRendererWidget.getScreen();
        return screen instanceof ListHudWindowActivity;
    }
    
    public boolean isHudWidgetOnTrashArea() {
        return this.editor.renderer().getDraggingHudWidget() != null && this.contentRendererWidget.isHovered();
    }
    
    public boolean canDropHudWidget() {
        final ScreenInstance screen = this.contentRendererWidget.getScreen();
        if (screen instanceof final HudWindowActivity hudWindowActivity) {
            if (hudWindowActivity.canDropHudWidget()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        DUMMY_REGISTRY = RootSettingRegistry.labymod("dummy");
        DUMMY_SETTING = new SettingElement("dummy", null, null, new String[0]);
    }
    
    private static class HudWidgetSettingRegistry extends RootSettingRegistry
    {
        private Component displayName;
        
        protected HudWidgetSettingRegistry(final String namespace, final String id) {
            super(namespace, id);
        }
        
        @Override
        public Component displayName() {
            if (this.displayName == null) {
                return super.displayName();
            }
            return this.displayName;
        }
        
        @Override
        public void addSetting(final AbstractSetting setting) {
            if (setting.getId().equals("useGlobal")) {
                setting.addSetting(HudWidgetWindowWidget.DUMMY_SETTING);
            }
            super.addSetting(setting);
        }
        
        public void setDisplayName(final Component displayName) {
            this.displayName = displayName;
        }
    }
}
