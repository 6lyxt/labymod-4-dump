// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.text.DecimalFormat;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 3, y = 4)
public class RotationHudWidget extends TextHudWidget<RotationHudWidgetConfig>
{
    private TextLine rotationLine;
    private TextLine yawLine;
    private TextLine pitchLine;
    private DecimalFormat precisionPattern;
    
    public RotationHudWidget() {
        super("rotation", RotationHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final RotationHudWidgetConfig config) {
        super.load(config);
        this.rotationLine = super.createLine("Rotation", "0 0");
        this.yawLine = super.createLine("Yaw", 0);
        this.pitchLine = super.createLine("Pitch", 0);
        this.updateFormat();
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        this.update();
        super.render(stack, mouse, partialTicks, isEditorContext, size);
    }
    
    private void update() {
        final Entity entity = this.labyAPI.minecraft().getCameraEntity();
        float yaw = (entity == null) ? 0.0f : (entity.getRotationYaw() % 360.0f);
        final float pitch = (entity == null) ? 0.0f : entity.getRotationPitch();
        if (yaw >= 180.0f) {
            yaw -= 360.0f;
        }
        if (yaw <= -180.0f) {
            yaw += 360.0f;
        }
        final boolean hasPrecision = this.precisionPattern != null;
        final String formattedYaw = hasPrecision ? this.precisionPattern.format(yaw) : String.valueOf(Math.round(yaw));
        final String formattedPitch = hasPrecision ? this.precisionPattern.format(pitch) : String.valueOf(Math.round(pitch));
        final boolean showYaw = ((RotationHudWidgetConfig)this.config).showYaw().get();
        final boolean showPitch = ((RotationHudWidgetConfig)this.config).showPitch().get();
        if (showYaw || showPitch) {
            if (showYaw && showPitch) {
                this.rotationLine.updateAndFlush(formattedYaw + ", " + formattedPitch);
            }
            else {
                this.rotationLine.updateAndFlush(showYaw ? formattedYaw : formattedPitch);
            }
        }
        else {
            this.rotationLine.updateAndFlush("<" + I18n.getTranslation("labymod.misc.disabled", new Object[0]));
        }
        this.yawLine.updateAndFlush(formattedYaw);
        this.pitchLine.updateAndFlush(formattedPitch);
    }
    
    private void updateFormat() {
        final int digits = ((RotationHudWidgetConfig)this.config).precision().get();
        this.precisionPattern = ((digits > 0) ? new DecimalFormat("0." + "0".repeat(digits)) : null);
        this.update();
        final boolean singleLine = ((RotationHudWidgetConfig)this.config).singleLine().get();
        final boolean showYaw = ((RotationHudWidgetConfig)this.config).showYaw().get();
        final boolean showPitch = ((RotationHudWidgetConfig)this.config).showPitch().get();
        this.rotationLine.setState((singleLine || (!showYaw && !showPitch)) ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
        this.yawLine.setState((singleLine || !showYaw) ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.pitchLine.setState((singleLine || !showPitch) ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
    }
    
    public static class RotationHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> singleLine;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showYaw;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showPitch;
        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision;
        
        public RotationHudWidgetConfig() {
            this.singleLine = new ConfigProperty<Boolean>(false);
            this.showYaw = new ConfigProperty<Boolean>(true);
            this.showPitch = new ConfigProperty<Boolean>(true);
            this.precision = new ConfigProperty<Integer>(0);
        }
        
        public ConfigProperty<Integer> precision() {
            return this.precision;
        }
        
        public ConfigProperty<Boolean> singleLine() {
            return this.singleLine;
        }
        
        public ConfigProperty<Boolean> showYaw() {
            return this.showYaw;
        }
        
        public ConfigProperty<Boolean> showPitch() {
            return this.showPitch;
        }
    }
}
