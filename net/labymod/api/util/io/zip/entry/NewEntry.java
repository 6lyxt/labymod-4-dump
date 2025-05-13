// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;
import net.labymod.api.util.time.TimeUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewEntry extends AbstractEntry<NewEntry>
{
    private NewEntry(final String name, final long time, final byte[] data) {
        super(name, time, data);
    }
    
    public static NewEntry create(final String name, final Path path, final long time) throws IOException {
        return new NewEntry(name, time, Files.readAllBytes(path));
    }
    
    public static NewEntry create(final Path path, final long time) throws IOException {
        return create(path.getFileName().toString(), path, time);
    }
    
    public static NewEntry create(final String name, final Path path) throws IOException {
        return create(name, path, TimeUtil.getCurrentTimeMillis());
    }
    
    public static NewEntry create(final Path path) throws IOException {
        return create(path, TimeUtil.getCurrentTimeMillis());
    }
    
    public static NewEntry create(final String name, final long time, final byte[] data) {
        return new NewEntry(name, time, data);
    }
    
    @Override
    public NewEntry process(final EntryTransformer<NewEntry> transformer) {
        throw new UnsupportedOperationException("An new entry cannot be transformed");
    }
}
