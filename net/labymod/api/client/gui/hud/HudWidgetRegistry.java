// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import java.util.function.Supplier;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.service.Registry;

@Referenceable
public interface HudWidgetRegistry extends Registry<HudWidget<?>>
{
    public static final Logging LOGGER = Logging.getLogger();
    
    void registerDefaults();
    
    void selectProfile(final String p0);
    
    String getSelectedProfile();
    
    void updateLinkedWidgets(final HudWidget<?> p0);
    
    void saveConfig();
    
    default void register(final Supplier<HudWidget<?>> hudWidgetSupplier) {
        this.register(hudWidgetSupplier, throwable -> HudWidgetRegistry.LOGGER.error("Error while registering hud widget", throwable));
    }
    
    GlobalHudWidgetConfig globalHudWidgetConfig();
    
    RootSettingRegistry globalHudWidgetSettingRegistry();
    
    HudWidgetCategoryRegistry categoryRegistry();
    
    void updateHudWidgets(@NotNull final String p0);
    
    @Deprecated
    default void updateHudWidgets() {
        this.updateHudWidgets("unknown");
    }
}
