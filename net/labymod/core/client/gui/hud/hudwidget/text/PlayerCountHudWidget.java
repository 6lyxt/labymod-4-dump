// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 3)
public class PlayerCountHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private TextLine playerCountLine;
    
    public PlayerCountHudWidget() {
        super("player_count");
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.playerCountLine = super.createLine("Online", "?");
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final ClientPacketListener clientPacketListener = super.labyAPI.minecraft().clientPacketListener();
        if (clientPacketListener == null) {
            return;
        }
        final int playerCount = clientPacketListener.getPlayerCount();
        this.playerCountLine.updateAndFlush(playerCount);
    }
}
