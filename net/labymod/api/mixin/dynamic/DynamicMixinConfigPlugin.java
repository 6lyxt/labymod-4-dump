// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mixin.dynamic;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Enumeration;
import java.io.IOException;
import java.net.URL;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicMixinConfigPlugin
{
    private static final String DYNAMIC_MIXIN_CONFIG_LOCATION = "META-INF/mixin/dynamic-mixin-config.txt";
    private static final String DYNAMIC_MIXIN_APPLIER_CONFIG_LOCATION = "META-INF/mixin/dynamic-mixin-applier-config.txt";
    private final Map<String, List<DynamicMixinApplier>> dynamicMixinAppliers;
    private final Map<String, List<String>> dynamicMixins;
    
    public DynamicMixinConfigPlugin() {
        this.dynamicMixinAppliers = new HashMap<String, List<DynamicMixinApplier>>();
        this.dynamicMixins = new HashMap<String, List<String>>();
    }
    
    public void onLoad(final ClassLoader classLoader, final String mixinPackage, final String runningVersion) {
        this.readResources(classLoader, "META-INF/mixin/dynamic-mixin-config.txt", runningVersion, namespace -> this.dynamicMixins.computeIfAbsent(namespace, s -> new ArrayList()), (list, name) -> {
            if (!list.contains(name)) {
                list.add(name);
            }
            return;
        });
        this.readResources(classLoader, "META-INF/mixin/dynamic-mixin-applier-config.txt", runningVersion, namespace -> this.dynamicMixinAppliers.computeIfAbsent(namespace, s -> new ArrayList()), (list, name) -> {
            if (name == null || Objects.equals(name, "null")) {
                list.add(AlwaysDynamicMixinApplier.INSTANCE);
            }
            else {
                try {
                    final Class<?> cls = classLoader.loadClass(name);
                    final Object instance = cls.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                    if (!(!(instance instanceof DynamicMixinApplier))) {
                        list.add(instance);
                    }
                }
                catch (final Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    
    private <T> void readResources(final ClassLoader classLoader, final String location, final String runningVersion, final Function<String, List<T>> function, final BiConsumer<List<T>, String> consumer) {
        try {
            final Enumeration<URL> resources = DynamicMixinUtil.getResources(classLoader, location);
            while (resources.hasMoreElements()) {
                final URL url = resources.nextElement();
                if (url == null) {
                    continue;
                }
                this.readResource(url, runningVersion, function, consumer);
            }
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public boolean shouldApply(final Function<String, Boolean> condition, final String targetClassName, final String mixinClassName) {
        for (final Map.Entry<String, List<DynamicMixinApplier>> entry : this.dynamicMixinAppliers.entrySet()) {
            final String key = entry.getKey();
            if (!condition.apply(key)) {
                final List<String> dynamicMixins = this.dynamicMixins.get(key);
                for (final String dynamicMixin : dynamicMixins) {
                    if (Objects.equals(dynamicMixin, mixinClassName)) {
                        return false;
                    }
                }
            }
            else {
                for (final DynamicMixinApplier dynamicMixinApplier : entry.getValue()) {
                    final boolean apply = dynamicMixinApplier.apply(targetClassName, mixinClassName);
                    if (!apply) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private <T> void readResource(final URL url, final String runningVersion, final Function<String, List<T>> function, final BiConsumer<List<T>, String> consumer) throws IOException {
        try (final InputStream inputStream = url.openStream()) {
            final String content = this.toString(inputStream);
            final String[] split2;
            final String[] lines = split2 = content.split("\n");
            for (String line : split2) {
                line = line.trim();
                if (!line.isEmpty()) {
                    final String[] split = line.split(":");
                    if (split.length != 3) {
                        throw new IllegalStateException("Invalid format: \"" + line + "\" (key:value:version)");
                    }
                    final String namespace = split[0];
                    final List<T> list = function.apply(namespace);
                    final String className = split[1];
                    final String version = split[2];
                    if (Objects.equals(version, runningVersion)) {
                        consumer.accept(list, className);
                    }
                }
            }
        }
    }
    
    private String toString(final InputStream inputStream) throws IOException {
        return new String(this.readBytes(inputStream), StandardCharsets.UTF_8);
    }
    
    private byte[] readBytes(final InputStream inputStream) throws IOException {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final byte[] data = new byte[512];
            int read;
            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, read);
            }
            outputStream.flush();
            return outputStream.toByteArray();
        }
    }
}
