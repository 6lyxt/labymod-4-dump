// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.remap;

import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public class JarRemapEntry
{
    private final Path inputPath;
    private final Path outputPath;
    
    public JarRemapEntry(@NotNull final Path inputPath, @NotNull final Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }
    
    @NotNull
    public Path getInputPath() {
        return this.inputPath;
    }
    
    @NotNull
    public Path getOutputPath() {
        return this.outputPath;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final JarRemapEntry that = (JarRemapEntry)object;
        return this.inputPath.equals(that.inputPath) && this.outputPath.equals(that.outputPath);
    }
    
    @Override
    public int hashCode() {
        int result = this.inputPath.hashCode();
        result = 31 * result + this.outputPath.hashCode();
        return result;
    }
}
