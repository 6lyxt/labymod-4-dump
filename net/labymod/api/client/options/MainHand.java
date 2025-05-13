// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.options;

import net.labymod.api.util.CollectionHelper;
import java.util.List;

public enum MainHand
{
    LEFT, 
    RIGHT;
    
    public static final List<MainHand> VALUES;
    
    public MainHand opposite() {
        return (this == MainHand.LEFT) ? MainHand.RIGHT : MainHand.LEFT;
    }
    
    static {
        VALUES = CollectionHelper.asUnmodifiableList(values());
    }
}
