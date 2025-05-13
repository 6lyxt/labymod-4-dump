// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import java.util.Locale;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 1, y = 3)
public class WorldTimeHudWidget extends TextHudWidget<WorldTimeHudWidgetConfig>
{
    private TextLine timeLine;
    
    public WorldTimeHudWidget() {
        super("world_time", WorldTimeHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final WorldTimeHudWidgetConfig config) {
        super.load(config);
        this.timeLine = super.createLine("World Time", "00:00");
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final boolean militaryFormat = ((WorldTimeHudWidgetConfig)this.config).militaryFormat().get();
        boolean showDays = ((WorldTimeHudWidgetConfig)this.config).showDays().get();
        final long timePassed = this.labyAPI.minecraft().clientWorld().getDayTime();
        final long days = timePassed / 24000L + 1L;
        final long millisSinceMidnight = timePassed % 24000L;
        int hour = (int)((millisSinceMidnight / 1000L + 6L) % 24L);
        final int minute = (int)(millisSinceMidnight % 1000L / 1000.0 * 60.0);
        if (((WorldTimeHudWidgetConfig)this.config).showOnNewDay().get()) {
            showDays = (millisSinceMidnight >= 0L && millisSinceMidnight <= 500L);
        }
        final String daySuffix = showDays ? " (%s)".formatted(I18n.translate("labymod.hudWidget.world_time.showDays.day", days)) : "";
        if (militaryFormat) {
            this.timeLine.updateAndFlush(String.format(Locale.ROOT, "%02d:%02d", hour, minute) + daySuffix);
        }
        else {
            final String suffix = (hour >= 12) ? "pm" : "am";
            hour = ((hour % 12 == 0) ? 12 : (hour % 12));
            this.timeLine.updateAndFlush(String.format(Locale.ROOT, "%d:%02d %s", hour, minute, suffix) + daySuffix);
        }
    }
    
    public static class WorldTimeHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> militaryFormat;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showDays;
        @SettingRequires("showDays")
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showOnNewDay;
        
        public WorldTimeHudWidgetConfig() {
            this.militaryFormat = new ConfigProperty<Boolean>(true);
            this.showDays = new ConfigProperty<Boolean>(true);
            this.showOnNewDay = new ConfigProperty<Boolean>(false);
        }
        
        public ConfigProperty<Boolean> militaryFormat() {
            return this.militaryFormat;
        }
        
        public ConfigProperty<Boolean> showDays() {
            return this.showDays;
        }
        
        public ConfigProperty<Boolean> showOnNewDay() {
            return this.showOnNewDay;
        }
    }
}
