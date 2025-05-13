// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.crash;

import java.util.function.Supplier;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;

public interface GameCrashReport
{
    default GameCrashReport forThrowable(@NotNull final String title, final Throwable throwable) {
        return Laby.references().gameCrashReportFactory().create(title, throwable);
    }
    
    Category addCategory(final String p0);
    
     <T> T crashReport();
    
    default void crashGame() {
        Laby.labyAPI().minecraft().crashGame(this);
    }
    
    @Referenceable
    public interface Factory
    {
        GameCrashReport create(@NotNull final String p0, final Throwable p1);
    }
    
    public interface Category
    {
        void setDetail(final String p0, final Supplier<String> p1);
    }
}
