// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas;

import net.labymod.api.Laby;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.client.world.signobject.object.SignObject;

public interface Canvas extends SignObject<CanvasMeta>
{
    public static final float PIXEL_PER_BLOCK = 15.882353f;
    
    default float getWidth() {
        final CanvasMeta meta = this.meta();
        final float width = meta.size().getWidth();
        if (width == 0.0f) {
            return meta.template().calculateWidth(meta.size().getHeight());
        }
        return width;
    }
    
    default float getHeight() {
        final CanvasMeta meta = this.meta();
        final float height = meta.size().getHeight();
        if (height == 0.0f) {
            return meta.template().calculateHeight(meta.size().getWidth());
        }
        return height;
    }
    
    CanvasRenderer renderer();
    
    void setRenderer(final CanvasRenderer p0);
    
    default Canvas createCanvas(final CanvasMeta meta, final SignObjectPosition position) {
        return Laby.references().canvasFactory().createCanvas(meta, position);
    }
}
