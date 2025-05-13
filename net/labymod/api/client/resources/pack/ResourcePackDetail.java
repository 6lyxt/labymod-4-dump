// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.pack;

import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.HashUtil;
import java.nio.charset.StandardCharsets;
import net.labymod.api.util.StringUtil;
import java.io.InputStream;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;

public class ResourcePackDetail
{
    private static final ComponentMapper COMPONENT_MAPPER;
    private static final ResourcePackRepository RESOURCE_PACK_REPOSITORY;
    private final String packId;
    private final Component title;
    private final Component description;
    private final PackIcon packIcon;
    private final boolean selected;
    private InputStream packIconStream;
    private boolean hidden;
    
    private ResourcePackDetail(final String packId, final Component title, final Component description) {
        this.packId = packId;
        this.title = title;
        this.description = description;
        final String sanitizedPackId = StringUtil.sanitizePath(this.packId);
        final String iconId = HashUtil.sha1Hex(this.packId.getBytes(StandardCharsets.UTF_8));
        this.packIcon = PackIcon.create(sanitizedPackId, iconId);
        this.selected = ResourcePackDetail.RESOURCE_PACK_REPOSITORY.getSelectedPacks().contains(this.packId);
    }
    
    public static ResourcePackDetail create(final String packId, final Object title, final Object description) {
        return new ResourcePackDetail(packId, mapToComponent(title), mapToComponent(description));
    }
    
    private static Component mapToComponent(final Object obj) {
        if (obj instanceof final Component component) {
            return component;
        }
        return ResourcePackDetail.COMPONENT_MAPPER.fromMinecraftComponent(obj);
    }
    
    public String getPackId() {
        return this.packId;
    }
    
    public Component getTitle() {
        return this.title;
    }
    
    public Component getDescription() {
        return this.description;
    }
    
    public Icon getPackIcon() {
        this.packIcon.load(this.packIconStream);
        return this.packIcon.getIcon();
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public void setPackIconStream(final InputStream packIconStream) {
        this.packIconStream = packIconStream;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    static {
        COMPONENT_MAPPER = Laby.references().componentMapper();
        RESOURCE_PACK_REPOSITORY = Laby.references().resourcePackRepository();
    }
    
    private static class PackIcon
    {
        private final ResourceLocation location;
        private Icon icon;
        private boolean loaded;
        
        private PackIcon(final ResourceLocation location) {
            this.location = location;
        }
        
        public static PackIcon create(final String packId, final String hash) {
            return new PackIcon(ResourceLocation.create("labymod", "icon/" + packId + "/" + hash + ".png"));
        }
        
        public void load(final InputStream packIconStream) {
            if (this.loaded) {
                return;
            }
            this.loaded = true;
            if (packIconStream == null) {
                this.icon = Icon.defaultServer();
                return;
            }
            try {
                packIconStream.reset();
                final GameImage image = GameImage.IMAGE_PROVIDER.getImage(packIconStream);
                image.uploadTextureAt(this.location);
                this.icon = Icon.texture(this.location);
            }
            catch (final IOException exception) {
                this.icon = Icon.defaultServer();
            }
        }
        
        public Icon getIcon() {
            return this.icon;
        }
    }
}
