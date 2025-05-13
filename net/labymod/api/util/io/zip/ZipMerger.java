// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip;

import java.io.InputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.util.Enumeration;
import java.util.Iterator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Objects;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;
import java.nio.file.Path;
import java.util.List;

public class ZipMerger
{
    private final List<Path> sources;
    private final List<Predicate<String>> excludes;
    private final List<String> entries;
    private final Map<String, List<String>> serviceFiles;
    private Path destination;
    
    public ZipMerger() {
        this.sources = new ArrayList<Path>();
        this.excludes = new ArrayList<Predicate<String>>();
        this.entries = new ArrayList<String>();
        this.serviceFiles = new HashMap<String, List<String>>();
    }
    
    public ZipMerger destination(final Path destination) {
        this.destination = destination;
        return this;
    }
    
    public ZipMerger source(final Path source) {
        this.sources.add(source);
        return this;
    }
    
    public ZipMerger source(final Path... sources) {
        Collections.addAll(this.sources, sources);
        return this;
    }
    
    public ZipMerger exclude(final Predicate<String> filter) {
        this.excludes.add(filter);
        return this;
    }
    
    public ZipMerger exclude(final Predicate<String>... filters) {
        Collections.addAll(this.excludes, filters);
        return this;
    }
    
    public void merge() {
        Objects.requireNonNull(this.destination);
        try (final ZipOutputStream jos = new ZipOutputStream(Files.newOutputStream(this.destination, new OpenOption[0]))) {
            for (final Path source : this.sources) {
                try (final ZipFile zipFile = new ZipFile(source.toFile())) {
                    final Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        final ZipEntry entry = (ZipEntry)entries.nextElement();
                        if (this.shouldExclude(entry.getName())) {
                            continue;
                        }
                        this.writeEntry(jos, zipFile, entry);
                    }
                }
            }
            for (final Map.Entry<String, List<String>> entry2 : this.serviceFiles.entrySet()) {
                final String name = entry2.getKey();
                final List<String> value = entry2.getValue();
                final JarEntry newEntry = new JarEntry(name);
                final String newFileContent = this.mergeContent(name, value);
                if (newFileContent == null) {
                    continue;
                }
                jos.putNextEntry(newEntry);
                jos.write(newFileContent.getBytes(StandardCharsets.UTF_8));
            }
        }
        catch (final IOException exception) {
            throw new IllegalStateException("Zip could not be merged.", exception);
        }
    }
    
    private String mergeContent(final String name, final List<String> data) {
        final boolean isJson = name.endsWith(".json");
        final boolean isManifest = name.endsWith("MANIFEST.MF");
        final JsonArray array = new JsonArray();
        final List<String> mergedLines = new ArrayList<String>();
        for (final String content : data) {
            if (isJson) {
                final JsonElement element = JsonParser.parseString(content);
                if (!element.isJsonArray()) {
                    throw new IllegalStateException("Json cannot be merged because it is not an array. " + String.valueOf(element));
                }
                for (final JsonElement arrayElement : element.getAsJsonArray()) {
                    array.add(arrayElement);
                }
            }
            else {
                final String[] split;
                final String[] lines = split = content.split(System.lineSeparator());
                for (final String line : split) {
                    if (!mergedLines.contains(line)) {
                        if (isManifest) {
                            if (!line.startsWith("Minecraft-")) {
                                if (!line.isBlank()) {
                                    mergedLines.add(line);
                                }
                            }
                        }
                        else {
                            mergedLines.add(line);
                        }
                    }
                }
            }
        }
        if (isJson) {
            return (array.size() == 0) ? null : array.toString();
        }
        return String.join(System.lineSeparator(), mergedLines);
    }
    
    private boolean shouldExclude(final String name) {
        for (final Predicate<String> exclude : this.excludes) {
            if (exclude.test(name)) {
                return true;
            }
        }
        return false;
    }
    
    private void writeEntry(final ZipOutputStream outputStream, final ZipFile file, final ZipEntry entry) throws IOException {
        final String name = entry.getName();
        if (this.entries.contains(name)) {
            return;
        }
        if (name.startsWith("META-INF/")) {
            this.serviceFiles.computeIfAbsent(name, l -> new ArrayList()).add(new String(this.readInputStream(file, entry)));
            return;
        }
        final byte[] data = this.readInputStream(file, entry);
        outputStream.putNextEntry(entry);
        outputStream.write(data);
        this.entries.add(name);
    }
    
    private byte[] readInputStream(final ZipFile file, final ZipEntry entry) throws IOException {
        try (final InputStream stream = file.getInputStream(entry)) {
            return stream.readAllBytes();
        }
    }
}
