// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.header.type;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget;

@AutoWidget
public class LegacyMinecraftLogoWidget extends MinecraftLogoWidget
{
    private final MinecraftTextures textures;
    
    public LegacyMinecraftLogoWidget() {
        super("legacy");
        this.textures = this.labyAPI.minecraft().textures();
    }
    
    @Override
    protected Widget createMinecraftWidget() {
        final DivWidget legacyContainer = new DivWidget();
        final IconWidget logoLeftWidget = new IconWidget(Icon.sprite(this.textures.minecraftLogoTexture(), 0, 0, 155, 44, 256, 256));
        logoLeftWidget.addId("mine");
        ((AbstractWidget<IconWidget>)legacyContainer).addChild(logoLeftWidget);
        final IconWidget logoRightWidget = new IconWidget(Icon.sprite(this.textures.minecraftLogoTexture(), 0, 45, 155, 44, 256, 256));
        logoRightWidget.addId("craft");
        ((AbstractWidget<IconWidget>)legacyContainer).addChild(logoRightWidget);
        return legacyContainer;
    }
    
    @Override
    protected Widget createEditionWidget() {
        final ResourceLocation resource = this.textures.minecraftEditionTexture();
        if (resource == null) {
            return null;
        }
        return new IconWidget(Icon.sprite(resource, 0, 0, 98, 14, 128, 16));
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return 310.0f;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return 51.0f;
    }
}
