// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.Constants;
import net.labymod.api.client.sound.SoundType;
import net.labymod.core.client.gui.screen.theme.fancy.controller.FancyActivityAnimator;
import net.labymod.api.client.gui.screen.theme.ThemeEventListener;
import net.labymod.core.client.gui.screen.theme.fancy.eventlistener.TitleMenuThemeEventListener;
import net.labymod.api.Laby;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.background.FancyScreenBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyItemStackPickerRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.hudwidget.FancyHudWidgetCanvasRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyNotificationRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyTabRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyCheckBoxRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyWindowRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyHrRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyScrollbarRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyDropdownPopupRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyDropdownRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySwitchRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyDirtBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyResizeRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyAddonProfileInstallButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyAddonProfileButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyNavigationButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySlimSliderRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySliderRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyButtonRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyBackgroundRenderer;
import javax.inject.Inject;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.core.client.gui.screen.theme.fancy.eventlistener.FancyVariableThemeEventListener;
import net.labymod.api.client.gui.screen.theme.ExtendingTheme;

public class FancyTheme extends ExtendingTheme
{
    public static final String ID = "fancy";
    private FancyVariableThemeEventListener variableListener;
    
    @Inject
    public FancyTheme() {
        super("fancy", FancyThemeConfig.class);
        this.setDisplayName("Fancy");
    }
    
    @Override
    public void onPostLoad() {
        super.onPostLoad();
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyBackgroundRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancySliderRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancySlimSliderRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyNavigationButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyAddonProfileButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyAddonProfileInstallButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyResizeRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyDirtBackgroundRenderer(this));
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancySwitchRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyDropdownRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyDropdownPopupRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyScrollbarRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyHrRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyWindowRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyCheckBoxRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyTabRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyNotificationRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyHudWidgetCanvasRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new FancyItemStackPickerRenderer());
        this.registerBackgroundRenderer(new FancyScreenBackgroundRenderer(this));
        this.bindType(MinecraftWidgetType.BUTTON, "Button");
        this.bindType(MinecraftWidgetType.SLIDER, "Slider");
        final Metadata metadata = this.metadata();
        metadata.set("interpolate_scroll", true);
        metadata.set("hide_default_background", true);
        metadata.set("hide_list_separators", true);
        metadata.set("hud_widget_floating_point_position", true);
        this.updateTransitionProperty();
        metadata.set("above_name_entry_y_offset", () -> {
            if (PlatformEnvironment.isAncientOpenGL()) {
                return Float.valueOf(-2.0f);
            }
            else {
                final RenderAttributesStack renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
                final RenderAttributes attributes = renderAttributesStack.last();
                if (attributes.isForceVanillaFont()) {
                    return Float.valueOf(-3.0f);
                }
                else if (Laby.references().textRendererProvider().useCustomFont()) {
                    return Float.valueOf(-1.0f);
                }
                else {
                    return Float.valueOf(-3.0f);
                }
            }
        });
        metadata.set("below_name_entry_y_offset", 9.0f);
        metadata.set("legacy_loading_screen_renderer_tile_size", 0.0f);
        metadata.set("legacy_loading_screen_renderer_tile_brightness", 255);
        metadata.set("show_dirt_separator", false);
        this.registerHoverBackgroundRenderer(Laby.references().hoverBackgroundEffect("fancy_hover_effect"));
        this.registerEventListener(new TitleMenuThemeEventListener(this));
        this.registerEventListener(this.variableListener = new FancyVariableThemeEventListener(this));
        this.registerEventListener(new FancyActivityAnimator(this));
        final SoundService soundService = Laby.references().soundService();
        soundService.bindConditionally(SoundType.BUTTON_CLICK, "fancy", Constants.Resources.SOUND_UI_BUTTON_CLICK, this::enabledFancySounds);
        soundService.bindConditionally(SoundType.SWITCH_TOGGLE_ON, "fancy", Constants.Resources.SOUND_UI_SWITCH_ON, this::enabledFancySounds);
        soundService.bindConditionally(SoundType.SWITCH_TOGGLE_OFF, "fancy", Constants.Resources.SOUND_UI_SWITCH_OFF, this::enabledFancySounds);
    }
    
    public void updateTransitionProperty() {
        final FancyThemeConfig config = Laby.labyAPI().themeService().getThemeConfig(this, FancyThemeConfig.class);
        if (config != null) {
            final boolean transitions = config.activityTransitions().get();
            this.metadata().set("transition", transitions);
        }
    }
    
    private boolean enabledFancySounds() {
        if (this.labyAPI.themeService().currentTheme() != this) {
            return false;
        }
        final FancyThemeConfig themeConfig = super.labyAPI.themeService().getThemeConfig(this, FancyThemeConfig.class);
        return themeConfig != null && themeConfig.fancySounds().get();
    }
    
    @NotNull
    @Override
    public TextRenderer textRenderer() {
        final FancyThemeConfig themeConfig = super.labyAPI.themeService().getThemeConfig(this, FancyThemeConfig.class);
        return (themeConfig != null && themeConfig.fancyFont().get()) ? super.textRenderer() : super.parentTheme().textRenderer();
    }
    
    public FancyVariableThemeEventListener getVariableListener() {
        return this.variableListener;
    }
}
