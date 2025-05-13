// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class DirectionHudWidgetDropzone extends HudWidgetDropzone
{
    public DirectionHudWidgetDropzone() {
        super("direction");
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - hudWidgetSize.getScaledWidth() / 2.0f;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final HudWidget<?> hudWidget = renderer.getRelevantHudWidgetForDropzone(NamedHudWidgetDropzones.BOSS_BAR);
        float offset = 0.0f;
        if (hudWidget != null) {
            offset += renderer.getWidget(hudWidget).scaledBounds().getHeight();
        }
        return renderer.getArea().getTop() + 2.0f + offset;
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_TOP;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new DirectionHudWidgetDropzone();
    }
}
