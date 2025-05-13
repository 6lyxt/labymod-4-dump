// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.marker;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.Textures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.entity.Entity;
import java.util.UUID;
import net.labymod.api.labyconnect.object.marker.Marker;
import net.labymod.api.client.world.object.AbstractWorldObject;

public class MarkerObject extends AbstractWorldObject implements Marker
{
    private final UUID owner;
    private final Entity targetEntity;
    private final long timestamp;
    private final boolean large;
    
    private MarkerObject(@NotNull final UUID owner, @NotNull final Entity targetEntity, final boolean large) {
        this.timestamp = TimeUtil.getMillis();
        this.owner = owner;
        this.targetEntity = targetEntity;
        this.large = large;
    }
    
    private MarkerObject(@NotNull final UUID owner, @NotNull final DoubleVector3 location, final boolean large) {
        super(location);
        this.timestamp = TimeUtil.getMillis();
        this.owner = owner;
        this.targetEntity = null;
        this.large = large;
    }
    
    public static MarkerObject forEntity(@NotNull final UUID owner, @NotNull final Entity targetEntity, final boolean large) {
        return new MarkerObject(owner, targetEntity, large);
    }
    
    public static MarkerObject fixed(@NotNull final UUID owner, @NotNull final DoubleVector3 location, final boolean large) {
        return new MarkerObject(owner, location, large);
    }
    
    @Override
    public boolean isLarge() {
        return this.large;
    }
    
    @Nullable
    @Override
    public Entity getTargetEntity() {
        return this.targetEntity;
    }
    
    @NotNull
    @Override
    public DoubleVector3 position() {
        if (this.targetEntity != null) {
            final float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
            final AxisAlignedBoundingBox box = this.targetEntity.axisAlignedBoundingBox();
            final Position position = this.targetEntity.position();
            final Position previousPosition = this.targetEntity.previousPosition();
            this.position.set(position.lerpX(previousPosition, partialTicks), position.lerpY(previousPosition, partialTicks) + box.getHeight(), position.lerpZ(previousPosition, partialTicks));
        }
        return super.position();
    }
    
    @Nullable
    @Override
    public Widget createWidget() {
        return new LabyMarkerWidget(this);
    }
    
    @Override
    public boolean shouldRemove() {
        return TimeUtil.getMillis() - this.timestamp >= this.getDurationMillis();
    }
    
    @Override
    public boolean isSeeThrough() {
        return true;
    }
    
    @Override
    public UUID getOwner() {
        return this.owner;
    }
    
    @Override
    public void renderInWorld(@NotNull final MinecraftCamera cam, @NotNull final Stack stack, final double x, final double y, final double z, final float delta, final boolean darker) {
        final long markerDuration = this.getDurationMillis();
        final long timeRemaining = markerDuration - (TimeUtil.getMillis() - this.timestamp);
        final long fadeInDuration = 300L;
        final double distance = x * x + y * y + z * z;
        final float fadeOut = Math.min(1.0f, 1.0f / markerDuration * (timeRemaining * timeRemaining));
        final float fadeIn = (float)Math.cos(Math.min(fadeInDuration, TimeUtil.getMillis() - this.timestamp) / (float)fadeInDuration * 3.141592653589793 / 1.2999999523162842 - 2.0943951023931953 + 0.5);
        final float transition = fadeIn * fadeOut;
        final double scale = Math.max(0.5, Math.min(this.large ? 4.0 : 2.0, distance / 100.0)) * transition;
        final double offsetY = (0.5 + Math.max(0.0, Math.min(this.large ? 10.0 : 2.0, distance / 100.0))) * transition;
        final double lineHeight = offsetY + scale / 2.0 + (this.large ? (scale * 1.5) : (scale / 2.0));
        this.renderCircle(cam, stack, transition, darker);
        this.renderLine(cam, stack, lineHeight, scale, darker);
        this.renderHead(cam, stack, lineHeight, scale, darker);
    }
    
    private void renderCircle(final MinecraftCamera cam, final Stack stack, final float transition, final boolean darker) {
        stack.push();
        stack.rotate(90.0f, -1.0f, 0.0f, 0.0f);
        stack.scale(transition, transition, 1.0f);
        Textures.SpriteMarker.CIRCLE.render(stack, -0.5f, -0.5f, 1.0f, 1.0f, false, this.getARGB(darker));
        stack.pop();
    }
    
    private void renderLine(final MinecraftCamera cam, final Stack stack, final double lineHeight, final double scale, final boolean darker) {
        stack.push();
        final float lineWidth = (float)(0.4000000059604645 * scale);
        this.rotateHorizontally(cam, stack);
        Textures.SpriteMarker.LINE.render(stack, -lineWidth / 2.0f, (float)(-lineHeight), lineWidth, (float)lineHeight, false, this.getARGB(darker));
        stack.pop();
    }
    
    private void renderHead(final MinecraftCamera cam, final Stack stack, final double lineHeight, final double scale, final boolean darker) {
        final RenderPipeline pipeline = Laby.labyAPI().renderPipeline();
        stack.push();
        final float faceScale = (float)(1.2999999523162842 * scale);
        this.rotateHorizontally(cam, stack);
        stack.translate(-faceScale / 2.0f, -lineHeight, 0.0);
        this.rotateVertically(cam, stack);
        stack.translate(0.0f, -faceScale / 2.0f, 0.0f);
        pipeline.rectangleRenderer().pos(0.0f, 0.0f).color(-1).size(faceScale, faceScale).render(stack);
        final float padding = (float)(0.05000000074505806 * scale);
        Laby.references().renderPipeline().resourceRenderer().pos(-padding, -padding).color(0.6f, 0.0f, 0.0f, darker ? 0.4f : 1.0f).size(faceScale + padding * 2.0f, faceScale + padding * 2.0f).texture(Textures.WHITE).sprite(32.0f, 32.0f, 32.0f, 32.0f).render(stack);
        final ResourceLocation texture = Laby.references().mojangTextureService().getTexture(this.owner, MojangTextureType.SKIN).getCompleted();
        stack.translate(0.0f, 0.0f, -0.01f);
        Laby.references().renderPipeline().resourceRenderer().texture(texture).pos(0.0f, 0.0f).size(faceScale, faceScale).sprite(8.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).color(this.getARGB(darker)).render(stack);
        stack.translate(0.0f, 0.0f, -0.01f);
        Laby.references().renderPipeline().resourceRenderer().texture(texture).pos(0.0f, 0.0f).size(faceScale, faceScale).sprite(40.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).color(this.getARGB(darker)).render(stack);
        stack.pop();
    }
    
    private long getDurationMillis() {
        return Laby.labyAPI().config().multiplayer().marker().duration().get() * 1000;
    }
    
    private int getARGB(final boolean darker) {
        return ColorFormat.ARGB32.pack(255, 255, 255, darker ? 75 : 255);
    }
}
