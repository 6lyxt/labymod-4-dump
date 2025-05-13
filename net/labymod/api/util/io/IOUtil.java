// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io;

import java.nio.file.FileStore;
import javax.imageio.ImageIO;
import java.util.Base64;
import java.awt.image.BufferedImage;
import java.util.jar.JarFile;
import java.nio.file.CopyOption;
import java.net.ServerSocket;
import java.nio.file.OpenOption;
import java.nio.file.FileAlreadyExistsException;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.models.OperatingSystem;
import java.nio.file.Path;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;

public final class IOUtil
{
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    
    private IOUtil() {
    }
    
    @Contract("_ -> new")
    @NotNull
    public static String toString(final InputStream inputStream) throws IOException {
        return toString(inputStream, StandardCharsets.UTF_8);
    }
    
    @Contract("_, _ -> new")
    @NotNull
    public static String toString(@NotNull final InputStream inputStream, final Charset encoding) throws IOException {
        return new String(readBytes(inputStream), encoding);
    }
    
    public static byte[] readBytes(@NotNull final InputStream inputStream) throws IOException {
        return readBytes(inputStream, true);
    }
    
    public static byte[] readBytes(@NotNull final InputStream inputStream, final boolean close) throws IOException {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final byte[] data = new byte[1024];
            int readable;
            while ((readable = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, readable);
            }
            outputStream.flush();
            if (close) {
                inputStream.close();
            }
            return outputStream.toByteArray();
        }
    }
    
    public static void writeBytes(@NotNull final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final byte[] data = new byte[1024];
        int readable;
        while ((readable = inputStream.read(data, 0, data.length)) != -1) {
            outputStream.write(data, 0, readable);
        }
        outputStream.flush();
    }
    
    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public static InputStream writeBytes(final byte[] data) {
        return new ByteArrayInputStream(data);
    }
    
    public static void hideFileInWindowsFileSystem(final Path path) {
        try {
            if (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) {
                Files.setAttribute(path, "dos:hidden", true, new LinkOption[0]);
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean exists(final Path path) {
        if (path.getFileSystem() == FileSystems.getDefault()) {
            return Files.exists(path, new LinkOption[0]);
        }
        return toFile(path).exists();
    }
    
    public static boolean isDirectory(final Path path) {
        return toFile(path).isDirectory();
    }
    
    public static void createDirectories(final Path path) throws IOException {
        if (exists(path)) {
            return;
        }
        final File file = toFile(path);
        if (!file.mkdirs()) {
            throw new IOException("Failed to create directories");
        }
    }
    
    public static void createFile(final Path path) throws IOException {
        if (exists(path)) {
            throw new FileAlreadyExistsException("File already exists");
        }
        final File file = toFile(path);
        if (!file.createNewFile()) {
            throw new IOException("Failed to create file");
        }
    }
    
    public static void delete(final Path path) throws IOException {
        Files.delete(path);
    }
    
    public static boolean deleteIfExits(final Path path) throws IOException {
        return Files.deleteIfExists(path);
    }
    
    public static InputStream newInputStream(final Path path) throws IOException {
        return Files.newInputStream(path, new OpenOption[0]);
    }
    
    public static OutputStream newOutputStream(final Path path) throws IOException {
        return Files.newOutputStream(path, new OpenOption[0]);
    }
    
    public static long size(final Path path) {
        return toFile(path).length();
    }
    
    public static int getFreePort() throws IOException {
        try (final ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
    
    public static File toFile(final Path path) {
        return path.toFile().getAbsoluteFile();
    }
    
    public static void copyAndDelete(final Path source, final Path target, final CopyOption... options) throws IOException {
        Files.copy(source, target, options);
        Files.deleteIfExists(source);
    }
    
    public static boolean isCorrupted(final File file) {
        try (final JarFile jarFile = new JarFile(file)) {
            return false;
        }
        catch (final IOException exception) {
            return true;
        }
    }
    
    public static boolean isCorrupted(final Path path) {
        return isCorrupted(toFile(path));
    }
    
    public static BufferedImage base64ToBufferedImage(final String base64) throws IOException, IllegalArgumentException {
        final byte[] buffer = Base64.getDecoder().decode(base64.substring(base64.indexOf(",") + 1));
        try (final ByteArrayInputStream input = new ByteArrayInputStream(buffer)) {
            return ImageIO.read(input);
        }
    }
    
    public static void closeSilent(final InputStream stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        }
        catch (final IOException ex) {}
    }
    
    public static InputStream copyStream(final InputStream stream) throws IOException {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            stream.transferTo(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (final IOException exception) {
            throw new IOException("Stream could not be copied.", exception);
        }
    }
    
    public static long getAvailableSpace(final Path file) {
        try {
            return getUsableSpace(file);
        }
        catch (final Exception exception) {
            return Long.MAX_VALUE;
        }
    }
    
    public static long getUsableSpace(final Path file) throws IOException {
        if (!exists(file)) {
            createFile(file);
        }
        final FileStore store = Files.getFileStore(file);
        return store.getUsableSpace();
    }
    
    public static String getFileStoreName(final Path file) {
        final Path normalizedPath = file.toAbsolutePath().normalize();
        final Path root = normalizedPath.getRoot();
        if (root == null) {
            return normalizedPath.toString().substring(0, 3);
        }
        return root.toString();
    }
}
