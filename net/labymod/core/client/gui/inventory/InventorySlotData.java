// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.inventory;

public class InventorySlotData
{
    private final Version version;
    private final String name;
    private final int slotIndex;
    private final int x;
    private final int y;
    
    public InventorySlotData(final Version version, final String name, final int slotIndex, final int x, final int y) {
        this.version = version;
        this.name = name;
        this.slotIndex = slotIndex;
        this.x = x;
        this.y = y;
    }
    
    public static InventorySlotData createLegacy(final String name, final int slotIndex, final int x, final int y) {
        return new InventorySlotData(Version.LEGACY, name, slotIndex, x, y);
    }
    
    public static InventorySlotData createModern(final String name, final int slotIndex, final int x, final int y) {
        return new InventorySlotData(Version.MODERN, name, slotIndex, x, y);
    }
    
    public Version getVersion() {
        return this.version;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSlotIndex() {
        return this.slotIndex;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    enum Version
    {
        LEGACY, 
        MODERN;
    }
}
