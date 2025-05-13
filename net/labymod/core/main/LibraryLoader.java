// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Path;
import net.labymod.api.models.addon.info.dependency.MavenDependency;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.BuildData;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.nio.file.FileSystems;
import java.util.Locale;
import com.google.gson.JsonElement;
import java.io.IOException;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;

public class LibraryLoader
{
    private static final DefaultWebResolver WEB_RESOLVER;
    private final Logging logger;
    private final PlatformClassloader platformClassloader;
    
    public LibraryLoader() {
        this.logger = DefaultLoggingFactory.createLogger(LibraryLoader.class);
        this.platformClassloader = PlatformEnvironment.getPlatformClassloader();
    }
    
    public void loadLabyModLibrary() {
        JsonObject jsonObject;
        try {
            jsonObject = this.downloadManifest();
        }
        catch (final IOException e) {
            this.logger.error("Could not download manifest", e.getMessage());
            System.exit(1);
            return;
        }
        final JsonArray libraries = jsonObject.get("libraries").getAsJsonArray();
        for (final JsonElement library : libraries) {
            final JsonObject object = library.getAsJsonObject();
            final String name = object.get("name").getAsString();
            final String[] names = name.split(":");
            final String url = object.get("url").getAsString();
            final String path = String.format(Locale.ROOT, "%s;%s;%s", names[0].replace(".", ";"), names[1], names[2]).replace(";", FileSystems.getDefault().getSeparator());
            final String fileName = String.format(Locale.ROOT, "%s-%s.jar", names[1], names[2]);
            try {
                this.downloadAndAddToClassPath(path, fileName, url);
            }
            catch (final IOException e2) {
                this.logger.error("Could not download LabyMod library {} from {}. Cause: {}", name, url, e2.getMessage());
                System.exit(1);
                break;
            }
        }
    }
    
    private JsonObject downloadManifest() throws IOException {
        final Request<JsonObject> request = Request.ofGson(JsonObject.class).url(Constants.Urls.LIBRARY_MANIFEST, new Object[] { BuildData.releaseType() });
        final Response<JsonObject> response = LibraryLoader.WEB_RESOLVER.resolveConnection(request);
        if (response.hasException()) {
            throw response.exception();
        }
        return response.get();
    }
    
    public Path loadMavenDependency(final MavenDependency mavenDependency) throws IOException {
        return this.downloadAndAddToClassPath(mavenDependency.buildFileDictionary(), mavenDependency.buildFileName(), mavenDependency.buildURL());
    }
    
    private Path downloadAndAddToClassPath(final String filePath, final String fileName, final String downloadUrl) throws IOException {
        final Path path = Constants.Files.LIBRARIES.resolve(filePath).resolve(fileName);
        if (!IOUtil.exists(path)) {
            this.logger.info("Downloading library {}...", fileName.replace(".jar", ""));
            this.downloadLibrary(downloadUrl, path);
            this.platformClassloader.addPath(path);
        }
        else if (!this.isLibraryOnClasspath(path)) {
            this.platformClassloader.addPath(path);
        }
        return path;
    }
    
    private boolean isLibraryOnClasspath(final Path path) {
        final String allClassPaths = System.getProperty("java.class.path");
        final String[] split;
        final String[] classPaths = split = allClassPaths.split(";");
        for (final String classPath : split) {
            if (classPath.equals(path.toAbsolutePath().toString())) {
                return true;
            }
        }
        return false;
    }
    
    private void downloadLibrary(final String url, final Path destination) throws IOException {
        IOUtil.createDirectories(destination.getParent());
        final Request<Path> request = ((AbstractRequest<T, Request<Path>>)Request.ofFile(destination)).url(url, new Object[0]);
        final Response<Path> response = LibraryLoader.WEB_RESOLVER.resolveConnection(request);
        if (response.hasException()) {
            throw response.exception();
        }
    }
    
    static {
        WEB_RESOLVER = DefaultWebResolver.instance();
    }
}
