// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.util;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import java.io.IOException;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import java.util.function.Function;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.core.client.render.font.text.msdf.MSDFTextRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.client.render.model.ModelTransformType;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.matrix.Stack;
import java.io.File;

public final class MinecraftUtil
{
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    private static File lastFileGrab;
    
    public static Stack obtainStackFromGraphics(final esf graphics) {
        return ((VanillaStackAccessor)graphics.c()).stack();
    }
    
    public static ItemStack fromMinecraftSlot(final bjg entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.c(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(final cjf itemStack) {
        return (ItemStack)itemStack;
    }
    
    public static cjf toMinecraft(final ItemStack itemStack) {
        return (cjf)itemStack;
    }
    
    public static Direction fromMinecraft(final ha direction) {
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
    
    public static ha toMinecraft(@NotNull final Direction direction) {
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case DOWN -> ha.a;
            case UP -> ha.b;
            case NORTH -> ha.c;
            case SOUTH -> ha.d;
            case WEST -> ha.e;
            case EAST -> ha.f;
        };
    }
    
    public static biv toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> biv.f;
            case CHEST -> biv.e;
            case LEGS -> biv.d;
            case FEET -> biv.c;
        };
    }
    
    public static ModelTransformType fromMinecraft(final cjc context) {
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
    
    public static ServerType fromMinecraft(final fjh data) {
        if (data.e()) {
            return ServerType.REALM;
        }
        if (data.d()) {
            return ServerType.LAN;
        }
        return ServerType.THIRD_PARTY;
    }
    
    public static fjh.b toMinecraft(final ServerType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case LAN -> fjh.b.a;
            case REALM -> fjh.b.b;
            case THIRD_PARTY -> fjh.b.c;
        };
    }
    
    public static ServerType fromMinecraft(final fjh.b type) {
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
    
    public static void exportScreenshotToFile(final ekq image, final File file) throws IOException {
        WriteScreenshotEventCaller.call(image.g(), file);
    }
    
    public static File getLastFileGrab() {
        return MinecraftUtil.lastFileGrab;
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final ftf dispatcher = eqv.O().ao();
        final ftg entityRenderer = dispatcher.a((biq)player);
        if (entityRenderer instanceof final fyh playerRenderer) {
            return (PlayerModel)playerRenderer.a();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    public static TextDrawMode fromMinecraft(final esd.a displayMode) {
        return switch (displayMode) {
            default -> throw new MatchException(null, null);
            case a -> TextDrawMode.NORMAL;
            case b -> TextDrawMode.SEE_THROUGH;
            case c -> TextDrawMode.POLYGON_OFFSET;
        };
    }
    
    public static esd.a toMinecraft(final TextDrawMode textDrawMode) {
        return switch (textDrawMode) {
            default -> throw new MatchException(null, null);
            case NORMAL -> esd.a.a;
            case SEE_THROUGH -> esd.a.b;
            case POLYGON_OFFSET -> esd.a.c;
        };
    }
    
    public static arj fromText(final String text, final Style style) {
        final qr language = qr.a();
        return language.a(tp.a(text, (uh)style));
    }
}
