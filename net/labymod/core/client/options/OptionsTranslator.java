// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.options;

import java.util.Iterator;
import java.util.Set;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;
import java.io.Writer;
import java.io.PrintWriter;
import net.labymod.api.loader.MinecraftVersions;
import java.util.Objects;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.List;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import net.labymod.api.Laby;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class OptionsTranslator
{
    private static final boolean OLD_LANGUAGE_NAMES;
    private static final Logging LOGGER;
    private final Map<String, String> optionCache;
    
    public OptionsTranslator() {
        this.optionCache = new HashMap<String, String>();
    }
    
    public Map<String, String> getOptionCache() {
        return this.optionCache;
    }
    
    public void translateOptions(final File file) {
        this.optionCache.clear();
        if (!file.exists()) {
            return;
        }
        final AtomicBoolean hasVersion = new AtomicBoolean(false);
        final AtomicBoolean modified = new AtomicBoolean(false);
        final int currentVersion = Laby.labyAPI().minecraft().getDataVersion();
        final StringBuilder builder = new StringBuilder();
        try {
            final List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(line -> this.processLine(line, currentVersion, modified, builder, hasVersion));
            if (!hasVersion.get() && currentVersion != -1) {
                builder.append(this.buildOptionString("version", String.valueOf(currentVersion)));
                modified.set(true);
            }
            if (modified.get()) {
                Files.writeString(file.toPath(), builder.toString(), new OpenOption[0]);
            }
        }
        catch (final IOException exception) {
            OptionsTranslator.LOGGER.error("An error occurred while translating the options.", exception);
        }
    }
    
    private void processLine(final String line, final int currentVersion, final AtomicBoolean modified, final StringBuilder builder, final AtomicBoolean hasVersion) {
        try {
            final List<String> optionPair = this.parseOptionLine(line);
            if (optionPair.isEmpty() || optionPair.size() == 1) {
                return;
            }
            final String key = optionPair.get(0);
            String value = optionPair.get(1);
            if (key.startsWith("key_")) {
                value = this.checkAndConvertKeyValue(currentVersion, value, modified);
            }
            if (key.equalsIgnoreCase("version")) {
                builder.append(this.updateVersionInformation(currentVersion, value, modified));
                hasVersion.set(true);
            }
            else if (key.equalsIgnoreCase("lang")) {
                builder.append(this.buildOptionString(key, this.updateLanguage(value)));
                modified.set(true);
            }
            else {
                builder.append(this.buildOptionString(key, value));
            }
            this.optionCache.put(key, value);
        }
        catch (final Exception exception) {
            OptionsTranslator.LOGGER.warn("Option line could not be processed: {}", line, exception);
        }
    }
    
    private String updateLanguage(final String value) {
        final String[] entries = value.split("_");
        if (entries.length != 2) {
            OptionsTranslator.LOGGER.warn("Failed to convert language name: {}", value);
            return value;
        }
        if (OptionsTranslator.OLD_LANGUAGE_NAMES) {
            return entries[0] + "_" + entries[1].toUpperCase(Locale.ROOT);
        }
        return entries[0] + "_" + entries[1].toLowerCase(Locale.ROOT);
    }
    
    private List<String> parseOptionLine(final String line) {
        if (line == null || line.isBlank()) {
            return Collections.emptyList();
        }
        try {
            final String[] split = line.split(":", 2);
            return new ArrayList<String>(Arrays.asList(split));
        }
        catch (final Exception exception) {
            OptionsTranslator.LOGGER.warn("Option line could not be parsed. {}", line, exception);
            return Collections.emptyList();
        }
    }
    
    private String checkAndConvertKeyValue(final int currentVersion, String value, final AtomicBoolean modified) {
        final Integer parsedValue = this.getParsedValue(value);
        try {
            value = this.convertVersionIfNeeded(currentVersion, value, parsedValue, modified);
        }
        catch (final NumberFormatException ex) {}
        return value;
    }
    
    private String convertVersionIfNeeded(final int currentVersion, final String value, final Integer parsedValue, final AtomicBoolean modified) {
        if (currentVersion == -1) {
            return this.convertNewToLegacyIfNeeded(value, parsedValue, modified);
        }
        return this.convertLegacyToNewIfNeeded(value, parsedValue, modified);
    }
    
    private String convertNewToLegacyIfNeeded(String value, final Integer parsedValue, final AtomicBoolean modified) {
        if (parsedValue == null) {
            final int keyCode = LegacyKeyIdConverter.getKeyId(value);
            if (keyCode != -1) {
                value = String.valueOf(keyCode);
                modified.set(true);
            }
        }
        return value;
    }
    
    private String convertLegacyToNewIfNeeded(String value, final Integer parsedValue, final AtomicBoolean modified) {
        if (parsedValue != null) {
            value = LegacyKeyIdConverter.getKeyName(parsedValue);
            modified.set(true);
        }
        return value;
    }
    
    private String updateVersionInformation(final int currentVersion, final String value, final AtomicBoolean modified) {
        final StringBuilder builder = new StringBuilder();
        if (currentVersion != -1) {
            builder.append(this.buildOptionString("version", String.valueOf(currentVersion)));
        }
        if (!Objects.equals(String.valueOf(currentVersion), value)) {
            modified.set(true);
        }
        return builder.toString();
    }
    
    private String buildOptionString(final String key, final String value) {
        return key + ":" + value;
    }
    
    private Integer getParsedValue(final String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (final NumberFormatException exception) {
            return null;
        }
    }
    
    static {
        OLD_LANGUAGE_NAMES = MinecraftVersions.V1_8_9.orOlder();
        LOGGER = Logging.create(OptionsTranslator.class);
    }
    
    public static class OptionWriter extends PrintWriter
    {
        private final Map<String, String> values;
        private String tempKey;
        private boolean closing;
        
        public OptionWriter(@NotNull final Writer out) {
            super(out);
            this.values = new HashMap<String, String>();
        }
        
        @Override
        public void println(final String x) {
            if (this.tempKey != null) {
                this.values.put(this.tempKey, x);
                this.tempKey = null;
                return;
            }
            final String[] split = x.split(":", 2);
            this.values.put(split[0], split[1]);
        }
        
        @Override
        public void println(final boolean x) {
            this.println("" + x);
        }
        
        @Override
        public void println(final char x) {
            this.println("" + x);
        }
        
        @Override
        public void println(final int x) {
            this.println("" + x);
        }
        
        @Override
        public void println(final long x) {
            this.println("" + x);
        }
        
        @Override
        public void println(final float x) {
            this.println("" + x);
        }
        
        @Override
        public void println(final double x) {
            this.println("" + x);
        }
        
        @Override
        public void println(final Object x) {
            this.println(String.valueOf(x));
        }
        
        @Override
        public void print(final String s) {
            if (this.closing) {
                return;
            }
            this.tempKey = s;
        }
        
        @Override
        public void print(final char c) {
        }
        
        private void superPrintLine(final String string) {
            synchronized (this.lock) {
                super.print(string);
                super.println();
            }
        }
        
        @Override
        public void close() {
            this.closing = true;
            final Set<Map.Entry<String, String>> entries = this.values.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                this.superPrintLine((String)entry.getKey() + ":" + (String)entry.getValue());
            }
            final Map<String, String> optionCache = LabyMod.references().optionsTranslator().optionCache;
            for (Map.Entry<String, String> cachedEntry : optionCache.entrySet()) {
                boolean found = false;
                String key = cachedEntry.getKey();
                if (key.equals("version")) {
                    continue;
                }
                String value = cachedEntry.getValue();
                if (key.isEmpty()) {
                    final int i = value.lastIndexOf(58);
                    if (i <= 0) {
                        continue;
                    }
                    if (i == value.length() - 1) {
                        continue;
                    }
                    key = value.substring(value.lastIndexOf(58, i - 1) + 1, i);
                    value = value.substring(i + 1);
                }
                for (final Map.Entry<String, String> entry2 : entries) {
                    if (entry2.getKey().equalsIgnoreCase(key)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    continue;
                }
                this.superPrintLine(key + ":" + value);
            }
            super.close();
        }
    }
}
