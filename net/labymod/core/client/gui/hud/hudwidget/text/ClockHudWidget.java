// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import java.util.TimeZone;
import net.labymod.api.util.time.DateUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.time.format.DateTimeFormatter;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.time.ZoneId;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 2)
public class ClockHudWidget extends TextHudWidget<ClockHudWidgetConfig>
{
    private static final Logging LOGGER;
    private static final ZoneId DEFAULT_TIMEZONE;
    private ZoneId selectedZoneId;
    private TextLine clockLine;
    private String selectedClockFormat;
    private DateTimeFormatter clockFormat;
    private String lastTimeString;
    
    public ClockHudWidget() {
        super("clock", ClockHudWidgetConfig.class);
        this.selectedZoneId = ClockHudWidget.DEFAULT_TIMEZONE;
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final ClockHudWidgetConfig config) {
        super.load(config);
        this.clockLine = super.createLine("Clock", "?");
        this.updateConfig();
    }
    
    @Override
    public void onUpdate() {
        this.updateConfig();
        super.onUpdate();
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final String formattedTime = (this.clockFormat == null) ? ("Invalid time format: '" + this.selectedClockFormat) : DateUtil.formatNow(this.clockFormat, this.selectedZoneId, DateUtil.TemporalAccessorType.TIME);
        if (this.lastTimeString != null && this.lastTimeString.equals(formattedTime)) {
            return;
        }
        this.lastTimeString = formattedTime;
        this.clockLine.updateAndFlush(formattedTime);
    }
    
    private void updateConfig() {
        final TimeFormat timeFormat = this.getConfig().timeFormat().get();
        if (timeFormat.equals(TimeFormat.CUSTOM)) {
            this.selectedClockFormat = this.getConfig().customFormat().getOrDefault(TimeFormat.CLOCK_24.getFormat());
        }
        else {
            this.selectedClockFormat = timeFormat.getFormat();
        }
        try {
            this.clockFormat = DateUtil.ofPattern(this.selectedClockFormat, DateUtil.TemporalAccessorType.TIME);
            final String customTimeZone = this.getConfig().customTimeZone().get();
            if (customTimeZone != null && !customTimeZone.isEmpty()) {
                try {
                    final ZoneId zoneId = ZoneId.of(customTimeZone);
                    final TimeZone timeZone = TimeZone.getTimeZone(customTimeZone);
                    if (timeZone != null) {
                        this.selectedZoneId = zoneId;
                    }
                }
                catch (final Exception e) {
                    ClockHudWidget.LOGGER.error("Invalid time zone: {}", customTimeZone);
                    this.selectedZoneId = ClockHudWidget.DEFAULT_TIMEZONE;
                }
            }
            else {
                this.selectedZoneId = ClockHudWidget.DEFAULT_TIMEZONE;
            }
        }
        catch (final IllegalArgumentException error) {
            ClockHudWidget.LOGGER.error(error.getMessage(), new Object[0]);
            this.clockFormat = null;
        }
        this.lastTimeString = null;
    }
    
    static {
        LOGGER = Logging.create(ClockHudWidget.class);
        DEFAULT_TIMEZONE = ZoneId.systemDefault();
    }
    
    public enum TimeFormat
    {
        CLOCK_12_SECONDS("h:mm:ss a"), 
        CLOCK_12("h:mm a"), 
        CLOCK_24_SECONDS("HH:mm:ss"), 
        CLOCK_24("HH:mm"), 
        CUSTOM((String)null);
        
        private final String format;
        
        private TimeFormat(final String format) {
            this.format = format;
        }
        
        public String getFormat() {
            return this.format;
        }
    }
    
    public static class ClockHudWidgetConfig extends TextHudWidgetConfig
    {
        @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.clock.timeFormat.entries")
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<TimeFormat> timeFormat;
        @TextFieldWidget.TextFieldSetting
        @SettingRequires(value = "timeFormat", required = "CUSTOM")
        private final ConfigProperty<String> customFormat;
        @TextFieldWidget.TextFieldSetting
        private final ConfigProperty<String> customTimeZone;
        
        public ClockHudWidgetConfig() {
            this.timeFormat = ConfigProperty.createEnum(TimeFormat.CLOCK_12);
            this.customFormat = new ConfigProperty<String>("HH:mm:ss");
            this.customTimeZone = new ConfigProperty<String>("");
        }
        
        public ConfigProperty<TimeFormat> timeFormat() {
            return this.timeFormat;
        }
        
        public ConfigProperty<String> customFormat() {
            return this.customFormat;
        }
        
        public ConfigProperty<String> customTimeZone() {
            return this.customTimeZone;
        }
    }
}
