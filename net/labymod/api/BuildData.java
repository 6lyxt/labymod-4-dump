// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api;

import net.labymod.api.util.version.SemanticVersion;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.TypeAdapter;
import net.labymod.api.loader.MinecraftVersions;
import java.util.Locale;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.io.InputStream;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.lang.reflect.Type;
import net.labymod.api.models.version.Version;
import com.google.gson.GsonBuilder;

public class BuildData
{
    private static final String OPERATING_SYSTEM_NAME;
    private static final String PRODUCTION_NAME = "production";
    private static String qualifiedVersion;
    private static BuildDataModel model;
    
    private static BuildDataModel model() {
        if (BuildData.model != null) {
            return BuildData.model;
        }
        final Gson gson = new GsonBuilder().registerTypeAdapter((Type)Version.class, (Object)new SchematicVersionTypeAdapterFactory()).create();
        final ClassLoader classLoader = PlatformEnvironment.getPlatformClassloader().getPlatformClassloader();
        try (final InputStream stream = classLoader.getResourceAsStream("build-data.json");
             final InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
            BuildData.model = (BuildDataModel)gson.fromJson((Reader)reader, (Class)BuildDataModel.class);
            return BuildData.model;
        }
        catch (final IOException exception) {
            throw new RuntimeException("Could not read build-data.json");
        }
    }
    
    private static String qualifiedVersion() {
        final BuildDataModel model = model();
        final String releaseType = model.getReleaseType();
        final String branchName = model.getBranchName();
        final String commitReference = model.getCommitReference();
        if (!branchName.equals(releaseType)) {
            BuildData.qualifiedVersion = String.format(Locale.ROOT, "%s+%s %s/%s", model.version().toString(), releaseType, branchName, commitReference);
        }
        else if (BuildData.qualifiedVersion == null) {
            if ("production".equals(releaseType)) {
                BuildData.qualifiedVersion = model.version().toString();
            }
            else {
                BuildData.qualifiedVersion = String.format(Locale.ROOT, "%s+%s %s/%s", model.version().toString(), model.getBuildNumber(), getReleaseType(releaseType), commitReference);
            }
        }
        return BuildData.qualifiedVersion;
    }
    
    private static String getReleaseType(final String releaseType) {
        if (Laby.labyAPI().labyModLoader().isAddonDevelopmentEnvironment()) {
            return "addon-environment";
        }
        return releaseType;
    }
    
    public static String commitReference() {
        return model().getCommitReference();
    }
    
    public static String branchName() {
        return model().getBranchName();
    }
    
    public static String releaseType() {
        return model().getReleaseType();
    }
    
    public static String getVersion() {
        return qualifiedVersion();
    }
    
    public static Version version() {
        return model().version();
    }
    
    public static Version latestFullRelease() {
        return model().latestFullRelease();
    }
    
    public static String getUserAgent() {
        return "LabyMod " + getVersion() + " (" + BuildData.OPERATING_SYSTEM_NAME;
    }
    
    public static int getBuildNumber() {
        return model().getBuildNumber();
    }
    
    public static boolean isLatestFullRelease() {
        final Version version = Laby.labyAPI().labyModLoader().version();
        return version.isCompatible(latestFullRelease());
    }
    
    public static boolean hasRealms() {
        final Version version = Laby.labyAPI().labyModLoader().version();
        return version.isCompatible(latestFullRelease()) || MinecraftVersions.V23w41a.orNewer();
    }
    
    static {
        OPERATING_SYSTEM_NAME = System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version", "");
        BuildData.qualifiedVersion = null;
    }
    
    private static class SchematicVersionTypeAdapterFactory extends TypeAdapter<Version>
    {
        public void write(final JsonWriter out, final Version value) throws IOException {
            out.value(value.toString());
        }
        
        public Version read(final JsonReader in) throws IOException {
            return new SemanticVersion(in.nextString());
        }
    }
}
