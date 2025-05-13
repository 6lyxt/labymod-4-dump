// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.Component;
import java.util.Locale;
import net.labymod.api.util.Color;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 7, y = 3)
public class AfkTimerHudWidget extends TextHudWidget<AfkTimeHudWidgetConfig>
{
    private TextLine textLine;
    private int lastMouseX;
    private int lastMouseY;
    private long timeLastMovement;
    private long timeLastIdle;
    private long lastDurationInSeconds;
    private boolean lastWarning;
    
    public AfkTimerHudWidget() {
        super("afk_timer", AfkTimeHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final AfkTimeHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("AFK", "00:00");
        this.timeLastIdle = TimeUtil.getMillis();
        this.timeLastMovement = TimeUtil.getMillis();
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final Minecraft minecraft = this.labyAPI.minecraft();
        final int currentMouseX = minecraft.absoluteMouse().getX();
        final int currentMouseY = minecraft.absoluteMouse().getY();
        if (currentMouseX == this.lastMouseX && currentMouseY == this.lastMouseY) {
            if (this.isIdle()) {
                this.timeLastIdle = TimeUtil.getMillis();
            }
        }
        else {
            this.timeLastMovement = TimeUtil.getMillis();
        }
        this.lastMouseX = currentMouseX;
        this.lastMouseY = currentMouseY;
        if (!this.isVisibleInGame()) {
            return;
        }
        final boolean warning = this.isWarning();
        final long currentSeconds = warning ? this.lastDurationInSeconds : ((TimeUtil.getMillis() - this.timeLastMovement) / 1000L);
        if (this.lastDurationInSeconds == currentSeconds && this.lastWarning == warning) {
            return;
        }
        this.lastWarning = warning;
        this.lastDurationInSeconds = currentSeconds;
        final long minutes = currentSeconds / 60L;
        final long seconds = currentSeconds % 60L;
        final int valueColor = this.isWarning() ? this.getConfig().warningColor().get().get() : this.getConfig().valueColor().get().get();
        final String time = String.format(Locale.ROOT, "%02d:%02d", minutes, seconds);
        final Component component = ((BaseComponent<Component>)Component.text(time)).color(TextColor.color(valueColor));
        this.textLine.updateAndFlush(component);
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.isIdle() || this.isWarning();
    }
    
    private boolean isIdle() {
        return this.timeLastMovement < TimeUtil.getMillis() - ((AfkTimeHudWidgetConfig)this.config).idleTime().get() * 1000;
    }
    
    private boolean isWarning() {
        return !this.isIdle() && this.timeLastIdle > TimeUtil.getMillis() - 3000L;
    }
    
    @Subscribe
    public void keyPress(final KeyEvent event) {
        this.timeLastMovement = TimeUtil.getMillis();
    }
    
    public static class AfkTimeHudWidgetConfig extends TextHudWidgetConfig
    {
        @SliderWidget.SliderSetting(steps = 1.0f, min = 3.0f, max = 1800.0f)
        private final ConfigProperty<Integer> idleTime;
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> warningColor;
        
        public AfkTimeHudWidgetConfig() {
            this.idleTime = new ConfigProperty<Integer>(20);
            this.warningColor = new ConfigProperty<Color>(Color.of(-43691));
        }
        
        public ConfigProperty<Integer> idleTime() {
            return this.idleTime;
        }
        
        public ConfigProperty<Color> warningColor() {
            return this.warningColor;
        }
    }
}
