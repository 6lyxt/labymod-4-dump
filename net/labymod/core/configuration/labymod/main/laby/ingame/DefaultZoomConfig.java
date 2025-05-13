// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomTransitionConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.core.configuration.labymod.main.laby.ingame.zoom.DefaultZoomTransitionConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.zoom.DefaultZoomCinematicConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultZoomConfig extends Config implements ZoomConfig
{
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @SpriteSlot(y = 5)
    private final ConfigProperty<Key> zoomKey;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 5)
    private final ConfigProperty<Boolean> zoomHold;
    @SpriteSlot(x = 2, y = 5)
    private DefaultZoomCinematicConfig zoomCinematic;
    @SpriteSlot(y = 6)
    private DefaultZoomTransitionConfig zoomTransition;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 5, y = 5)
    private final ConfigProperty<Boolean> shouldRenderFirstPersonHand;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 5)
    private final ConfigProperty<Boolean> scrollToZoom;
    @SliderWidget.SliderSetting(min = 0.6f, max = 10.0f, steps = 0.1f)
    @SpriteSlot(x = 7, y = 5)
    private final ConfigProperty<Double> zoomDistance;
    
    public DefaultZoomConfig() {
        this.zoomKey = new ConfigProperty<Key>(Key.C);
        this.zoomHold = new ConfigProperty<Boolean>(true);
        this.zoomCinematic = new DefaultZoomCinematicConfig();
        this.zoomTransition = new DefaultZoomTransitionConfig();
        this.shouldRenderFirstPersonHand = new ConfigProperty<Boolean>(true);
        this.scrollToZoom = new ConfigProperty<Boolean>(false);
        this.zoomDistance = new ConfigProperty<Double>(4.0);
    }
    
    @Override
    public ConfigProperty<Key> zoomKey() {
        return this.zoomKey;
    }
    
    @Override
    public ConfigProperty<Boolean> zoomHold() {
        return this.zoomHold;
    }
    
    @Override
    public ZoomCinematicConfig zoomCinematic() {
        return this.zoomCinematic;
    }
    
    @Override
    public ZoomTransitionConfig zoomTransition() {
        return this.zoomTransition;
    }
    
    @Override
    public ConfigProperty<Boolean> shouldRenderFirstPersonHand() {
        return this.shouldRenderFirstPersonHand;
    }
    
    @Override
    public ConfigProperty<Boolean> scrollToZoom() {
        return this.scrollToZoom;
    }
    
    @Override
    public ConfigProperty<Double> zoomDistance() {
        return this.zoomDistance;
    }
    
    @Override
    public int getConfigVersion() {
        return 2;
    }
}
