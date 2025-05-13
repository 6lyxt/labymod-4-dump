// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader;

import java.io.InputStream;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.reflection.Reflection;
import java.lang.invoke.MethodType;
import java.nio.file.Path;
import java.util.List;

public final class ReflectLabyModLoader
{
    public static void invokeGameOptions(final List<String> arguments, final Path gameDirectory, final Path assetsDirectory, final String profile, final boolean labyModDevEnvironment, final boolean addonDevEnvironment, final String minecraftVersion) {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            Reflection.findVirtual(labyModLoader.getClass(), "gameOptions", MethodType.methodType(Void.TYPE, List.class, Path.class, Path.class, String.class, Boolean.TYPE, Boolean.TYPE, String.class)).invoke(labyModLoader, (List)arguments, gameDirectory, assetsDirectory, profile, labyModDevEnvironment, addonDevEnvironment, minecraftVersion);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static String[] invokeLaunchArguments() {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            final List<String> list = Reflection.findVirtual(labyModLoader.getClass(), "getArguments", MethodType.methodType(List.class)).invoke(labyModLoader);
            return list.toArray(new String[0]);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static void invokeLoadAddons() {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            Reflection.findVirtual(labyModLoader.getClass(), "loadAddons", MethodType.methodType(Void.TYPE)).invoke(labyModLoader);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static void invokeLoadIsolatedLibraries() {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            Reflection.findVirtual(labyModLoader.getClass(), "loadIsolatedLibraries", MethodType.methodType(Void.TYPE)).invoke(labyModLoader);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static String invokeGetGameVersion() {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            final Class<?> versionClass = PlatformEnvironment.getPlatformClassloader().findClass("net.labymod.api.models.version.Version");
            final Object getVersion = Reflection.findVirtual(labyModLoader.getClass(), "version", MethodType.methodType(versionClass)).invoke(labyModLoader);
            return Reflection.findVirtual(versionClass, "toString", MethodType.methodType(String.class)).invoke(getVersion);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static boolean invokeIsLabyModDevEnvironment() {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            return Reflection.findVirtual(labyModLoader.getClass(), "isLabyModDevelopmentEnvironment", MethodType.methodType(Boolean.TYPE)).invoke(labyModLoader);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static InputStream invokeGetMixinServiceResourceAsStream(final String name, final InputStream parentInputStream) {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            return Reflection.findVirtual(labyModLoader.getClass(), "getMixinServiceResourceAsStream", MethodType.methodType(InputStream.class, String.class, InputStream.class)).invoke(labyModLoader, name, parentInputStream);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    public static byte[] invokeGetMixinServiceClassBytes(final String name, final String transformedName, final byte[] parentClassData) {
        try {
            final Object labyModLoader = invokeGetLabyModLoader();
            return Reflection.findVirtual(labyModLoader.getClass(), "getMixinServiceClassBytes", MethodType.methodType(byte[].class, String.class, String.class, byte[].class)).invoke(labyModLoader, name, transformedName, parentClassData);
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
    
    private static Object invokeGetLabyModLoader() {
        try {
            final Class<?> interfaceClass = PlatformEnvironment.getPlatformClassloader().findClass("net.labymod.api.loader.LabyModLoader");
            final Class<?> implementationClass = PlatformEnvironment.getPlatformClassloader().findClass("net.labymod.core.loader.DefaultLabyModLoader");
            return Reflection.findStatic(implementationClass, "getInstance", MethodType.methodType(interfaceClass)).invoke();
        }
        catch (final Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
}
