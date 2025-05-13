// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.Iterator;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragonPart;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.boss.enderdragon.EnderDragon;
import net.labymod.api.util.Color;
import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class HitboxOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "hitbox";
    private final LabyAPI labyAPI;
    
    public HitboxOldAnimation() {
        super("hitbox");
        this.labyAPI = Laby.labyAPI();
    }
    
    public void renderHitbox(final double x, final double y, final double z, final Stack stack, final Entity entity, final float partialTicks) {
        final HitboxConfig hitbox = this.labyAPI.config().ingame().hitbox();
        final boolean legacyVersion = PlatformEnvironment.isAncientOpenGL();
        final Position position = entity.position();
        final AxisAlignedBoundingBox boundingBox = entity.axisAlignedBoundingBox().move(legacyVersion ? ((double)(float)(-position.getX() + x)) : (-position.getX()), legacyVersion ? ((double)(float)(-position.getY() + y)) : (-position.getY()), legacyVersion ? ((double)(float)(-position.getZ() + z)) : (-position.getZ()));
        final BufferBuilder builder = Laby.labyAPI().renderPipeline().createBufferBuilder();
        final boolean enabled = hitbox.enabled().get();
        final BufferBuilder bufferBuilder = builder;
        final RenderPhase lines = RenderPhases.lines(enabled ? ((float)hitbox.lineSize().get()) : 1.0f);
        Objects.requireNonNull(stack);
        bufferBuilder.begin(lines, stack::getMultiBufferSource);
        final int boxColor = enabled ? hitbox.boxColor().get().get() : -1;
        this.renderBox(stack, builder, boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ(), boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ(), boxColor);
        if (entity instanceof final EnderDragon enderDragon) {
            if (!legacyVersion) {
                final Position previousPosition = entity.previousPosition();
                final double enderDragonX = -MathHelper.lerp(position.getX(), previousPosition.getX(), partialTicks);
                final double enderDragonY = -MathHelper.lerp(position.getY(), previousPosition.getY(), partialTicks);
                final double enderDragonZ = -MathHelper.lerp(position.getZ(), previousPosition.getZ(), partialTicks);
                for (final EnderDragonPart part : enderDragon.getParts()) {
                    final Position partPosition = part.position();
                    final Position partPreviousPosition = part.previousPosition();
                    stack.push();
                    final double xPart = enderDragonX + MathHelper.lerp(partPosition.getX(), partPreviousPosition.getX(), partialTicks);
                    final double yPart = enderDragonY + MathHelper.lerp(partPosition.getY(), partPreviousPosition.getY(), partialTicks);
                    final double zPart = enderDragonZ + MathHelper.lerp(partPosition.getZ(), partPreviousPosition.getZ(), partialTicks);
                    stack.translate(xPart, yPart, zPart);
                    final AxisAlignedBoundingBox partBox = part.axisAlignedBoundingBox().move(-partPosition.getX(), -partPosition.getY(), -partPosition.getZ());
                    this.renderBox(stack, builder, partBox.getMinX(), partBox.getMinY(), partBox.getMinZ(), partBox.getMaxX(), partBox.getMaxY(), partBox.getMaxZ(), boxColor);
                    stack.pop();
                }
            }
        }
        if (this.labyAPI.config().multiplayer().classicPvP().oldHitbox().get()) {
            builder.uploadToBuffer();
            return;
        }
        final float eyeHeight = entity.getEyeHeight();
        if (entity instanceof LivingEntity) {
            this.renderBox(stack, builder, boundingBox.getMinX(), legacyVersion ? ((double)((float)y + eyeHeight - 0.01f)) : ((double)(eyeHeight - 0.01f)), boundingBox.getMinZ(), boundingBox.getMaxX(), legacyVersion ? ((double)((float)y + eyeHeight + 0.01f)) : ((double)(eyeHeight + 0.01f)), boundingBox.getMaxZ(), enabled ? hitbox.eyeBoxColor().get().get() : -65536);
        }
        final FloatVector3 perspectiveVector = entity.perspectiveVector(partialTicks);
        this.renderEyeLine(stack, builder, legacyVersion ? ((float)x) : 0.0f, legacyVersion ? ((float)(y + eyeHeight)) : eyeHeight, legacyVersion ? ((float)z) : 0.0f, enabled ? hitbox.eyeLineColor().get().get() : -16776961, perspectiveVector);
        builder.uploadToBuffer();
    }
    
    private void renderEyeLine(@NotNull final Stack stack, @NotNull final BufferBuilder builder, final float x, final float eyeHeight, final float z, final int eyeLineColor, @NotNull final FloatVector3 perspectiveVector) {
        builder.vertex(stack, x, eyeHeight, z).color(eyeLineColor).normal(stack, perspectiveVector.getX(), perspectiveVector.getY(), perspectiveVector.getZ()).next();
        builder.vertex(stack, x + perspectiveVector.getX() * 2.0f, eyeHeight + perspectiveVector.getY() * 2.0f, z + perspectiveVector.getZ() * 2.0f).color(eyeLineColor).normal(stack, perspectiveVector.getX(), perspectiveVector.getY(), perspectiveVector.getZ()).next();
    }
    
    private void renderBox(final Stack stack, final BufferBuilder builder, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final int color) {
        builder.vertex(stack, minX, minY, minZ).color(color).normal(stack, 1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, maxX, minY, minZ).color(color).normal(stack, 1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, minX, minY, minZ).color(color).normal(stack, 0.0f, 1.0f, 0.0f).next();
        builder.vertex(stack, minX, maxY, minZ).color(color).normal(stack, 0.0f, 1.0f, 0.0f).next();
        builder.vertex(stack, minX, minY, minZ).color(color).normal(stack, 0.0f, 0.0f, 1.0f).next();
        builder.vertex(stack, minX, minY, maxZ).color(color).normal(stack, 0.0f, 0.0f, 1.0f).next();
        builder.vertex(stack, maxX, minY, minZ).color(color).normal(stack, 0.0f, 1.0f, 0.0f).next();
        builder.vertex(stack, maxX, maxY, minZ).color(color).normal(stack, 0.0f, 1.0f, 0.0f).next();
        builder.vertex(stack, maxX, maxY, minZ).color(color).normal(stack, -1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, minX, maxY, minZ).color(color).normal(stack, -1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, minX, maxY, minZ).color(color).normal(stack, 0.0f, 0.0f, 1.0f).next();
        builder.vertex(stack, minX, maxY, maxZ).color(color).normal(stack, 0.0f, 0.0f, 1.0f).next();
        builder.vertex(stack, minX, maxY, maxZ).color(color).normal(stack, 0.0f, -1.0f, 0.0f).next();
        builder.vertex(stack, minX, minY, maxZ).color(color).normal(stack, 0.0f, -1.0f, 0.0f).next();
        builder.vertex(stack, minX, minY, maxZ).color(color).normal(stack, 1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, maxX, minY, maxZ).color(color).normal(stack, 1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, maxX, minY, maxZ).color(color).normal(stack, 0.0f, 0.0f, -1.0f).next();
        builder.vertex(stack, maxX, minY, minZ).color(color).normal(stack, 0.0f, 0.0f, -1.0f).next();
        builder.vertex(stack, minX, maxY, maxZ).color(color).normal(stack, 1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, maxX, maxY, maxZ).color(color).normal(stack, 1.0f, 0.0f, 0.0f).next();
        builder.vertex(stack, maxX, minY, maxZ).color(color).normal(stack, 0.0f, 1.0f, 0.0f).next();
        builder.vertex(stack, maxX, maxY, maxZ).color(color).normal(stack, 0.0f, 1.0f, 0.0f).next();
        builder.vertex(stack, maxX, maxY, minZ).color(color).normal(stack, 0.0f, 0.0f, 1.0f).next();
        builder.vertex(stack, maxX, maxY, maxZ).color(color).normal(stack, 0.0f, 0.0f, 1.0f).next();
    }
    
    @Override
    public boolean isEnabled() {
        return this.labyAPI.config().ingame().hitbox().enabled().get();
    }
}
