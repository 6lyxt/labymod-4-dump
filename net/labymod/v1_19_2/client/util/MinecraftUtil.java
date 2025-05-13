// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.util;

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
import net.labymod.api.client.render.model.ModelTransformType;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;
import java.io.File;

public final class MinecraftUtil
{
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    private static File lastFileGrab;
    
    public static ItemStack fromMinecraftSlot(final bcc entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.c(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(final cax itemStack) {
        return (ItemStack)itemStack;
    }
    
    public static cax toMinecraft(final ItemStack itemStack) {
        return (cax)itemStack;
    }
    
    public static Direction fromMinecraft(final gy direction) {
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
    
    public static gy toMinecraft(@NotNull final Direction direction) {
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case DOWN -> gy.a;
            case UP -> gy.b;
            case NORTH -> gy.c;
            case SOUTH -> gy.d;
            case WEST -> gy.e;
            case EAST -> gy.f;
        };
    }
    
    public static bbs toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> bbs.f;
            case CHEST -> bbs.e;
            case LEGS -> bbs.d;
            case FEET -> bbs.c;
        };
    }
    
    public static ModelTransformType fromMinecraft(final fay.b context) {
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
    
    public static void exportScreenshotToFile(final dzq image, final File file) throws IOException {
        WriteScreenshotEventCaller.call(image.f(), file);
    }
    
    public static File getLastFileGrab() {
        return MinecraftUtil.lastFileGrab;
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final fej dispatcher = efu.I().ag();
        final fek entityRenderer = dispatcher.a((bbn)player);
        if (entityRenderer instanceof final fjk playerRenderer) {
            return (PlayerModel)playerRenderer.a();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    public static TextDrawMode fromMinecraft(final eha.a displayMode) {
        return switch (displayMode) {
            default -> throw new MatchException(null, null);
            case a -> TextDrawMode.NORMAL;
            case b -> TextDrawMode.SEE_THROUGH;
            case c -> TextDrawMode.POLYGON_OFFSET;
        };
    }
    
    public static eha.a toMinecraft(final TextDrawMode textDrawMode) {
        return switch (textDrawMode) {
            default -> throw new MatchException(null, null);
            case NORMAL -> eha.a.a;
            case SEE_THROUGH -> eha.a.b;
            case POLYGON_OFFSET -> eha.a.c;
        };
    }
    
    public static alu fromText(final String text, final Style style) {
        final pe language = pe.a();
        return language.a(ru.a(text, (sj)style));
    }
}
