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
import net.labymod.api.util.time.DateUtil;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.time.format.DateTimeFormatter;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 7)
public class DateHudWidget extends TextHudWidget<DateHudWidgetConfig>
{
    private String selectedDateFormat;
    private DateTimeFormatter dateFormat;
    private long lastUpdate;
    private TextLine clockLine;
    private String lastDateString;
    
    public DateHudWidget() {
        super("date", DateHudWidgetConfig.class);
        this.lastUpdate = -1L;
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final DateHudWidgetConfig config) {
        super.load(config);
        this.clockLine = super.createLine("Date", "?");
        this.updateConfig();
    }
    
    @Override
    public void onUpdate() {
        this.updateConfig();
        super.onUpdate();
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.lastUpdate + 10000L >= TimeUtil.getCurrentTimeMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getCurrentTimeMillis();
        final String formattedTime = (this.dateFormat == null) ? ("Invalid date format: '" + this.selectedDateFormat) : DateUtil.formatNow(this.dateFormat, DateUtil.DEFAULT_TIMEZONE, DateUtil.TemporalAccessorType.DATE);
        if (this.lastDateString != null && this.lastDateString.equals(formattedTime)) {
            return;
        }
        this.lastDateString = formattedTime;
        this.clockLine.updateAndFlush(formattedTime);
    }
    
    private void updateConfig() {
        final DateFormat timeFormat = this.getConfig().dateFormat().get();
        if (timeFormat.equals(DateFormat.CUSTOM)) {
            this.selectedDateFormat = this.getConfig().customFormat().getOrDefault(DateFormat.US.getFormat());
        }
        else {
            this.selectedDateFormat = timeFormat.getFormat();
        }
        try {
            this.dateFormat = DateUtil.ofPattern(this.selectedDateFormat, DateUtil.TemporalAccessorType.DATE);
        }
        catch (final IllegalArgumentException error) {
            this.dateFormat = null;
        }
        this.lastUpdate = -1L;
        this.lastDateString = null;
    }
    
    public enum DateFormat
    {
        FULL_US("MM/dd/YYYY"), 
        US("MM/dd/YY"), 
        SHORT_US("MM/dd"), 
        FULL_EU("dd.MM.yyyy"), 
        EU("dd.MM.yy"), 
        SHORT_EU("dd.MM"), 
        CUSTOM((String)null);
        
        private final String format;
        
        private DateFormat(final String format) {
            this.format = format;
        }
        
        public String getFormat() {
            return this.format;
        }
    }
    
    public static class DateHudWidgetConfig extends TextHudWidgetConfig
    {
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DateFormat> dateFormat;
        @TextFieldWidget.TextFieldSetting
        @SettingRequires(value = "dateFormat", required = "CUSTOM")
        private final ConfigProperty<String> customFormat;
        
        public DateHudWidgetConfig() {
            this.dateFormat = ConfigProperty.createEnum(DateFormat.FULL_US);
            this.customFormat = new ConfigProperty<String>("");
        }
        
        public ConfigProperty<DateFormat> dateFormat() {
            return this.dateFormat;
        }
        
        public ConfigProperty<String> customFormat() {
            return this.customFormat;
        }
    }
}
