// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.nio.file.OpenOption;
import net.labymod.api.util.GsonUtil;
import java.nio.file.Files;
import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Response;
import java.util.List;
import com.google.gson.JsonArray;
import net.labymod.api.util.io.web.request.Request;
import java.nio.file.Path;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.logging.Logging;

public class FlintDefaultModifications
{
    private static final String URL = "https://releases.labymod.net/api/v1/flint/default-modifications";
    private static final FlintDefaultModifications INSTANCE;
    private static final Logging LOGGER;
    private static final Lazy<DefaultWebResolver> WEB_RESOLVER;
    @Deprecated
    private final Path deletionInfoPath;
    private final Path installedInfoPath;
    private final Request<JsonArray> request;
    private final List<String> defaultModifications;
    private Response<JsonArray> response;
    
    private FlintDefaultModifications() {
        this.deletionInfoPath = Constants.Files.ADDONS.resolve("deleted-default-modifications.json");
        this.installedInfoPath = Constants.Files.ADDONS.resolve("installed-default-modifications.json");
        this.request = Request.ofGson(JsonArray.class).url("https://releases.labymod.net/api/v1/flint/default-modifications", new Object[0]).userAgent("LabyMod4");
        this.defaultModifications = new ArrayList<String>();
    }
    
    public static FlintDefaultModifications instance() {
        return FlintDefaultModifications.INSTANCE;
    }
    
    public void loadDefaultAddons() {
        this.response = FlintDefaultModifications.WEB_RESOLVER.get().resolveConnection(this.request);
        if (!this.response.isPresent()) {
            return;
        }
        this.defaultModifications.clear();
        for (final JsonElement jsonElement : this.response.get()) {
            if (!jsonElement.isJsonPrimitive()) {
                continue;
            }
            final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (!jsonPrimitive.isString()) {
                continue;
            }
            this.defaultModifications.add(jsonPrimitive.getAsString());
        }
    }
    
    public boolean hasInstalledBefore(final String namespace) {
        final JsonArray installedModifications = this.getInstalledModifications();
        for (final JsonElement installedModification : installedModifications) {
            if (!installedModification.isJsonPrimitive()) {
                continue;
            }
            final JsonPrimitive jsonPrimitive = installedModification.getAsJsonPrimitive();
            if (jsonPrimitive.isString() && jsonPrimitive.getAsString().equals(namespace)) {
                return true;
            }
        }
        return false;
    }
    
    public void install(final String namespace) {
        if (!this.isDefaultAddon(namespace)) {
            return;
        }
        final JsonArray deletionInfo = this.getInstalledModifications();
        for (final JsonElement jsonElement : deletionInfo) {
            if (!jsonElement.isJsonPrimitive()) {
                continue;
            }
            final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (!jsonPrimitive.isString()) {
                continue;
            }
            if (jsonPrimitive.getAsString().equals(namespace)) {
                return;
            }
        }
        deletionInfo.add((JsonElement)new JsonPrimitive(namespace));
        this.saveInstalledModificationInfo(deletionInfo);
    }
    
    public Response<JsonArray> getResponse() {
        return this.response;
    }
    
    public List<String> getDefaultModifications() {
        return this.defaultModifications;
    }
    
    public boolean isDefaultAddon(final String namespace) {
        return this.defaultModifications.contains(namespace);
    }
    
    private JsonArray getInstalledModifications() {
        if (IOUtil.exists(this.installedInfoPath)) {
            return this.readJsonArrayFromPath(this.installedInfoPath);
        }
        if (IOUtil.exists(this.deletionInfoPath)) {
            final JsonArray jsonArray = this.readJsonArrayFromPath(this.deletionInfoPath);
            this.saveInstalledModificationInfo(jsonArray);
            try {
                IOUtil.delete(this.deletionInfoPath);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
            return jsonArray;
        }
        return new JsonArray();
    }
    
    private JsonArray readJsonArrayFromPath(final Path path) {
        try {
            final String content = new String(Files.readAllBytes(path));
            return (JsonArray)GsonUtil.DEFAULT_GSON.fromJson(content, (Class)JsonArray.class);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
            return new JsonArray();
        }
    }
    
    private void saveInstalledModificationInfo(final JsonArray jsonArray) {
        try {
            if (!IOUtil.exists(this.installedInfoPath)) {
                IOUtil.createFile(this.installedInfoPath);
            }
            Files.write(this.installedInfoPath, GsonUtil.DEFAULT_GSON.toJson((JsonElement)jsonArray).getBytes(), new OpenOption[0]);
            IOUtil.hideFileInWindowsFileSystem(this.installedInfoPath);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    static {
        INSTANCE = new FlintDefaultModifications();
        LOGGER = DefaultLoggingFactory.createLogger(FlintDefaultModifications.class);
        WEB_RESOLVER = Lazy.of(DefaultWebResolver::instance);
    }
}
