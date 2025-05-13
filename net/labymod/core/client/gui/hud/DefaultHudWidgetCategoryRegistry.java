// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud;

import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(HudWidgetCategoryRegistry.class)
public class DefaultHudWidgetCategoryRegistry extends DefaultRegistry<HudWidgetCategory> implements HudWidgetCategoryRegistry
{
    @Inject
    public DefaultHudWidgetCategoryRegistry() {
    }
}
