// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class SelectionWidget extends SimpleWidget
{
    private static final boolean GUI_ATLAS;
    private static final String VANILLA_SELECTION_ADD_LEFT_VARIABLE = "--vanilla-selection-add-left";
    private static final String VANILLA_SELECTION_DOWN_TOP_VARIABLE = "--vanilla-selection-down-top";
    private static final String VANILLA_SELECTION_TRANSFERABLE_SELECT_LEFT_VARIABLE = "--vanilla-selection-transferable-select-left";
    private static final String VANILLA_SELECTION_TRANSFERABLE_UNSELECT_LEFT_VARIABLE = "--vanilla-selection-transferable-unselect-left";
    private final SelectionIcon icon;
    
    public SelectionWidget(final SelectionIcon icon) {
        this.icon = icon;
        this.addId(icon.getId());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean serverSelectionTextureFeature = this.isServerSelectionTextureFeature();
        this.setVariable("--vanilla-selection-add-left", serverSelectionTextureFeature ? 0.0f : 16.0f);
        this.setVariable("--vanilla-selection-down-top", serverSelectionTextureFeature ? 0.0f : 16.0f);
        this.setVariable("--vanilla-selection-transferable-select-left", 0.0f);
        this.setVariable("--vanilla-selection-transferable-unselect-left", 16.0f);
    }
    
    @Override
    public void render(final ScreenContext context) {
        final Icon selectionIcon = this.icon.getIcon(this.isHovered());
        final Bounds bounds = this.bounds();
        if (this.isVisible()) {
            selectionIcon.render(context.stack(), bounds.getLeft(), bounds.getTop(), bounds.getWidth(), bounds.getHeight());
        }
        super.render(context);
    }
    
    @Override
    protected boolean isHovered(final float mouseX, final float mouseY) {
        final boolean serverSelectionTextureFeature = this.isServerSelectionTextureFeature();
        if (!serverSelectionTextureFeature) {
            return super.isHovered(mouseX, mouseY);
        }
        final Bounds bounds = this.bounds();
        final MutableRectangle middleBounds = bounds.copy(BoundsType.MIDDLE);
        middleBounds.setPosition(middleBounds.getX() + this.icon.offset.getX(), middleBounds.getY() + this.icon.offset.getY());
        return (!(this.parent instanceof Widget) || ((Widget)this.parent).isHovered()) && middleBounds.isInRectangle(mouseX, mouseY) && (this.inOverlay || !this.labyAPI.screenOverlayHandler().isOverlayHovered()) && this.canHover();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isHovered()) {
            this.callActionListeners();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    private boolean isServerSelectionTextureFeature() {
        return SelectionWidget.GUI_ATLAS && this.labyAPI.themeService().currentTheme().metadata().getBoolean("server_selection_texture_feature");
    }
    
    static {
        GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();
    }
    
    public enum SelectionIcon
    {
        ADD("join", Sprite.of(16.0f, 0.0f, 16.0f, 32.0f), 16.0f, 0.0f, Type.SERVER_LIST), 
        UP("move_up", Sprite.of(96.0f, 0.0f, 16.0f, 16.0f), 0.0f, 0.0f, Type.SERVER_LIST), 
        DOWN("move_down", Sprite.of(64.0f, 16.0f, 16.0f, 16.0f), 0.0f, 16.0f, Type.SERVER_LIST), 
        TRANSFERABLE_SELECT("select", Sprite.of(16.0f, 0.0f, 16.0f, 32.0f), 0.0f, 0.0f, Type.TRANSFERABLE_LIST), 
        TRANSFERABLE_UNSELECT("unselect", Sprite.of(32.0f, 0.0f, 16.0f, 32.0f), 0.0f, 0.0f, Type.TRANSFERABLE_LIST), 
        TRANSFERABLE_UP("move_up", Sprite.of(96.0f, 0.0f, 16.0f, 16.0f), 16.0f, 0.0f, Type.TRANSFERABLE_LIST), 
        TRANSFERABLE_DOWN("move_down", Sprite.of(64.0f, 16.0f, 16.0f, 16.0f), 16.0f, 16.0f, Type.TRANSFERABLE_LIST);
        
        private static final int HOVERED_Y = 32;
        private final Sprite sprite;
        private final FloatVector2 offset;
        private final ResourceLocation defaultResourceLocation;
        private final ResourceLocation highlightedResourceLocation;
        private final String id;
        
        private SelectionIcon(final String path, final Sprite sprite, final float xOffset, final float yOffset, final Type type) {
            this.sprite = sprite;
            this.offset = new FloatVector2(xOffset, yOffset);
            this.defaultResourceLocation = ResourceLocation.create("minecraft", type.buildPath(path, false));
            this.highlightedResourceLocation = ResourceLocation.create("minecraft", type.buildPath(path, true));
            this.id = "selection-" + TextFormat.SNAKE_CASE.toDashCase(this.name());
        }
        
        public Icon getIcon(final boolean hovered) {
            ResourceLocation location = Textures.SpriteServerSelection.TEXTURE;
            if (!location.exists()) {
                location = Laby.labyAPI().minecraft().textures().serverSelectionTexture();
                if (SelectionWidget.GUI_ATLAS) {
                    final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(location);
                    final TextureSprite sprite = atlas.findSprite(this.getResourceLocation(hovered));
                    return Icon.sprite(atlas, sprite, 32.0f, 32.0f);
                }
            }
            final int spriteY = MathHelper.ceil(this.sprite.getY() + (hovered ? 32 : 0));
            return Icon.sprite(location, MathHelper.ceil(this.sprite.getX()), MathHelper.ceil((float)spriteY), MathHelper.ceil(this.sprite.getWidth()), MathHelper.ceil(this.sprite.getHeight()), 256, 256);
        }
        
        public String getId() {
            return this.id;
        }
        
        private ResourceLocation getResourceLocation(final boolean hovered) {
            return hovered ? this.highlightedResourceLocation : this.defaultResourceLocation;
        }
        
        enum Type
        {
            SERVER_LIST("server_list"), 
            TRANSFERABLE_LIST("transferable_list");
            
            private final String prefix;
            
            private Type(final String prefix) {
                this.prefix = prefix;
            }
            
            public String buildPath(final String name, final boolean highlighted) {
                String path = this.prefix + "/" + name;
                if (highlighted) {
                    path += "_highlighted";
                }
                return path;
            }
        }
    }
}
