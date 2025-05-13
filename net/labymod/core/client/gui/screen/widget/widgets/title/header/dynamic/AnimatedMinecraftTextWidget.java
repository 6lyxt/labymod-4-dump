// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Deprecated
@AutoWidget
public class AnimatedMinecraftTextWidget extends AbstractWidget<Widget>
{
    private static boolean preLoaded;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (!AnimatedMinecraftTextWidget.preLoaded) {
            AnimatedMinecraftTextWidget.preLoaded = true;
            this.preLoad();
        }
    }
    
    private void preLoad() {
        for (final ResourceLocation resourceLocation : Textures.Splash.MINECRAFT_FRAMES) {
            Laby.references().textureRepository().preloadTexture(resourceLocation);
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final int emptyFrameSlots = 3;
        final int framesPerSprite = 8;
        final int frameWidth = 2403;
        final int frameHeight = 749;
        final int maxFrames = Textures.Splash.MINECRAFT_FRAMES.length;
        final int lastFrame = framesPerSprite * maxFrames - 1 - emptyFrameSlots;
        final float timePassed = LabyMod.references().dynamicBackgroundController().getTimePassed();
        final int frame = (int)MathHelper.clamp((timePassed - 1000.0f) / 40.0f, 0.0f, (float)lastFrame);
        final float ratio = frameWidth / (float)frameHeight;
        final int sprite = frame / framesPerSprite % maxFrames;
        final int offset = frame % framesPerSprite * frameHeight;
        final Bounds bounds = this.bounds();
        final float expandFactor = 1.8f;
        final float width = bounds.getWidth() * expandFactor;
        final float height = width / ratio;
        this.labyAPI.renderPipeline().resourceRenderer().texture(Textures.Splash.MINECRAFT_FRAMES[sprite]).pos(bounds.getCenterX() - width / 2.0f, bounds.getBottom() - height).size(width, height).sprite(0.0f, (float)offset, (float)frameWidth, (float)frameHeight).resolution((float)frameWidth, (float)(frameHeight * framesPerSprite)).render(context.stack());
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return 310.0f;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return 51.0f;
    }
    
    static {
        AnimatedMinecraftTextWidget.preLoaded = false;
    }
}
