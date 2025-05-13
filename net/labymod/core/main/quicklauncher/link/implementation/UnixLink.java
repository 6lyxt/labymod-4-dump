// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.quicklauncher.link.implementation;

import java.io.IOException;
import java.util.Locale;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import net.labymod.core.main.quicklauncher.link.Link;

public class UnixLink implements Link
{
    @Override
    public Path create(final String id, final Path backendDirectory, final String[] command, final String displayName, final BufferedImage icon) throws IOException {
        final Path homePath = Paths.get(System.getProperty("user.home"), new String[0]);
        final Path desktopPath = homePath.resolve("Desktop");
        Path scriptPath = (Files.exists(desktopPath, new LinkOption[0]) ? desktopPath : homePath).resolve(String.format(Locale.ROOT, "%s.sh", displayName));
        for (int i = 1; Files.exists(scriptPath, new LinkOption[0]); scriptPath = desktopPath.resolve(String.format(Locale.ROOT, "%s (%d).sh", displayName, i)), ++i) {}
        this.writeToFile(scriptPath, this.buildCommand(command, "'", " "));
        return scriptPath;
    }
}
