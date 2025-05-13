// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.labymod.api.util.io.execption.InsufficientStorageSpace;
import net.labymod.api.util.io.IOUtil;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.Gson;

public class GsonUtil
{
    public static final Gson DEFAULT_GSON;
    
    private GsonUtil() {
    }
    
    public static JsonElement parse(final String json) {
        return parse(GsonUtil.DEFAULT_GSON, json);
    }
    
    public static JsonElement parse(final Gson gson, final String json) {
        return (JsonElement)gson.fromJson(json, (Class)JsonElement.class);
    }
    
    public static JsonArray removeFromArray(final JsonArray source, final JsonElement element) {
        final JsonArray newArray = new JsonArray();
        for (final JsonElement jsonElement : source) {
            if (jsonElement != element) {
                newArray.add(jsonElement);
            }
        }
        return newArray;
    }
    
    public static void writeJson(final Path destination, final Object src) throws IOException {
        writeJson(GsonUtil.DEFAULT_GSON, destination, src);
    }
    
    public static void writeJson(final Gson gson, final Path destination, final Object src) throws IOException {
        final String value = gson.toJson(src);
        final int requiredSpace = value.getBytes(StandardCharsets.UTF_8).length;
        final long availableSpace = IOUtil.getAvailableSpace(destination);
        if (availableSpace < requiredSpace) {
            throw new InsufficientStorageSpace("Not enough space to write " + String.valueOf(destination), availableSpace, (long)requiredSpace);
        }
        try (final BufferedWriter writer = Files.newBufferedWriter(destination, new OpenOption[0])) {
            writer.write(value);
            writer.flush();
        }
    }
    
    static {
        DEFAULT_GSON = new GsonBuilder().create();
    }
}
