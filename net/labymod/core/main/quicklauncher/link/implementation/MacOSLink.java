// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.quicklauncher.link.implementation;

import java.io.IOException;
import java.util.Set;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Locale;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import net.labymod.core.main.quicklauncher.link.Link;

public class MacOSLink implements Link
{
    @Override
    public Path create(final String id, final Path backendDirectory, final String[] command, final String displayName, final BufferedImage icon) throws IOException {
        final Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
        Path commandFilePath = desktopPath.resolve(String.format(Locale.ROOT, "%s.command", displayName));
        for (int i = 1; Files.exists(commandFilePath, new LinkOption[0]); commandFilePath = desktopPath.resolve(String.format(Locale.ROOT, "%s (%d).command", displayName, i)), ++i) {}
        this.writeToFile(commandFilePath, this.buildCommand(command, "'", " "));
        try {
            final Set<PosixFilePermission> permissions = new HashSet<PosixFilePermission>();
            permissions.add(PosixFilePermission.OWNER_EXECUTE);
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            Files.setPosixFilePermissions(commandFilePath, permissions);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return commandFilePath;
    }
}
