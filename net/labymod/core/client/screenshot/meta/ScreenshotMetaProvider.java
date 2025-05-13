// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot.meta;

import java.util.Iterator;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import java.util.HashMap;
import java.util.Map;
import java.io.RandomAccessFile;

public class ScreenshotMetaProvider
{
    private static final int VERSION = 1;
    private RandomAccessFile file;
    private final Map<String, Long> offsets;
    
    public ScreenshotMetaProvider() {
        this.offsets = new HashMap<String, Long>();
    }
    
    public void load() {
        try {
            final File file = IOUtil.toFile(Constants.Files.SCREENSHOT_META_CACHE);
            final File directory = file.getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            (this.file = new RandomAccessFile(file, "rw")).seek(0L);
            final int version = (this.file.length() == 0L) ? 0 : this.file.readInt();
            if (version == 1) {
                while (this.file.getFilePointer() < this.file.length()) {
                    final long offset = this.file.getFilePointer();
                    final long length = this.file.readLong();
                    final byte[] fileNameBytes = new byte[this.file.readInt()];
                    this.file.readFully(fileNameBytes);
                    final String name = new String(fileNameBytes);
                    this.offsets.put(name, offset);
                    this.file.seek(offset + length + 8L);
                }
            }
            else {
                this.file.setLength(0L);
                this.file.writeInt(1);
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public ScreenshotMeta provide(final Path path) throws IOException {
        final Long offset = this.offsets.get(ScreenshotMeta.identifierOfPath(path));
        if (offset != null) {
            return this.readMetaAtOffset(offset);
        }
        final ScreenshotMeta meta = new ScreenshotMeta(path);
        this.writeMetaAtOffset(meta);
        return meta;
    }
    
    private ScreenshotMeta readMetaAtOffset(final long offset) throws IOException {
        this.file.seek(offset);
        final long length = this.file.readLong();
        final byte[] fileNameBytes = new byte[this.file.readInt()];
        this.file.readFully(fileNameBytes);
        final String name = new String(fileNameBytes);
        final long time = this.file.readLong();
        final ScreenshotMeta meta = new ScreenshotMeta(name, time);
        for (int attributeCount = this.file.readInt(), i = 0; i < attributeCount; ++i) {
            final byte[] keyBytes = new byte[this.file.readInt()];
            this.file.read(keyBytes);
            final String key = new String(keyBytes);
            final byte[] valueBytes = new byte[this.file.readInt()];
            this.file.read(valueBytes);
            final String value = new String(valueBytes);
            meta.set(key, value);
        }
        return meta;
    }
    
    private void writeMetaAtOffset(final ScreenshotMeta meta) throws IOException {
        this.file.seek(this.file.length());
        long length = 0L;
        length += 4 + meta.getIdentifier().getBytes().length;
        length += 8L;
        length += 4L;
        for (final Map.Entry<String, String> entry : meta.getAttributes().entrySet()) {
            length += 4 + entry.getKey().getBytes().length;
            length += 4 + entry.getValue().getBytes().length;
        }
        this.file.writeLong(length);
        final byte[] nameBytes = meta.getIdentifier().getBytes();
        this.file.writeInt(nameBytes.length);
        this.file.write(nameBytes);
        this.file.writeLong(meta.getTimestamp());
        this.file.writeInt(meta.getAttributes().size());
        for (final Map.Entry<String, String> entry2 : meta.getAttributes().entrySet()) {
            final byte[] keyBytes = entry2.getKey().getBytes();
            this.file.writeInt(keyBytes.length);
            this.file.write(keyBytes);
            final byte[] valueBytes = entry2.getValue().getBytes();
            this.file.writeInt(valueBytes.length);
            this.file.write(valueBytes);
        }
    }
    
    public void close() throws IOException {
        this.file.close();
    }
    
    public void flush() {
        try {
            this.file.getFD().sync();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
