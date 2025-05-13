// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class ActionBarWidgetDropzone extends HudWidgetDropzone
{
    public ActionBarWidgetDropzone() {
        super("action_bar");
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - hudWidgetSize.getScaledWidth() / 2.0f;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final float padding = 2.0f;
        return renderer.getArea().getHeight() - 72.0f - padding;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new ActionBarWidgetDropzone();
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_BOTTOM;
    }
}
