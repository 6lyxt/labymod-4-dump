// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import java.util.Locale;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 1, y = 4)
public class SystemMemoryHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private final SystemInfo systemInfo;
    private TextLine memoryUsageLine;
    private long lastUpdate;
    private int lastMemoryUsage;
    
    public SystemMemoryHudWidget() {
        super("system_memory_usage");
        this.lastUpdate = -1L;
        this.systemInfo = Laby.references().systemInfo();
        this.bindCategory(HudWidgetCategory.SYSTEM);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.memoryUsageLine = super.createLine("System Memory", "?");
        this.lastUpdate = -1L;
        this.lastMemoryUsage = Integer.MIN_VALUE;
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.lastUpdate + 1000L >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getMillis();
        try {
            final float totalMemory = (float)this.systemInfo.getTotalMemorySize();
            final float freeMemory = (float)this.systemInfo.getFreeMemorySize();
            final int memoryUsage = MathHelper.ceil((1.0f - freeMemory / totalMemory) * 100.0f);
            if (this.lastMemoryUsage == memoryUsage) {
                return;
            }
            this.lastMemoryUsage = memoryUsage;
            this.memoryUsageLine.updateAndFlush(String.format(Locale.ROOT, "%s%%", memoryUsage));
        }
        catch (final AbstractMethodError abstractMethodError) {}
    }
}
