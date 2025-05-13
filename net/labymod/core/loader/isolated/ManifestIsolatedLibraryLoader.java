// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.WebResolver;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.math.MathHelper;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.util.logging.Logging;
import java.util.List;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.nio.file.Path;
import net.labymod.core.loader.isolated.util.IsolatedClassLoader;
import java.util.Iterator;
import java.io.InputStream;
import java.net.URL;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import java.io.IOException;
import java.io.Reader;
import net.labymod.api.util.GsonUtil;
import java.io.InputStreamReader;

public class ManifestIsolatedLibraryLoader extends IsolatedLibraryLoader
{
    public void onLoad(final ClassLoader loader) {
        final URL resource = loader.getResource("isolated_libraries.json");
        if (resource == null) {
            return;
        }
        IsolatedLibraryManifest manifest;
        try (final InputStream stream = resource.openStream();
             final InputStreamReader reader = new InputStreamReader(stream)) {
            manifest = (IsolatedLibraryManifest)GsonUtil.DEFAULT_GSON.fromJson((Reader)reader, (Class)IsolatedLibraryManifest.class);
        }
        catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        for (final IsolatedLibraryPredicate predicate : this.predicates) {
            for (final IsolatedLibrary library : manifest.getLibraries()) {
                if (!this.isSameVersion(library)) {
                    continue;
                }
                final boolean filtered = predicate.test(library);
                if (filtered) {
                    continue;
                }
                final IsolatedClassLoader classLoader = this.getClassLoader(library.getGroup());
                final Path libraryPath = Constants.Files.LIBRARIES.resolve(library.getPath());
                if (IOUtil.exists(libraryPath)) {
                    final boolean corrupted = IOUtil.isCorrupted(libraryPath);
                    if (!corrupted) {
                        this.addUrl(classLoader, library);
                        continue;
                    }
                    ManifestIsolatedLibraryLoader.LOGGER.error("{} is a corrupted library", libraryPath.toAbsolutePath());
                    try {
                        IOUtil.deleteIfExits(libraryPath);
                    }
                    catch (final IOException exception2) {
                        ManifestIsolatedLibraryLoader.LOGGER.error("Could not delete library {}", libraryPath, exception2);
                    }
                }
                try {
                    IOUtil.createDirectories(libraryPath.getParent());
                }
                catch (final IOException exception3) {
                    throw new RuntimeException("Failed to create directories", exception3);
                }
                final DownloadTask downloadTask = new DownloadTask(library.getUrl(), libraryPath, () -> this.addUrl(classLoader, library));
                downloadTask.download();
            }
        }
    }
    
    private boolean isSameVersion(final IsolatedLibrary library) {
        final List<String> versions = library.getVersions();
        if (versions.isEmpty()) {
            return true;
        }
        for (final String version : versions) {
            if (version.equals(PlatformEnvironment.getRunningVersion())) {
                return true;
            }
        }
        return false;
    }
    
    private void addUrl(final IsolatedClassLoader classLoader, final IsolatedLibrary library) {
        try {
            final Path path = Constants.Files.LIBRARIES.resolve(library.getPath());
            classLoader.addPath(path);
            ManifestIsolatedLibraryLoader.LOGGER.info("Library {}", path.toAbsolutePath().toString());
            this.loadedLibraries.add(library);
        }
        catch (final IOException exception) {
            ManifestIsolatedLibraryLoader.LOGGER.error("Could not load library {}", library.getPath(), exception);
        }
    }
    
    public static final class DownloadTask
    {
        private static final Logging LOGGER;
        private static final int MAX_TRIES = 3;
        private final String libraryUrl;
        private final Path destination;
        private final Runnable downloadHandler;
        private int tries;
        
        public DownloadTask(final String libraryUrl, final Path destination, final Runnable downloadHandler) {
            this.tries = 1;
            this.libraryUrl = libraryUrl;
            this.destination = destination;
            this.downloadHandler = downloadHandler;
        }
        
        public void download() {
            if (this.tries > 3) {
                throw new RuntimeException("Failed to download library " + this.libraryUrl);
            }
            final AtomicInteger currentProgress = new AtomicInteger(-1);
            final Response<Path> response = WebResolver.resolveSync(((AbstractRequest<T, Request<Path>>)Request.ofFile(this.destination, percentage -> {
                final int progressPercentage = MathHelper.floor(percentage);
                if (currentProgress.get() >= progressPercentage) {
                    return;
                }
                else {
                    currentProgress.set(progressPercentage);
                    DownloadTask.LOGGER.info("Downloading library {} (Progress {}/100)", this.libraryUrl, currentProgress);
                    return;
                }
            })).url(this.libraryUrl, new Object[0]));
            if (response.hasException()) {
                DownloadTask.LOGGER.error("Failed to download library {}. Retrying ({}/{})", this.libraryUrl, this.tries, 3, response.exception());
                ++this.tries;
                this.download();
                return;
            }
            this.downloadHandler.run();
        }
        
        static {
            LOGGER = Logging.getLogger();
        }
    }
}
