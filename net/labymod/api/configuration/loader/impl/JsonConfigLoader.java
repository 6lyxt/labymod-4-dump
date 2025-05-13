// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.impl;

import java.lang.reflect.Field;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.util.Debounce;
import net.labymod.api.util.io.execption.InsufficientStorageSpace;
import net.labymod.api.util.GsonUtil;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import net.labymod.api.event.labymod.config.ConfigurationVersionUpdateEvent;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.util.reflection.Reflection;
import com.google.gson.JsonArray;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.event.labymod.config.ConfigurationLoadEvent;
import java.io.BufferedReader;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import com.google.gson.JsonElement;
import java.io.Reader;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.util.gson.SettingDevelopmentExclusionStrategy;
import com.google.gson.ExclusionStrategy;
import java.lang.reflect.Type;
import net.labymod.api.configuration.loader.property.ConfigPropertyTypeAdapter;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.config.JsonConfigLoaderInitializeEvent;
import com.google.gson.GsonBuilder;
import java.nio.file.Path;
import com.google.gson.Gson;
import net.labymod.api.util.logging.Logging;

public class JsonConfigLoader extends AbstractConfigLoader
{
    private static final Logging LOGGER;
    private static final String CONFIG_VERSION_KEY = "configVersion";
    private final Gson gson;
    
    public JsonConfigLoader(final Path directory) {
        this(directory, new GsonBuilder());
    }
    
    public JsonConfigLoader(final Path directory, final GsonBuilder builder) {
        super(directory);
        Laby.fireEvent(new JsonConfigLoaderInitializeEvent(builder));
        builder.registerTypeAdapter((Type)ConfigProperty.class, (Object)new ConfigPropertyTypeAdapter());
        builder.setExclusionStrategies(new ExclusionStrategy[] { (ExclusionStrategy)new SettingDevelopmentExclusionStrategy(Laby.labyAPI()) });
        this.gson = builder.create();
    }
    
