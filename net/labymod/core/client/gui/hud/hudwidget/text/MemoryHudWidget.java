// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import java.util.Locale;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 4)
public class MemoryHudWidget extends TextHudWidget<MemoryHudWidgetConfig>
{
    private TextLine textLine;
    private long lastUpdate;
    private long lastMemoryPercent;
    
    public MemoryHudWidget() {
        super("memory", MemoryHudWidgetConfig.class);
        this.lastUpdate = 0L;
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final MemoryHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Memory", "?");
        this.lastUpdate = -1L;
        this.lastMemoryPercent = -1L;
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.lastUpdate + 50L >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getMillis();
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();
        final long usedMemory = totalMemory - freeMemory;
        final long percent = usedMemory * 100L / maxMemory;
        if (this.lastMemoryPercent == percent) {
            return;
        }
        this.lastMemoryPercent = percent;
        final boolean moreSpace = this.getConfig().moreSpace().get();
        final MemoryType memoryType = this.getConfig().memoryType().get();
        Component component = Component.empty();
        TextColor textColor = TextColor.color(this.getConfig().valueColor().get().get());
        if (this.getConfig().coloredNumbers().get()) {
            textColor = ((percent >= 70L) ? ((percent >= 90L) ? TextColor.color(255, 0, 0) : TextColor.color(255, 125, 0)) : textColor);
        }
        if (memoryType.hasPercent()) {
            component = component.append(((BaseComponent<Component>)Component.text("" + percent)).color(textColor));
        }
        if (this.getConfig().memoryType().get().isUsed()) {
            String used = memoryType.equals(MemoryType.USED_PERCENT) ? " " : "";
            used += this.humanReadableByteCount(usedMemory, true, moreSpace);
            component = component.append(((BaseComponent<Component>)Component.text(used)).color(textColor));
            if (memoryType != MemoryType.USED && memoryType != MemoryType.USED_PERCENT) {
                component = component.append(((BaseComponent<Component>)Component.text(memoryType.equals(MemoryType.USED_SLASH_MAX) ? "/" : " of ")).color(TextColor.color(this.getConfig().labelColor().get().get())));
                component = component.append(((BaseComponent<Component>)Component.text(this.humanReadableByteCount(maxMemory, true, moreSpace))).color(TextColor.color(-1)));
            }
        }
        this.textLine.updateAndFlush(component);
    }
    
    public String humanReadableByteCount(final long bytes, final boolean si, final boolean space) {
        final int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        final int exp = (int)(Math.log((double)bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.ROOT, "%.1f" + (space ? " " : "") + "%sB", bytes / Math.pow(unit, exp), pre);
    }
    
    public enum MemoryType
    {
        PERCENT, 
        USED, 
        USED_PERCENT, 
        USED_SLASH_MAX, 
        USED_OF_MAX;
        
        public boolean hasPercent() {
            return this == MemoryType.PERCENT || this == MemoryType.USED_PERCENT;
        }
        
        public boolean isUsed() {
            return this == MemoryType.USED || this == MemoryType.USED_PERCENT || this == MemoryType.USED_SLASH_MAX || this == MemoryType.USED_OF_MAX;
        }
    }
    
    public static class MemoryHudWidgetConfig extends TextHudWidgetConfig
    {
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<MemoryType> memoryType;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> coloredNumbers;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> moreSpace;
        
        public MemoryHudWidgetConfig() {
            this.memoryType = ConfigProperty.createEnum(MemoryType.PERCENT);
            this.coloredNumbers = new ConfigProperty<Boolean>(true);
            this.moreSpace = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<MemoryType> memoryType() {
            return this.memoryType;
        }
        
        public ConfigProperty<Boolean> coloredNumbers() {
            return this.coloredNumbers;
        }
        
        public ConfigProperty<Boolean> moreSpace() {
            return this.moreSpace;
        }
    }
}
