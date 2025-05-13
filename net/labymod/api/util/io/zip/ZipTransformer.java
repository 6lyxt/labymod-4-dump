// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip;

import java.util.Iterator;
import java.io.InputStream;
import java.util.Enumeration;
import java.io.IOException;
import java.util.Locale;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.zip.ZipFile;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.function.Consumers;
import java.util.function.Consumer;
import net.labymod.api.util.io.zip.entry.factory.ManifestEntryFactory;
import net.labymod.api.util.io.zip.entry.factory.ClassEntryFactory;
import java.util.ArrayList;
import java.util.Objects;
import net.labymod.api.util.io.zip.entry.Entry;
import java.util.zip.ZipEntry;
import java.util.function.BiFunction;
import net.labymod.api.property.Property;
import net.labymod.api.util.io.zip.entry.NewEntry;
import net.labymod.api.util.io.zip.entry.factory.EntryFactory;
import java.util.List;
import java.nio.file.Path;
import net.labymod.api.util.io.zip.entry.factory.ResourceEntryFactory;

public class ZipTransformer
{
    private static final ResourceEntryFactory FALLBACK_ENTRY_FACTORY;
    private final Path source;
    private final Path destination;
    private final List<EntryTransformer<?>> entryTransformers;
    private final List<EntryFactory<?>> entryFactories;
    private final List<NewEntry> newEntries;
    private final Property<BiFunction<ZipEntry, byte[], Entry>> fallbackFunction;
    
    private ZipTransformer(final Path source, final Path destination) {
        final ResourceEntryFactory fallback_ENTRY_FACTORY = ZipTransformer.FALLBACK_ENTRY_FACTORY;
        Objects.requireNonNull(fallback_ENTRY_FACTORY);
        this.fallbackFunction = new Property<BiFunction<ZipEntry, byte[], Entry>>(fallback_ENTRY_FACTORY::create);
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(destination, "destination must not be null");
        this.source = source;
        this.destination = destination;
        this.entryTransformers = new ArrayList<EntryTransformer<?>>();
        this.entryFactories = new ArrayList<EntryFactory<?>>();
        this.newEntries = new ArrayList<NewEntry>();
    }
    
    public static ZipTransformer createDefault(final Path source, final Path destination) {
        return createDefault(source, destination, zipTransformer -> zipTransformer.addEntryFactory((EntryFactory<Entry>)new ClassEntryFactory()).addEntryFactory((EntryFactory<Entry>)new ManifestEntryFactory()));
    }
    
    public static ZipTransformer createDefault(final Path source, final Path destination, final Consumer<ZipTransformer> configureZipConsumer) {
        final ZipTransformer zipTransformer = new ZipTransformer(source, destination);
        Consumers.accept(configureZipConsumer, zipTransformer);
        return zipTransformer;
    }
    
    public <T extends Entry<T>> ZipTransformer addTransformer(final EntryTransformer<T> transformer) {
        Objects.requireNonNull(transformer, "transformer must not be null");
        this.entryTransformers.add(transformer);
        return this;
    }
    
    public <T extends Entry<T>> ZipTransformer addEntryFactory(final EntryFactory<T> factory) {
        Objects.requireNonNull(factory, "factory must not be null");
        this.entryFactories.add(factory);
        return this;
    }
    
    public ZipTransformer fallbackEntry(final BiFunction<ZipEntry, byte[], Entry> fallbackFunction) {
        this.fallbackFunction.set(fallbackFunction);
        return this;
    }
    
    public ZipTransformer addNewEntry(final NewEntry entry) {
        this.newEntries.add(entry);
        return this;
    }
    
    public void transform() throws ZipException {
        try (final ZipFile zipFile = new ZipFile(IOUtil.toFile(this.source));
             final ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(this.destination, new OpenOption[0]))) {
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry zipEntry = (ZipEntry)entries.nextElement();
                final String name = zipEntry.getName();
                Entry entry = null;
                byte[] data;
                try (final InputStream stream = zipFile.getInputStream(zipEntry)) {
                    data = IOUtil.readBytes(stream);
                }
                for (final EntryFactory<?> entryFactory : this.entryFactories) {
                    if (entryFactory.shouldCreate(name)) {
                        entry = (Entry)entryFactory.create(zipEntry, data);
                        break;
                    }
                }
                if (entry == null) {
                    entry = this.fallbackFunction.getOrDefault().apply(zipEntry, data);
                }
                for (final EntryTransformer entryTransformer : this.entryTransformers) {
                    if (!entryTransformer.canProcess(entry)) {
                        continue;
                    }
                    entry = entry.process(entryTransformer);
                    if (entry == null) {
                        break;
                    }
                }
                if (entry == null) {
                    continue;
                }
                this.writeEntry(outputStream, entry);
            }
            for (final NewEntry newEntry : this.newEntries) {
                this.writeEntry(outputStream, newEntry);
            }
        }
        catch (final IOException exception) {
            throw new ZipException(String.format(Locale.ROOT, "Zip could not be transformed (Source: %s | Destination: %s)", this.source.toAbsolutePath(), this.destination.toAbsolutePath()), exception);
        }
    }
    
     <T extends Entry<T>> void writeEntry(final ZipOutputStream outputStream, final T entry) throws IOException {
        final ZipEntry zipEntry = new ZipEntry(entry.getName());
        zipEntry.setTime(entry.getTime());
        outputStream.putNextEntry(zipEntry);
        outputStream.write(entry.getData());
    }
    
    static {
        FALLBACK_ENTRY_FACTORY = new ResourceEntryFactory();
    }
}
