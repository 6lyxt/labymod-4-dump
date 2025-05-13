// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.io.IOException;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;
import java.nio.file.Path;

public class FileRequest extends AbstractFileRequest<Path, FileRequest>
{
    private FileRequest() {
        super(null, null);
    }
    
    protected FileRequest(@NotNull final Path path, @Nullable final Consumer<Double> percentageConsumer) {
        this();
        Objects.requireNonNull(path, "Path cannot be null");
        this.path = path;
        this.percentageConsumer = percentageConsumer;
    }
    
    public static FileRequest of(@NotNull final Path path) {
        return new FileRequest(path, null);
    }
    
    public static FileRequest of(@NotNull final Path path, @Nullable final Consumer<Double> percentageConsumer) {
        return new FileRequest(path, percentageConsumer);
    }
    
    @Override
    protected Path handle(final Response<Path> response, final WebInputStream inputStream) throws IOException {
        return ((AbstractFileRequest<Path, R>)this).download(response, inputStream);
    }
    
    @Override
    protected FileRequest prepareCopy() {
        return this.applyRequestDataTo(new FileRequest());
    }
}
