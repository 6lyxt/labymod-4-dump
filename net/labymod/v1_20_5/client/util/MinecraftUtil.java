// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.util;

import net.labymod.api.client.component.format.Style;
import java.util.Iterator;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import java.util.HashMap;
import net.labymod.v1_20_5.client.multiplayer.server.VersionedCookieStorage;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import java.io.IOException;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import java.util.function.Function;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.client.render.model.ModelTransformType;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.joml.Matrix4f;
import java.io.File;

public final class MinecraftUtil
{
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    private static File lastFileGrab;
    public static LevelRenderContext levelRenderContext;
    private static final ezz VIEW_MATRIX_STACK;
    private static Matrix4f vanillaViewMatrix;
    
    public static Matrix4f setViewMatrix(Matrix4f vanillaViewMatrix) {
        MinecraftUtil.VIEW_MATRIX_STACK.a();
        MinecraftUtil.VIEW_MATRIX_STACK.e();
        MinecraftUtil.VIEW_MATRIX_STACK.a(vanillaViewMatrix);
        final Stack viewStack = ((VanillaStackAccessor)MinecraftUtil.VIEW_MATRIX_STACK).stack();
        Laby.fireEvent(new CameraSetupEvent(viewStack, Phase.PRE));
        vanillaViewMatrix = (MinecraftUtil.vanillaViewMatrix = MinecraftUtil.VIEW_MATRIX_STACK.c().a());
        Laby.fireEvent(new CameraSetupEvent(viewStack, Phase.POST));
        vanillaViewMatrix = MinecraftUtil.VIEW_MATRIX_STACK.c().a();
        MinecraftUtil.VIEW_MATRIX_STACK.b();
        final FloatMatrix4 viewMatrix = MathHelper.mapper().fromMatrix4f(vanillaViewMatrix);
        Laby.references().gfxRenderPipeline().setViewMatrix(viewMatrix.copy());
        return vanillaViewMatrix;
    }
    
    public static void setDefaultViewMatrix() {
        Laby.references().gfxRenderPipeline().setViewMatrix(FloatMatrix4.newIdentity());
    }
    
    public static Matrix4f getViewMatrix() {
        return MinecraftUtil.vanillaViewMatrix;
    }
    
    public static Stack obtainStackFromGraphics(final fgs graphics) {
        return ((VanillaStackAccessor)graphics.c()).stack();
    }
    
    public static ItemStack fromMinecraftSlot(final btq entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.a(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(final cuq itemStack) {
        return (ItemStack)itemStack;
    }
    
    public static cuq toMinecraft(final ItemStack itemStack) {
        return (cuq)itemStack;
    }
    
    public static Direction fromMinecraft(final je direction) {
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case a -> Direction.DOWN;
            case b -> Direction.UP;
            case c -> Direction.NORTH;
            case d -> Direction.SOUTH;
            case e -> Direction.WEST;
            case f -> Direction.EAST;
        };
    }
    
    public static je toMinecraft(@NotNull final Direction direction) {
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case DOWN -> je.a;
            case UP -> je.b;
            case NORTH -> je.c;
            case SOUTH -> je.d;
            case WEST -> je.e;
            case EAST -> je.f;
        };
    }
    
