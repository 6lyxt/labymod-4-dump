// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.index;

import java.util.Locale;
import java.util.Iterator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import net.labymod.core.util.jsonfilecache.DefaultJsonFileCacheFactory;
import net.labymod.api.Constants;
import net.labymod.core.flint.FlintUrls;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import com.google.gson.JsonArray;
import net.labymod.api.util.JsonFileCache;
import java.text.SimpleDateFormat;

public class FlintIndexLoader
{
    private static FlintIndexLoader instance;
    public static final SimpleDateFormat DATE_FORMAT;
    private final JsonFileCache<JsonArray> indexFileCache;
    private ResultCallback<JsonArray> callback;
    private Result<JsonArray> latestIndex;
    
    private FlintIndexLoader() {
        final String releaseChannel = FlintUrls.getCurrentReleaseChannel();
        Path path;
        if (releaseChannel.equals("production")) {
            path = Constants.Files.FILE_CACHE.resolve("index.json");
        }
        else {
            path = Constants.Files.FILE_CACHE.resolve("addons-" + releaseChannel + ".json");
        }
        this.indexFileCache = DefaultJsonFileCacheFactory.createJsonFileCache(path, String.format("https://flintmc.net/api/client-store/get-index/%s", FlintUrls.getCurrentReleaseChannel()), "index", JsonArray.class).readLastModifiedDateFromHeader("Last-Modified", FlintIndexLoader.DATE_FORMAT);
        this.setupIndex();
    }
    
    public static FlintIndexLoader getInstance() {
        if (FlintIndexLoader.instance == null) {
            FlintIndexLoader.instance = new FlintIndexLoader();
        }
        return FlintIndexLoader.instance;
    }
    
    private void setupIndex() {
        this.indexFileCache.read(false, this::handleResult);
    }
    
    public Result<JsonArray> getLatestIndex() {
        return (this.latestIndex == null) ? Result.empty() : this.latestIndex;
    }
    
    public void addCallback(final ResultCallback<JsonArray> callback) {
        this.callback = callback;
        if (this.latestIndex != null) {
            this.callback.accept(this.latestIndex);
        }
    }
    
    public JsonFileCache<JsonArray> getIndexFileCache() {
        return this.indexFileCache;
    }
    
    public void handleResult(final Result<JsonArray> result) {
        if (result.isPresent()) {
            this.latestIndex = result;
            if (this.callback != null) {
                this.callback.accept(result);
            }
        }
    }
    
    public JsonObject getModificationObject(final String namespace) {
        if (!this.latestIndex.isPresent()) {
            return null;
        }
        for (final JsonElement jsonElement : this.latestIndex.get()) {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get("namespace").getAsString().equals(namespace)) {
                return jsonObject;
            }
        }
        return null;
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    }
}
