// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter;

import java.nio.file.Paths;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.OpenOption;
import java.nio.file.LinkOption;
import com.google.gson.JsonElement;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import net.labymod.api.util.io.IOUtil;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import com.google.gson.Gson;
import java.nio.file.Path;
import net.labymod.api.util.logging.Logging;

public abstract class LegacyConverter<T>
{
    private static final Logging LOGGER;
    public static final Path LEGACY_PATH;
    protected final Class<? extends T> type;
    protected Path path;
    private T value;
    private Exception exception;
    private boolean loaded;
    protected final Gson gson;
    
    protected LegacyConverter(final String fileName, final Class<? extends T> type) {
        this(LegacyConverter.LEGACY_PATH.resolve(fileName), type);
    }
    
    protected LegacyConverter(final Path path, final Class<? extends T> type) {
        this.path = path;
        this.type = type;
        this.gson = this.createGson();
    }
    
    protected Gson createGson() {
        return new Gson();
    }
    
    public boolean isEmpty() {
        return this.value == null && this.exception == null;
    }
    
    @Nullable
    public T getValue() {
        return this.value;
    }
    
    @Nullable
    public Exception getException() {
        return this.exception;
    }
    
    @NotNull
    public final String getNamespace() {
        return Laby.labyAPI().getNamespace(this);
    }
    
    public boolean wasLoaded() {
        return this.loaded;
    }
    
    public final void load() {
        Objects.requireNonNull(this.path, "Path is null");
        this.loaded = true;
        if (!IOUtil.exists(this.path)) {
            this.value = null;
            this.exception = null;
            return;
        }
        try {
            final String content = new String(Files.readAllBytes(this.path), StandardCharsets.UTF_8);
            final T value = this.fromJson(content, this.type);
            this.load(value);
            this.value = value;
            this.exception = null;
        }
        catch (final Exception e) {
            this.value = null;
            this.exception = e;
        }
    }
    
    public final void convert() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        }
        if (this.value == null) {
            throw new IllegalStateException("Value is null");
        }
        this.convert(this.value);
    }
    
    protected abstract void convert(final T p0) throws Exception;
    
    public abstract boolean hasStuffToConvert() throws Exception;
    
    protected void load(final T value) throws Exception {
    }
    
    protected final <M> M fromJson(final String json, final Class<? extends M> type) {
        return (M)this.gson.fromJson(json, (Class)type);
    }
    
    protected final <M> M fromJson(final JsonElement json, final Class<? extends M> type) {
        return (M)this.gson.fromJson(json, (Class)type);
    }
    
    @Nullable
    protected final <M> M fromJson(final Path path, final Class<? extends M> type) {
        if (Files.notExists(path, new LinkOption[0])) {
            return null;
        }
        try (final Reader reader = new InputStreamReader(Files.newInputStream(path, new OpenOption[0]))) {
            final Object fromJson = this.gson.fromJson(reader, (Class)type);
            return (M)fromJson;
        }
        catch (final IOException e) {
            LegacyConverter.LOGGER.error("Failed to read file {} for legacy converter {} in {}", path, this.getClass().getSimpleName(), this.getNamespace());
            return null;
        }
    }
    
    protected final Map<String, String> loadMinecraftSettings(final Path path) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            return Collections.emptyMap();
        }
        final Map<String, String> map = new HashMap<String, String>();
        for (final String line : Files.readAllLines(path)) {
            final String[] split = line.split(":", 2);
            if (split.length != 2) {
                continue;
            }
            map.put(split[0], split[1]);
        }
        return map;
    }
    
    protected Key getKey(final int keyCode) {
        if (keyCode == -1) {
            return Key.NONE;
        }
        if (keyCode < 0) {
            return KeyMapper.getMouseButton(keyCode + 100, KeyMapper.KeyCodeType.LWJGL);
        }
        return KeyMapper.getKey(keyCode, KeyMapper.KeyCodeType.LWJGL);
    }
    
    static {
        LOGGER = Logging.create(LegacyConverter.class);
        LEGACY_PATH = Paths.get("LabyMod", new String[0]);
    }
}
