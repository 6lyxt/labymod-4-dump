// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.bridge;

import net.labymod.api.tag.Tag;

public final class ItemTags
{
    public static final Tag FIRST_PERSON;
    public static final Tag ACTIVITY;
    public static final Tag WORLD;
    
    static {
        FIRST_PERSON = Tag.of("labymod", "item/first_person");
        ACTIVITY = Tag.of("labymod", "item/activity");
        WORLD = Tag.of("labymod", "item/world");
    }
}
