// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.legacy.head;

import net.labymod.api.util.math.position.Position;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.time.TimeUtil;
import java.util.Random;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.material.SimpleMaterial;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.material.Material;
import net.labymod.api.util.Color;
import net.labymod.api.client.gfx.pipeline.material.MaterialColor;
import net.labymod.api.util.math.Direction;
import net.labymod.core.client.render.model.box.DefaultModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.core.client.render.model.DefaultModelPart;
import net.labymod.core.client.render.model.DefaultModel;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.Laby;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.Entity;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.items.legacy.LegacyItem;

public class EyelidsItem extends LegacyItem
{
    public static final int ID = 36;
    private static final ResourceLocation EYELIDS_SLEEP_LOCATION;
    private static final int BLINK_SPEED = 100;
    private static final int MAX_IDLE_DURATION = 240000;
    private static final float IDLE_CLOSE_SPEED = 5000.0f;
    
    public EyelidsItem(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
    }
    
    @Override
    protected void render(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        final ModelPart head = this.playerModel.getHead();
        if (head.isInvisible()) {
            return;
        }
        stack.push();
        head.getAnimationTransformation().transform(stack);
        final float scale = 0.0625f;
        final AnimationData animationData = ((DefaultGameUser)this.user).getUserItemStorage().getEyelidsAnimationData(this.getIdentifier());
        final FloatVector4 size = this.itemMetadata.getSize();
        final float x = size.getX();
        final float y = size.getY();
        final float width = size.getZ();
        final float height = size.getW();
        final long blinkTimeLeft = animationData.getBlinkTimeLeft();
        final float frameTime = (float)(animationData.getBlinkPauseDuration() - blinkTimeLeft - 100L);
        final float animation = Math.min(Math.abs(frameTime), 100.0f);
        float percentage = this.itemMetadata.canBlink() ? (1.0f - animation / 100.0f) : 0.0f;
        final long idleDuration = animationData.getIdleDuration(this.player);
        final boolean isIdle = idleDuration > 240000L && this.itemMetadata.canSleep();
        if (isIdle) {
            percentage = 2.0E-4f * Math.min(5000.0f, (float)(idleDuration - 240000L));
        }
        final float scalePercentage = scale * -4.0f - ((percentage == 1.0f) ? 0.001f : 0.0055f);
        stack.translate(scale * -4.0f, scale * -8.0f, scalePercentage);
        animationData.setLastRenderedPercentage(percentage);
        final float smooth = -MathHelper.cos(1.5707963267948966 + 1.5707963267948966 * percentage);
        final float offset = 0.001f;
        if (percentage != 0.0f) {
            for (int index = 0; index < 2; ++index) {
                stack.push();
                this.setPosition((index == 0) ? Position.LEFT : Position.RIGHT);
                stack.translate(((index == 0) ? x : (8.0f - x - width)) * scale - offset, y * scale - offset, -0.001f);
                stack.scale(width + offset * 2.0f / scale, height * smooth + offset * 2.0f / scale, 1.0f);
                this.fixOversizeEyelids(stack, scale);
                this.renderModel(stack, packedLight, packedOverlay, false);
                stack.pop();
            }
        }
        stack.pop();
        if (!isIdle) {
            return;
        }
        this.renderSleepingParticles(stack, scale, idleDuration);
    }
    
    private void fixOversizeEyelids(final Stack stack, final float scale) {
        final float newScale = 0.875f;
        stack.scale(newScale, newScale, 1.0f);
        stack.translate(0.07125f * scale, 0.07125f * scale, 0.0f);
    }
    
    private void renderSleepingParticles(final Stack stack, final float scale, final long idleDuration) {
        stack.push();
        this.playerModel.getHead().getAnimationTransformation().transform(stack);
        stack.translate(scale * 5.0f, scale * -4.0f, scale * -(this.physicData.getPitch() / 15.0f + 4.0f));
        final int totalAmount = 4;
        final int speed = 1000;
        final int renderedAmount = totalAmount - Math.min(totalAmount, (int)(idleDuration - 240000L) / speed);
        final int startTimeOffset = (totalAmount - 1) * speed;
        for (int index = renderedAmount; index < totalAmount + 1; ++index) {
            final int sleepTimeFrame = ((int)idleDuration + index * speed + startTimeOffset) % (totalAmount * speed);
            final float progress = 1.0f / (totalAmount * speed) * sleepTimeFrame;
            stack.push();
            stack.translate(progress / 2.0f, -progress / 2.0f, 0.0f);
            stack.rotate((MathHelper.cos(progress * 5.0f) * 20.0f - 10.0f) * ((index % 2 == 0) ? -1 : 1), 0.0f, 0.0f, 1.0f);
            final float particleScale = scale / 2.0f + progress / 8.0f;
            final float opacity = 1.0f - Math.abs((progress - 0.5f) * 2.0f);
            final float finalScale = -particleScale / 2.0f;
            this.labyAPI.gfxRenderPipeline().setModelViewMatrix(stack.getProvider().getPosition());
            final MinecraftCamera camera = this.labyAPI.minecraft().getCamera();
            if (camera != null) {
                stack.rotate(camera.getYaw(), 0.0f, 1.0f, 0.0f);
                stack.rotate(camera.getPitch(), 1.0f, 0.0f, 0.0f);
                stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            }
            this.renderImmediateParticle(finalScale, finalScale, particleScale, particleScale, opacity);
            stack.pop();
        }
        stack.pop();
    }
    
