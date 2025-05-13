// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service;

import net.labymod.api.util.io.IOUtil;
import net.labymod.api.models.version.Version;
import java.io.InputStream;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.volt.asm.util.ASMHelper;
import java.io.Reader;
import net.labymod.api.service.model.ServiceModel;
import java.io.InputStreamReader;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import net.labymod.api.util.reflection.Reflection;
import java.util.Enumeration;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.net.URL;
import net.labymod.api.util.function.ThrowableConsumer;
import java.util.Iterator;
import java.lang.reflect.Constructor;
import java.io.IOException;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.function.ThrowableFunction;
import java.util.List;

public class CustomServiceLoader<T> implements Iterable<T>
{
    private static final String CUSTOM_SERVICES_DIRECTORY = "META-INF/custom-services/";
    private static final String SERVICES_DIRECTORY = "META-INF/services/";
    private final List<T> serviceClasses;
    private final Class<T> serviceClass;
    private final String serviceClassName;
    private final ClassLoader loader;
    private final ServiceType serviceType;
    private final ThrowableFunction<String, T, Throwable> constructorFactory;
    
    private CustomServiceLoader(@NotNull final Class<T> serviceClass, @NotNull final ClassLoader loader, @NotNull final ServiceType serviceType, @NotNull final ThrowableFunction<String, T, Throwable> constructorFactory) {
        this.serviceType = serviceType;
        this.serviceClasses = new ArrayList<T>();
        this.serviceClass = serviceClass;
        this.serviceClassName = serviceClass.getName();
        this.loader = loader;
        this.constructorFactory = constructorFactory;
        this.loadServices();
    }
    
    private void loadServices() {
        try {
            this.serviceType.load((CustomServiceLoader<Object>)this);
            if (this.serviceType == ServiceType.CROSS_VERSION) {
                ServiceType.ADVANCED.load((CustomServiceLoader<Object>)this);
            }
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
    
    @NotNull
    public static <T> CustomServiceLoader<T> load(@NotNull final Class<T> serviceClass, final ClassLoader loader, final ServiceType serviceType) {
        return load(serviceClass, loader, serviceType, className -> {
            final Constructor<?> constructor = findEmptyConstructor(className, loader);
            return (constructor == null) ? null : constructor.newInstance(new Object[0]);
        });
    }
    
    @NotNull
    public static <T> CustomServiceLoader<T> load(@NotNull final Class<T> serviceClass, final ClassLoader loader, final ServiceType serviceType, final ThrowableFunction<String, T, Throwable> constructorFactory) {
        return new CustomServiceLoader<T>(serviceClass, loader, serviceType, constructorFactory);
    }
    
    public List<T> getServiceClasses() {
        return this.serviceClasses;
    }
    
    public String getServiceClassName() {
        return this.serviceClassName;
    }
    
    public Class<T> getServiceClass() {
        return this.serviceClass;
    }
    
    public ClassLoader getLoader() {
        return this.loader;
    }
    
    public ServiceType getServiceType() {
        return this.serviceType;
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.serviceClasses.iterator();
    }
    
    void iterateResources(final String serviceFile, final ThrowableConsumer<URL, IOException> resourceConsumer) throws IOException {
        final Enumeration<URL> resources = PlatformEnvironment.getResources(this.loader, serviceFile);
        while (resources.hasMoreElements()) {
            final URL resource = resources.nextElement();
            resourceConsumer.accept(resource);
        }
    }
    
    String getServiceFileName(final String directoryPath) {
        return directoryPath + this.serviceClassName;
    }
    
    static Constructor<?> findEmptyConstructor(final String className, final ClassLoader loader) throws ClassNotFoundException {
        return Reflection.searchEmptyConstructor(Class.forName(className, false, loader));
    }
    
    boolean registerClass(final String className) {
        try {
            final T value = this.constructorFactory.apply(className);
            if (value == null) {
                return false;
            }
            this.serviceClasses.add(value);
            return true;
        }
        catch (final Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
    }
    
    public enum ServiceType permits CustomServiceLoader$ServiceType$1, CustomServiceLoader$ServiceType$2, CustomServiceLoader$ServiceType$3
    {
        ADVANCED {
            private final Gson gson;
            
            {
                this.gson = new GsonBuilder().create();
            }
            
            @Override
             <T> void load(final CustomServiceLoader<T> serviceLoader) throws IOException {
                final String file = serviceLoader.getServiceFileName("META-INF/custom-services/") + ".json";
                serviceLoader.iterateResources(file, resource -> {
                    try (final InputStream stream = resource.openStream();
                         final InputStreamReader reader = new InputStreamReader(stream)) {
                        final ServiceModel[] array;
                        final ServiceModel[] models = array = (ServiceModel[])this.gson.fromJson((Reader)reader, (Class)ServiceModel[].class);
                        int i = 0;
                        for (int length = array.length; i < length; ++i) {
                            final ServiceModel model = array[i];
                            if (!(!ASMHelper.canLoadClass((int)model.getClassVersion()))) {
                                final Version version = model.getVersion();
                                if (version != null) {
                                    final Version runningVersion = VersionDeserializer.from(PlatformEnvironment.getRunningVersion());
                                    if (!runningVersion.isCompatible(version)) {
                                        continue;
                                    }
                                }
                                serviceLoader.registerClass(model.getServiceClass());
                            }
                        }
                    }
                });
            }
        }, 
        @Deprecated
        CROSS_VERSION {
            @Override
             <T> void load(final CustomServiceLoader<T> serviceLoader) throws IOException {
                final String serviceFile = serviceLoader.getServiceFileName("META-INF/services/");
                serviceLoader.iterateResources(serviceFile, resource -> {
                    final InputStream stream = resource.openStream();
                    try {
                        final String serviceContent = IOUtil.toString(stream);
                        final String[] split2;
                        final String[] split = split2 = serviceContent.split("\n");
                        int i = 0;
                        for (int length = split2.length; i < length; ++i) {
                            final String s = split2[i];
                            final String[] serviceSplit = s.split(";", 2);
                            try {
                                final double version = Double.parseDouble(serviceSplit[1]);
                                if (!(!ASMHelper.canLoadClass((int)version))) {
                                    final String className = serviceSplit[0];
                                    final String className2 = className.trim();
                                    serviceLoader.registerClass(className2);
                                }
                            }
                            catch (final NumberFormatException ignored) {
                                System.err.println("Invalid service format " + s);
                            }
                        }
                        if (stream != null) {
                            stream.close();
                        }
                    }
                    catch (final Throwable t2) {
                        if (stream != null) {
                            try {
                                stream.close();
                            }
                            catch (final Throwable exception) {
                                t2.addSuppressed(exception);
                            }
                        }
                        throw t2;
                    }
                });
            }
        }, 
        NORMAL {
            @Override
             <T> void load(final CustomServiceLoader<T> serviceLoader) throws IOException {
                final String serviceFile = serviceLoader.getServiceFileName("META-INF/services/");
                serviceLoader.iterateResources(serviceFile, resource -> {
                    try (final InputStream stream = resource.openStream()) {
                        final String content = IOUtil.toString(stream);
                        final String[] split;
                        final String[] lines = split = content.split(System.lineSeparator());
                        int i = 0;
                        for (int length = split.length; i < length; ++i) {
                            final String line = split[i];
                            final String line2 = line.trim();
                            serviceLoader.registerClass(line2);
                        }
                    }
                });
            }
        };
        
        abstract <T> void load(final CustomServiceLoader<T> p0) throws IOException;
    }
}
