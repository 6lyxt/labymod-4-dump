// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.effects;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor;

public final class OldMotionBlurCustomPostPassProcessor implements CustomPostPassProcessor
{
    @Override
    public void process(final String name, final ShaderProgram program, final float time) {
        if (!name.equals("main")) {
            return;
        }
        final MotionBlurConfig motionBlurConfig = Laby.labyAPI().config().ingame().motionBlur();
        final ConfigProperty<MotionBlurConfig.MotionBlurType> typeProperty = motionBlurConfig.motionBlurType();
        final MotionBlurConfig.MotionBlurType type = typeProperty.get();
        program.setVec3("BlurStrength", type.clamp(motionBlurConfig.blurStrength().get() / 100.0f), (type == MotionBlurConfig.MotionBlurType.MAX) ? 1.0f : 0.0f, ((boolean)motionBlurConfig.enabled().get()) ? 1.0f : 0.0f);
    }
}
