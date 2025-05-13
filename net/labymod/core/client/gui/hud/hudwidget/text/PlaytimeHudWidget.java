// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import java.util.Locale;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 4, y = 4)
public class PlaytimeHudWidget extends TextHudWidget<PlaytimeHudWidgetConfig>
{
    private final long timeGameStart;
    private long timeServerStart;
    private long timeServerEnd;
    private TextLine timeLine;
    
    public PlaytimeHudWidget() {
        super("playtime", PlaytimeHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.timeServerStart = -1L;
        this.timeGameStart = TimeUtil.getMillis();
    }
    
    @Override
    public void load(final PlaytimeHudWidgetConfig config) {
        super.load(config);
        this.timeLine = super.createLine("Playtime", "00:00");
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final boolean gameSession = ((PlaytimeHudWidgetConfig)this.config).gameSession().get();
        final long gameTime = TimeUtil.getMillis() - this.timeGameStart;
        final long serverTime = (this.timeServerStart == -1L) ? 0L : ((this.timeServerEnd == -1L) ? (TimeUtil.getMillis() - this.timeServerStart) : (this.timeServerEnd - this.timeServerStart));
        final long timePassed = gameSession ? gameTime : serverTime;
        final long minutesPassed = timePassed / 1000L / 60L;
        final long days = minutesPassed / 1440L;
        final int hour = (int)(minutesPassed / 60L) % 24;
        final int minute = (int)minutesPassed % 60;
        this.timeLine.updateAndFlush((days > 0L) ? String.format(Locale.ROOT, "%dd %02d:%02d", days, hour, minute) : String.format(Locale.ROOT, "%02d:%02d", hour, minute));
    }
    
    @Subscribe
    public void onServerJoin(final ServerJoinEvent event) {
        this.timeServerStart = TimeUtil.getMillis();
        this.timeServerEnd = -1L;
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.timeServerEnd = TimeUtil.getMillis();
    }
    
    public static class PlaytimeHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> gameSession;
        
        public PlaytimeHudWidgetConfig() {
            this.gameSession = new ConfigProperty<Boolean>(false);
        }
        
        public ConfigProperty<Boolean> gameSession() {
            return this.gameSession;
        }
    }
}
