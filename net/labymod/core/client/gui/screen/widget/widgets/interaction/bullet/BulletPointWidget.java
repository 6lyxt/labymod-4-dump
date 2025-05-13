// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractPointWidget;

@AutoWidget
public class BulletPointWidget extends AbstractPointWidget
{
    private static final ModifyReason UPDATE_ROTATION;
    private final BulletPoint bulletPoint;
    private final Side side;
    private IconWidget bulletIconWidget;
    private HorizontalListWidget entryContainer;
    
    public BulletPointWidget(final BulletPoint bulletPoint, final Side side) {
        this.bulletPoint = bulletPoint;
        this.side = side;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.bulletIconWidget = new IconWidget(Textures.SpriteCommon.BULLET_POINT)).addId("bullet-icon");
        ((AbstractWidget<IconWidget>)this).addChild(this.bulletIconWidget);
        ((AbstractWidget<Widget>)(this.entryContainer = new HorizontalListWidget())).addId("entry-container");
        final ComponentWidget titleWidget = ComponentWidget.component(this.bulletPoint.getTitle());
        titleWidget.addId("title");
        this.entryContainer.addEntry(titleWidget);
        final Icon icon = this.bulletPoint.getIcon();
        if (icon != null) {
            final IconWidget iconWidget = new IconWidget(icon);
            iconWidget.addId("icon");
            this.entryContainer.addEntry(iconWidget);
        }
        ((AbstractWidget<HorizontalListWidget>)this).addChild(this.entryContainer);
    }
    
    @Override
    public void render(final ScreenContext context) {
        final int brightness = 50 + (int)(this.getHoverStrength() * 200.0f);
        this.bulletIconWidget.color().set(ColorFormat.ARGB32.pack(brightness, brightness, brightness));
        super.render(context);
    }
    
    public void updateRotation(final float rotation) {
        final Bounds originBounds = this.bounds();
        float originX = originBounds.getCenterX();
        float originY = originBounds.getCenterY();
        final double radius = originBounds.getWidth() / 3.0f;
        originX += (float)(Math.cos(rotation) * radius);
        originY += (float)(Math.sin(rotation) * radius);
        final boolean isLeft = this.side == Side.LEFT;
        final Bounds bounds = this.entryContainer.bounds();
        bounds.setPosition(originX - (isLeft ? bounds.getWidth() : 0.0f), originY - bounds.getHeight() / 2.0f, BulletPointWidget.UPDATE_ROTATION);
    }
    
    public Side getSide() {
        return this.side;
    }
    
    public BulletPoint bulletPoint() {
        return this.bulletPoint;
    }
    
    static {
        UPDATE_ROTATION = ModifyReason.of("bulletPointRotation");
    }
    
    public enum Side
    {
        LEFT, 
        RIGHT;
    }
}
