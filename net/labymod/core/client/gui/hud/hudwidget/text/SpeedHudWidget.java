// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.Minecraft;
import java.util.Locale;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.text.DecimalFormat;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 0, y = 2)
public class SpeedHudWidget extends TextHudWidget<SpeedHudWidgetConfig>
{
    private DecimalFormat precisionPattern;
    private TextLine textLine;
    private double tickX;
    private double tickY;
    private double tickZ;
    
    public SpeedHudWidget() {
        super("speed", SpeedHudWidgetConfig.class);
    }
    
    @Override
    public void load(final SpeedHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Speed", "0 blocks/s");
        this.bindCategory(HudWidgetCategory.INGAME);
        final int digits = ((SpeedHudWidgetConfig)this.config).precision().get();
        this.precisionPattern = new DecimalFormat((digits > 0) ? ("0." + "0".repeat(digits)) : "0");
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        super.render(stack, mouse, partialTicks, isEditorContext, size);
        final double speed = this.getSpeed();
        this.textLine.updateAndFlush(String.format(Locale.ROOT, "%s blocks/s", this.precisionPattern.format(speed)));
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        final Minecraft minecraft = this.labyAPI.minecraft();
        final Entity cameraEntity = minecraft.getCameraEntity();
        if (cameraEntity == null) {
            return;
        }
        final Position position = cameraEntity.position();
        final double diffX = position.getX() - this.tickX;
        final double diffY = position.getY() - this.tickY;
        final double diffZ = position.getZ() - this.tickZ;
        if (diffX > 10.0) {
            this.tickX = position.getX();
        }
        if (diffZ > 10.0) {
            this.tickZ = position.getZ();
        }
        if (diffY > 10.0) {
            this.tickY = position.getY();
        }
        if (diffX < -10.0) {
            this.tickX = position.getX();
        }
        if (diffZ < -10.0) {
            this.tickZ = position.getZ();
        }
        if (diffY < -10.0) {
            this.tickY = position.getY();
        }
        this.tickX += diffX * 0.25;
        this.tickZ += diffZ * 0.25;
        this.tickY += diffY * 0.25;
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.getSpeed() != 0.0 || ((SpeedHudWidgetConfig)this.config).displayAtZeroSpeed().get();
    }
    
    private double getSpeed() {
        final Minecraft minecraft = this.labyAPI.minecraft();
        final Entity cameraEntity = minecraft.getCameraEntity();
        final double factor = 0.15;
        final double deltaX = (cameraEntity == null) ? 0.0 : ((cameraEntity.position().getX() - this.tickX) / factor);
        final double deltaY = (cameraEntity == null) ? 0.0 : ((cameraEntity.position().getY() - this.tickY) / factor);
        final double deltaZ = (cameraEntity == null) ? 0.0 : ((cameraEntity.position().getZ() - this.tickZ) / factor);
        return ((SpeedHudWidgetConfig)this.config).groundSpeed().get() ? Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) : Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    
    public static class SpeedHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> displayAtZeroSpeed;
        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> groundSpeed;
        
        public SpeedHudWidgetConfig() {
            this.displayAtZeroSpeed = new ConfigProperty<Boolean>(true);
            this.precision = new ConfigProperty<Integer>(2);
            this.groundSpeed = new ConfigProperty<Boolean>(false);
        }
        
        public ConfigProperty<Boolean> displayAtZeroSpeed() {
            return this.displayAtZeroSpeed;
        }
        
        public ConfigProperty<Integer> precision() {
            return this.precision;
        }
        
        public ConfigProperty<Boolean> groundSpeed() {
            return this.groundSpeed;
        }
    }
}
