// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model.box;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.box.ModelBoxVertex;

public class DefaultModelBoxVertex implements ModelBoxVertex
{
    private final FloatVector3 position;
    private final FloatVector2 uv;
    
    public DefaultModelBoxVertex(final float x, final float y, final float z, final float texU, final float texV) {
        this(new FloatVector3(x, y, z), texU, texV);
    }
    
    public DefaultModelBoxVertex(final FloatVector3 position, final float textureU, final float textureV) {
        this(position, new FloatVector2(textureU, textureV));
    }
    
    public DefaultModelBoxVertex(final FloatVector3 position, final FloatVector2 uv) {
        this.position = position;
        this.uv = uv;
    }
    
    @Override
    public ModelBoxVertex remap(final float u, final float v) {
        return this.remap(new FloatVector2(u, v));
    }
    
    @Override
    public ModelBoxVertex remap(final FloatVector2 uv) {
        return new DefaultModelBoxVertex(this.position, uv);
    }
    
    @Override
    public FloatVector3 getPosition() {
        return this.position;
    }
    
    @Override
    public FloatVector2 getUV() {
        return this.uv;
    }
}
