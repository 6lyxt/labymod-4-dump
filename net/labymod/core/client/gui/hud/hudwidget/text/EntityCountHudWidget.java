// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.core.main.LabyMod;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 6, y = 1)
public class EntityCountHudWidget extends TextHudWidget<EntityCountHudWidgetConfig>
{
    private final LabyMod labyMod;
    private TextLine entityCountTextLine;
    
    public EntityCountHudWidget() {
        super("entity_count", EntityCountHudWidgetConfig.class);
        this.labyMod = LabyMod.getInstance();
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final EntityCountHudWidgetConfig config) {
        super.load(config);
        this.entityCountTextLine = super.createLine("Entity", 0);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final int renderedEntities = this.labyMod.minecraft().worldRenderer().getRenderedEntities();
        Object value;
        if (this.getConfig().showMaxEntityCount().get()) {
            final ClientWorld world = this.labyMod.minecraft().clientWorld();
            if (world == null) {
                return;
            }
            value = renderedEntities + "/" + world.getEntities().size();
        }
        else {
            value = renderedEntities;
        }
        this.entityCountTextLine.updateAndFlush(value);
    }
    
    public static class EntityCountHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showMaxEntityCount;
        
        public EntityCountHudWidgetConfig() {
            this.showMaxEntityCount = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<Boolean> showMaxEntityCount() {
            return this.showMaxEntityCount;
        }
    }
}
