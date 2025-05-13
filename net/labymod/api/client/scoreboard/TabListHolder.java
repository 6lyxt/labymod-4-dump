// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.scoreboard;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;

public interface TabListHolder
{
    @Nullable
    Component getHeader();
    
    @Nullable
    Component getFooter();
    
    boolean isVisible();
}
