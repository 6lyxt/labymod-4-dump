// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Locale;
import net.labymod.api.Textures;
import java.awt.Color;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class RatingWidget extends SimpleWidget
{
    private static final ModifyReason RATING_RELATIVE_WIDTH;
    private final Icon fullRatingIcon;
    private final ResourceLocation flintresourceLocation;
    private LssProperty<Boolean> displayExactRating;
    private LssProperty<Boolean> displayEmptyRating;
    private LssProperty<Float> spaceBetweenRatings;
    private LssProperty<Integer> fillColor;
    private LssProperty<Integer> emptyColor;
    private LssProperty<Integer> hoverColor;
    private final int count;
    private final double rating;
    private final int flooredRating;
    private final Component component;
    private Icon decimalIcon;
    private Icon emptyDecimalIcon;
    
    public RatingWidget(final double rating, final int count) {
        this.displayExactRating = new LssProperty<Boolean>(false);
        this.displayEmptyRating = new LssProperty<Boolean>(false);
        this.spaceBetweenRatings = new LssProperty<Float>(1.0f);
        this.fillColor = new LssProperty<Integer>(Color.ORANGE.getRGB());
        this.emptyColor = new LssProperty<Integer>(Color.GRAY.getRGB());
        this.hoverColor = new LssProperty<Integer>(Color.ORANGE.getRGB());
        this.rating = rating;
        this.count = count;
        this.flooredRating = (int)Math.floor(rating);
        this.flintresourceLocation = Textures.SpriteFlint.TEXTURE;
        this.fullRatingIcon = Icon.sprite16(this.flintresourceLocation, 0, 0);
        String beautifiedRating;
        if (rating % 1.0 == 0.0) {
            beautifiedRating = String.valueOf((int)rating);
        }
        else {
            beautifiedRating = String.format(Locale.ROOT, "%.1f", rating);
        }
        if (count == -1) {
            this.component = Component.translatable("labymod.addons.store.rating.hover", Component.text(beautifiedRating));
        }
        else if (count == 0) {
            this.component = Component.translatable("labymod.addons.store.rating.hoverNoRating", new Component[0]);
        }
        else if (count == 1) {
            this.component = Component.translatable("labymod.addons.store.rating.hoverOneRating", Component.text(beautifiedRating));
        }
        else {
            this.component = Component.translatable("labymod.addons.store.rating.hoverWithCount", Component.text(beautifiedRating), Component.text(this.count));
        }
    }
    
    public RatingWidget(final double rating) {
        this(rating, -1);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        int width;
        if (this.rating % 1.0 != 0.0) {
            if (this.displayExactRating.get()) {
                width = (int)(16.0 * (this.rating - this.flooredRating));
            }
            else {
                width = 8;
            }
        }
        else {
            width = 0;
        }
        if (width != 0) {
            this.decimalIcon = Icon.sprite16(this.flintresourceLocation, 1, 0);
        }
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.setHoverComponent(this.component.color(TextColor.color(this.hoverColor().get())));
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final Bounds bounds = this.bounds();
        float x = bounds.getX();
        final float y = bounds.getY();
        final float ratingSize = bounds.getHeight();
        final ResourceRenderContext renderContext = this.labyAPI.renderPipeline().renderContexts().resourceRenderContext();
        renderContext.begin(context.stack(), this.flintresourceLocation);
        final int fillColor = this.fillColor.get();
        final int emptyColor = this.emptyColor.get();
        final boolean displayEmptyRating = this.displayEmptyRating.get();
        final float spaceBetweenRatings = this.spaceBetweenRatings.get();
        for (int i = 0; i < 5; ++i) {
            if (i < this.flooredRating) {
                this.fullRatingIcon.render(renderContext, x, y, ratingSize, false, fillColor);
            }
            else if (i == this.flooredRating && this.decimalIcon != null) {
                this.decimalIcon.render(renderContext, x, y, ratingSize, false, fillColor);
                if (displayEmptyRating) {
                    if (this.emptyDecimalIcon == null) {
                        this.emptyDecimalIcon = Icon.sprite16(this.flintresourceLocation, 2, 0);
                    }
                    this.emptyDecimalIcon.render(renderContext, x, y, ratingSize, false, emptyColor);
                }
            }
            else if (displayEmptyRating) {
                this.fullRatingIcon.render(renderContext, x, y, ratingSize, false, emptyColor);
            }
            x += ratingSize + spaceBetweenRatings;
        }
        renderContext.uploadToBuffer();
    }
    
    @Override
    public void updateBounds() {
        final Bounds bounds = this.bounds();
        bounds.setOuterWidth(bounds.getHeight() * 5.0f + this.spaceBetweenRatings.get() * 4.0f, RatingWidget.RATING_RELATIVE_WIDTH);
        super.updateBounds();
    }
    
    public LssProperty<Boolean> displayExactRating() {
        return this.displayExactRating;
    }
    
    public LssProperty<Boolean> displayEmptyRating() {
        return this.displayEmptyRating;
    }
    
    public LssProperty<Float> spaceBetweenRatings() {
        return this.spaceBetweenRatings;
    }
    
    public LssProperty<Integer> fillColor() {
        return this.fillColor;
    }
    
    public LssProperty<Integer> emptyColor() {
        return this.emptyColor;
    }
    
    public LssProperty<Integer> hoverColor() {
        return this.hoverColor;
    }
    
    static {
        RATING_RELATIVE_WIDTH = ModifyReason.of("ratingRelativeWidth");
    }
}
