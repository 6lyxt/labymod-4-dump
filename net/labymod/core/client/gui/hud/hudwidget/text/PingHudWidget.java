// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.Minecraft;
import java.util.Iterator;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.util.ArrayList;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.util.List;
import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 1)
public class PingHudWidget extends TextHudWidget<PingHudWidgetConfig>
{
    private static final Color LOW;
    private static final Color MEDIUM;
    private static final Color HIGH;
    private static final Color EXTREME;
    private final List<Integer> storedPing;
    private int lastPing;
    private long lastUpdateMillis;
    private TextLine textLine;
    
    public PingHudWidget() {
        super("ping", PingHudWidgetConfig.class);
        this.storedPing = new ArrayList<Integer>();
        this.lastPing = -1;
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final PingHudWidgetConfig config) {
        super.load(config);
        this.textLine = this.createLine("Ping", "?");
        this.lastPing = -1;
        this.updatePing();
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.lastUpdateMillis >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdateMillis = TimeUtil.getMillis() + 5000L;
        this.updatePing();
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.labyAPI.minecraft().getClientPlayer() != null && !this.labyAPI.minecraft().isSingleplayer();
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.storedPing.clear();
        this.lastPing = -1;
        this.textLine.updateAndFlush("?");
    }
    
    private void updatePing() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        if (playerInfo == null) {
            return;
        }
        final int currentPing = playerInfo.getCurrentPing();
        if (currentPing <= 0 || this.lastPing == currentPing) {
            return;
        }
        this.lastPing = currentPing;
        int averagePing = 0;
        if (this.getConfig().displayAverage().get()) {
            this.storedPing.add(currentPing);
            if (this.storedPing.size() > 12) {
                this.storedPing.remove(0);
            }
            int total = 0;
            for (final int ping : this.storedPing) {
                total += ping;
            }
            averagePing = total / this.storedPing.size();
        }
        Color color = this.getConfig().valueColor().get();
        if (this.getConfig().coloredPing().get()) {
            if (currentPing < 80) {
                color = PingHudWidget.LOW;
            }
            else if (currentPing < 120) {
                color = PingHudWidget.MEDIUM;
            }
            else if (currentPing < 250) {
                color = PingHudWidget.HIGH;
            }
            else {
                color = PingHudWidget.EXTREME;
            }
        }
        Component component = ((BaseComponent<Component>)Component.empty()).append(((BaseComponent<Component>)Component.text(currentPing)).color(TextColor.color(color.get())));
        if (this.getConfig().displayAverage().get()) {
            component = component.append(Component.space()).append(((BaseComponent<Component>)Component.text(averagePing)).color(TextColor.color(this.getConfig().averageColor().get().get())));
        }
        this.textLine.updateAndFlush(component);
    }
    
    private NetworkPlayerInfo getPlayerInfo() {
        final Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft.isSingleplayer()) {
            return null;
        }
        final ClientPlayer player = minecraft.getClientPlayer();
        if (player == null) {
            return null;
        }
        final ClientPacketListener clientPacketListener = minecraft.getClientPacketListener();
        if (clientPacketListener == null) {
            return null;
        }
        return clientPacketListener.getNetworkPlayerInfo(player.getUniqueId());
    }
    
    static {
        LOW = Color.GREEN;
        MEDIUM = Color.YELLOW;
        HIGH = Color.of("#FF5555");
        EXTREME = Color.of("#AA0000");
    }
    
    public static class PingHudWidgetConfig extends TextHudWidgetConfig
    {
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> averageColor;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> displayAverage;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> coloredPing;
        
        public PingHudWidgetConfig() {
            this.averageColor = new ConfigProperty<Color>(Color.YELLOW);
            this.displayAverage = new ConfigProperty<Boolean>(false);
            this.coloredPing = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<Color> averageColor() {
            return this.averageColor;
        }
        
        public ConfigProperty<Boolean> displayAverage() {
            return this.displayAverage;
        }
        
        public ConfigProperty<Boolean> coloredPing() {
            return this.coloredPing;
        }
    }
}
