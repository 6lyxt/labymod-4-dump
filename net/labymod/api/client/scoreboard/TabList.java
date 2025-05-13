// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;

public interface TabList
{
    @Nullable
    default Component header() {
        return this.holder().getHeader();
    }
    
    @Nullable
    default Component footer() {
        return this.holder().getFooter();
    }
    
    default boolean isVisible() {
        return this.holder().isVisible();
    }
    
    @NotNull
    TabListHolder holder();
}
