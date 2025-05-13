// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import net.labymod.api.client.options.MinecraftOptions;
import org.lwjgl.PointerBuffer;
import java.nio.file.Paths;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import net.labymod.api.Laby;
import org.lwjgl.system.MemoryStack;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.AbstractFileDialogs;

@Singleton
@Implements(FileDialogs.class)
public class LWJGL3FileDialogs extends AbstractFileDialogs
{
    @Override
    public void open(@Nullable final String title, @Nullable final Path defaultPath, @Nullable final String fileDescription, @NotNull final String[] extensions, final boolean allowMultipleSelections, @NotNull final Consumer<Path[]> callback) {
        if (extensions != null) {
            for (final String extension : extensions) {
                if (!extension.matches("^[a-zA-Z0-9]*$")) {
                    throw new IllegalArgumentException("Extension doesn't match a-zA-Z0-9");
                }
            }
        }
        this.executor.execute(() -> {
            final MemoryStack stack = MemoryStack.stackPush();
            try {
                PointerBuffer filterPatterns = null;
                if (extensions != null && extensions.length != 0) {
                    filterPatterns = stack.mallocPointer(extensions.length);
                    int j = 0;
                    for (int length2 = extensions.length; j < length2; ++j) {
                        final String extension2 = extensions[j];
                        filterPatterns.put(stack.UTF8("*." + extension2));
                    }
                    filterPatterns.flip();
                }
                final MinecraftOptions options = Laby.labyAPI().minecraft().options();
                if (options.isFullscreen()) {
                    options.setFullscreen(false);
                }
                final String path = TinyFileDialogs.tinyfd_openFileDialog((CharSequence)title, (CharSequence)((defaultPath != null) ? defaultPath.toAbsolutePath().toString() : null), filterPatterns, (CharSequence)fileDescription, allowMultipleSelections);
                Path[] array;
                if (path == null) {
                    array = new Path[0];
                }
                else {
                    array = new Path[] { Paths.get(path, new String[0]) };
                }
                callback.accept(array);
                if (stack != null) {
                    stack.close();
                }
            }
            catch (final Throwable t) {
                if (stack != null) {
                    try {
                        stack.close();
                    }
                    catch (final Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        });
    }
}
