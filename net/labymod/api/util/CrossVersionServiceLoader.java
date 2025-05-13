// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Enumeration;
import java.io.IOException;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.util.io.IOUtil;
import java.net.URL;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.util.logging.Logging;

@Deprecated
public class CrossVersionServiceLoader<T> implements Iterable<T>
{
    private static final Logging LOGGER;
    private static final String LABYMOD_SERVICES_DIRECTORY = "META-INF/services/";
    private final List<T> serviceClasses;
    private final Class<T> serviceClass;
    private final ClassLoader loader;
    
    private CrossVersionServiceLoader(final Class<T> serviceClass, final ClassLoader loader) {
        this.serviceClasses = new ArrayList<T>();
        this.serviceClass = serviceClass;
        this.loader = loader;
        this.prepare();
    }
    
    private void prepare() {
        try {
            final List<String> classes = new ArrayList<String>();
            final String name = "META-INF/services/" + this.serviceClass.getName();
            final Enumeration<URL> resources = PlatformEnvironment.getResources(this.loader, name);
            while (resources.hasMoreElements()) {
                final URL url = resources.nextElement();
                final String serviceContent = IOUtil.toString(url.openStream());
                final String[] split = serviceContent.split("\n");
                final List<Pair<String, Double>> services = new ArrayList<Pair<String, Double>>();
                for (final String s : split) {
                    final String[] serviceSplit = s.split(";", 2);
                    try {
                        final double version = Double.parseDouble(serviceSplit[1]);
                        if (ASMHelper.canLoadClass((int)version, ASMHelper.getCurrentJavaClassVersion())) {
                            services.add(Pair.of(serviceSplit[0], version));
                        }
                    }
                    catch (final NumberFormatException ignored) {
                        CrossVersionServiceLoader.LOGGER.error("Invalid service format. ({})", s);
                    }
                }
                for (final Pair<String, Double> service : services) {
                    classes.add(service.getFirst());
                }
            }
            for (String clazz : classes) {
                try {
                    clazz = clazz.trim();
                    final Constructor<?> constructor = Reflection.searchEmptyConstructor(Class.forName(clazz, false, this.loader));
                    if (constructor == null) {
                        continue;
                    }
                    this.serviceClasses.add((T)constructor.newInstance(new Object[0]));
                }
                catch (final Exception exception) {
                    if (exception instanceof ClassNotFoundException) {
                        CrossVersionServiceLoader.LOGGER.error("The class \"{}\" was not found.", clazz, exception);
                    }
                    else {
                        CrossVersionServiceLoader.LOGGER.error("An error occurred while invoking the constructor.", exception);
                    }
                }
            }
        }
        catch (final IOException exception2) {
            CrossVersionServiceLoader.LOGGER.error("An error occurred while reading the LabyMod services.", exception2);
        }
    }
    
    public static <T> CrossVersionServiceLoader<T> load(final Class<T> serviceClass, final ClassLoader loader) {
        return new CrossVersionServiceLoader<T>(serviceClass, loader);
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.serviceClasses.iterator();
    }
    
    static {
        LOGGER = new CrossVersionServiceLoaderLogging();
    }
    
    private static class CrossVersionServiceLoaderLogging implements Logging
    {
        @Override
        public void info(final CharSequence message, final Throwable throwable) {
        }
        
        @Override
        public void info(final CharSequence message, final Object... arguments) {
        }
        
        @Override
        public void warn(final CharSequence message, final Throwable throwable) {
        }
        
        @Override
        public void warn(final CharSequence message, final Object... arguments) {
        }
        
        @Override
        public void error(final CharSequence message, final Throwable throwable) {
            System.out.println(message);
            System.out.println("Stacktrace: ");
            final StackTraceElement[] stackTrace = throwable.getStackTrace();
            for (int length = stackTrace.length, i = 0; i < length; ++i) {
                final StackTraceElement element = stackTrace[i];
                System.out.println("\t" + element.getClassName() + "/" + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber());
            }
        }
        
        @Override
        public void error(final CharSequence message, final Object... arguments) {
            System.out.println(String.format(Locale.ROOT, CharSequences.toString(message), arguments));
        }
        
        @Override
        public void debug(final CharSequence message, final Throwable throwable) {
        }
        
        @Override
        public void debug(final CharSequence message, final Object... arguments) {
        }
    }
}
