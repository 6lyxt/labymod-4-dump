// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.color;

import net.labymod.api.util.gson.ResourceLocationTypeAdapter;
import net.labymod.api.client.resources.ResourceLocation;
import java.lang.reflect.Type;
import net.labymod.api.util.version.serial.VersionDeserializer;
import com.google.gson.GsonBuilder;
import net.labymod.api.loader.MinecraftVersion;
import java.io.IOException;
import net.labymod.api.loader.MinecraftVersions;
import java.io.Reader;
import java.io.InputStreamReader;
import net.labymod.core.util.classpath.ClasspathUtil;
import java.util.HashMap;
import net.labymod.api.models.version.Version;
import java.util.Map;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import net.labymod.api.util.logging.Logging;

public class BlockColorCache
{
    private static final Logging LOGGER;
    private static final Gson GSON;
    private static final TypeToken<?> TOKEN;
    private final Map<Version, Map<String, BlockColor>> colors;
    private final Map<String, BlockColor> currentColors;
    
    public BlockColorCache() {
        this.colors = new HashMap<Version, Map<String, BlockColor>>();
        this.currentColors = new HashMap<String, BlockColor>();
        this.load();
    }
    
    private void load() {
        try (final InputStreamReader reader = new InputStreamReader(ClasspathUtil.getResourceAsInputStream("labymod", "assets/labymod/data/block_colors.json", false))) {
            this.colors.putAll((Map<? extends Version, ? extends Map<String, BlockColor>>)BlockColorCache.GSON.fromJson((Reader)reader, BlockColorCache.TOKEN.getType()));
            this.currentColors.putAll(this.findCurrentColors(MinecraftVersions.current()));
        }
        catch (final IOException exception) {
            BlockColorCache.LOGGER.error("Failed to load block colors", exception);
        }
    }
    
    public BlockColor getBlockColor(final String blockId) {
        return this.currentColors.get(blockId);
    }
    
    private Map<String, BlockColor> findCurrentColors(final MinecraftVersion version) {
        return this.colors.get(version.version());
    }
    
    static {
        LOGGER = Logging.getLogger();
        GSON = new GsonBuilder().registerTypeAdapter((Type)Version.class, (Object)new VersionDeserializer()).registerTypeAdapter((Type)ResourceLocation.class, (Object)new ResourceLocationTypeAdapter()).create();
        TOKEN = TypeToken.getParameterized((Type)Map.class, new Type[] { Version.class, TypeToken.getParameterized((Type)Map.class, new Type[] { String.class, BlockColor.class }).getType() });
    }
}
