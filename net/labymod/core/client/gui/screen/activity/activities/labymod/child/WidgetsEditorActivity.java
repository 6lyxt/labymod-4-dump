// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.client.gui.hud.HudWidgetUpdateRequestEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.hud.HudWidgetRegisterEvent;
import net.labymod.api.Laby;
import net.labymod.core.client.render.font.text.TextUtil;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.HudWidgetWindowWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.SplitContentWidget;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/hudwidget/hud-widget-editor.lss")
@AutoActivity
public class WidgetsEditorActivity extends Activity
{
    private final SplitContentWidget<Widget, Widget> split;
    private final HudWidgetWindowWidget windowWidget;
    private final HudWidgetInteractionWidget renderer;
    private boolean fullScreen;
    private ScreenWrapper previousRootScreen;
    
    public WidgetsEditorActivity() {
        (this.split = new SplitContentWidget<Widget, Widget>()).addId("split", "windowed");
        (this.windowWidget = new HudWidgetWindowWidget(this)).addId("window");
        ((AbstractWidget<Widget>)(this.renderer = new HudWidgetInteractionWidget(this.split.bounds(), this))).addId("renderer", "bounds");
        this.renderer.setOpenSettingsListener(hudWidget -> {
            if (hudWidget == null) {
                this.windowWidget.displayList();
            }
            else {
                this.windowWidget.displaySettings(hudWidget);
            }
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ThemeService themeService = this.labyAPI.themeService();
        final FancyThemeConfig config = themeService.getThemeConfig(FancyThemeConfig.class);
        if (config != null) {
            this.setVariable("--force-vanilla-font", !config.isIngameFancyFont());
        }
        this.split.setLeft(this.windowWidget);
        final DivWidget rendererWrapper = new DivWidget();
        rendererWrapper.addId("renderer-wrapper");
        if (DynamicBackgroundController.isEnabled()) {
            this.renderer.removeId("border");
        }
        else {
            ((AbstractWidget<Widget>)this.renderer).addId("border");
        }
        ((AbstractWidget<HudWidgetInteractionWidget>)rendererWrapper).addChild(this.renderer);
        this.split.setRight(rendererWrapper);
        ((AbstractWidget<SplitContentWidget<Widget, Widget>>)this.document).addChild(this.split);
    }
    
    @Override
    protected void postInitialize() {
        TextUtil.pushAndApplyAttributes();
        super.postInitialize();
        TextUtil.popRenderAttributes();
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        this.labyAPI.hudWidgetRegistry().updateHudWidgets("open_editor");
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.labyAPI.hudWidgetRegistry().updateHudWidgets("close_editor");
        Laby.references().hudWidgetRegistry().saveConfig();
    }
    
    @Subscribe
    public void onHudWidgetRegister(final HudWidgetRegisterEvent event) {
        if (this.renderer != null) {
            this.renderer.addHudWidget(event.hudWidget());
        }
    }
    
    @Subscribe
    public void onHudWidgetUpdateRequest(final HudWidgetUpdateRequestEvent event) {
        if (this.renderer != null && this.renderer.isInitialized()) {
            this.renderer.reinitializeHudWidget(event.hudWidget(), event.getReason());
        }
    }
    
    public void toggleFullscreen() {
        this.setFullScreen(!this.fullScreen);
    }
    
    public void setFullScreen(final boolean fullScreen) {
        if (this.fullScreen == fullScreen) {
            return;
        }
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final ScreenWrapper previousRootScreen = window.currentScreen();
        this.fullScreen = fullScreen;
        this.split.removeId(fullScreen ? "windowed" : "full-screen");
        this.split.addId(fullScreen ? "full-screen" : "windowed");
        window.displayScreen((ScreenInstance)(fullScreen ? this : this.previousRootScreen));
        this.previousRootScreen = previousRootScreen;
    }
    
    public boolean isFullScreen() {
        return this.fullScreen;
    }
    
    public HudWidgetInteractionWidget renderer() {
        return this.renderer;
    }
    
    public HudWidgetWindowWidget window() {
        return this.windowWidget;
    }
    
    @Override
    public boolean shouldHandleEscape() {
        if (this.windowWidget == null) {
            return super.shouldHandleEscape();
        }
        return this.windowWidget.shouldHandleEscape() || this.renderer.shouldHandleEscape();
    }
}
