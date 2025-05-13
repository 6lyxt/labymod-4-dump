// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.interaction.social;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractPointWidget;

@AutoWidget
public class SocialPointWidget extends AbstractPointWidget
{
    private final SocialMediaEntry entry;
    private final Icon icon;
    
    public SocialPointWidget(final SocialMediaEntry entry) {
        this.entry = entry;
        this.icon = Textures.SpriteBrands.byName(entry.getService());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<IconWidget>)this).addChild(new IconWidget(this.icon));
    }
    
    public SocialMediaEntry entry() {
        return this.entry;
    }
}
