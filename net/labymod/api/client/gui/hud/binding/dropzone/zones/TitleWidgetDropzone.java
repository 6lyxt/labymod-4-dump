// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class TitleWidgetDropzone extends HudWidgetDropzone
{
    private static final float TITLE_OFFSET_X = 0.0f;
    private static final float TITLE_OFFSET_Y = -8.0f;
    
    public TitleWidgetDropzone() {
        super("title");
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final float x = renderer.getArea().getCenterX() - hudWidgetSize.getScaledWidth() / 2.0f;
        return x + 0.0f;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final float y = renderer.getArea().getCenterY() - hudWidgetSize.getScaledHeight() / 2.0f;
        return y - 8.0f;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new TitleWidgetDropzone();
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_TOP;
    }
}
