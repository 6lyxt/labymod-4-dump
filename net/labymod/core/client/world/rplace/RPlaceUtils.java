// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace;

import net.labymod.core.main.LabyMod;
import net.labymod.api.client.world.block.Block;
import net.labymod.core.client.world.rplace.art.ColoredBlock;
import java.util.Iterator;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.Laby;
import net.labymod.api.client.world.item.ItemStack;

public class RPlaceUtils
{
    private static final RPlaceRegistry REGISTRY;
    
    public static void selectSlot(final ItemStack itemStack) {
        final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        int freeSlot = -1;
        final Inventory inventory = clientPlayer.inventory();
        for (int i = 0; i <= 8; ++i) {
            final ItemStack itemInSlot = inventory.itemStackAt(i);
            if (equalsItem(itemInSlot, itemStack)) {
                inventory.setSelectedIndex(i);
                return;
            }
            if (itemInSlot.isAir()) {
                freeSlot = i;
            }
        }
        if (freeSlot != -1) {
            inventory.setSelectedIndex(freeSlot);
        }
    }
    
    public static void pickItem(final ItemStack itemStack) {
        final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        final Inventory inventory = clientPlayer.inventory();
        inventory.setCreativeModeItemStack(inventory.getSelectedIndex(), itemStack.copy());
    }
    
    public static PixelArt getPixelArtAt(final int x, final int y, final int z) {
        final ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        final BlockState blockState = world.getBlockState(x, y, z);
        if (blockState == null) {
            return null;
        }
        final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return null;
        }
        for (final KeyValue<PixelArt> entry : RPlaceUtils.REGISTRY.getElements()) {
            final PixelArt art = entry.getValue();
            final int minX = art.getMinX();
            final int minZ = art.getMinZ();
            final int maxX = art.getMaxX();
            final int maxZ = art.getMaxZ();
            final int artY = art.getY();
            if (artY == y && x >= minX && x <= maxX && z >= minZ && z <= maxZ) {
                return art;
            }
        }
        return null;
    }
    
    public static ItemStack getRequiredItemStack(final int x, final int y, final int z) {
        final PixelArt art = getPixelArtAt(x, y, z);
        if (art == null) {
            return null;
        }
        return getRequiredItemStack(art, x, z);
    }
    
    public static ItemStack getRequiredItemStack(final PixelArt art, final int x, final int z) {
        final ColoredBlock requiredBlock = art.getBlockAt(x - art.getMinX(), z - art.getMinZ());
        if (requiredBlock == null) {
            return null;
        }
        final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return null;
        }
        final Inventory inventory = clientPlayer.inventory();
        final int selectedIndex = inventory.getSelectedIndex();
        final ItemStack selectedItem = inventory.itemStackAt(selectedIndex);
        final ItemStack requiredItem = requiredBlock.getItemStack();
        if (selectedItem.equals(requiredItem)) {
            return null;
        }
        return requiredItem;
    }
    
    public static boolean equalsItem(final ItemStack item1, final ItemStack item2) {
        return item1 != null && item2 != null && item1.getIdentifier().getPath().equals(item2.getIdentifier().getPath());
    }
    
    public static boolean equalsItem(final Block block, final ItemStack item) {
        return block != null && item != null && block.id().getPath().equals(item.getIdentifier().getPath());
    }
    
    static {
        REGISTRY = LabyMod.references().rPlaceRegistry();
    }
}
