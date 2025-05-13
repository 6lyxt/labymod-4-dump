// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.util;

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
    
    public static ItemStack fromMinecraftSlot(final bfx entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.c(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(final cfv itemStack) {
        return (ItemStack)itemStack;
    }
    
    public static cfv toMinecraft(final ItemStack itemStack) {
        return (cfv)itemStack;
    }
    
    public static Direction fromMinecraft(final gz direction) {
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
    
    public static gz toMinecraft(@NotNull final Direction direction) {
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case DOWN -> gz.a;
            case UP -> gz.b;
            case NORTH -> gz.c;
            case SOUTH -> gz.d;
            case WEST -> gz.e;
            case EAST -> gz.f;
        };
    }
    
    public static bfm toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> bfm.f;
            case CHEST -> bfm.e;
            case LEGS -> bfm.d;
            case FEET -> bfm.c;
        };
    }
    
    public static ModelTransformType fromMinecraft(final cfs context) {
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
    
    public static void exportScreenshotToFile(final egf image, final File file) throws IOException {
        WriteScreenshotEventCaller.call(image.g(), file);
    }
    
    public static File getLastFileGrab() {
        return MinecraftUtil.lastFileGrab;
    }
    
    public static dds getHighestGeneratedStatus(final ddn access) {
        final dds currentStatus = access.j();
        final dgn belowZeroRetrogen = access.x();
        if (belowZeroRetrogen == null) {
            return currentStatus;
        }
        final dds targetStatus = belowZeroRetrogen.a();
        return targetStatus.b(currentStatus) ? targetStatus : currentStatus;
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final fnc dispatcher = emh.N().an();
        final fnd entityRenderer = dispatcher.a((bfh)player);
        if (entityRenderer instanceof final fsf playerRenderer) {
            return (PlayerModel)playerRenderer.a();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    public static TextDrawMode fromMinecraft(final enp.a displayMode) {
        return switch (displayMode) {
            default -> throw new MatchException(null, null);
            case a -> TextDrawMode.NORMAL;
            case b -> TextDrawMode.SEE_THROUGH;
            case c -> TextDrawMode.POLYGON_OFFSET;
        };
    }
    
    public static enp.a toMinecraft(final TextDrawMode textDrawMode) {
        return switch (textDrawMode) {
            default -> throw new MatchException(null, null);
            case NORMAL -> enp.a.a;
            case SEE_THROUGH -> enp.a.b;
            case POLYGON_OFFSET -> enp.a.c;
        };
    }
    
    public static aov fromText(final String text, final Style style) {
        final qz language = qz.a();
        return language.a(tn.a(text, (uf)style));
    }
}
