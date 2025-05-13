// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.quicklauncher.link;

import java.io.OutputStream;
import net.labymod.api.util.io.IOUtil;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public interface Link
{
    Path create(final String p0, final Path p1, final String[] p2, final String p3, final BufferedImage p4) throws IOException;
    
    default void writeToFile(final Path path, final String content) throws IOException {
        this.writeToFile(path, content.getBytes());
    }
    
    default void writeToFile(final Path path, final byte[] bytes) throws IOException {
        final OutputStream output = IOUtil.newOutputStream(path);
        output.write(bytes);
        output.close();
    }
    
    default String buildCommand(final String[] command, final String wrapped, final String delimiter) {
        final StringBuilder builder = new StringBuilder();
        for (final String arg : command) {
            if (builder.length() > 0) {
                builder.append(delimiter);
            }
            builder.append(wrapped).append(arg).append(wrapped);
        }
        return builder.toString();
    }
}