    public static btc toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> btc.f;
            case CHEST -> btc.e;
            case LEGS -> btc.d;
            case FEET -> btc.c;
        };
    }
    
    public static ModelTransformType fromMinecraft(final cun context) {
        return switch (context) {
            default -> throw new MatchException(null, null);
            case a -> ModelTransformType.NONE;
            case b -> ModelTransformType.THIRD_PERSON_LEFT_HAND;
            case c -> ModelTransformType.THIRD_PERSON_RIGHT_HAND;
            case d -> ModelTransformType.FIRST_PERSON_LEFT_HAND;
            case e -> ModelTransformType.FIRST_PERSON_RIGHT_HAND;
            case f -> ModelTransformType.HEAD;
            case g -> ModelTransformType.GUI;
            case h -> ModelTransformType.GROUND;
            case i -> ModelTransformType.FIXED;
        };
    }
    
    public static ServerType fromMinecraft(final fyk data) {
        if (data.e()) {
            return ServerType.REALM;
        }
        if (data.d()) {
            return ServerType.LAN;
        }
        return ServerType.THIRD_PARTY;
    }
    
    public static fyk.c toMinecraft(final ServerType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case LAN -> fyk.c.a;
            case REALM -> fyk.c.b;
            case THIRD_PARTY -> fyk.c.c;
        };
    }
    
    public static ServerType fromMinecraft(final fyk.c type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case a -> ServerType.LAN;
            case b -> ServerType.REALM;
            case c -> ServerType.THIRD_PARTY;
        };
    }
    
    public static void updateTextEdgeStrength(final float strength) {
        final TextRenderer textRenderer = Laby.references().renderPipeline().textRenderer();
        if (textRenderer instanceof final MSDFTextRenderer msdfTextRenderer) {
            msdfTextRenderer.updateEdgeStrength(strength);
        }
    }
    
    public static void grabFile(final File gameDirectory, final String customName, final Function<File, File> fileMapper) {
        final File screenshotsDirectory = new File(gameDirectory, "screenshots");
        screenshotsDirectory.mkdir();
        File destination;
        if (customName == null) {
            destination = fileMapper.apply(screenshotsDirectory);
        }
        else {
            destination = new File(screenshotsDirectory, customName);
        }
        Laby.fireEvent(new CaptureScreenshotEvent(destination));
        MinecraftUtil.lastFileGrab = destination;
    }
    
    public static void exportScreenshotToFile(final eza image, final File file) throws IOException {
        WriteScreenshotEventCaller.call(image.g(), file);
    }
    
    public static File getLastFileGrab() {
        return MinecraftUtil.lastFileGrab;
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final giw dispatcher = ffg.Q().ap();
        final gix entityRenderer = dispatcher.a((bsv)player);
        if (entityRenderer instanceof final god playerRenderer) {
            return (PlayerModel)playerRenderer.a();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    public static TextDrawMode fromMinecraft(final fgq.a displayMode) {
        return switch (displayMode) {
            default -> throw new MatchException(null, null);
            case a -> TextDrawMode.NORMAL;
            case b -> TextDrawMode.SEE_THROUGH;
            case c -> TextDrawMode.POLYGON_OFFSET;
        };
    }
    
    public static fgq.a toMinecraft(final TextDrawMode textDrawMode) {
        return switch (textDrawMode) {
            default -> throw new MatchException(null, null);
            case NORMAL -> fgq.a.a;
            case SEE_THROUGH -> fgq.a.b;
            case POLYGON_OFFSET -> fgq.a.c;
        };
    }
    
    public static CookieStorage fromMinecraft(final fyo state) {
        return (state == null) ? null : new VersionedCookieStorage(state);
    }
    
    public static fyo toMinecraft(final CookieStorage storage) {
        final Map<ResourceLocation, byte[]> apiCookies = storage.cookies();
        final Map<alf, byte[]> cookies = new HashMap<alf, byte[]>(apiCookies.size());
        for (final Map.Entry<ResourceLocation, byte[]> entry : apiCookies.entrySet()) {
            cookies.put(entry.getKey().getMinecraftLocation(), entry.getValue());
        }
        return new fyo((Map)cookies);
    }
    
    public static ayl fromText(final String text, final Style style) {
        final un language = un.a();
        return language.a(xu.a(text, (ym)style));
    }
    
    public static LevelRenderContext levelRenderContext() {
        return MinecraftUtil.levelRenderContext;
    }
    
    static {
        MinecraftUtil.levelRenderContext = new LevelRenderContext();
        VIEW_MATRIX_STACK = new ezz();
        MinecraftUtil.vanillaViewMatrix = new Matrix4f().identity();
    }
    
    public static class LevelRenderContext
    {
        private ezz poseStack;
        
        public ezz getPoseStack() {
            return this.poseStack;
        }
        
        public void setPoseStack(final ezz poseStack) {
            this.poseStack = poseStack;
        }
    }
}
