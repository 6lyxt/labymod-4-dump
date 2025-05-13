// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.background;

import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

public abstract class BackgroundHudWidget<T extends BackgroundHudWidgetConfig> extends SimpleHudWidget<T> implements HudWidgetBackgroundRenderer
{
    protected BackgroundHudWidget(final String id, final Class<T> configClass) {
        super(id, configClass);
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        if (stack != null) {
            this.renderEntireBackground(stack, size);
        }
    }
    
    @Override
    public BackgroundConfig config() {
        return this.config.background();
    }
    
    public abstract static class BackgroundHudWidgetConfig extends HudWidgetConfig
    {
        @SettingOrder(10)
        @CustomTranslation("labymod.hudWidget.background")
        @SettingRequires(value = "useGlobal", invert = true)
        private BackgroundConfig background;
        
        public BackgroundHudWidgetConfig() {
            this.background = new BackgroundConfig();
        }
        
        public BackgroundConfig background() {
            return this.config(GlobalHudWidgetConfig::background, this.background);
        }
    }
}