    @Override
    public <T extends ConfigAccessor> T load(final Class<T> clazz) throws ConfigurationLoadException {
        final Path path = this.getPath(clazz);
        try {
            if (IOUtil.exists(path)) {
                final BufferedReader reader = Files.newBufferedReader(path);
                try {
                    final JsonObject jsonObject = (JsonObject)this.gson.fromJson((Reader)reader, (Class)JsonObject.class);
                    JsonObject converted;
                    try {
                        converted = this.getConvertedConfigurationJson(clazz, jsonObject);
                    }
                    catch (final Exception e) {
                        JsonConfigLoader.LOGGER.error("Failed to check version of of " + clazz.getName(), (Throwable)e);
                        converted = jsonObject;
                    }
                    T config = (T)this.gson.fromJson((JsonElement)converted, (Class)clazz);
                    if (config == null) {
                        config = (T)this.loadConfig((Class<ConfigAccessor>)clazz);
                    }
                    final ConfigAccessor configAccessor = config;
                    if (reader != null) {
                        reader.close();
                    }
                    return (T)configAccessor;
                }
                catch (final Throwable t) {
                    if (reader != null) {
                        try {
                            reader.close();
                        }
                        catch (final Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
            }
            return (T)this.loadConfig((Class<ConfigAccessor>)clazz);
        }
        catch (final Exception e2) {
            throw new ConfigurationLoadException(clazz, e2);
        }
    }
    
    private <T extends ConfigAccessor> JsonObject getConvertedConfigurationJson(final Class<T> cls, final JsonObject jsonObject) {
        Laby.fireEvent(new ConfigurationLoadEvent(cls, jsonObject));
        Reflection.getFields(cls, false, member -> {
            if (member.isAnnotationPresent(Exclude.class) || !jsonObject.has(member.getName())) {
                return;
            }
            else {
                final JsonElement jsonElement = jsonObject.get(member.getName());
                if (!jsonElement.isJsonObject() || !ConfigAccessor.class.isAssignableFrom(member.getType())) {
                    Class<?> type = member.getType();
                    Type genericType = member.getGenericType();
                    if (ConfigProperty.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
                        final ParameterizedType parameterizedType = (ParameterizedType)genericType;
                        final Type[] actualTypeArguments3;
                        final Type[] actualTypeArguments = actualTypeArguments3 = parameterizedType.getActualTypeArguments();
                        final int length = actualTypeArguments3.length;
                        int i = 0;
                        while (i < length) {
                            final Type actualTypeArgument = actualTypeArguments3[i];
                            if (actualTypeArgument instanceof Class) {
                                type = (Class<?>)(genericType = actualTypeArgument);
                                break;
                            }
                            else if (actualTypeArgument instanceof final ParameterizedType subType) {
                                type = (Class)subType.getRawType();
                                genericType = subType;
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    if (jsonElement.isJsonArray() && List.class.isAssignableFrom(type)) {
                        Class<? extends ConfigAccessor> accessorClass = null;
                        if (genericType instanceof final ParameterizedType parameterizedType2) {
                            final Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                            if (actualTypeArguments2.length != 0) {
                                final Type patt0$temp = actualTypeArguments2[0];
                                if (patt0$temp instanceof Class) {
                                    final Class<?> genericClass = (Class<?>)patt0$temp;
                                    if (ConfigAccessor.class.isAssignableFrom(genericClass)) {
                                        accessorClass = (Class<? extends ConfigAccessor>)genericClass;
                                    }
                                }
                            }
                        }
                        if (accessorClass != null) {
                            final JsonArray newArray = new JsonArray();
                            jsonElement.getAsJsonArray().iterator();
                            final Iterator iterator;
                            while (iterator.hasNext()) {
                                final JsonElement element = iterator.next();
                                if (element.isJsonObject()) {
                                    JsonObject convertedConfigurationJson;
                                    try {
                                        convertedConfigurationJson = this.getConvertedConfigurationJson(accessorClass, element.getAsJsonObject());
                                    }
                                    catch (final Exception e2) {
                                        JsonConfigLoader.LOGGER.error("Failed to check version of sub configuration of " + member.getName() + " (" + cls.getName(), (Throwable)e2);
                                        convertedConfigurationJson = element.getAsJsonObject();
                                    }
                                    newArray.add((JsonElement)convertedConfigurationJson);
                                }
                                else {
                                    return;
                                }
                            }
                            jsonObject.add(member.getName(), (JsonElement)newArray);
                        }
                        return;
                    }
                    else {
                        return;
                    }
                }
                else {
                    final Class<? extends ConfigAccessor> configAccessorClass = (Class<? extends ConfigAccessor>)member.getType();
                    JsonObject convertedConfigurationJson2;
                    try {
                        convertedConfigurationJson2 = this.getConvertedConfigurationJson(configAccessorClass, jsonElement.getAsJsonObject());
                    }
                    catch (final Exception e3) {
                        JsonConfigLoader.LOGGER.error("Failed to check version of " + member.getName() + " (" + cls.getName(), (Throwable)e3);
                        convertedConfigurationJson2 = jsonElement.getAsJsonObject();
                    }
                    jsonObject.add(member.getName(), (JsonElement)convertedConfigurationJson2);
                    return;
                }
            }
        });
        if (!Config.class.isAssignableFrom(cls)) {
            return jsonObject;
        }
        final Class<? extends Config> configClass = (Class<? extends Config>)cls;
        Config config;
        try {
            config = this.createInstance(configClass);
        }
        catch (final ReflectiveOperationException e) {
            return jsonObject;
        }
        final int configVersion = config.getConfigVersion();
        int usedConfigVersion;
        if (jsonObject.has("configVersion") && jsonObject.get("configVersion").isJsonPrimitive() && jsonObject.get("configVersion").getAsJsonPrimitive().isNumber()) {
            usedConfigVersion = jsonObject.get("configVersion").getAsInt();
        }
        else {
            usedConfigVersion = -1;
        }
        if (configVersion > usedConfigVersion) {
            final ConfigurationVersionUpdateEvent configurationVersionUpdateEvent = Laby.fireEvent(new ConfigurationVersionUpdateEvent(configClass, jsonObject, usedConfigVersion, configVersion));
            final JsonObject editedJsonObject = configurationVersionUpdateEvent.getEditedJsonObject();
            if (editedJsonObject != null) {
                editedJsonObject.addProperty("configVersion", (Number)configVersion);
                return editedJsonObject;
            }
            if (usedConfigVersion == -1) {
                jsonObject.addProperty("configVersion", (Number)configVersion);
            }
        }
        return jsonObject;
    }
    
    private <T extends ConfigAccessor> T loadConfig(final Class<T> clazz) throws ReflectiveOperationException {
        final T config = (T)this.createInstance((Class<ConfigAccessor>)clazz);
        this.save(config);
        return config;
    }
    
    private <T extends ConfigAccessor> T createInstance(final Class<T> cls) throws ReflectiveOperationException {
        final Constructor<T> constructor = cls.getDeclaredConstructor((Class<?>[])new Class[0]);
        constructor.setAccessible(true);
        return constructor.newInstance(new Object[0]);
    }
    
    @Override
    public void save(final ConfigAccessor config) throws ConfigurationSaveException {
        final Path path = this.getPath(config.getClass());
        final Path parent = path.getParent();
        try {
            if (!IOUtil.exists(parent)) {
                IOUtil.createDirectories(parent);
            }
            GsonUtil.writeJson(this.gson, path, config);
        }
        catch (final Exception exception) {
            if (exception instanceof final InsufficientStorageSpace insufficientStorageSpace) {
                Debounce.of("unable-to-save-configuration", 200L, this.createDebounceTask(path));
                return;
            }
            throw new ConfigurationSaveException(config, exception);
        }
    }
    
    @Override
    public Object serialize(final ConfigAccessor config) throws Exception {
        return this.gson.toJsonTree((Object)config);
    }
    
    @Override
    public String getFileExtension() {
        return "json";
    }
    
    public Gson getGson() {
        return this.gson;
    }
    
    public static JsonConfigLoader createDefault() {
        return new JsonConfigLoader(AbstractConfigLoader.defaultDirectory());
    }
    
    private Runnable createDebounceTask(final Path file) {
        return () -> {
            final NotificationController controller = Laby.references().notificationController();
            controller.push(Notification.builder().title(Component.translatable("labymod.notification.insufficientStorageSpace.configuration.title", new Component[0])).text(Component.translatable("labymod.notification.insufficientStorageSpace.configuration.description", Component.text(IOUtil.getFileStoreName(file), NamedTextColor.YELLOW, TextDecoration.BOLD))).type(Notification.Type.SYSTEM).duration(10000L).build());
        };
    }
    
    static {
        LOGGER = Logging.create(JsonConfigLoader.class);
    }
}
