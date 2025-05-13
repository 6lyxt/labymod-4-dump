// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model;

record ItemNameTag(String content, int backgroundTopColor, int backgroundBottomColor, int borderColor) {
    public static final ItemNameTag DEFAULT;
    
    public boolean isVisible() {
        return !this.content.isEmpty();
    }
    
    static {
        DEFAULT = new ItemNameTag("", 0, 0, 0);
    }
}
