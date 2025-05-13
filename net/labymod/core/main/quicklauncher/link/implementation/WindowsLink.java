// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.quicklauncher.link.implementation;

import java.io.OutputStream;
import java.io.BufferedOutputStream;
import net.labymod.api.util.io.IOUtil;
import java.awt.image.ImageObserver;
import java.awt.Image;
import mslinks.ShellLink;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Locale;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import net.labymod.core.main.quicklauncher.link.Link;

public class WindowsLink implements Link
{
    @Override
    public Path create(final String id, final Path backendDirectory, final String[] command, final String displayName, final BufferedImage icon) throws IOException {
        final Path batPath = backendDirectory.resolve(String.format(Locale.ROOT, "%s.bat", id));
        final String content = this.buildCommand(command, "\"", " ");
        this.writeToFile(batPath, content);
        final Path icoPath = backendDirectory.resolve(String.format(Locale.ROOT, "%s.ico", id));
        this.writeIco(icoPath, icon);
        final Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
        Path lnkPath = desktopPath.resolve(String.format(Locale.ROOT, "%s.lnk", displayName));
        for (int i = 1; Files.exists(lnkPath, new LinkOption[0]); lnkPath = desktopPath.resolve(String.format(Locale.ROOT, "%s (%d).lnk", displayName, i)), ++i) {}
        this.writeLnkFile(lnkPath, batPath, icoPath);
        return lnkPath;
    }
    
    public void writeLnkFile(final Path lnkPath, final Path targetPath, final Path icoPath) throws IOException {
        final ShellLink link = new ShellLink().setIconLocation(icoPath.toAbsolutePath().toString());
        link.setTarget(targetPath.toAbsolutePath().toString());
        link.saveTo(lnkPath.toAbsolutePath().toString());
    }
    
    private void writeIco(final Path path, BufferedImage image) throws IOException {
        if (image.getWidth() > 64 || image.getHeight() > 64) {
            final BufferedImage resizedImage = new BufferedImage(64, 64, 2);
            resizedImage.getGraphics().drawImage(image, 0, 0, 64, 64, null);
            image = resizedImage;
        }
        try (final OutputStream os = IOUtil.newOutputStream(path)) {
            final BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(new byte[] { 0, 0, 1, 0, 1, 0 });
            bos.write(new byte[] { 16, 0, 16, 0, 1, 0, 32, 32 });
            final int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            os.write(new byte[] { 66, 77 });
            os.write(intToBytes(54 + 4 * pixels.length));
            os.write(new byte[] { 0, 0 });
            os.write(new byte[] { 0, 0 });
            os.write(intToBytes(54));
            os.write(intToBytes(40));
            os.write(intToBytes(image.getWidth()));
            os.write(intToBytes(image.getHeight()));
            os.write(new byte[] { 1, 0 });
            os.write(new byte[] { 32, 0 });
            os.write(new byte[] { 0, 0, 0, 0 });
            os.write(intToBytes(4 * pixels.length));
            os.write(new byte[] { 19, 11, 0, 0 });
            os.write(new byte[] { 19, 11, 0, 0 });
            os.write(new byte[] { 0, 0, 0, 0 });
            os.write(new byte[] { 0, 0, 0, 0 });
            for (int i = pixels.length - 1; i >= 0; --i) {
                os.write(intToBytes(pixels[i] & 0xFFFFFF));
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    private static byte[] intToBytes(final int value) {
        final byte[] bytes = { (byte)(value & 0xFF), (byte)(value >> 8 & 0xFF), (byte)(value >> 16 & 0xFF), (byte)(value >> 24 & 0xFF) };
        return bytes;
    }
}
