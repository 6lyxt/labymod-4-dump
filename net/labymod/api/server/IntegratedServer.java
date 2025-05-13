// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.server;

import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface IntegratedServer
{
    boolean isLanWorldOpened();
    
    @Nullable
    LocalWorld getLocalWorld();
    
    void requestLanWorld(final Consumer<LocalWorld> p0);
}
