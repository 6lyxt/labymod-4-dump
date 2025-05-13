// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import java.util.Locale;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class ScoreboardHudWidgetDropzone extends HudWidgetDropzone
{
    private final boolean rightBound;
    
    public ScoreboardHudWidgetDropzone(final boolean rightBound) {
        super(String.format(Locale.ROOT, "scoreboard_%s", rightBound ? "right" : "left"));
        this.rightBound = rightBound;
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final Rectangle area = renderer.getArea();
        return this.rightBound ? (area.getRight() - hudWidgetSize.getScaledWidth()) : area.getLeft();
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterY() - 9.0f - hudWidgetSize.getScaledHeight() / 2.0f;
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return this.rightBound ? HudWidgetAnchor.RIGHT_TOP : HudWidgetAnchor.LEFT_TOP;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new ScoreboardHudWidgetDropzone(this.rightBound);
    }
}
