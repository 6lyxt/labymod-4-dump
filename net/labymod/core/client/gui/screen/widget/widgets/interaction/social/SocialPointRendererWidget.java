// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.interaction.social;

import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.labynet.models.SocialMediaEntry;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractCirclePointWidget;

@AutoWidget
public class SocialPointRendererWidget extends AbstractCirclePointWidget<SocialPointWidget>
{
    private static final ModifyReason UPDATE_LAYOUT;
    
    public SocialPointRendererWidget(final InteractionAnimationController animationController, final Player player) {
        super(animationController);
        final LabyNetController controller = Laby.references().labyNetController();
        controller.loadSocials(player.getUniqueId(), socials -> {
            if (!(!socials.isPresent())) {
                final List entries = (List)socials.get();
                if (!entries.isEmpty()) {
                    ThreadSafe.executeOnRenderThread(() -> this.initializeEntries(entries));
                }
            }
        });
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.updateLayout();
        super.renderWidget(context);
    }
    
    private void initializeEntries(final List<SocialMediaEntry> entries) {
        final List<SocialPointWidget> children = new ArrayList<SocialPointWidget>();
        for (final SocialMediaEntry entry : entries) {
            final SocialPointWidget iconWidget = new SocialPointWidget(entry);
            iconWidget.addId("social-icon");
            children.add(iconWidget);
        }
        if (this.initialized) {
            this.addChildrenInitialized(children, true);
        }
        else {
            for (final SocialPointWidget child : children) {
                this.addChild(child, false);
            }
            this.sortChildren();
        }
    }
    
    private void updateLayout() {
        final int amount = this.children.size();
        if (amount == 0) {
            return;
        }
        final float transitionProgress = this.animationController.getSocialTransitionProgress();
        final float linearFilterStrength = 6.0f;
        final float linearFilter = 1.0f + linearFilterStrength - transitionProgress * linearFilterStrength;
        final Bounds rendererBounds = this.bounds();
        final float centerX = rendererBounds.getCenterX();
        final float centerY = rendererBounds.getCenterY();
        final float radius = rendererBounds.getHeight() / 2.0f;
        final float range = (float)(amount * 3.141592653589793 / 10.0) / linearFilter;
        float rotation = (float)(-range / 2.0f + 1.5707963267948966) + ((amount == 1) ? (range / 2.0f) : 0.0f);
        final float step = (amount == 1) ? 0.0f : (range / (amount - 1));
        int index = 0;
        for (final SocialPointWidget widget : this.children) {
            final Bounds bounds = widget.bounds();
            final float cos = MathHelper.cos(rotation);
            final float sin = MathHelper.sin(rotation);
            final float posX = centerX + radius * cos * linearFilter;
            final float posY = centerY + radius * sin;
            final float animationProgress = this.getBounceFadeInProgress(widget, index);
            final float scale = animationProgress + widget.getHoverStrength() / 2.0f;
            bounds.setPosition(posX - bounds.getWidth() / 2.0f, posY - bounds.getHeight() / 2.0f + 10.0f, SocialPointRendererWidget.UPDATE_LAYOUT);
            widget.setCenteredScale(scale);
            rotation += step;
            ++index;
        }
    }
    
    private float getBounceFadeInProgress(final SocialPointWidget widget, final int index) {
        final int amount = this.children.size();
        long timePassed = TimeUtil.getMillis() - widget.getLastInitialTime();
        timePassed -= (amount - index) * 30L;
        return (float)CubicBezier.BOUNCE.curve(MathHelper.clamp(timePassed / 300.0f, 0.0f, 1.0f));
    }
    
    static {
        UPDATE_LAYOUT = ModifyReason.of("socialBarLayout");
    }
}
