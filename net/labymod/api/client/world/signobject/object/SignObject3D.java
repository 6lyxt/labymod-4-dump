// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public class SignObject3D<M extends SignObjectMeta<?>> extends AbstractSignObject<M>
{
    private SignObject3DRenderer<M> renderer;
    private long lastRenderTimestamp;
    
    protected SignObject3D(final M meta, final SignObjectPosition position) {
        super(meta, position);
    }
    
    public void set3DRenderer(final SignObject3DRenderer<M> renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public long lastRenderTimestamp() {
        return this.lastRenderTimestamp;
    }
    
    @Override
    public void render(final Stack stack, final double x, final double y, final double z, final float tickDelta) {
        if (this.renderer == null) {
            return;
        }
        this.lastRenderTimestamp = TimeUtil.getCurrentTimeMillis();
        this.renderer.render3D(stack, this, x, y, z, tickDelta);
    }
    
    @Override
    public boolean hasRendering() {
        return true;
    }
}
