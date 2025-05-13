// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import java.util.Locale;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.lang.management.ManagementFactory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import com.sun.management.OperatingSystemMXBean;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 7, y = 2)
public class SystemCpuHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private final OperatingSystemMXBean osBean;
    private TextLine cpuUsageLine;
    private long lastUpdate;
    private int lastCpuUsage;
    
    public SystemCpuHudWidget() {
        super("system_cpu_usage");
        this.lastUpdate = -1L;
        this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        this.bindCategory(HudWidgetCategory.SYSTEM);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.cpuUsageLine = super.createLine("CPU", "?");
        this.lastUpdate = -1L;
        this.lastCpuUsage = Integer.MIN_VALUE;
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        if (this.lastUpdate + 1000L >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getMillis();
        final int cpuUsage = MathHelper.ceil(this.osBean.getSystemCpuLoad() * 100.0);
        if (this.lastCpuUsage == cpuUsage) {
            return;
        }
        this.lastCpuUsage = cpuUsage;
        this.cpuUsageLine.updateAndFlush(String.format(Locale.ROOT, "%s%%", cpuUsage));
    }
}
