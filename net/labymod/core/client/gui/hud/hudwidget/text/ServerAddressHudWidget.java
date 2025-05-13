// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.core.main.LabyMod;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 0, y = 1)
public class ServerAddressHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private final LabyMod labyMod;
    private TextLine textLine;
    
    public ServerAddressHudWidget() {
        super("server_address");
        this.labyMod = LabyMod.getInstance();
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("IP", "labymod.net");
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final ServerData serverData = this.labyAPI.serverController().getCurrentServerData();
        if (serverData == null) {
            return;
        }
        final String host = serverData.address().getHost();
        this.textLine.updateAndFlush(host);
    }
    
    @Override
    public boolean isVisibleInGame() {
        return !this.labyMod.minecraft().isSingleplayer() && this.labyMod.serverController().getCurrentServerData() != null;
    }
}
