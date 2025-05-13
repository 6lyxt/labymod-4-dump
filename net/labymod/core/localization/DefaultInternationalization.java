// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.localization;

import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import org.jetbrains.annotations.Nullable;
import java.util.IllegalFormatException;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import com.google.gson.JsonObject;
import java.io.InputStream;
import com.google.gson.JsonSyntaxException;
import java.io.Reader;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonElement;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.labymod.api.localization.I18nException;
import net.labymod.core.util.classpath.ClasspathUtil;
import net.labymod.core.client.resources.PathResourceLocation;
import net.labymod.api.util.StringUtil;
import java.util.Locale;
import java.io.IOException;
import net.labymod.api.Laby;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.localization.Internationalization;

@Singleton
@Implements(Internationalization.class)
public class DefaultInternationalization implements Internationalization
{
    private static final String LANGUAGE_PATH = "assets/%s/i18n/%s";
    private static final String FALLBACK_LANGUAGE = "en_us";
    private final Logging logging;
    private final List<ResourceLocation> hotswapLocations;
    private final List<ResourceLocation> languageLocations;
    private final Map<String, String> fallbackTranslations;
    private final Map<String, String> translations;
    private String selectedLanguage;
    
    @Inject
    public DefaultInternationalization() {
        this.logging = Logging.create("I18n");
        this.hotswapLocations = new ArrayList<ResourceLocation>();
        this.languageLocations = new ArrayList<ResourceLocation>();
        this.fallbackTranslations = new HashMap<String, String>();
        this.translations = new HashMap<String, String>();
    }
    
    public void loadTranslations(final String namespace) throws IOException {
        this.loadTranslations(namespace, Laby.labyAPI().minecraft().options().getCurrentLanguage());
    }
    
    public void loadTranslations(final String namespace, final String selectedLanguage) throws IOException {
        this.loadTranslations(namespace, selectedLanguage, this.translations);
        this.loadTranslations(namespace, "en_us", this.fallbackTranslations);
    }
    
    private void loadTranslations(final String namespace, String selectedLanguage, final Map<String, String> target) throws IOException {
        selectedLanguage = selectedLanguage.toLowerCase(Locale.ENGLISH);
        if (this.selectedLanguage == null || !this.selectedLanguage.equals(selectedLanguage)) {
            this.selectedLanguage = selectedLanguage;
        }
        if (!this.existsLanguageDirectory(namespace)) {
            return;
        }
        final String name = this.languagePath(namespace, selectedLanguage);
        final ResourceLocation location = ResourceLocation.create(namespace, "i18n/" + StringUtil.toLowercase(selectedLanguage) + ".json");
        InputStream inputStream = null;
        try {
            inputStream = ((location instanceof PathResourceLocation) ? location.openStream() : ClasspathUtil.getResourceAsInputStream(namespace, name));
        }
        catch (final IOException exception) {
            if (selectedLanguage.equals("en_us")) {
                throw new I18nException(String.format(Locale.ROOT, "No fallback language found in namespace \"%s\"", namespace), exception);
            }
        }
        if (inputStream == null) {
            return;
        }
        if (!this.languageLocations.contains(location)) {
            this.languageLocations.add(location);
        }
        try (final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            JsonElement element;
            try {
                element = (JsonElement)GsonUtil.DEFAULT_GSON.fromJson((Reader)reader, (Class)JsonElement.class);
            }
            catch (final JsonSyntaxException exception2) {
                this.logging.error("Could not load the translations of {}.", namespace, exception2);
                reader.close();
                return;
            }
            if (!element.isJsonObject()) {
                this.logging.error("Invalid language file: \"{}:{}\"", namespace, name);
                reader.close();
                return;
            }
            final JsonObject object = element.getAsJsonObject();
            for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                this.readJsonTree(target, entry.getKey(), entry.getValue());
            }
        }
        finally {
            inputStream.close();
        }
    }
    
    @NotNull
    @Override
    public String getRawTranslation(@NotNull final String key) {
        final String translation = this.translations.get(key);
        if (translation != null) {
            return translation;
        }
        return this.fallbackTranslations.getOrDefault(key, key);
    }
    
    @NotNull
    @Override
    public String translate(@NotNull final String key, final Object... args) {
        try {
            return String.format(Locale.ROOT, this.getRawTranslation(key), args);
        }
        catch (final IllegalFormatException exception) {
            return key;
        }
    }
    
    @Nullable
    @Override
    public String getTranslation(@NotNull final String key, final Object... args) {
        final String translate = this.translate(key, args);
        return translate.equals(key) ? null : translate;
    }
    
    @Override
    public String getSelectedLanguage() {
        return this.selectedLanguage;
    }
    
    @Override
    public boolean has(@NotNull final String key) {
        return this.translations.containsKey(key) || this.fallbackTranslations.containsKey(key);
    }
    
    @Override
    public boolean isAssumedTranslatable(@NotNull final String key) {
        return !key.contains(" ") && key.contains(".");
    }
    
    public void onTick() {
        for (final ResourceLocation languageLocation : this.languageLocations) {
            if (languageLocation instanceof final PathResourceLocation pathLocation) {
                if (!pathLocation.isModified()) {
                    continue;
                }
                this.hotswapLocations.add(languageLocation);
            }
        }
        if (this.hotswapLocations.isEmpty()) {
            return;
        }
        for (final ResourceLocation hotswapLocation : this.hotswapLocations) {
            this.fallbackTranslations.entrySet().removeIf(entry -> entry.getKey().startsWith(hotswapLocation.getNamespace()));
            this.translations.entrySet().removeIf(entry -> entry.getKey().startsWith(hotswapLocation.getNamespace()));
        }
        for (ResourceLocation hotswapLocation : this.hotswapLocations) {
            try {
                this.loadTranslations(hotswapLocation.getNamespace());
                this.logging.info("Translation file " + String.valueOf(hotswapLocation) + " has been reloaded", new Object[0]);
            }
            catch (final IOException | JsonSyntaxException exception) {
                this.logging.error("Translations could not be loaded (" + exception.getMessage(), new Object[0]);
            }
        }
        this.hotswapLocations.clear();
        ((DefaultThemeService)Laby.labyAPI().themeService()).reload(true);
    }
    
    public void onResourceReload() {
        this.fallbackTranslations.clear();
        this.translations.clear();
    }
    
    private boolean existsLanguageDirectory(final String namespace) {
        try {
            final InputStream inputStream = ClasspathUtil.getResourceAsInputStream(namespace, "assets/" + namespace + "/i18n/");
            inputStream.close();
            return true;
        }
        catch (final IOException exception) {
            return false;
        }
    }
    
    private String languagePath(final String namespace, final String selectedLanguage) {
        return String.format(Locale.ROOT, "assets/%s/i18n/%s", namespace, StringUtil.toLowercase(selectedLanguage) + ".json");
    }
    
    private void readJsonTree(final Map<String, String> target, final String key, final JsonElement element) {
        if (element.isJsonObject()) {
            final JsonObject object = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                this.readJsonTree(target, key + "." + (String)entry.getKey(), (JsonElement)entry.getValue());
            }
            return;
        }
        if (!element.isJsonPrimitive()) {
            return;
        }
        target.putIfAbsent(key, element.getAsString());
    }
}
