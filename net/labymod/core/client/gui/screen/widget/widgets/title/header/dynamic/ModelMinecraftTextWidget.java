// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Textures;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ModelMinecraftTextWidget extends AbstractWidget<Widget>
{
    private Model model;
    private AnimationLoader animationLoader;
    private AnimationController animationController;
    private CinematicCamera camera;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        try {
            this.camera = new CinematicCamera(70.0f);
            final ModelService modelService = Laby.references().modelService();
            (this.model = modelService.loadBlockBenchModel(Constants.Resources.MINECRAFT_LOGO)).setTextureLocation(Textures.Splash.MINECRAFT_SPRITE);
            (this.animationLoader = new AnimationLoader(Constants.Resources.MINECRAFT_LOGO_ANIMATION)).load();
            (this.animationController = modelService.createAnimationController()).tickProvider(() -> TimeUtil.convertMillisecondsToTicks(TimeUtil.getMillis() - this.getLastInitialTime()));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        if (this.camera == null || this.model == null || this.animationController == null) {
            return;
        }
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final ModelPart modelPart = this.model.getPart("logo");
        modelPart.getAnimationTransformation().setRotationX(MathHelper.toRadiansFloat(25.0f));
        final Location location = new Location(0.0, -3.0, 0.0, 0.0, -90.0, 0.0);
        this.camera.teleport(location);
        final Bounds bounds = window.bounds();
        this.camera.setup(bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom(), context.getTickDelta());
        final GFXRenderPipeline pipeline = this.labyAPI.gfxRenderPipeline();
        final GFXBridge gfx = Laby.gfx();
        gfx.enableDepth();
        gfx.disableCull();
        final FloatMatrix4 modelViewMatrix = FloatMatrix4.createTranslateMatrix(0.0f, 0.0f, CinematicCamera.getZLevel());
        modelViewMatrix.multiply(this.camera.viewMatrix());
        pipeline.setProjectionMatrix(this.camera.projectionMatrix());
        pipeline.setModelViewMatrix(modelViewMatrix);
        this.renderModel();
    }
    
    private void renderModel() {
        this.animationController.applyAnimation(this.model, new String[0]);
        final RenderedBuffer renderedBuffer = this.model.getRenderedBuffer();
        if (renderedBuffer != null) {
            renderedBuffer.dispose();
        }
        final BufferState state = Laby.references().modelRenderer().modelUploader().model(this.model).modelVertexWriter((consumer, x, y, z, u, v, packedColor, normalX, normalY, normalZ, modelId, glowing, rainbowCycle) -> {
            consumer.pos(x, y, z);
            consumer.uv(u, v);
            consumer.color(packedColor);
            return;
        }).upload(RenderPrograms.getDefaultTexture(this.model.getTextureLocation()));
        if (state == null) {
            return;
        }
        this.model.setDrawCommand(state);
        this.model.getRenderedBuffer().drawWithProgram();
    }
    
    public void play() {
        final ModelAnimation animation = this.animationLoader.getAnimation("assemble");
        if (animation != null) {
            this.animationController.playNext(animation);
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    public AnimationLoader getAnimationLoader() {
        return this.animationLoader;
    }
    
    public AnimationController getAnimationController() {
        return this.animationController;
    }
    
    public CinematicCamera getCamera() {
        return this.camera;
    }
}
