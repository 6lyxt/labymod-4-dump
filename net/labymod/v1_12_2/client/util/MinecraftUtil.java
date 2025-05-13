// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.util;

import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;

public class MinecraftUtil
{
    public static final aip AIR;
    
    public static ItemStack fromMinecraftSlot(final vp entity, final LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.b(toMinecraft(spot)));
    }
    
    public static ItemStack fromMinecraft(aip itemStack) {
        if (itemStack == null) {
            itemStack = MinecraftUtil.AIR;
        }
        return (ItemStack)itemStack;
    }
    
    public static aip toMinecraft(final ItemStack itemStack) {
        return (aip)itemStack;
    }
    
    public static ModelTransformType fromMinecraft(final bwc.b type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case a -> ModelTransformType.NONE;
            case d -> ModelTransformType.FIRST_PERSON_LEFT_HAND;
            case e -> ModelTransformType.FIRST_PERSON_RIGHT_HAND;
            case b -> ModelTransformType.THIRD_PERSON_LEFT_HAND;
            case c -> ModelTransformType.THIRD_PERSON_RIGHT_HAND;
            case f -> ModelTransformType.HEAD;
            case g -> ModelTransformType.GUI;
            case h -> ModelTransformType.GROUND;
            case i -> ModelTransformType.FIXED;
        };
    }
    
    public static vl toMinecraft(final LivingEntity.EquipmentSpot spot) {
        return switch (spot) {
            default -> throw new MatchException(null, null);
            case HEAD -> vl.f;
            case CHEST -> vl.e;
            case LEGS -> vl.d;
            case FEET -> vl.c;
        };
    }
    
    public static <T extends vg> int getPackedLight(final T entity) {
        return getPackedLight(entity, Laby.labyAPI().minecraft().getPartialTicks());
    }
    
    public static <T extends vg> int getPackedLight(final T entity, final float delta) {
        return entity.av();
    }
    
    public static int getPackedLight(final bsb level, final et blockPos) {
        return level.e(blockPos) ? level.b(blockPos, 0) : 0;
    }
    
    public static void drawTexturedModalRect(final int x, final int y, final int spriteX, final int spriteY, final int spriteWidth, final int spriteHeight) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final bve var9 = bve.a();
        final buk var10 = var9.c();
        var10.a(7, cdy.g);
        var10.b((double)x, (double)(y + spriteHeight), 0.0).a((double)(spriteX * var7), (double)((spriteY + spriteHeight) * var8)).d();
        var10.b((double)(x + spriteWidth), (double)(y + spriteHeight), 0.0).a((double)((spriteX + spriteWidth) * var7), (double)((spriteY + spriteHeight) * var8)).d();
        var10.b((double)(x + spriteWidth), (double)y, 0.0).a((double)((spriteX + spriteWidth) * var7), (double)(spriteY * var8)).d();
        var10.b((double)x, (double)y, 0.0).a((double)(spriteX * var7), (double)(spriteY * var8)).d();
        var9.b();
    }
    
    public static PlayerModel obtainPlayerModel(final Player player) {
        final bzf dispatcher = bib.z().ac();
        final bzg entityRenderer = dispatcher.a((vg)player);
        if (entityRenderer instanceof final cct renderPlayer) {
            return (PlayerModel)renderPlayer.h();
        }
        throw new IllegalStateException("Renderer for the player could not be found");
    }
    
    static {
        AIR = new aip(aox.a);
    }
}
