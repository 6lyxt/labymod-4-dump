// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class LastUsedSkinsContainerWidget extends VerticalListWidget<Widget>
{
    private final SkinActivity skinActivity;
    private TextureResult lastUsedSkins;
    
    public LastUsedSkinsContainerWidget(final SkinActivity skinActivity) {
        this.skinActivity = skinActivity;
        this.lazy = true;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (!this.hasValidTextures()) {
            return;
        }
        this.addChild(ComponentWidget.text("Last used skins").addId("last-used-skins-title"));
        final WrappedListWidget<SkinPreviewWidget> skinsContainer = new WrappedListWidget<SkinPreviewWidget>();
        skinsContainer.addId("skins-container");
        final List<Skin> textures = this.lastUsedSkins.getTextures();
        for (final Skin texture : textures) {
            skinsContainer.addChild(new SkinPreviewWidget(this.skinActivity, texture));
        }
        ((AbstractWidget<WrappedListWidget<SkinPreviewWidget>>)this).addChild(skinsContainer);
    }
    
    public void setTextureResult(final TextureResult textureResult) {
        this.lastUsedSkins = textureResult;
        this.visible().set(this.hasValidTextures());
        this.reInitialize();
    }
    
    public boolean hasValidTextures() {
        if (this.lastUsedSkins == null) {
            return false;
        }
        final List<Skin> textures = this.lastUsedSkins.getTextures();
        return textures != null && !textures.isEmpty();
    }
}
