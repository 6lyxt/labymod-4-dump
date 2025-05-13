// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.loader.mojang;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.io.IOException;
import net.labymod.api.util.io.web.request.Response;
import java.nio.file.OpenOption;
import net.labymod.api.models.version.manifest.ManifestDownload;
import net.labymod.api.util.io.web.request.types.FileRequest;
import net.labymod.api.models.version.manifest.VersionManifest;
import java.util.Arrays;
import net.labymod.api.models.version.manifest.index.VersionEntry;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.models.version.manifest.index.VersionIndex;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Paths;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Constants;
import java.util.Locale;
import java.nio.file.Path;
import net.labymod.api.mapping.loader.MappingLoader;

public class MojangMappingLoader implements MappingLoader
{
    private static final String VERSION_INDEX_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private final Path mappingsPath;
    
    public MojangMappingLoader() {
        this.mappingsPath = Paths.get(String.format(Locale.ROOT, Constants.Files.MOJANG_MAPPINGS_PATH, PlatformEnvironment.getRunningVersion()), new String[0]);
    }
    
    @NotNull
    @Override
    public String getSourceNamespace() {
        return "named";
    }
    
    @NotNull
    @Override
    public String getTargetNamespace() {
        return "official";
    }
    
    @NotNull
    @Override
    public MappingType type() {
        return MappingType.PROGUARD;
    }
    
    @NotNull
    @Override
    public InputStream load() throws IOException {
        if (!Files.exists(this.mappingsPath, new LinkOption[0])) {
            final Response<VersionIndex> versionIndexResponse = Request.ofGson(VersionIndex.class).url("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json", new Object[0]).executeSync();
            if (versionIndexResponse.hasException()) {
                throw versionIndexResponse.exception();
            }
            final VersionEntry currentEntry = Arrays.stream(versionIndexResponse.get().getVersions()).filter(versionEntry -> versionEntry.getId().equals(PlatformEnvironment.getRunningVersion())).findFirst().orElseThrow(() -> new IllegalStateException("Could not find version entry for " + PlatformEnvironment.getRunningVersion()));
            final Response<VersionManifest> versionManifestResponse = Request.ofGson(VersionManifest.class).url(currentEntry.getUrl(), new Object[0]).executeSync();
            if (versionManifestResponse.hasException()) {
                throw versionManifestResponse.exception();
            }
            final Response<Path> response = ((AbstractRequest<Path, R>)((AbstractRequest<T, FileRequest>)Request.ofFile(this.mappingsPath)).url(versionManifestResponse.get().getDownloads().get("client_mappings").getUrl(), new Object[0])).executeSync();
            if (response.hasException()) {
                throw response.exception();
            }
        }
        return Files.newInputStream(this.mappingsPath, new OpenOption[0]);
    }
}
