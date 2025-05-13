// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.util.crashtracker;

import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.launchwrapper.Launch;
import java.util.Locale;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.Function;
import java.io.PrintStream;

public class CrashTracker
{
    private static final PrintStream SYSOUT;
    private static Function<Class<?>, String> addonNamespaceFunction;
    private static Function<String, Boolean> addonExistsFunction;
    private static Supplier<String> additionalDataSupplier;
    private static Supplier<List<MixinConfig>> mixinConfigSupplier;
    
    public static void setAddonNamespaceFunction(final Function<Class<?>, String> addonNamespaceFunction) {
        CrashTracker.addonNamespaceFunction = addonNamespaceFunction;
    }
    
    public static void setAddonExistsFunction(final Function<String, Boolean> addonExistsFunction) {
        CrashTracker.addonExistsFunction = addonExistsFunction;
    }
    
    public static void setAdditionalDataSupplier(final Supplier<String> additionalDataSupplier) {
        CrashTracker.additionalDataSupplier = additionalDataSupplier;
    }
    
    public static void setMixinConfigSupplier(final Supplier<List<MixinConfig>> mixinConfigSupplier) {
        CrashTracker.mixinConfigSupplier = mixinConfigSupplier;
    }
    
    public static Cause findCause(final Throwable throwable) {
        final String launcher = System.getProperty("minecraft.launcher.brand", "Unknown");
        if (!launcher.equals("labymod-launcher") && !launcher.equals("LabyGradle")) {
            return null;
        }
        Cause crashCause = null;
        CrashType type;
        if (throwable instanceof OutOfMemoryError) {
            type = CrashType.MEMORY;
        }
        else {
            crashCause = getCause(throwable);
            if (crashCause != null) {
                type = crashCause.type;
            }
            else {
                type = getCrashType(throwable);
            }
        }
        String crashReportJson = "{";
        if (type != null) {
            crashReportJson = crashReportJson + "\"type\":\"" + type.name().toLowerCase(Locale.ROOT);
        }
        if (crashCause != null && crashCause.namespace != null) {
            if (crashReportJson.length() > 1) {
                crashReportJson = crashReportJson;
            }
            crashReportJson = crashReportJson + "\"cause\":\"" + crashCause.namespace;
        }
        if (CrashTracker.additionalDataSupplier != null) {
            try {
                final String additionalData = CrashTracker.additionalDataSupplier.get();
                if (additionalData != null) {
                    if (crashReportJson.length() > 1) {
                        crashReportJson = crashReportJson;
                    }
                    crashReportJson = crashReportJson + "\"additionalData\":" + additionalData;
                }
            }
            catch (final Throwable t) {}
        }
        crashReportJson = crashReportJson;
        CrashTracker.SYSOUT.println("#!@_% LABYMOD CRASH REPORT %_@!# " + crashReportJson);
        if (crashCause != null) {
            return crashCause;
        }
        return new Cause(type, null);
    }
    
    private static CrashType getCrashType(final Throwable throwable) {
        if (throwableContains(throwable, "net.labymod.")) {
            return CrashType.LABYMOD;
        }
        if (throwableContains(throwable, "net.minecraft.")) {
            return CrashType.MINECRAFT;
        }
        return null;
    }
    
    private static boolean throwableContains(Throwable throwable, final String string) {
        while (throwable != null) {
            try {
                for (final StackTraceElement frame : throwable.getStackTrace()) {
                    if (frame != null && frame.getClassName().contains(string)) {
                        return true;
                    }
                }
            }
            catch (final Throwable t) {}
            throwable = throwable.getCause();
        }
        return false;
    }
    
