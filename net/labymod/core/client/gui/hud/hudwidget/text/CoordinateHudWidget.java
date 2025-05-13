// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Objects;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.MathHelper;
import java.util.Locale;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import java.text.DecimalFormat;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 5)
public class CoordinateHudWidget extends TextHudWidget<CoordinateHudWidgetConfig>
{
    private static final String XYZ_FORMAT = "%s %s %s";
    private DecimalFormat precisionPattern;
    private TextLine xyzLine;
    private CoordinateTextLine xLine;
    private CoordinateTextLine yLine;
    private CoordinateTextLine zLine;
    private BiomeTextLine biomeLine;
    
    public CoordinateHudWidget() {
        super("coordinates", CoordinateHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final CoordinateHudWidgetConfig config) {
        super.load(config);
        this.xyzLine = this.createLine("XYZ", String.format(Locale.ROOT, "%s %s %s", 0, 0, 0));
        this.xLine = this.createLine("X", 0, CoordinateTextLine::new);
        this.yLine = this.createLine("Y", 0, CoordinateTextLine::new);
        this.zLine = this.createLine("Z", 0, CoordinateTextLine::new);
        this.biomeLine = this.createLine("Biome", "Plains", BiomeTextLine::new);
        this.updateFormat();
    }
    
    private void update() {
        final Entity cameraEntity = this.labyAPI.minecraft().getCameraEntity();
        final boolean inGame = cameraEntity != null;
        final double x = inGame ? cameraEntity.position().getX() : 3141.59265359;
        final double y = inGame ? cameraEntity.position().getY() : 64.0;
        final double z = inGame ? cameraEntity.position().getZ() : -31.4159265359;
        final boolean useBlockCoordinate = ((CoordinateHudWidgetConfig)this.config).blockCoordinate().get();
        final boolean hasPrecision = this.precisionPattern != null;
        final String formattedX = this.getCoordinate(hasPrecision, useBlockCoordinate, x);
        final String formattedY = this.getCoordinate(hasPrecision, useBlockCoordinate, y);
        final String formattedZ = this.getCoordinate(hasPrecision, useBlockCoordinate, z);
        if (((CoordinateHudWidgetConfig)this.config).singleLine().get()) {
            this.xyzLine.updateAndFlush(String.format(Locale.ROOT, "%s %s %s", formattedX, formattedY, formattedZ));
        }
        else {
            this.xLine.updateAndFlush(formattedX);
            this.yLine.updateAndFlush(formattedY);
            this.zLine.updateAndFlush(formattedZ);
            if (((CoordinateHudWidgetConfig)this.config).compass.get()) {
                final float yaw = inGame ? (MathHelper.wrapDegrees(cameraEntity.getRotationYaw()) + 180.0f) : 0.0f;
                final boolean xPositive = yaw > 0.0f && yaw < 180.0f;
                final boolean zPositive = yaw > 90.0f && yaw < 270.0f;
                final float margin = 10.0f;
                final boolean axisX = (yaw > margin && yaw < 180.0f - margin) || (yaw > 180.0f + margin && yaw < 360.0f - margin);
                final boolean axisZ = (yaw > 90.0f + margin && yaw < 270.0f - margin) || yaw > 270.0f + margin || yaw < 90.0f - margin;
                String direction;
                if (yaw >= 337.5 || yaw < 22.5) {
                    direction = "N";
                }
                else if (yaw >= 22.5 && yaw < 67.5) {
                    direction = "N/E";
                }
                else if (yaw >= 67.5 && yaw < 112.5) {
                    direction = "E";
                }
                else if (yaw >= 112.5 && yaw < 157.5) {
                    direction = "S/E";
                }
                else if (yaw >= 157.5 && yaw < 202.5) {
                    direction = "S";
                }
                else if (yaw >= 202.5 && yaw < 247.5) {
                    direction = "S/W";
                }
                else if (yaw >= 247.5 && yaw < 292.5) {
                    direction = "W";
                }
                else if (yaw >= 292.5 && yaw < 337.5) {
                    direction = "N/W";
                }
                else {
                    direction = "?";
                }
                final TextColor labelColor = TextColor.color(((CoordinateHudWidgetConfig)this.config).labelColor().get().get());
                final TextColor bracketColor = TextColor.color(((CoordinateHudWidgetConfig)this.config).bracketColor().get().get());
                final TextComponent xComponent = Component.text(axisX ? (xPositive ? "+" : "-") : "", bracketColor);
                final TextComponent zComponent = Component.text(axisZ ? (zPositive ? "+" : "-") : "", bracketColor);
                final TextComponent yComponent = Component.text(direction, labelColor);
                this.xLine.updateSuffixAndFlush(xComponent);
                this.yLine.updateSuffixAndFlush(yComponent);
                this.zLine.updateSuffixAndFlush(zComponent);
            }
        }
        if (((CoordinateHudWidgetConfig)this.config).biome.get()) {
            final String biome = inGame ? this.labyAPI.minecraft().clientWorld().getReadableBiomeName() : "Plains";
            this.biomeLine.updateAndFlush(biome);
        }
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        this.update();
    }
    
    private String getCoordinate(final boolean hasPrecision, final boolean useBlockCoordinate, final double value) {
        if (hasPrecision) {
            return this.precisionPattern.format(value);
        }
        if (useBlockCoordinate) {
            return String.valueOf(MathHelper.floor(value));
        }
        return String.valueOf((int)value);
    }
    
    private void updateFormat() {
        final int digits = ((CoordinateHudWidgetConfig)this.config).precision().get();
        this.precisionPattern = ((digits > 0) ? new DecimalFormat("0." + "0".repeat(digits)) : null);
        this.update();
        final boolean singleLine = ((CoordinateHudWidgetConfig)this.config).singleLine().get();
        this.xyzLine.setState(singleLine ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
        this.xLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.yLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.zLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.biomeLine.setState(((boolean)((CoordinateHudWidgetConfig)this.config).biome.get()) ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
    }
    
    public static class CoordinateHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> singleLine;
        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> blockCoordinate;
        @SettingRequires(value = "singleLine", invert = true)
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> compass;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> biome;
        
        public CoordinateHudWidgetConfig() {
            this.singleLine = new ConfigProperty<Boolean>(false);
            this.precision = new ConfigProperty<Integer>(0);
            this.blockCoordinate = new ConfigProperty<Boolean>(true);
            this.compass = new ConfigProperty<Boolean>(false);
            this.biome = new ConfigProperty<Boolean>(false);
        }
        
        public ConfigProperty<Boolean> singleLine() {
            return this.singleLine;
        }
        
        public ConfigProperty<Integer> precision() {
            return this.precision;
        }
        
        public ConfigProperty<Boolean> blockCoordinate() {
            return this.blockCoordinate;
        }
        
        public ConfigProperty<Boolean> compass() {
            return this.compass;
        }
        
        public ConfigProperty<Boolean> biome() {
            return this.biome;
        }
    }
    
    private static class CoordinateTextLine extends TextLine
    {
        private RenderableComponent suffixRenderableComponent;
        private Component suffixComponent;
        
        public CoordinateTextLine(final TextHudWidget<?> hudWidget, final Component key, final Object value) {
            super(hudWidget, key, value);
        }
        
        @Override
        public void renderLine(final Stack stack, final float x, final float y, final float space, final HudSize hudWidgetSize) {
            final boolean hasCompass = this.config().compass().get();
            if (hasCompass && this.suffixComponent != null && this.suffixRenderableComponent != null) {
                super.renderLine(stack, 0.0f, y, space, hudWidgetSize);
                final float suffixX = hudWidgetSize.getActualWidth() - space * 2.0f - this.suffixRenderableComponent.getWidth();
                CoordinateTextLine.BUILDER.pos(suffixX, y).shadow(true).text(this.suffixRenderableComponent).useFloatingPointPosition(this.floatingPointPosition).render(stack);
            }
            else {
                super.renderLine(stack, x, y, space, hudWidgetSize);
            }
        }
        
        public void updateSuffixAndFlush(final Component suffix) {
            if (Objects.equals(this.suffixComponent, suffix)) {
                return;
            }
            this.suffixComponent = suffix;
            this.suffixRenderableComponent = RenderableComponent.of(this.suffixComponent);
        }
        
        @Override
        protected boolean isLeftAligned() {
            return this.config().compass().get() || super.isLeftAligned();
        }
        
        @Override
        public float getWidth() {
            final float padding = this.config().compass().get() ? 25.0f : 0.0f;
            return super.getWidth() + padding;
        }
    }
    
    private static class BiomeTextLine extends TextLine
    {
        public BiomeTextLine(final TextHudWidget<?> hudWidget, final Component key, final Object value) {
            super(hudWidget, key, value);
        }
        
        @Override
        protected void flushInternal() {
            final TextHudWidgetConfig config = this.hudWidget.getConfig();
            final TextColor bracketColor = TextColor.color(config.bracketColor().get().get());
            final Component valueComponent = this.updateColor(this.valueComponent, bracketColor);
            this.renderableComponent = RenderableComponent.builder().disableCache().format(valueComponent).disableWidthCaching();
        }
        
        @Override
        protected boolean isLeftAligned() {
            return this.config().compass().get() || super.isLeftAligned();
        }
    }
}
