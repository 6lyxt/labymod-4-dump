// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import java.util.Objects;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.util.function.Mapper;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class ButtonConverter extends AbstractMinecraftWidgetConverter<fid, ButtonWidget>
{
    final Map<ResourceLocation, String> mappedIcons;
    
    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.mappedIcons = new HashMap<ResourceLocation, String>();
        this.prepareMappedIcons();
        this.registerMapper(new ImageButtonMapper());
        this.registerMapper(new LockIconButtonMapper());
    }
    
    @Override
    public ButtonWidget createDefault(final fid source) {
        final ButtonWidget destination = ButtonWidget.component((source instanceof fjr.b) ? null : this.mapComponent(source.z()), null, Pressable.NOOP);
        destination.setFocused(source.aO_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fid source, final ButtonWidget destination) {
        destination.setVisible(source.k);
        destination.setEnabled(source.j);
        destination.setFocused(source.aO_());
        this.copyBounds(source, destination);
    }
    
    private void prepareMappedIcons() {
        final ResourceLocationFactory factory = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
        this.mappedIcons.put(factory.createMinecraft("textures/gui/recipe_button.png"), "icon-button-recipe-book");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/accessibility.png"), "icon-button-accessibility");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/widgets.png"), "icon-button-language");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/social_interactions.png"), "icon-social-interactions");
    }
    
    @Override
    public String getWidgetId(final fid source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.z());
    }
    
    @Override
    public List<String> getWidgetIds(final fid source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.z());
    }
    
    class ImageButtonMapper implements Mapper<fid, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final fid source) {
            if (source instanceof final fiy imageButton) {
                final ImageButtonAccessor accessor = (ImageButtonAccessor)imageButton;
                final ResourceLocation location = accessor.getResourceLocation();
                final Icon icon = Icon.sprite(location, accessor.getXTexStart(), accessor.getYTexStart(), source.y(), source.w(), accessor.getTextureWidth(), accessor.getTextureHeight());
                icon.setHoverOffset(0, accessor.getYDiffTex());
                final ButtonWidget button = new ButtonWidget();
                button.icon().set(icon);
                final String id = ButtonConverter.this.mappedIcons.get(location);
                if (id != null) {
                    ((AbstractWidget<Widget>)button).addId(id);
                }
                return button;
            }
            return null;
        }
    }
    
    class LockIconButtonMapper implements Mapper<fid, ButtonWidget>
    {
        LockIconButtonMapper(final ButtonConverter this$0) {
        }
        
        @Override
        public ButtonWidget map(final fid source) {
            if (source instanceof final fjc lockIconButton) {
                final int size = source.y();
                final ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
                final boolean locked = lockIconButton.a();
                LockIconSprite sprite;
                if (!lockIconButton.C()) {
                    sprite = (locked ? LockIconSprite.LOCKED_DISABLED : LockIconSprite.UNLOCKED_DISABLED);
                }
                else if (lockIconButton.B()) {
                    sprite = (locked ? LockIconSprite.LOCKED_HOVER : LockIconSprite.UNLOCKED_HOVER);
                }
                else {
                    sprite = (locked ? LockIconSprite.LOCKED : LockIconSprite.UNLOCKED);
                }
                final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
                final TextureSprite textureSprite = atlas.findSprite(sprite.sprite());
                final Icon sprite2;
                final Icon icon = sprite2 = Icon.sprite(atlas, textureSprite);
                final LockIconSprite obj = locked ? LockIconSprite.LOCKED_HOVER : LockIconSprite.UNLOCKED_HOVER;
                Objects.requireNonNull(obj);
                sprite2.setHoverSprite(obj::sprite);
                final ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
                ((AbstractWidget<Widget>)button).addId("lock-icon-button");
                return button;
            }
            return null;
        }
        
        enum LockIconSprite
        {
            LOCKED("widget/locked_button"), 
            LOCKED_HOVER("widget/locked_button_highlighted"), 
            LOCKED_DISABLED("widget/locked_button_disabled"), 
            UNLOCKED("widget/unlocked_button"), 
            UNLOCKED_HOVER("widget/unlocked_button_highlighted"), 
            UNLOCKED_DISABLED("widget/unlocked_button_disabled");
            
            private final ResourceLocation sprite;
            
            private LockIconSprite(final String spritePath) {
                this.sprite = ResourceLocation.create("minecraft", spritePath);
            }
            
            public ResourceLocation sprite() {
                return this.sprite;
            }
        }
    }
}
