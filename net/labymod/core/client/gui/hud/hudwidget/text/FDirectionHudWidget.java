// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import java.util.List;
import java.util.Arrays;
import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 6)
public class FDirectionHudWidget extends TextHudWidget<FDirectionHudWidgetConfig>
{
    private TextLine directionTextLine;
    private float lastYaw;
    
    public FDirectionHudWidget() {
        super("fdirection", FDirectionHudWidgetConfig.class);
    }
    
    @Override
    public void load(final FDirectionHudWidgetConfig config) {
        super.load(config);
        this.directionTextLine = super.createLine("F", 0);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.lastYaw = 0.0f;
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            return;
        }
        final float yaw = player.getRotationYaw();
        final float f = this.getFValue(yaw);
        if (this.lastYaw == f) {
            return;
        }
        this.lastYaw = f;
        this.directionTextLine.updateAndFlush(this.createComponent(f));
    }
    
    private Component createComponent(final float fValue) {
        Component component = Component.empty();
        if (this.getConfig().directionNumber().get()) {
            component = component.append(Component.text(String.format(Locale.ROOT, "%.1f ", fValue)));
        }
        final Boolean cardinalPoints = this.getConfig().cardinalPoints().get();
        final Boolean xzDirection = this.getConfig().xzDirection().get();
        if (cardinalPoints || xzDirection) {
            final XZDRange range = XZDRange.getRangeByF(fValue);
            final List<XZDRange.DisplayedDirection> displayedDirections = Arrays.asList(range.getDisplayed());
            if (cardinalPoints) {
                component = this.appendCardinalPoints(component, displayedDirections, this.getConfig().shortenNames().get());
            }
            if (xzDirection) {
                component = this.appendXZDirection(component, displayedDirections);
            }
        }
        return component;
    }
    
    private Component appendXZDirection(Component component, final List<XZDRange.DisplayedDirection> displayedDirections) {
        Component directionComponent = Component.empty();
        final int valueColor = this.getConfig().valueColor().get().get();
        for (int i = 0; i < displayedDirections.size(); ++i) {
            if (i > 0) {
                directionComponent = directionComponent.append(Component.text(", ", TextColor.color(valueColor)));
            }
            directionComponent = directionComponent.append(Component.text(displayedDirections.get(i).getText(), TextColor.color(valueColor)));
        }
        final Formatting formatting = this.getConfig().formatting().get();
        final boolean enclosed = formatting.isEnclosed();
        if (enclosed) {
            directionComponent = formatting.build(directionComponent, Component.empty(), !this.anchor().isRight(), this.getConfig().bracketColor().get().get());
        }
        else {
            component = component.append(Component.space());
        }
        component = component.append(Component.text(" "));
        component = component.append(directionComponent);
        return component;
    }
    
    private float getFValue(final float yaw) {
        float f = MathHelper.wrapDegrees(yaw);
        if (f <= 0.0f) {
            f += 360.0f;
        }
        f /= 88.0f;
        f = (float)(Math.round(f * 10.0) / 10.0);
        if (f >= 4.0f) {
            f = 0.0f;
        }
        return f;
    }
    
    private Component appendCardinalPoints(Component component, final List<XZDRange.DisplayedDirection> list, final boolean isShortname) {
        for (int i = 0; i < list.size(); ++i) {
            String text = "";
            if (i > 0) {
                text = text;
            }
            text += list.get(i).getName(isShortname);
            component = component.append(Component.text(text));
        }
        return component;
    }
    
    enum XZDRange
    {
        ZPLUS(0.0, 0.3, new DisplayedDirection[] { DisplayedDirection.ZPLUS }), 
        ZPLUS_XMINUS(0.3, 0.8, new DisplayedDirection[] { DisplayedDirection.ZPLUS, DisplayedDirection.XMINUS }), 
        XMINUS(0.8, 1.4, new DisplayedDirection[] { DisplayedDirection.XMINUS }), 
        XMINUS_ZMINUS(1.4, 1.8, new DisplayedDirection[] { DisplayedDirection.ZMINUS, DisplayedDirection.XMINUS }), 
        ZMINUS(1.8, 2.4, new DisplayedDirection[] { DisplayedDirection.ZMINUS }), 
        ZMINUS_XPLUS(2.4, 2.8, new DisplayedDirection[] { DisplayedDirection.ZMINUS, DisplayedDirection.XPLUS }), 
        XPLUS(2.8, 3.4, new DisplayedDirection[] { DisplayedDirection.XPLUS }), 
        XPLUS_ZPLUS(3.4, 3.8, new DisplayedDirection[] { DisplayedDirection.ZPLUS, DisplayedDirection.XPLUS }), 
        ZPLUS_2(3.8, 4.0, new DisplayedDirection[] { DisplayedDirection.ZPLUS });
        
        private final double min;
        private final double max;
        private final DisplayedDirection[] displayed;
        
        private XZDRange(final double min, final double max, final DisplayedDirection[] displayed) {
            this.min = min;
            this.max = max;
            this.displayed = displayed;
        }
        
        public static XZDRange getRangeByF(final double f) {
            if (f >= 4.0) {
                return XZDRange.ZPLUS_2;
            }
            for (final XZDRange range : values()) {
                if (f >= range.min && f < range.max) {
                    return range;
                }
            }
            return XZDRange.ZPLUS;
        }
        
        public DisplayedDirection[] getDisplayed() {
            return this.displayed;
        }
        
        enum DisplayedDirection
        {
            XPLUS("X+", "East", "E"), 
            XMINUS("X-", "West", "W"), 
            ZMINUS("Z-", "North", "N"), 
            ZPLUS("Z+", "South", "S");
            
            private final String text;
            private final String longName;
            private final String shortName;
            
            private DisplayedDirection(final String text, final String longName, final String shortName) {
                this.text = text;
                this.longName = longName;
                this.shortName = shortName;
            }
            
            public String getText() {
                return this.text;
            }
            
            public String getName(final boolean isShortName) {
                return isShortName ? this.shortName : this.longName;
            }
        }
    }
    
    public static class FDirectionHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> directionNumber;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> cardinalPoints;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> xzDirection;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> shortenNames;
        
        public FDirectionHudWidgetConfig() {
            this.directionNumber = new ConfigProperty<Boolean>(true);
            this.cardinalPoints = new ConfigProperty<Boolean>(true);
            this.xzDirection = new ConfigProperty<Boolean>(true);
            this.shortenNames = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<Boolean> directionNumber() {
            return this.directionNumber;
        }
        
        public ConfigProperty<Boolean> cardinalPoints() {
            return this.cardinalPoints;
        }
        
        public ConfigProperty<Boolean> xzDirection() {
            return this.xzDirection;
        }
        
        public ConfigProperty<Boolean> shortenNames() {
            return this.shortenNames;
        }
    }
}
