// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot
public class FpsHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private TextLine fpsLine;
    
    public FpsHudWidget() {
        super("fps");
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.fpsLine = super.createLine("FPS", 0);
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        this.fpsLine.updateAndFlush(this.labyAPI.minecraft().getFPS());
    }
}
