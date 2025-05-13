// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;

public class BasicThemeFile
{
    protected final Theme theme;
    protected final String namespace;
    protected final String path;
    private ResourceLocation resourceLocation;
    
    public BasicThemeFile(final Theme theme, final String namespace, final String path) {
        this.theme = theme;
        this.namespace = namespace;
        this.path = path;
    }
    
    public Theme getTheme() {
        return this.theme;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public ResourceLocation toResourceLocation() {
        if (this.resourceLocation != null) {
            return this.resourceLocation;
        }
        return this.resourceLocation = ResourceLocation.create(this.namespace, this.theme.getDirectoryPath() + this.getPath());
    }
    
    public boolean exists() {
        return Laby.references().themeFileFinder().exists(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BasicThemeFile file = (BasicThemeFile)o;
        return this.namespace.equals(file.namespace) && this.theme.equals(file.theme) && this.path.equals(file.path);
    }
    
    @Override
    public int hashCode() {
        int result = (this.theme != null) ? this.theme.hashCode() : 0;
        result = 31 * result + ((this.namespace != null) ? this.namespace.hashCode() : 0);
        result = 31 * result + ((this.path != null) ? this.path.hashCode() : 0);
        return result;
    }
}
