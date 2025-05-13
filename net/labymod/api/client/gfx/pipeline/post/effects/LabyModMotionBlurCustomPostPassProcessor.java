// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.effects;

import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.world.MinecraftCamera;
import java.util.function.Supplier;
import net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor;

public final class LabyModMotionBlurCustomPostPassProcessor implements CustomPostPassProcessor
{
    private final Supplier<MinecraftCamera> camera;
    private float prevPitch;
    private float prevYaw;
    
    public LabyModMotionBlurCustomPostPassProcessor(final MinecraftCamera camera) {
        this(() -> camera);
    }
    
    public LabyModMotionBlurCustomPostPassProcessor(final Supplier<MinecraftCamera> camera) {
        this.camera = camera;
    }
    
    @Override
    public void process(final String name, final ShaderProgram program, final float time) {
        if (!name.equals("main")) {
            return;
        }
        final MinecraftCamera camera = this.camera.get();
        if (camera == null) {
            return;
        }
        final float yaw = Math.abs(camera.getYaw());
        float horizontalStrength = this.prevYaw - yaw;
        this.prevYaw = yaw;
        horizontalStrength /= 50.0f;
        final float pitch = Math.abs(camera.getPitch());
        float verticalStrength = this.prevPitch - pitch;
        this.prevPitch = pitch;
        verticalStrength /= 20.0f;
        final boolean hasCameraMovement = horizontalStrength != 0.0f && verticalStrength != 0.0f;
        final MotionBlurConfig motionBlurConfig = Laby.labyAPI().config().ingame().motionBlur();
        program.setFloat("BlurEnabled", hasCameraMovement ? 1.0f : 0.0f);
        program.setVec2("BlurDirection", horizontalStrength, verticalStrength);
        program.setInt("BlurQuality", motionBlurConfig.blurQuality().get());
        program.setFloat("BlurStrength", motionBlurConfig.blurStrength().get() / 100.0f);
    }
}
