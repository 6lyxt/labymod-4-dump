// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip;

import java.util.Enumeration;
import java.util.zip.ZipFile;
import net.labymod.api.util.io.IOUtil;
import java.util.zip.ZipInputStream;
import java.util.Objects;
import java.io.IOException;
import java.util.zip.ZipEntry;
import net.labymod.api.util.function.ThrowableBiFunction;
import java.io.InputStream;
import java.util.function.Consumer;
import java.nio.file.Path;
import java.io.File;

public final class Zips
{
    private Zips() {
    }
    
    public static ZipTransformer createDefaultTransformer(final File source, final File destination) {
        return ZipTransformer.createDefault(source.toPath(), destination.toPath());
    }
    
    public static ZipTransformer createDefaultTransformer(final Path source, final Path destination) {
        return ZipTransformer.createDefault(source, destination);
    }
    
    public static ZipTransformer createDefaultTransformer(final File source, final File destination, final Consumer<ZipTransformer> consumer) {
        return ZipTransformer.createDefault(source.toPath(), destination.toPath(), consumer);
    }
    
    public static ZipTransformer createDefaultTransformer(final Path source, final Path destination, final Consumer<ZipTransformer> consumer) {
        return ZipTransformer.createDefault(source, destination, consumer);
    }
    
    public static void readStream(final InputStream stream, final ThrowableBiFunction<ZipEntry, byte[], Boolean, IOException> consumer) throws ZipException {
        Objects.requireNonNull(consumer, "consumer must not be null");
        try (final ZipInputStream zipInputStream = new ZipInputStream(stream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null && !consumer.apply(entry, zipInputStream.readAllBytes())) {
                zipInputStream.closeEntry();
            }
        }
        catch (final IOException exception) {
            throw new ZipException(null, exception);
        }
    }
    
    public static void read(final Path source, final ThrowableBiFunction<ZipEntry, byte[], Boolean, IOException> consumer) throws ZipException {
        Objects.requireNonNull(consumer, "consumer must not be null");
        try (final ZipFile zipFile = new ZipFile(IOUtil.toFile(source))) {
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = (ZipEntry)entries.nextElement();
                if (consumer.apply(entry, IOUtil.readBytes(zipFile.getInputStream(entry)))) {
                    break;
                }
            }
        }
        catch (final IOException exception) {
            throw new ZipException(null, exception);
        }
    }
}
