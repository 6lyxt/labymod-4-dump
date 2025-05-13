// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas;

import net.labymod.api.client.world.signobject.object.SignCanvasSize;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public class CanvasMeta extends SignObjectMeta<CanvasTemplate>
{
    private final SignCanvasSize size;
    
    public CanvasMeta(final CanvasTemplate template, final String[] meta, final SignCanvasSize size) {
        super(template, meta);
        this.size = size;
    }
    
    public SignCanvasSize size() {
        return this.size;
    }
    
    @Deprecated(forRemoval = true, since = "4.2.13")
    public float widthBlocks() {
        return this.size.getWidthBlocks();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.13")
    public float heightBlocks() {
        return this.size.getHeightBlocks();
    }
}
