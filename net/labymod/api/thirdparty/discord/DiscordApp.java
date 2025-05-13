// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.thirdparty.discord;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface DiscordApp
{
    void displayDefaultActivity(final boolean p0);
    
    void displayActivity(@NotNull final DiscordActivity p0);
    
    @Nullable
    DiscordActivity getDisplayedActivity();
    
    boolean isRunning();
    
    default void displayDefaultActivity() {
        this.displayDefaultActivity(true);
    }
}
