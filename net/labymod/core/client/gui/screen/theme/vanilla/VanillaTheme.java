// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaTexturedBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaItemStackPickerRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget.VanillaHudWidgetCanvasRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBulletEntryContainerRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat.VanillaChatSliderRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat.VanillaChatSwitchRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaColorPreviewRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaResizeRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaNotificationRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget.VanillaHudWidgetTilesGridRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaWindowRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaCheckBoxRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaCrosshairRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaWheelRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonDropdownRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaTextFieldRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaSliderRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaSwitchRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaScrollbarRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaListCutOutRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaPopupBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaWindowButtonRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaIngameButtonRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaDirtBackgroundRenderer;
import javax.inject.Inject;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.core.client.gui.screen.theme.RootTheme;

public class VanillaTheme extends RootTheme
{
    public static final String ID = "vanilla";
    private static final boolean GUI_ATLAS;
    
    @Inject
    public VanillaTheme() {
        super("vanilla", VanillaThemeConfig.class);
        this.setDisplayName("Vanilla");
    }
    
    @Override
    public void onPostLoad() {
        super.onPostLoad();
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaDirtBackgroundRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaIngameButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaWindowButtonRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaBackgroundRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaPopupBackgroundRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaListCutOutRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaScrollbarRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaSwitchRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaSliderRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaTextFieldRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaButtonDropdownRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaButtonDropdownRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaWheelRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaCrosshairRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaCheckBoxRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaWindowRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaHudWidgetTilesGridRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaNotificationRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaResizeRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaColorPreviewRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaButtonDropdownRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaChatSwitchRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaChatSliderRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaBulletEntryContainerRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaHudWidgetCanvasRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaItemStackPickerRenderer());
        this.registerWidgetRenderer((ThemeRenderer<Widget>)new VanillaTexturedBackgroundRenderer(this));
        this.registerBackgroundRenderer(new VanillaScreenBackgroundRenderer());
        this.registerHoverBackgroundRenderer(Laby.references().hoverBackgroundEffect("vanilla_hover_effect"));
        this.bindType(MinecraftWidgetType.BUTTON, "Button");
        this.bindType(MinecraftWidgetType.SLIDER, "Slider");
        final Metadata metadata = this.metadata();
        metadata.set("scroll_background", "DirtBackground");
        metadata.set("above_name_entry_y_offset", () -> PlatformEnvironment.isAncientOpenGL() ? -2.0f : -3.0f);
        metadata.set("below_name_entry_y_offset", 9.0f);
        metadata.set("server_selection_texture_feature", VanillaTheme.GUI_ATLAS);
    }
    
    static {
        GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();
    }
}
