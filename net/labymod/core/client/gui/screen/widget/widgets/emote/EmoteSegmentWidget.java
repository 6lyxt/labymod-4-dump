// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.emote;

import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.client.gui.screen.widget.widgets.model.EmoteModelWidget;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

@AutoWidget
public class EmoteSegmentWidget extends WheelWidget.Segment
{
    private static final float Z_DISTANCE = 100.0f;
    private final EmoteItem emote;
    private final AnimationController animationController;
    private final boolean blocked;
    private final boolean cosmetics;
    private final Style emoteTextStyle;
    private final Model model;
    private EmoteModelWidget modelWidget;
    
    public EmoteSegmentWidget(final EmoteItem emote, final Model playerModel, final boolean blocked) {
        this(emote, playerModel, blocked, false, ((StyleableBuilder<T, Style.Builder>)Style.builder()).color(NamedTextColor.WHITE).build());
    }
    
    public EmoteSegmentWidget(final EmoteItem emote, final Model model, final boolean blocked, final boolean cosmetics, final Style emoteTextStyle) {
        this.emote = emote;
        this.animationController = new DefaultAnimationController();
        this.blocked = blocked;
        this.cosmetics = cosmetics;
        this.emoteTextStyle = emoteTextStyle;
        this.model = model;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.blocked) {
            final Widget widget = new IconWidget(Textures.SpriteCommon.WHITE_X);
            super.addChild(widget);
            this.modelWidget = null;
        }
        else {
            (this.modelWidget = new EmoteModelWidget(this.model, this.animationController, 16.0f, 32.0f, null, this.cosmetics)).setPlayerUniqueId(Laby.labyAPI().getUniqueId());
            this.modelWidget.addId("emote-segment-model");
            super.addChild(this.modelWidget);
        }
        final Widget emoteNameWidget = ComponentWidget.component(Component.text(this.getEmoteName(), this.emoteTextStyle));
        emoteNameWidget.addId("emote-segment-name");
        super.addChild(emoteNameWidget);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        if (this.modelWidget != null) {
            final Bounds bounds = this.bounds();
            final float xDistance = bounds.getCenterX(BoundsType.INNER) - mouse.getX();
            final float yDistance = bounds.getY(BoundsType.INNER) + bounds.getHeight(BoundsType.INNER) * 0.0625f * 2.0f - mouse.getY();
            final double distance = Math.sqrt(MathHelper.square(xDistance) + MathHelper.square(yDistance) + MathHelper.square(100.0f));
            final float yaw = (float)Math.atan2(xDistance, 100.0);
            final float pitch = (float)(-Math.asin(yDistance / distance));
            this.modelWidget.rotation().set(0.0f, yaw, 0.0f);
            this.modelWidget.playerModel().getHead().getAnimationTransformation().setRotation(pitch, 0.0f, 0.0f);
        }
        super.renderWidget(context);
        if (this.modelWidget != null && this.emote != null) {
            if (super.isSegmentSelected() && !this.animationController.isPlaying()) {
                this.modelWidget.playEmote(this.emote);
            }
            else if (!super.isSegmentSelected() && this.animationController.isPlaying()) {
                this.modelWidget.stopEmote();
            }
        }
    }
    
    public EmoteItem getEmote() {
        return this.emote;
    }
    
    public void updateModel(final Model model) {
        if (this.modelWidget != null) {
            this.modelWidget.setModel(model);
        }
    }
    
    private String getEmoteName() {
        if (this.emote == null) {
            return "?";
        }
        return this.emote.getName();
    }
}
