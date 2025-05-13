// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class SaturationHudWidgetDropzone extends HudWidgetDropzone
{
    public SaturationHudWidgetDropzone() {
        super("saturation");
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() + 10.0f;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getBottom() - 38.0f - 10.0f;
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_BOTTOM;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new SaturationHudWidgetDropzone();
    }
}
