// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.cave;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic.ModelMinecraftTextWidget;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.test.TestActivity;

@Link("test/model-minecraft-logo.lss")
@AutoActivity
public class ModelMinecraftTextTestActivity extends TestActivity
{
    private ModelMinecraftTextWidget widget;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.widget = new ModelMinecraftTextWidget()).addId("fractured-logo");
        ((AbstractWidget<ModelMinecraftTextWidget>)this.document).addChild(this.widget);
    }
    
    @Override
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.widget.play();
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final AnimationController controller = this.widget.getAnimationController();
        if (this.widget == null || controller == null) {
            return;
        }
        final ModelAnimation animation = controller.getPlaying();
        if (animation == null) {
            return;
        }
        final float width = this.bounds().getWidth();
        final float timePassed = (float)controller.getProgress();
        final long length = animation.getLength();
        final float percentage = MathHelper.clamp(timePassed / length, 0.0f, 1.0f);
        this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), 0.0f, 0.0f, width, 5.0f, Integer.MIN_VALUE);
        this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), 0.0f, 0.0f, width * percentage, 5.0f, -7851213);
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.widget == null) {
            return;
        }
        final AnimationController controller = this.widget.getAnimationController();
        if (!controller.isPlaying()) {
            this.reload();
        }
    }
}
