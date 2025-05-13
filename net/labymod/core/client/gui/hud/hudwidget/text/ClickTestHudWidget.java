// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.screen.key.tracker.RealTimeKeyTracker;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.screen.key.tracker.KeyTracker;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@SpriteSlot(x = 0, y = 4)
public class ClickTestHudWidget extends TextHudWidget<ClickTestHudWidgetConfig>
{
    private TextLine singleLine;
    private TextLine leftLine;
    private TextLine rightLine;
    private KeyTracker leftTracker;
    private KeyTracker rightTracker;
    
    public ClickTestHudWidget() {
        super("click_test", ClickTestHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final ClickTestHudWidgetConfig config) {
        super.load(config);
        this.createTrackers();
        this.singleLine = super.createLine("Clicks", "0 | 0");
        this.leftLine = super.createLine("Left Clicks", 0);
        this.rightLine = super.createLine("Right Clicks", 0);
        final boolean singleLine = config.singleLine.get();
        final boolean displayAtZero = config.displayAtZeroSpeed.get();
        this.singleLine.setState(singleLine ? (displayAtZero ? TextLine.State.VISIBLE : TextLine.State.HIDDEN) : TextLine.State.DISABLED);
        this.leftLine.setState(singleLine ? TextLine.State.DISABLED : (displayAtZero ? TextLine.State.VISIBLE : TextLine.State.HIDDEN));
        this.rightLine.setState(singleLine ? TextLine.State.DISABLED : (displayAtZero ? TextLine.State.VISIBLE : TextLine.State.HIDDEN));
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        final int leftClicks = this.leftTracker.getClicksPerSecond();
        final int rightClicks = this.rightTracker.getClicksPerSecond();
        boolean updated = this.leftLine.updateAndFlush(leftClicks);
        if (this.rightLine.updateAndFlush(rightClicks)) {
            updated = true;
        }
        if (updated) {
            this.singleLine.updateAndFlush(leftClicks + " | " + rightClicks);
        }
        final boolean singleLine = ((ClickTestHudWidgetConfig)this.config).singleLine.get();
        if (((ClickTestHudWidgetConfig)this.config).displayAtZeroSpeed.get()) {
            this.singleLine.setState(singleLine ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
            this.leftLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
            this.rightLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
            return;
        }
        this.leftLine.setState(singleLine ? TextLine.State.DISABLED : ((leftClicks == 0) ? TextLine.State.HIDDEN : TextLine.State.VISIBLE));
        this.rightLine.setState(singleLine ? TextLine.State.DISABLED : ((rightClicks == 0) ? TextLine.State.HIDDEN : TextLine.State.VISIBLE));
        this.singleLine.setState(singleLine ? ((leftClicks == 0 && rightClicks == 0) ? TextLine.State.HIDDEN : TextLine.State.VISIBLE) : TextLine.State.DISABLED);
    }
    
    @Override
    public boolean isVisibleInGame() {
        this.leftTracker.update();
        this.rightTracker.update();
        return ((ClickTestHudWidgetConfig)this.config).displayAtZeroSpeed.get() || this.leftTracker.getClicksPerSecond() != 0 || this.rightTracker.getClicksPerSecond() != 0;
    }
    
    @Subscribe
    public void onMouseButton(final MouseButtonEvent event) {
        if (event.action() != MouseButtonEvent.Action.CLICK || !this.isEnabled()) {
            return;
        }
        final MouseButton button = event.button();
        if (button == MouseButton.LEFT) {
            this.leftTracker.press();
        }
        else if (button == MouseButton.RIGHT) {
            this.rightTracker.press();
        }
    }
    
    private void createTrackers() {
        this.leftTracker = RealTimeKeyTracker.of(MouseButton.LEFT);
        this.rightTracker = RealTimeKeyTracker.of(MouseButton.RIGHT);
    }
    
    public static class ClickTestHudWidgetConfig extends TextHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> displayAtZeroSpeed;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> singleLine;
        
        public ClickTestHudWidgetConfig() {
            this.displayAtZeroSpeed = new ConfigProperty<Boolean>(true);
            this.singleLine = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<Boolean> displayAtZeroSpeed() {
            return this.displayAtZeroSpeed;
        }
        
        public ConfigProperty<Boolean> singleLine() {
            return this.singleLine;
        }
    }
}
