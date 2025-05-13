// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.gui.screen.widget.converter;

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

public class ButtonConverter extends AbstractMinecraftWidgetConverter<ent, ButtonWidget>
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
    public ButtonWidget createDefault(final ent source) {
        final ButtonWidget destination = ButtonWidget.component(this.mapComponent(source.k()), null, Pressable.NOOP);
        destination.setFocused(source.aD_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final ent source, final ButtonWidget destination) {
        destination.setVisible(source.w);
        destination.setEnabled(source.v);
        destination.setFocused(source.aD_());
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
    public String getWidgetId(final ent source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.k());
    }
    
    @Override
    public List<String> getWidgetIds(final ent source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.k());
    }
    
    class ImageButtonMapper implements Mapper<ent, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final ent source) {
            if (source instanceof final eom imageButton) {
                final ImageButtonAccessor accessor = (ImageButtonAccessor)imageButton;
                final ResourceLocation location = accessor.getResourceLocation();
                final Icon icon = Icon.sprite(location, accessor.getXTexStart(), accessor.getYTexStart(), source.j(), source.h(), accessor.getTextureWidth(), accessor.getTextureHeight());
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
    
    class LockIconButtonMapper implements Mapper<ent, ButtonWidget>
    {
        LockIconButtonMapper(final ButtonConverter this$0) {
        }
        
        @Override
        public ButtonWidget map(final ent source) {
            if (source instanceof final eop lockIconButton) {
                final int size = source.j();
                final ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
                final boolean locked = !lockIconButton.a();
                final boolean enabled = lockIconButton.aG_();
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
