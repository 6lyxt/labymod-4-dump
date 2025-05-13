// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic;

import java.util.function.Consumer;
import net.labymod.core.util.camera.CinematicCamera;

public class ShaderCamera extends CinematicCamera
{
    private static Consumer<CinematicCamera> shaderHook;
    
    public ShaderCamera(final float fov) {
        super(fov);
    }
    
    @Override
    public void setup(final float left, final float top, final float right, final float bottom, final float partialTicks) {
        super.setup(left, top, right, bottom, partialTicks);
        if (ShaderCamera.shaderHook != null) {
            ShaderCamera.shaderHook.accept(this);
        }
    }
    
    public static void setShaderHook(final Consumer<CinematicCamera> cameraConsumer) {
        ShaderCamera.shaderHook = cameraConsumer;
    }
}
