// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface FileDialogs
{
    void open(@Nullable final String p0, @Nullable final Path p1, @Nullable final String p2, @NotNull final String[] p3, final boolean p4, @NotNull final Consumer<Path[]> p5);
}
