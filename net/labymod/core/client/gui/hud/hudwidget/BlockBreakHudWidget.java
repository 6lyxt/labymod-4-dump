// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.Color;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import java.util.Locale;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.text.DecimalFormat;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

public class BlockBreakHudWidget extends SimpleHudWidget<BlockBreakHudWidgetConfig>
{
    private static final float TPS = 20.0f;
    private static final float DUMMY_SECONDS_REMAINING = 11.0f;
    private static final float DUMMY_PROGRESS = 0.9f;
    private final IntVector3 lastBlockPos;
    private DecimalFormat precisionPattern;
    
    public BlockBreakHudWidget() {
        super("block_break", BlockBreakHudWidgetConfig.class);
        this.lastBlockPos = new IntVector3();
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.CROSSHAIR_TOP, NamedHudWidgetDropzones.CROSSHAIR_BOTTOM);
    }
    
    @Override
    public void load(final BlockBreakHudWidgetConfig config) {
        super.load(config);
        final int digits = ((BlockBreakHudWidgetConfig)this.config).precision().get();
        this.precisionPattern = new DecimalFormat((digits > 0) ? ("0." + "0".repeat(digits)) : "0");
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final Minecraft minecraft = this.labyAPI.minecraft();
        float progress = 0.9f;
        float ticksRemaining = 220.0f;
        final BlockState blockState = this.getDestroyingBlockState();
        if (blockState != null) {
            final float hardness = blockState.getHardness(minecraft.getClientPlayer());
            progress = minecraft.getDestroyProgress();
            ticksRemaining = (1.0f - progress) / hardness;
            if (minecraft.isDestroying()) {
                this.lastBlockPos.setX(blockState.position().getX());
                this.lastBlockPos.setY(blockState.position().getY());
                this.lastBlockPos.setZ(blockState.position().getZ());
            }
        }
        final float secondsRemaining = ticksRemaining / 20.0f;
        final Component component = Component.text(String.format(Locale.ROOT, "%s s", this.precisionPattern.format(secondsRemaining)));
        final RenderableComponent renderableComponent = RenderableComponent.of(component);
        final float width = renderableComponent.getWidth();
        float height = renderableComponent.getHeight();
        final BlockBreakHudWidgetConfig.DisplayType display = ((BlockBreakHudWidgetConfig)this.config).display().get();
        final BlockBreakHudWidgetConfig.DisplayType gradientDisplay = ((BlockBreakHudWidgetConfig)this.config).gradient().applyTo().get();
        final int gradientColor = this.getGradientColor(progress);
        if (stack != null && display.isText()) {
            this.labyAPI.renderPipeline().componentRenderer().builder().useFloatingPointPosition(false).pos(0.0f, 0.0f).text(component).color((gradientDisplay.isText() && gradientColor != -1) ? gradientColor : ((BlockBreakHudWidgetConfig)this.config).color().get().get()).render(stack);
        }
        if (display.isProgressBar()) {
            if (stack != null) {
                final float progressY = display.isText() ? height : (height / 2.0f);
                Laby.labyAPI().renderPipeline().rectangleRenderer().pos(0.0f, progressY, width * progress, progressY + 1.0f).color((gradientDisplay.isProgressBar() && gradientColor != -1) ? gradientColor : ((BlockBreakHudWidgetConfig)this.config).progressBarColor().get().get()).render(stack);
            }
            if (display.isText()) {
                height += 2.0f;
            }
        }
        if (isEditorContext || blockState != null || (size.getActualWidth() == 0.0f && size.getActualHeight() == 0.0f)) {
            size.set(width, height);
        }
    }
    
    @Override
    public boolean isVisibleInGame() {
        final BlockState blockState = this.getDestroyingBlockState();
        return blockState != null && this.lastBlockPos.equals(blockState.position());
    }
    
    private BlockState getDestroyingBlockState() {
        if (this.labyAPI.minecraft().getDestroyProgress() != 0.0f) {
            final HitResult hitResult2 = this.labyAPI.minecraft().getHitResult();
            if (hitResult2 instanceof final BlockHitResult hitResult) {
                if (hitResult.type() == HitResult.HitType.BLOCK) {
                    final BlockState blockState = this.labyAPI.minecraft().clientWorld().getBlockState(hitResult.getBlockPosition());
                    if (blockState != null && !blockState.block().isAir()) {
                        return blockState;
                    }
                }
            }
        }
        return null;
    }
    
    private int getGradientColor(final float progress) {
        if (((BlockBreakHudWidgetConfig)this.config).gradient().enabled().get()) {
            final Color first = (progress < 0.5f) ? ((BlockBreakHudWidgetConfig)this.config).gradient().startColor().get() : ((BlockBreakHudWidgetConfig)this.config).gradient().middleColor().get();
            final Color second = (progress < 0.5f) ? ((BlockBreakHudWidgetConfig)this.config).gradient().middleColor().get() : ((BlockBreakHudWidgetConfig)this.config).gradient().endColor().get();
            final CubicBezier bezier = CubicBezier.EASE_IN_OUT;
            final float interpolateProgress = (progress < 0.5f) ? (progress * 2.0f) : ((progress - 0.5f) * 2.0f);
            return Color.ofRGB((int)(first.getRed() + (second.getRed() - first.getRed()) * bezier.curve(interpolateProgress)), (int)(first.getGreen() + (second.getGreen() - first.getGreen()) * bezier.curve(interpolateProgress)), (int)(first.getBlue() + (second.getBlue() - first.getBlue()) * bezier.curve(interpolateProgress))).get();
        }
        return -1;
    }
    
    public static class BlockBreakHudWidgetConfig extends HudWidgetConfig
    {
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> color;
        @ColorPickerWidget.ColorPickerSetting
        @SettingRequires("progressBar")
        private final ConfigProperty<Color> progressBarColor;
        private final GradientConfig gradient;
        @SliderWidget.SliderSetting(min = 0.0f, max = 2.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision;
        @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.block_break.display.entries")
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DisplayType> display;
        
        public BlockBreakHudWidgetConfig() {
            this.color = new ConfigProperty<Color>(Color.WHITE);
            this.progressBarColor = new ConfigProperty<Color>(Color.LIGHT_GRAY);
            this.gradient = new GradientConfig();
            this.precision = new ConfigProperty<Integer>(1);
            this.display = ConfigProperty.createEnum(DisplayType.TEXT_AND_PROGRESS_BAR);
        }
        
        public ConfigProperty<Color> color() {
            return this.color;
        }
        
        public ConfigProperty<Color> progressBarColor() {
            return this.progressBarColor;
        }
        
        public GradientConfig gradient() {
            return this.gradient;
        }
        
        public ConfigProperty<Integer> precision() {
            return this.precision;
        }
        
        public ConfigProperty<DisplayType> display() {
            return this.display;
        }
        
        public enum DisplayType
        {
            TEXT, 
            PROGRESS_BAR, 
            TEXT_AND_PROGRESS_BAR;
            
            public boolean isText() {
                return this == DisplayType.TEXT || this == DisplayType.TEXT_AND_PROGRESS_BAR;
            }
            
            public boolean isProgressBar() {
                return this == DisplayType.PROGRESS_BAR || this == DisplayType.TEXT_AND_PROGRESS_BAR;
            }
        }
        
        @SettingRequires("enabled")
        public static class GradientConfig extends Config
        {
            @ShowSettingInParent
            @SwitchWidget.SwitchSetting
            private final ConfigProperty<Boolean> enabled;
            @ColorPickerWidget.ColorPickerSetting
            private final ConfigProperty<Color> startColor;
            @ColorPickerWidget.ColorPickerSetting
            private final ConfigProperty<Color> middleColor;
            @ColorPickerWidget.ColorPickerSetting
            private final ConfigProperty<Color> endColor;
            @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.block_break.display.entries")
            @DropdownWidget.DropdownSetting
            private final ConfigProperty<DisplayType> applyTo;
            
            public GradientConfig() {
                this.enabled = new ConfigProperty<Boolean>(true);
                this.startColor = new ConfigProperty<Color>(Color.GREEN);
                this.middleColor = new ConfigProperty<Color>(Color.YELLOW);
                this.endColor = new ConfigProperty<Color>(Color.of(NamedTextColor.RED.getValue(), 255));
                this.applyTo = ConfigProperty.createEnum(DisplayType.PROGRESS_BAR);
            }
            
            public ConfigProperty<Boolean> enabled() {
                return this.enabled;
            }
            
            public ConfigProperty<Color> startColor() {
                return this.startColor;
            }
            
            public ConfigProperty<Color> middleColor() {
                return this.middleColor;
            }
            
            public ConfigProperty<Color> endColor() {
                return this.endColor;
            }
            
            public ConfigProperty<DisplayType> applyTo() {
                return this.applyTo;
            }
        }
    }
}
