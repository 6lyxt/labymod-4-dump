// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.meta;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.theme.Theme;

public class LinkReference implements Comparable<LinkReference>
{
    private final String namespace;
    private final String path;
    private final int priority;
    
    public LinkReference(final String namespace, final String path, final int priority) {
        this.namespace = namespace;
        this.path = path;
        this.priority = priority;
    }
    
    public LinkReference(final String namespace, final String path) {
        this(namespace, path, 0);
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public ThemeFile toThemeFile(final Theme theme) {
        return ThemeFile.create(theme, this.namespace, this.path);
    }
    
    public StyleSheet loadStyleSheet() {
        final ThemeFile file = this.toThemeFile(Laby.labyAPI().themeService().currentTheme());
        final StyleSheet styleSheet = Laby.references().styleSheetLoader().load(file.normalize());
        if (styleSheet == null) {
            return null;
        }
        return styleSheet.withPriority(this.priority);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof LinkReference && ((LinkReference)obj).namespace.equals(this.namespace) && ((LinkReference)obj).path.equals(this.path) && ((LinkReference)obj).priority == this.priority;
    }
    
    @Override
    public int hashCode() {
        int result = (this.namespace != null) ? this.namespace.hashCode() : 0;
        result = 31 * result + ((this.path != null) ? this.path.hashCode() : 0);
        result = 31 * result + this.priority;
        return result;
    }
    
    @Override
    public int compareTo(@NotNull final LinkReference o) {
        return Integer.compare(this.priority, o.priority);
    }
}
