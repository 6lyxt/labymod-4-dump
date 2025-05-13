// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.Laby;
import java.util.List;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.util.function.Mapper;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class ButtonConverter extends AbstractMinecraftWidgetConverter<dld, ButtonWidget>
{
    private static final ComponentMapper MAPPER;
    final Map<ResourceLocation, String> mappedIcons;
    
    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.mappedIcons = new HashMap<ResourceLocation, String>();
        this.prepareMappedIcons();
        this.registerMapper(new ImageButtonMapper());
        this.registerMapper(new LockIconButtonMapper());
    }
    
    @Override
    public ButtonWidget createDefault(final dld source) {
        final ButtonWidget destination = ButtonWidget.component(this.mapComponent(source.i()), null, Pressable.NOOP);
        destination.setFocused(source.j());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final dld source, final ButtonWidget destination) {
        destination.setVisible(source.p);
        destination.setEnabled(source.o);
        destination.setFocused(source.j());
        destination.updateComponent(ButtonConverter.MAPPER.fromMinecraftComponent(source.i()));
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final dld source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.i());
    }
    
    @Override
    public List<String> getWidgetIds(final dld source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.i());
    }
    
    private void prepareMappedIcons() {
        final ResourceLocationFactory factory = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
        this.mappedIcons.put(factory.createMinecraft("textures/gui/recipe_button.png"), "icon-button-recipe-book");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/accessibility.png"), "icon-button-accessibility");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/widgets.png"), "icon-button-language");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/social_interactions.png"), "icon-social-interactions");
    }
    
    static {
        MAPPER = Laby.references().componentMapper();
    }
    
    class ImageButtonMapper implements Mapper<dld, ButtonWidget>
    {
        @Override
        public ButtonWidget map(final dld source) {
            if (source instanceof final dlr imageButton) {
                final ImageButtonAccessor accessor = (ImageButtonAccessor)imageButton;
                final ResourceLocation location = accessor.getResourceLocation();
                final Icon icon = Icon.sprite(location, accessor.getXTexStart(), accessor.getYTexStart(), source.h(), source.e(), accessor.getTextureWidth(), accessor.getTextureHeight());
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
    
    class LockIconButtonMapper implements Mapper<dld, ButtonWidget>
    {
        LockIconButtonMapper(final ButtonConverter this$0) {
        }
        
        @Override
        public ButtonWidget map(final dld source) {
            if (source instanceof final dlt lockIconButton) {
                final int size = source.h();
                final ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
                final boolean locked = !lockIconButton.a();
                final boolean enabled = true;
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
