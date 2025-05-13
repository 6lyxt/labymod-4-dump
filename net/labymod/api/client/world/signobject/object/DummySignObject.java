// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public class DummySignObject<M extends SignObjectMeta<?>> extends AbstractSignObject<M>
{
    protected DummySignObject(final M meta, final SignObjectPosition position) {
        super(meta, position);
    }
    
    @Override
    public long lastRenderTimestamp() {
        return 0L;
    }
    
    @Override
    public void render(final Stack stack, final double x, final double y, final double z, final float tickDelta) {
    }
    
    @Override
    public boolean hasRendering() {
        return false;
    }
}