    private void renderImmediateParticle(final float x, final float y, final float width, final float height, final float opacity) {
        final GFXRenderPipeline pipeline = Laby.references().gfxRenderPipeline();
        final BufferBuilder bufferBuilder = pipeline.getDefaultBufferBuilder();
        bufferBuilder.begin(RenderPrograms.getDefaultTexture(EyelidsItem.EYELIDS_SLEEP_LOCATION), () -> "Z Particle");
        bufferBuilder.pos(x, y, 0.0f).uv(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, opacity).endVertex();
        bufferBuilder.pos(x, y + height, 0.0f).uv(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, opacity).endVertex();
        bufferBuilder.pos(x + width, y + height, 0.0f).uv(1.0f, 1.0f).color(1.0f, 1.0f, 1.0f, opacity).endVertex();
        bufferBuilder.pos(x + width, y, 0.0f).uv(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, opacity).endVertex();
        ImmediateRenderer.drawWithProgram(bufferBuilder.end());
    }
    
    @Override
    public AbstractItem copy() {
        return new EyelidsItem(this.getListId(), this.itemDetails);
    }
    
    @Override
    public Model buildGeometry() {
        final Model model = new DefaultModel();
        final ModelPart part = new DefaultModelPart();
        part.addBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0625f, false);
        final ModelBox modelBox = part.getBoxes().get(0);
        for (final ModelBoxQuad quad : modelBox.getQuads()) {
            if (quad instanceof final DefaultModelBoxQuad defaultQuad) {
                if (defaultQuad.getDirection() != Direction.NORTH) {
                    quad.setVisible(false);
                }
            }
        }
        part.setTextureOffset(0, 0);
        part.setTextureSize(4, 2);
        final MaterialColor materialColor = new MaterialColor();
        materialColor.setColor(() -> {
            Color color = Color.WHITE;
            if (this.itemMetadata == null) {
                return color;
            }
            else {
                final Color[] colors = this.itemMetadata.getColors();
                if (colors != null && colors.length != 0) {
                    color = colors[0];
                }
                return color;
            }
        });
        part.setMaterial(Material.builder().withTextureLocation(Textures.WHITE).withColor(materialColor).build((Function<Material.Builder, Material>)SimpleMaterial::new));
        model.addChild("eyelid", part);
        return model;
    }
    
    static {
        EYELIDS_SLEEP_LOCATION = ResourceLocation.create("labymod", "textures/cosmetics/eyelids_sleep.png");
    }
    
    public static class AnimationData
    {
        private static final Random RANDOM;
        private float lastRenderedPercentage;
        private long nextBlinkTimestamp;
        private long blinkPauseDuration;
        private double lastPositionHash;
        private long lastPositionChanged;
        
        public AnimationData() {
            this.nextBlinkTimestamp = TimeUtil.getMillis();
        }
        
        private AnimationData(final AnimationData other) {
            this.nextBlinkTimestamp = TimeUtil.getMillis();
            this.lastRenderedPercentage = other.lastRenderedPercentage;
            this.nextBlinkTimestamp = other.nextBlinkTimestamp;
            this.blinkPauseDuration = other.blinkPauseDuration;
            this.lastPositionHash = other.lastPositionHash;
            this.lastPositionChanged = other.lastPositionChanged;
        }
        
        public static AnimationData copyOf(final AnimationData other) {
            return new AnimationData(other);
        }
        
        public long getBlinkTimeLeft() {
            final long currentTime = TimeUtil.getMillis();
            final long nextBlinkTime = this.nextBlinkTimestamp - currentTime;
            if (nextBlinkTime < 0L) {
                this.blinkPauseDuration = 800 + AnimationData.RANDOM.nextInt(8) * 1000;
                this.nextBlinkTimestamp = currentTime + this.blinkPauseDuration;
            }
            return nextBlinkTime;
        }
        
        public long getIdleDuration(final Entity entity) {
            final net.labymod.api.util.math.position.Position position = entity.position();
            double hash = position.getX() + position.getY() + position.getZ() + entity.getRotationYaw() + entity.getRotationPitch() + (entity.isCrouching() ? 1 : 0);
            if (entity instanceof final Player player) {
                hash += player.getLimbSwing();
            }
            if (this.lastPositionHash != hash) {
                this.lastPositionHash = hash;
                this.lastPositionChanged = TimeUtil.getMillis();
            }
            if (entity instanceof DummyPlayer) {
                this.lastPositionChanged = TimeUtil.getMillis();
            }
            return TimeUtil.getMillis() - this.lastPositionChanged;
        }
        
        public float getLastRenderedPercentage() {
            return this.lastRenderedPercentage;
        }
        
        public void setLastRenderedPercentage(final float lastRenderedPercentage) {
            this.lastRenderedPercentage = lastRenderedPercentage;
        }
        
        public long getNextBlinkTimestamp() {
            return this.nextBlinkTimestamp;
        }
        
        public long getBlinkPauseDuration() {
            return this.blinkPauseDuration;
        }
        
        public double getLastPositionHash() {
            return this.lastPositionHash;
        }
        
        public long getLastPositionChanged() {
            return this.lastPositionChanged;
        }
        
        static {
            RANDOM = new Random();
        }
    }
}
