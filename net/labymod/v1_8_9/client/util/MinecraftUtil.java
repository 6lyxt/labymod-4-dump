// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.util;

import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;

public class MinecraftUtil
{
    public static final zx AIR;
    private static final int EQUIPMENT_SLOT_HEAD = 4;
    private static final int EQUIPMENT_SLOT_CHEST = 3;
    private static final int EQUIPMENT_SLOT_LEGS = 2;
    private static final int EQUIPMENT_SLOT_FEET = 1;
    
    public static ItemStack fromMinecraftSlot(final pr entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.p(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(zx itemStack) {
        if (itemStack == null) {
            itemStack = MinecraftUtil.AIR;
        }
        return (ItemStack)itemStack;
    }
    
    public static zx toMinecraft(final ItemStack itemStack) {
        return (zx)itemStack;
    }
    
    public static ModelTransformType fromMinecraft(final bgr.b type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case a -> ModelTransformType.NONE;
            case c -> ModelTransformType.FIRST_PERSON_LEFT_HAND;
            case b -> ModelTransformType.THIRD_PERSON_LEFT_HAND;
            case d -> ModelTransformType.HEAD;
            case e -> ModelTransformType.GUI;
            case f -> ModelTransformType.GROUND;
            case g -> ModelTransformType.FIXED;
        };
    }
    
    public static int toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> 4;
            case CHEST -> 3;
            case LEGS -> 2;
            case FEET -> 1;
        };
    }
    
    public static <T extends pk> int getPackedLight(final T entity) {
        return getPackedLight(entity, Laby.labyAPI().minecraft().getPartialTicks());
    }
    
    public static <T extends pk> int getPackedLight(final T entity, final float delta) {
        return entity.b(delta);
    }
    
    public static int getPackedLight(final bdb level, final cj blockPos) {
        return level.e(blockPos) ? level.b(blockPos, 0) : 0;
    }
    
    public static void drawTexturedModalRect(final int x, final int y, final int spriteX, final int spriteY, final int spriteWidth, final int spriteHeight) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final bfx var9 = bfx.a();
        final bfd var10 = var9.c();
        var10.a(7, bms.g);
        var10.b((double)x, (double)(y + spriteHeight), 0.0).a((double)(spriteX * var7), (double)((spriteY + spriteHeight) * var8)).d();
        var10.b((double)(x + spriteWidth), (double)(y + spriteHeight), 0.0).a((double)((spriteX + spriteWidth) * var7), (double)((spriteY + spriteHeight) * var8)).d();
        var10.b((double)(x + spriteWidth), (double)y, 0.0).a((double)((spriteX + spriteWidth) * var7), (double)(spriteY * var8)).d();
        var10.b((double)x, (double)y, 0.0).a((double)(spriteX * var7), (double)(spriteY * var8)).d();
        var9.b();
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final biu dispatcher = ave.A().af();
        final biv entityRenderer = dispatcher.a((pk)player);
        if (entityRenderer instanceof final bln renderPlayer) {
            return (PlayerModel)renderPlayer.g();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    static {
        AIR = new zx(afi.a);
    }
}
