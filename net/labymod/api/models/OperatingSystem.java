// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models;

import java.net.URISyntaxException;
import java.net.URI;
import java.io.File;
import java.net.MalformedURLException;
import java.security.PrivilegedActionException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.net.URL;
import java.util.Locale;

public enum OperatingSystem permits OperatingSystem$1, OperatingSystem$2
{
    LINUX("linux", "lib", "so", new String[] { "linux", "unix" }), 
    SOLARIS("solaris", "", "so", new String[] { "solaris", "sunos" }), 
    WINDOWS("windows", "", "dll", new String[] { "win" }) {
        @Override
        protected String[] getOpenUrlArguments(final URL url) {
            return new String[] { "rundll32", "url.dll,FileProtocolHandler", url.toString() };
        }
    }, 
    MACOS("macos", "lib", "dylib", new String[] { "mac" }) {
        @Override
        protected String[] getOpenUrlArguments(final URL url) {
            return new String[] { "open", url.toString() };
        }
    }, 
    UNKNOWN((String)null, (String)null, (String)null, new String[0]);
    
    private static final OperatingSystem PLATFORM;
    private final String name;
    private final String libraryPrefix;
    private final String libraryExtensionName;
    private final String[] ids;
    private boolean is64Bit;
    
    private OperatingSystem(final String name, final String libraryPrefix, final String libraryExtensionName, final String[] ids) {
        this.name = name;
        this.libraryPrefix = libraryPrefix;
        this.libraryExtensionName = libraryExtensionName;
        this.ids = ids;
        this.is64Bit = this.checkIs64Bit();
    }
    
    public String[] getIds() {
        return this.ids;
    }
    
    public static boolean isOSX() {
        return OperatingSystem.PLATFORM == OperatingSystem.MACOS;
    }
    
    public static OperatingSystem getPlatform() {
        return OperatingSystem.PLATFORM;
    }
    
    private static OperatingSystem getPlatform0() {
        final String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        for (final OperatingSystem os : values()) {
            if (os != OperatingSystem.UNKNOWN) {
                for (final String name : os.getIds()) {
                    if (osName.contains(name)) {
                        return os;
                    }
                }
            }
        }
        return OperatingSystem.UNKNOWN;
    }
    
    public static boolean is64Bit() {
        return getPlatform().is64BitSystem();
    }
    
    public boolean is64BitSystem() {
        return this.is64Bit;
    }
    
    public String getLibraryFileName(final String name) {
        return String.format(Locale.ROOT, "%s%s-%s.%s", this.libraryPrefix, name, is64Bit() ? "64" : "32", this.libraryExtensionName);
    }
    
    public String getLibraryJarName(final String name, final String version) {
        return String.format(Locale.ROOT, "%s-%s-natives-%s-x%s.jar", name, version, this.name, is64Bit() ? "64" : "32");
    }
    
    public boolean isLibrary(final String name) {
        return name.endsWith(this.libraryExtensionName);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getArch() {
        return System.getProperty("os.arch");
    }
    
    public String getLibraryPrefix() {
        return this.libraryPrefix;
    }
    
    public String getLibraryExtensionName() {
        return this.libraryExtensionName;
    }
    
    public boolean launchUrlProcess(final URL url) {
        try {
            final Process process = AccessController.doPrivileged(() -> Runtime.getRuntime().exec(this.getOpenUrlArguments(url)));
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println(line);
                }
            }
            process.getInputStream().close();
            process.getErrorStream().close();
            process.getOutputStream().close();
            return true;
        }
        catch (final IOException | PrivilegedActionException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void openUrl(final URL url) {
        this.launchUrlProcess(url);
    }
    
    public void openUrl(final String url) {
        try {
            this.openUrl(new URL(url));
        }
        catch (final MalformedURLException exception) {
            exception.printStackTrace();
        }
    }
    
    public void openFile(final File file) {
        try {
            this.openUrl(file.toURI().toURL());
        }
        catch (final MalformedURLException exception) {
            exception.printStackTrace();
        }
    }
    
    public void openUri(final URI uri) {
        try {
            this.openUrl(uri.toURL());
        }
        catch (final MalformedURLException exception) {
            exception.printStackTrace();
        }
    }
    
    public void openUri(final String uri) {
        try {
            this.openUrl(new URI(uri).toURL());
        }
        catch (final MalformedURLException | IllegalArgumentException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }
    
    protected String[] getOpenUrlArguments(final URL param0) {
        String var0 = param0.toString();
        if ("file".equals(param0.getProtocol())) {
            var0 = var0.replace("file:", "file://");
        }
        return new String[] { "xdg-open", var0 };
    }
    
    private boolean checkIs64Bit() {
        final String[] array;
        final String[] properties = array = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (final String property : array) {
            final String propertyValue = System.getProperty(property);
            if (propertyValue != null && propertyValue.contains("64")) {
                return true;
            }
        }
        return false;
    }
    
    static {
        PLATFORM = getPlatform0();
    }
}
