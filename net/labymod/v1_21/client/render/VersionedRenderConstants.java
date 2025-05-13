// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.render;

import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.RenderConstants;

@Singleton
@Implements(RenderConstants.class)
public class VersionedRenderConstants implements RenderConstants
{
    private static final float MODEL_SCALE = 16.0f;
    private static final float NAME_TAG_SCALE = 0.025f;
    private static final float CAMERA_MOVEMENT_SCALE = -0.6f;
    
    @Inject
    public VersionedRenderConstants() {
    }
    
    @Override
    public float modelScale() {
        return 16.0f;
    }
    
    @Override
    public float nameTagScale() {
        return 0.025f;
    }
    
    @Override
    public float cameraMovementScale() {
        return -0.6f;
    }
}
