// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.inventory;

import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.util.Pair;
import java.util.List;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class InventorySlotRegistry
{
    private static final int OUT_OUF_SCREEN_COORDINATE = 32767;
    private static final InventorySlotData NONE_LEGACY;
    private static final InventorySlotData NONE_MODERN;
    private final Map<InventoryType, List<Pair<InventorySlotData, InventorySlotData>>> inventories;
    
    @Inject
    public InventorySlotRegistry() {
        this.inventories = new HashMap<InventoryType, List<Pair<InventorySlotData, InventorySlotData>>>();
        this.setupPlayerInventory();
        this.setupCreativeInventory();
    }
    
    private void setupPlayerInventory() {
        this.registerType(InventoryType.PLAYER);
        this.registerSlot(InventoryType.PLAYER, InventorySlotRegistry.NONE_LEGACY, InventorySlotRegistry.NONE_MODERN);
        this.registerSlot(InventoryType.PLAYER, InventorySlotData.createLegacy("crafting_result", 0, 144, 36), InventorySlotData.createModern("crafting_result", 0, 154, 28));
        this.registerSlot(InventoryType.PLAYER, InventorySlotData.createLegacy("crafting_matrix_1x1", 1, 88, 26), InventorySlotData.createModern("crafting_matrix_1x1", 1, 98, 18));
        this.registerSlot(InventoryType.PLAYER, InventorySlotData.createLegacy("crafting_matrix_1x2", 2, 106, 26), InventorySlotData.createModern("crafting_matrix_1x2", 2, 116, 18));
        this.registerSlot(InventoryType.PLAYER, InventorySlotData.createLegacy("crafting_matrix_2x1", 3, 88, 44), InventorySlotData.createModern("crafting_matrix_2x1", 3, 98, 36));
        this.registerSlot(InventoryType.PLAYER, InventorySlotData.createLegacy("crafting_matrix_2x2", 4, 106, 44), InventorySlotData.createModern("crafting_matrix_2x2", 4, 116, 36));
        this.registerSlot(InventoryType.PLAYER, InventorySlotData.createLegacy("offhand", 45, 32767, 32767), InventorySlotData.createModern("offhand", 45, 77, 62));
    }
    
    private void setupCreativeInventory() {
        this.registerType(InventoryType.CREATIVE);
        this.registerSlot(InventoryType.CREATIVE, InventorySlotRegistry.NONE_LEGACY, InventorySlotRegistry.NONE_MODERN);
        this.registerSlot(InventoryType.CREATIVE, InventorySlotData.createLegacy("helmet", 0, 9, 6), InventorySlotData.createModern("helmet", 0, 54, 6));
        this.registerSlot(InventoryType.CREATIVE, InventorySlotData.createLegacy("chestplate", 0, 9, 33), InventorySlotData.createModern("chestplate", 0, 54, 33));
        this.registerSlot(InventoryType.CREATIVE, InventorySlotData.createLegacy("leggings", 0, 63, 6), InventorySlotData.createModern("leggings", 0, 108, 6));
        this.registerSlot(InventoryType.CREATIVE, InventorySlotData.createLegacy("boots", 0, 63, 33), InventorySlotData.createModern("boots", 0, 108, 33));
        this.registerSlot(InventoryType.CREATIVE, InventorySlotData.createLegacy("offhand", 0, 32767, 32767), InventorySlotData.createModern("offhand", 0, 35, 20));
    }
    
    public void registerType(final InventoryType type) {
        this.inventories.computeIfAbsent(type, list -> new ArrayList());
    }
    
    public void registerSlot(final InventoryType type, final InventorySlotData legacySlot, final InventorySlotData modernSlot) {
        final List<Pair<InventorySlotData, InventorySlotData>> list = this.inventories.get(type);
        if (list == null) {
            throw new IllegalStateException();
        }
        list.add(Pair.of(legacySlot, modernSlot));
    }
    
    @NotNull
    public List<Pair<InventorySlotData, InventorySlotData>> getSlots(final InventoryType type) {
        List<Pair<InventorySlotData, InventorySlotData>> list = this.inventories.get(type);
        if (list == null) {
            list = new ArrayList<Pair<InventorySlotData, InventorySlotData>>();
        }
        return list;
    }
    
    @Nullable
    public Pair<InventorySlotData, InventorySlotData> getLegacySlot(final InventoryType type, final int index) {
        return this.getSlot(type, InventorySlotData.Version.LEGACY, index);
    }
    
    @Nullable
    public Pair<InventorySlotData, InventorySlotData> getModernSlot(final InventoryType type, final int index) {
        return this.getSlot(type, InventorySlotData.Version.MODERN, index);
    }
    
    @Nullable
    private Pair<InventorySlotData, InventorySlotData> getSlot(final InventoryType type, final InventorySlotData.Version version, final int index) {
        for (final Pair<InventorySlotData, InventorySlotData> slot : this.getSlots(type)) {
            final InventorySlotData slotData = this.getSlotData(slot, version == InventorySlotData.Version.LEGACY);
            if (slotData.getSlotIndex() == index) {
                return slot;
            }
        }
        return null;
    }
    
    private InventorySlotData getSlotData(final Pair<InventorySlotData, InventorySlotData> pair, final boolean legacy) {
        return legacy ? pair.getFirst() : pair.getSecond();
    }
    
    @Nullable
    public Pair<InventorySlotData, InventorySlotData> getLegacySlot(final InventoryType type, final int x, final int y) {
        return this.getSlot(type, InventorySlotData.Version.LEGACY, x, y);
    }
    
    @Nullable
    public Pair<InventorySlotData, InventorySlotData> getModernSlot(final InventoryType type, final int x, final int y) {
        return this.getSlot(type, InventorySlotData.Version.MODERN, x, y);
    }
    
    @Nullable
    private Pair<InventorySlotData, InventorySlotData> getSlot(final InventoryType type, final InventorySlotData.Version version, final int x, final int y) {
        for (final Pair<InventorySlotData, InventorySlotData> slot : this.getSlots(type)) {
            final InventorySlotData slotData = this.getSlotData(slot, version == InventorySlotData.Version.LEGACY);
            if (slotData.getX() == x && slotData.getY() == y) {
                return slot;
            }
        }
        return null;
    }
    
    static {
        NONE_LEGACY = InventorySlotData.createLegacy("none", -1000, 0, 0);
        NONE_MODERN = InventorySlotData.createModern("none", -1000, 0, 0);
    }
    
    public enum InventoryType
    {
        PLAYER, 
        CREATIVE;
    }
}
