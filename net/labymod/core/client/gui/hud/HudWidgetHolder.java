// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud;

import java.util.HashSet;
import net.labymod.core.event.addon.lifecycle.AddonStateChangeEvent;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import java.util.Set;

public class HudWidgetHolder
{
    private final String namespace;
    private final Set<HudWidget<?>> hudWidgets;
    private AddonStateChangeEvent.State state;
    
    public HudWidgetHolder(final String namespace, final boolean enabled) {
        this.namespace = namespace;
        this.hudWidgets = new HashSet<HudWidget<?>>();
        this.state = (enabled ? AddonStateChangeEvent.State.ENABLED : AddonStateChangeEvent.State.DISABLED);
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public void register(final HudWidget<?> hudWidget) {
        this.hudWidgets.add(hudWidget);
    }
    
    public Set<HudWidget<?>> getHudWidgets() {
        return this.hudWidgets;
    }
    
    public void updateState(final AddonStateChangeEvent.State state) {
        this.state = state;
    }
    
    public AddonStateChangeEvent.State state() {
        return this.state;
    }
}
