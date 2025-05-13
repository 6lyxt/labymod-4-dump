// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import java.util.Locale;
import net.labymod.api.util.I18n;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 5, y = 4)
public class CpuTemperatureHudWidget extends TextHudWidget<CpuTemperatureHudWidgetConfig>
{
    private final SystemInfo systemInfo;
    private TextLine temperatureLine;
    private long nextUpdate;
    private double lastTemperature;
    
    public CpuTemperatureHudWidget() {
        super("cpu_temperature", CpuTemperatureHudWidgetConfig.class);
        this.lastTemperature = -2.147483648E9;
        this.systemInfo = Laby.references().systemInfo();
        this.bindCategory(HudWidgetCategory.SYSTEM);
    }
    
    @Override
    public void load(final CpuTemperatureHudWidgetConfig config) {
        super.load(config);
        this.temperatureLine = super.createLine("CPU Temperature", "?");
        this.lastTemperature = -2.147483648E9;
        this.nextUpdate = TimeUtil.getMillis();
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.nextUpdate > TimeUtil.getMillis()) {
            return;
        }
        final double temperature = this.systemInfo.getCPUTemperature();
        final boolean invalid = temperature == -1.0 || temperature == 0.0 || Double.isNaN(temperature);
        this.nextUpdate = TimeUtil.getMillis() + 1000 * (invalid ? 10 : 1);
        if (this.lastTemperature == temperature) {
            return;
        }
        this.lastTemperature = temperature;
        if (invalid) {
            final boolean isWindows = OperatingSystem.getPlatform() == OperatingSystem.WINDOWS;
            this.temperatureLine.updateAndFlush(isWindows ? I18n.getTranslation("labymod.hudWidget.cpu_temperature.requires", "OpenHardwareMonitor") : "?");
        }
        else {
            final boolean displayInFahrenheit = ((CpuTemperatureHudWidgetConfig)this.config).fahrenheit().get();
            final float fahrenheit = (float)(temperature * 1.8 + 32.0);
            this.temperatureLine.updateAndFlush(String.format(Locale.ROOT, "%.1f°%s", displayInFahrenheit ? fahrenheit : temperature, displayInFahrenheit ? "F" : "C"));
        }
    }
    
    @Override
    public boolean isVisibleInGame() {
        return true;
    }
    
    public static class CpuTemperatureHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> fahrenheit;
        
        public CpuTemperatureHudWidgetConfig() {
            this.fahrenheit = new ConfigProperty<Boolean>(false);
        }
        
        public ConfigProperty<Boolean> fahrenheit() {
            return this.fahrenheit;
        }
    }
}
