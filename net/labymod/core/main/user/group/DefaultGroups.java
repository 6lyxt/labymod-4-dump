// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.group;

import net.labymod.api.user.group.Group;

public final class DefaultGroups
{
    public static final Group DEFAULT_GROUP;
    public static final Group LEGACY_GROUP;
    
    static {
        DEFAULT_GROUP = new Group(0, "default", "Default", "#FFFFFF", 'f', "", "", false);
        LEGACY_GROUP = new Group(-1, "legacy", "Legacy", "#888888", 'f', "", "", false);
    }
}
