// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot.meta;

import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.model.ScreenshotInsight;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.HashMap;
import java.io.IOException;
import net.labymod.core.labynet.insight.util.ImageCodec;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Path;
import java.util.Map;
import java.text.SimpleDateFormat;

public class ScreenshotMeta
{
    private static final SimpleDateFormat DATE_FORMAT;
    private final String identifier;
    private final long timestamp;
    private long monthTimestamp;
    private final Map<String, String> attributes;
    
    public ScreenshotMeta(final Path path) throws IOException {
        long timestamp = -1L;
        try {
            String fileName = path.getFileName().toString();
            if (fileName.split("_").length == 3) {
                final String[] parts = fileName.split("_", 3);
                fileName = parts[0] + "_" + parts[1];
            }
            if (fileName.endsWith(".png")) {
                fileName = fileName.substring(0, fileName.length() - 4);
            }
            timestamp = ScreenshotMeta.DATE_FORMAT.parse(fileName).getTime();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        if (timestamp <= 0L) {
            final BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
            timestamp = attributes.lastModifiedTime().toMillis();
        }
        this.timestamp = timestamp;
        this.identifier = identifierOfPath(path);
        final ImageCodec codec = new ImageCodec(path);
        this.attributes = codec.map();
        this.initialize();
    }
    
    public ScreenshotMeta(final String identifier, final long timestamp) throws IOException {
        this.identifier = identifier;
        this.timestamp = timestamp;
        this.attributes = new HashMap<String, String>();
        this.initialize();
    }
    
    private void initialize() {
        final Instant instance = Instant.ofEpochMilli(this.timestamp);
        final ZoneId zone = ZoneId.systemDefault();
        final LocalDate date = instance.atZone(zone).toLocalDate();
        final LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        final Instant firstDayOfMonthInstant = firstDayOfMonth.atStartOfDay(zone).toInstant();
        this.monthTimestamp = firstDayOfMonthInstant.toEpochMilli();
    }
    
    public void set(final String key, final String value) {
        this.attributes.put(key, value);
    }
    
    public String get(final String key) {
        return this.attributes.get(key);
    }
    
    public boolean has(final String key) {
        return this.attributes.containsKey(key);
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public long getMonthTimestamp() {
        return this.monthTimestamp;
    }
    
    public Map<String, String> getAttributes() {
        return this.attributes;
    }
    
    public static String identifierOfPath(final Path path) {
        return path.getFileName().toString().replace(".png", "");
    }
    
    public boolean hasInsight() {
        return this.has("Insight") || this.has("Screenshot Metadata");
    }
    
    public ScreenshotInsight getInsight() {
        try {
            final String json = this.has("Insight") ? this.get("Insight") : this.get("Screenshot Metadata");
            return (json == null) ? null : new ScreenshotInsight((JsonObject)GsonUtil.DEFAULT_GSON.fromJson(json, (Class)JsonObject.class));
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
}
