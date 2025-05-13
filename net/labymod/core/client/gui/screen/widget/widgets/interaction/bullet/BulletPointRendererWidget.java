// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.core.client.entity.player.interaction.autotext.AutoTextBulletPoint;
import net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import java.util.Collection;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractCirclePointWidget;

@AutoWidget
public class BulletPointRendererWidget extends AbstractCirclePointWidget<BulletPointWidget>
{
    private static final ModifyReason UPDATE_LAYOUT;
    private final Collection<BulletPoint> bulletPoints;
    
    public BulletPointRendererWidget(final InteractionAnimationController animationController, final Collection<BulletPoint> bulletPoints) {
        super(animationController);
        this.bulletPoints = bulletPoints;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final BulletPoint bulletPoint : this.bulletPoints) {
            final String namespace = this.labyAPI.getNamespace(bulletPoint);
            final BulletPointWidget.Side side = (namespace.equals("labymod") && !(bulletPoint instanceof ServerBulletPoint) && !(bulletPoint instanceof AutoTextBulletPoint)) ? BulletPointWidget.Side.RIGHT : BulletPointWidget.Side.LEFT;
            final BulletPointWidget bulletPointWidget = new BulletPointWidget(bulletPoint, side);
            bulletPointWidget.addId("bullet-point");
            this.addChild(bulletPointWidget);
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.updateLayoutOfSide(BulletPointWidget.Side.LEFT);
        this.updateLayoutOfSide(BulletPointWidget.Side.RIGHT);
        super.renderWidget(context);
    }
    
    private void updateLayoutOfSide(final BulletPointWidget.Side side) {
        final int amount = this.getBulletPointsAmountOnSide(side);
        if (amount == 0) {
            return;
        }
        final float radius = this.getRadius();
        final float range = 2.0943952f;
        final float step = range / amount;
        float rotation = (step - range) / 2.0f + this.animationController.getBulletRotationOffset(side);
        if (side == BulletPointWidget.Side.LEFT) {
            rotation += (float)3.141592653589793;
        }
        final float openProgress = this.animationController.getOpenProgress();
        rotation += (float)((1.0f - openProgress) / 2.0f * 3.141592653589793);
        final Bounds rendererBounds = this.bounds();
        final float centerX = rendererBounds.getCenterX();
        final float centerY = rendererBounds.getCenterY();
        for (final BulletPointWidget widget : this.children) {
            if (widget.getSide() != side) {
                continue;
            }
            final Bounds bounds = widget.bounds();
            final float cos = MathHelper.cos(rotation);
            final float sin = MathHelper.sin(rotation);
            final float posX = centerX + radius * cos;
            final float posY = centerY + radius * sin;
            final float scale = openProgress + widget.getHoverStrength() / 8.0f;
            bounds.setPosition(posX - bounds.getWidth() / 2.0f, posY - bounds.getHeight() / 2.0f, BulletPointRendererWidget.UPDATE_LAYOUT);
            widget.setCenteredScale(scale);
            widget.opacity().set(openProgress);
            widget.updateRotation(rotation);
            rotation += step;
        }
    }
    
    private int getBulletPointsAmountOnSide(final BulletPointWidget.Side side) {
        int amount = 0;
        for (final BulletPointWidget widget : this.children) {
            if (widget.getSide() != side) {
                continue;
            }
            ++amount;
        }
        return amount;
    }
    
    static {
        UPDATE_LAYOUT = ModifyReason.of("bulletPointLayout");
    }
}
