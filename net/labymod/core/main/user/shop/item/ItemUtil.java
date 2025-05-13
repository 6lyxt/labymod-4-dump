// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item;

@Deprecated(forRemoval = true, since = "4.1.0")
public final class ItemUtil
{
    private static boolean skipFlyingPets;
    
    public static void skipFlyingPets() {
        ItemUtil.skipFlyingPets = true;
    }
    
    public static boolean isSkipFlyingPets() {
        return ItemUtil.skipFlyingPets;
    }
    
    public static void resetSkipFlyingPets() {
        ItemUtil.skipFlyingPets = false;
    }
}
