// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.spray;

import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.core.main.user.shop.spray.model.SprayAssetProvider;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.user.shop.spray.model.Spray;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

public class SpraySegmentWidget extends WheelWidget.Segment
{
    private final Spray spray;
    private final boolean canSpray;
    
    public SpraySegmentWidget(final Spray pack, final boolean canSpray) {
        this.spray = pack;
        this.canSpray = canSpray;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean hasSpray = this.spray != null;
        if (!hasSpray) {
            return;
        }
        final boolean canNotSpray = !this.canSpray;
        final IconWidget iconWidget = new IconWidget(this.createSprayIcon(canNotSpray));
        iconWidget.addId("icon");
        if (canNotSpray) {
            iconWidget.addId("red");
        }
        super.addChild(iconWidget);
        final ComponentWidget packNameWidget = ComponentWidget.component(Component.text(this.spray.getName()));
        packNameWidget.addId("name");
        super.addChild(packNameWidget);
    }
    
    private Icon createSprayIcon(final boolean canNotSpray) {
        if (canNotSpray) {
            return Textures.SpriteCommon.WHITE_X;
        }
        final CompletableResourceLocation sprayTexture = LabyMod.references().sprayAssetProvider().getTexture(this.spray, SprayAssetProvider.TextureType.DIFFUSE);
        return Icon.completable(sprayTexture);
    }
    
    public Spray getSpray() {
        return this.spray;
    }
}
