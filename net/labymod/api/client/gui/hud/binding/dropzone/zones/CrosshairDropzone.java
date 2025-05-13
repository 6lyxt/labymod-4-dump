// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class CrosshairDropzone extends HudWidgetDropzone
{
    private static final float CROSSHAIR_OFFSET = 8.0f;
    private final boolean top;
    
    public CrosshairDropzone(final boolean top) {
        super("crosshair_" + (top ? "top" : "bottom"));
        this.top = top;
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - hudWidgetSize.getScaledWidth() / 2.0f;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterY() + (this.top ? (-(8.0f + hudWidgetSize.getScaledHeight())) : 8.0f);
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new CrosshairDropzone(this.top);
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return this.top ? HudWidgetAnchor.CENTER_TOP : HudWidgetAnchor.CENTER_BOTTOM;
    }
}
