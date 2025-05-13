// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import java.util.HashMap;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import java.util.Locale;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ThemeFile extends BasicThemeFile
{
    private static final Map<String, ThemeFile> CACHE;
    private static final String THEMES_DIR = "themes/";
    private final String[] pathSegments;
    private final String[] absolutePath;
    private final String fullRawPath;
    
    private ThemeFile(final Theme theme, final String namespace, final String[] pathSegments) {
        super(theme, namespace, String.join("/", (CharSequence[])pathSegments));
        this.pathSegments = pathSegments;
        this.absolutePath = this.rebuildPathStructure(pathSegments);
        this.fullRawPath = "themes/" + theme.getId() + "/" + String.join("/", (CharSequence[])this.absolutePath);
    }
    
    private ThemeFile(final Theme theme, final String namespace, final String path) {
        super(theme, namespace, path);
        this.pathSegments = path.split("/");
        this.absolutePath = this.rebuildPathStructure(this.pathSegments);
        this.fullRawPath = "themes/" + theme.getId() + "/" + String.join("/", (CharSequence[])this.absolutePath);
    }
    
    private String[] rebuildPathStructure(String[] path) {
        try {
            final List<String> nodes = new ArrayList<String>();
            for (final String node : path) {
                if (!node.equals(".")) {
                    if (node.equals("..") && !nodes.isEmpty() && !nodes.get(nodes.size() - 1).equals("..")) {
                        nodes.remove(nodes.size() - 1);
                    }
                    else {
                        nodes.add(node);
                    }
                }
            }
            path = new String[nodes.size()];
            nodes.toArray(path);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return path;
    }
    
    public String[] getAbsolutePath() {
        return this.absolutePath;
    }
    
    public String getFullRawPath() {
        return this.fullRawPath;
    }
    
    public ThemeFile parent() {
        return create(this.theme, this.namespace, Arrays.copyOf(this.pathSegments, this.pathSegments.length - 1));
    }
    
    @Override
    public String getNamespace() {
        return this.namespace;
    }
    
    public Theme theme() {
        return this.theme;
    }
    
    public String getName() {
        return this.pathSegments[this.pathSegments.length - 1];
    }
    
    public boolean isDirectory() {
        return !this.getName().contains(".");
    }
    
    public InputStream openInputStream() throws IOException {
        return this.toResourceLocation().openStream();
    }
    
    public ThemeFile forTheme(final Theme theme) {
        return create(theme, this.namespace, this.path);
    }
    
    public ThemeFile normalize() {
        return this.theme.file(this.namespace, this.path);
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s:%s", this.namespace, (this.theme != null) ? (this.theme.getId() + "/" + this.getPath()) : this.getPath());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof final ThemeFile themeFile) {
            return this.namespace.equals(themeFile.namespace) && this.theme.equals(themeFile.theme) && this.getPath().equals(themeFile.getPath());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = this.namespace.hashCode();
        result = 31 * result + this.theme.hashCode();
        result = 31 * result + this.getPath().hashCode();
        return result;
    }
    
    public LinkReference toLink() {
        return new LinkReference(this.namespace, this.path, 0);
    }
    
    public static ThemeFile create(final Theme theme, final String namespace, final String path) {
        final String cacheKey = theme.getId() + ":" + namespace + ":" + path;
        return ThemeFile.CACHE.computeIfAbsent(cacheKey, key -> new ThemeFile(theme, namespace, path));
    }
    
    public static ThemeFile create(final Theme theme, final String namespace, final String[] pathSegments) {
        final String cacheKey = theme.getId() + ":" + namespace + ":" + String.join("/", (CharSequence[])pathSegments);
        return ThemeFile.CACHE.computeIfAbsent(cacheKey, key -> new ThemeFile(theme, namespace, pathSegments));
    }
    
    static {
        CACHE = new HashMap<String, ThemeFile>();
    }
}