    private static Cause getCause(Throwable throwable) {
        boolean isMixin = throwable.getClass().getSimpleName().equals("MixinTransformerError");
        if (!isMixin) {
            final Throwable cause = throwable.getCause();
            if (cause != null && cause.getClass().getSimpleName().equals("MixinTransformerError")) {
                isMixin = true;
                throwable = cause;
            }
        }
        if (isMixin) {
            try {
                final Cause mixinTransformerError = getMixinTransformerError(throwable);
                if (mixinTransformerError != null) {
                    return mixinTransformerError;
                }
            }
            catch (final Throwable ignored) {
                ignored.printStackTrace();
            }
        }
        if (CrashTracker.addonNamespaceFunction == null) {
            return null;
        }
        while (throwable != null) {
            try {
                for (final StackTraceElement frame : throwable.getStackTrace()) {
                    try {
                        final Class<?> type = Launch.classLoader.findClass(frame.getClassName());
                        final String namespace = CrashTracker.addonNamespaceFunction.apply(type);
                        if (namespace != null) {
                            return new Cause(CrashType.ADDON, namespace);
                        }
                    }
                    catch (final Throwable t) {}
                }
            }
            catch (final Throwable t2) {}
            throwable = throwable.getCause();
        }
        return null;
    }
    
    private static Cause getMixinTransformerError(final Throwable throwable) {
        final Throwable cause = throwable.getCause();
        if (cause == null) {
            return null;
        }
        final String message = cause.getMessage();
        if (message == null) {
            return null;
        }
        final String causeClassName = cause.getClass().getSimpleName();
        if (causeClassName.equals("MixinApplyError")) {
            return getMixinCause(message, MixinChecker.MIXIN);
        }
        if (causeClassName.equals("InjectionError")) {
            return getMixinCause(message, MixinChecker.REFMAP);
        }
        return null;
    }
    
    private static Cause getMixinCause(final String message, final MixinChecker checker) {
        List<MixinConfig> mixinConfigs;
        if (CrashTracker.mixinConfigSupplier == null) {
            mixinConfigs = new ArrayList<MixinConfig>();
        }
        else {
            mixinConfigs = CrashTracker.mixinConfigSupplier.get();
        }
        MixinConfig mixinConfig = null;
        for (final MixinConfig config : mixinConfigs) {
            if (message.contains((checker == MixinChecker.MIXIN) ? config.mixinName() : config.refmapName())) {
                mixinConfig = config;
                break;
            }
        }
        if (mixinConfig != null) {
            if (mixinConfig.mixinName.contains("labymod4") && mixinConfig.modId == null && mixinConfig.parent() == null) {
                return new Cause(CrashType.LABYMOD, null);
            }
            while (mixinConfig.parent() != null) {
                mixinConfig = mixinConfig.parent();
            }
            CrashType crashType;
            if (mixinConfig.modId != null && CrashTracker.addonExistsFunction != null && CrashTracker.addonExistsFunction.apply(mixinConfig.modId)) {
                crashType = CrashType.ADDON;
            }
            else {
                crashType = CrashType.MOD;
            }
            return new Cause(crashType, mixinConfig.modId);
        }
        else {
            final int index = message.indexOf(checker.suffix);
            if (index == -1) {
                return null;
            }
            final int i = message.lastIndexOf(" ", index);
            String namespace = message.substring((i == -1) ? 0 : (i + 1), index - 1).trim();
            final char c = namespace.charAt(namespace.length() - 1);
            if (c == '.' || c == '-' || c == '_') {
                namespace = namespace.substring(0, namespace.length() - 1);
            }
            if (namespace.charAt(0) == '[') {
                namespace = namespace.substring(1);
            }
            final String[] split = namespace.split("-", 2);
            if (split.length == 2) {
                namespace = split[1];
            }
            CrashType type;
            if (namespace.equals("labymod4")) {
                type = CrashType.LABYMOD;
                namespace = null;
            }
            else if (CrashTracker.addonExistsFunction != null && CrashTracker.addonExistsFunction.apply(namespace)) {
                type = CrashType.ADDON;
            }
            else {
                type = CrashType.MOD;
            }
            return new Cause(type, namespace);
        }
    }
    
    static {
        SYSOUT = System.out;
    }
    
    private enum MixinChecker
    {
        MIXIN("mixins.json"), 
        REFMAP("refmap.json");
        
        private final String suffix;
        
        private MixinChecker(final String suffix) {
            this.suffix = suffix;
        }
    }
    
    public enum CrashType
    {
        MEMORY, 
        MINECRAFT, 
        LABYMOD, 
        ADDON, 
        MOD;
    }
    
    record Cause(CrashType type, String namespace) {}
    
    record MixinConfig(String mixinName, String refmapName, String modId, MixinConfig parent) {}
}
