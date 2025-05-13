// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.util;

import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.client.component.format.Style;
import java.util.Iterator;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import java.util.HashMap;
import net.labymod.v1_21_5.client.multiplayer.server.VersionedCookieStorage;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import java.io.IOException;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;
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
import org.joml.Matrix4fc;
import org.joml.Matrix4f;
import java.io.File;

public final class MinecraftUtil
{
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    public static final fld VISITOR_STACK;
    private static final fld VIEW_MATRIX_STACK;
    public static LevelRenderContext levelRenderContext;
    private static File lastFileGrab;
    private static Matrix4f vanillaViewMatrix;
    
    public static Matrix4f setViewMatrix(Matrix4f vanillaViewMatrix) {
        MinecraftUtil.VIEW_MATRIX_STACK.a();
        MinecraftUtil.VIEW_MATRIX_STACK.e();
        MinecraftUtil.VIEW_MATRIX_STACK.a((Matrix4fc)vanillaViewMatrix);
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
    
    public static Stack obtainStackFromGraphics(final ftk graphics) {
        return ((VanillaStackAccessor)graphics.c()).stack();
    }
    
    public static ItemStack fromMinecraftSlot(final byf entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.a(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(final dak itemStack) {
        return (ItemStack)itemStack;
    }
    
    public static dak toMinecraft(final ItemStack itemStack) {
        return (dak)itemStack;
    }
    
    public static Direction fromMinecraft(final jc direction) {
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
    
    public static jc toMinecraft(@NotNull final Direction direction) {
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case DOWN -> jc.a;
            case UP -> jc.b;
            case NORTH -> jc.c;
            case SOUTH -> jc.d;
            case WEST -> jc.e;
            case EAST -> jc.f;
        };
    }
    
    public static bxo toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> bxo.f;
            case CHEST -> bxo.e;
            case LEGS -> bxo.d;
            case FEET -> bxo.c;
        };
    }
    
    public static ModelTransformType fromMinecraft(final dai context) {
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
    
    public static ServerType fromMinecraft(final gmd data) {
        if (data.e()) {
            return ServerType.REALM;
        }
        if (data.d()) {
            return ServerType.LAN;
        }
        return ServerType.THIRD_PARTY;
    }
    
    public static gmd.c toMinecraft(final ServerType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case LAN -> gmd.c.a;
            case REALM -> gmd.c.b;
            case THIRD_PARTY -> gmd.c.c;
        };
    }
    
    public static ServerType fromMinecraft(final gmd.c type) {
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
    
    public static void exportScreenshotToFile(final fkf image, final File file) throws IOException {
        WriteScreenshotEventCaller.call(((NativeImageAccessor)image).toByteArray(), file);
    }
    
    public static File getLastFileGrab() {
        return MinecraftUtil.lastFileGrab;
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final gxt dispatcher = fqq.Q().aq();
        final gxu entityRenderer = dispatcher.a((bxe)player);
        if (entityRenderer instanceof final hdc playerRenderer) {
            return (PlayerModel)playerRenderer.c();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    public static TextDrawMode fromMinecraft(final fti.a displayMode) {
        return switch (displayMode) {
            default -> throw new MatchException(null, null);
            case a -> TextDrawMode.NORMAL;
            case b -> TextDrawMode.SEE_THROUGH;
            case c -> TextDrawMode.POLYGON_OFFSET;
        };
    }
    
    public static fti.a toMinecraft(final TextDrawMode textDrawMode) {
        return switch (textDrawMode) {
            default -> throw new MatchException(null, null);
            case NORMAL -> fti.a.a;
            case SEE_THROUGH -> fti.a.b;
            case POLYGON_OFFSET -> fti.a.c;
        };
    }
    
    public static CookieStorage fromMinecraft(final gmh state) {
        return (state == null) ? null : new VersionedCookieStorage(state);
    }
    
    public static gmh toMinecraft(final CookieStorage storage) {
        final Map<ResourceLocation, byte[]> apiCookies = storage.cookies();
        final Map<alr, byte[]> cookies = new HashMap<alr, byte[]>(apiCookies.size());
        for (final Map.Entry<ResourceLocation, byte[]> entry : apiCookies.entrySet()) {
            cookies.put(entry.getKey().getMinecraftLocation(), entry.getValue());
        }
        return new gmh((Map)cookies);
    }
    
    public static azk fromText(final String text, final Style style) {
        final tv language = tv.a();
        return language.a(xl.a(text, (yd)style));
    }
    
    public static LevelRenderContext levelRenderContext() {
        return MinecraftUtil.levelRenderContext;
    }
    
    public static int getGpuTextureId(final GpuTexture texture) {
        if (texture instanceof final fjl glTexture) {
            return glTexture.b();
        }
        throw new IllegalStateException("Unexpected gpu texture (" + ((texture == null) ? "UNKNOWN" : texture.getClass().getName()));
    }
    
    static {
        VISITOR_STACK = new fld();
        VIEW_MATRIX_STACK = new fld();
        MinecraftUtil.levelRenderContext = new LevelRenderContext();
        MinecraftUtil.vanillaViewMatrix = new Matrix4f().identity();
    }
    
    public static class LevelRenderContext
    {
        private fld poseStack;
        
        public fld getPoseStack() {
            return this.poseStack;
        }
        
        public void setPoseStack(final fld poseStack) {
            this.poseStack = poseStack;
        }
    }
}
