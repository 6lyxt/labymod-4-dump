// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;

public class BossBarWidgetDropzone extends HudWidgetDropzone
{
    public BossBarWidgetDropzone() {
        super("boss_bar");
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getCenterX() - 91.0f;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getTop();
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new BossBarWidgetDropzone();
    }
    
    @Override
    public HudWidgetAnchor getAnchor() {
        return HudWidgetAnchor.CENTER_TOP;
    }
}
