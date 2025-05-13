// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import net.labymod.api.client.gui.screen.theme.Theme;
import java.util.Objects;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceLocation;

public class DefaultThemeResourceLocation implements ThemeResourceLocation
{
    private final String id;
    private final String namespace;
    private final String path;
    private ResourceLocation location;
    private String themeId;
    
    protected DefaultThemeResourceLocation(final String id, final String namespace, final String path) {
        this.id = id;
        this.namespace = namespace;
        this.path = path;
    }
    
    @NotNull
    public String getId() {
        return this.id;
    }
    
    @Override
    public ResourceLocation resource() {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (this.location != null && Objects.equals(this.themeId, theme.getId())) {
            return this.location;
        }
        this.themeId = theme.getId();
        return this.location = this.resource(theme);
    }
    
    public ResourceLocation resource(@NotNull final Theme theme) {
        return theme.resource((this.namespace == null) ? theme.getNamespace() : this.namespace, this.path);
    }
    
    @Override
    public String toString() {
        return this.resource().toString();
    }
    
    public static class Builder
    {
        protected String id;
        protected String namespace;
        protected String path;
        
        public Builder id(final String id) {
            this.id = id;
            return this;
        }
        
        public Builder namespace(final String namespace) {
            this.namespace = namespace;
            return this;
        }
        
        public Builder path(final String path) {
            this.path = path;
            return this;
        }
        
        public DefaultThemeResourceLocation build() {
            if (this.namespace == null && this.path.contains(":")) {
                final String[] split = this.path.split(":");
                this.path = split[1];
                this.namespace = split[0];
            }
            if (this.id == null) {
                String id = this.path.replace(".", "");
                if (id.contains(":")) {
                    id = id.split(":")[1];
                }
                this.id = id;
            }
            return new DefaultThemeResourceLocation(this.id, this.namespace, this.path);
        }
    }
}
