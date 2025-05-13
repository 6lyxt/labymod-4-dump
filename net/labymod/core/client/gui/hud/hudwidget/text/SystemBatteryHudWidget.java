// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 6, y = 2)
public class SystemBatteryHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private final SystemInfo systemInfo;
    private TextLine batteryLevelLine;
    private long lastUpdate;
    private double lastBatteryLevel;
    
    public SystemBatteryHudWidget() {
        super("system_battery_level");
        this.lastUpdate = -1L;
        this.lastBatteryLevel = 0.0;
        this.systemInfo = Laby.references().systemInfo();
        this.bindCategory(HudWidgetCategory.SYSTEM);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.batteryLevelLine = super.createLine("Battery", "?");
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.lastUpdate + 10000L >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getMillis();
        final double batteryLevel = this.systemInfo.getBatteryLevel();
        if (this.lastBatteryLevel == batteryLevel) {
            return;
        }
        this.lastBatteryLevel = batteryLevel;
        this.batteryLevelLine.updateAndFlush((batteryLevel == -1.0) ? "?" : ("" + MathHelper.floor(batteryLevel)));
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.lastBatteryLevel != 0.0;
    }
}
