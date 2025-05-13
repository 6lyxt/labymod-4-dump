// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.processors;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.pipeline.post.effects.LabyModMotionBlurCustomPostPassProcessor;
import java.util.Objects;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gfx.pipeline.post.effects.OldMotionBlurCustomPostPassProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostProcessorLoader;
import net.labymod.api.client.gfx.pipeline.post.PostEffects;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.client.gfx.pipeline.post.PostProcessor;
import net.labymod.api.util.Lazy;

public final class PostProcessors
{
    private static final Lazy<PostProcessor> MENU_BLUR_POST_PROCESSOR;
    private static final Lazy<PostProcessor> MOTION_BLUR_POST_PROCESSOR;
    private static final Lazy<PostProcessor> OLD_BLUR_POST_PROCESSOR;
    private static float currentMenuBlurProgress;
    
    private PostProcessors() {
        throw new IllegalStateException();
    }
    
    public static void resetMenuBlur() {
        PostProcessors.currentMenuBlurProgress = 0.0f;
    }
    
    public static void processMenuBlur(final MenuBlurConfig.ScreenType type, final float tickDelta) {
        final MenuBlurConfig menuBlurConfig = Laby.labyAPI().config().appearance().menuBlur();
        if (!menuBlurConfig.isMenuBlurEnabled(type)) {
            return;
        }
        final PostProcessor processor = PostProcessors.MENU_BLUR_POST_PROCESSOR.get();
        final CustomPostPassProcessor customPostPassProcessor = processor.getCustomPostPassProcessor();
        if (customPostPassProcessor instanceof final ConfigurableMenuBlurCustomPostPassProcessor pass) {
            final float currentSpeed = 20.0f;
            final float speed = 1.0f / currentSpeed;
            pass.setProgress(PostProcessors.currentMenuBlurProgress += speed);
        }
        processor.process(tickDelta);
    }
    
    public static void processMotionBlur(final float partialTicks) {
        final MotionBlurConfig.MotionBlurType type = Laby.labyAPI().config().ingame().motionBlur().motionBlurType().get();
        final PostProcessor processor = type.isOld() ? PostProcessors.OLD_BLUR_POST_PROCESSOR.get() : PostProcessors.MOTION_BLUR_POST_PROCESSOR.get();
        processor.process(partialTicks);
    }
    
    static {
        MENU_BLUR_POST_PROCESSOR = Lazy.of(() -> {
            final LabyAPI labyAPI = Laby.labyAPI();
            final PostProcessor processor = PostProcessorLoader.load(labyAPI.minecraft().mainTarget(), PostEffects.MENU_BLUR);
            processor.setCustomPostPassProcessor(new ConfigurableMenuBlurCustomPostPassProcessor(labyAPI.config().appearance().menuBlur()));
            return processor;
        });
        MOTION_BLUR_POST_PROCESSOR = Lazy.of(() -> {
            final Minecraft minecraft = Laby.labyAPI().minecraft();
            final PostProcessor load;
            final PostProcessor processor2 = load = PostProcessorLoader.load(minecraft.mainTarget(), PostEffects.LABYMOD_MOTION_BLUR);
            new(net.labymod.api.client.gfx.pipeline.post.effects.LabyModMotionBlurCustomPostPassProcessor.class)();
            final Minecraft obj;
            Objects.requireNonNull(obj);
            new LabyModMotionBlurCustomPostPassProcessor(obj::getCamera);
            final CustomPostPassProcessor customPostPassProcessor;
            load.setCustomPostPassProcessor(customPostPassProcessor);
            return processor2;
        });
        OLD_BLUR_POST_PROCESSOR = Lazy.of(() -> {
            final Minecraft minecraft2 = Laby.labyAPI().minecraft();
            final PostProcessor processor3 = PostProcessorLoader.load(minecraft2.mainTarget(), PostEffects.OLD_MOTION_BLUR);
            processor3.setCustomPostPassProcessor(new OldMotionBlurCustomPostPassProcessor());
            return processor3;
        });
    }
    
    private static class ConfigurableMenuBlurCustomPostPassProcessor implements CustomPostPassProcessor
    {
        private static final String BLUR_DIRECTION = "BlurDirection";
        private static final String RADIUS = "Radius";
        private static final String PROGRESS = "Progress";
        private final MenuBlurConfig config;
        private float progress;
        
        public ConfigurableMenuBlurCustomPostPassProcessor(final MenuBlurConfig config) {
            this.config = config;
        }
        
        @Override
        public void process(final String name, final ShaderProgram program, final float time) {
            if (name.equals("main")) {
                program.setVec2("BlurDirection", 1.0f, 0.0f);
            }
            else {
                program.setVec2("BlurDirection", 0.0f, 1.0f);
            }
            program.setFloat("Radius", this.config.strength().get() * 4.0f);
            program.setFloat("Progress", this.progress);
        }
        
        public void setProgress(final float progress) {
            this.progress = MathHelper.clamp(progress, 0.0f, 1.0f);
        }
    }
}
