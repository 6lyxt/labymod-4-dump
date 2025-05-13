// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.Widget;
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

public class ButtonConverter extends AbstractMinecraftWidgetConverter<eoz, ButtonWidget>
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
    public ButtonWidget createDefault(final eoz source) {
        final ButtonWidget destination = ButtonWidget.component(this.mapComponent(source.l()), null, Pressable.NOOP);
        destination.setFocused(source.aB_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final eoz source, final ButtonWidget destination) {
        destination.setVisible(source.s);
        destination.setEnabled(source.r);
        destination.setFocused(source.aB_());
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
    public String getWidgetId(final eoz source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.l());
    }
    
    @Override
    public List<String> getWidgetIds(final eoz source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.l());
    }
    
    class ImageButtonMapper implements Mapper<eoz, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final eoz source) {
            if (source instanceof final ept imageButton) {
                final ImageButtonAccessor accessor = (ImageButtonAccessor)imageButton;
                final ResourceLocation location = accessor.getResourceLocation();
                final Icon icon = Icon.sprite(location, accessor.getXTexStart(), accessor.getYTexStart(), source.k(), source.h(), accessor.getTextureWidth(), accessor.getTextureHeight());
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
    
    class LockIconButtonMapper implements Mapper<eoz, ButtonWidget>
    {
        LockIconButtonMapper(final ButtonConverter this$0) {
        }
        
        @Override
        public ButtonWidget map(final eoz source) {
            if (source instanceof final epw lockIconButton) {
                final int size = source.k();
                final ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
                final boolean locked = !lockIconButton.a();
                final boolean enabled = lockIconButton.aD_();
                final Icon icon = Icon.sprite(widgetsTexture, locked ? 20 : 0, 146 + (enabled ? 0 : (size * 2)), size, size, 256, 256);
                if (enabled) {
                    icon.setHoverOffset(0, size);
                }
                final ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
                ((AbstractWidget<Widget>)button).addId("lock-icon-button");
                return button;
            }
            return null;
        }
    }
}
