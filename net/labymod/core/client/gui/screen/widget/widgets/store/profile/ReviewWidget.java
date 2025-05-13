// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store.profile;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.flint.marketplace.FlintModification;
import java.text.SimpleDateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class ReviewWidget extends VerticalListWidget<Widget>
{
    private static final SimpleDateFormat DATE_FORMAT;
    private final FlintModification.Review review;
    
    public ReviewWidget(final FlintModification.Review review) {
        this.review = review;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final HorizontalListWidget headerWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)headerWidget).addId("review-header");
        final IconWidget icon = new IconWidget(this.review.user().icon());
        icon.addId("review-user-head");
        headerWidget.addEntry(icon);
        final ComponentWidget name = ComponentWidget.text(this.review.user().getUserName());
        name.addId("review-user-name");
        headerWidget.addEntry(name);
        final RatingWidget rating = new RatingWidget(this.review.getRating());
        rating.addId("review-rating");
        headerWidget.addEntry(rating);
        ((AbstractWidget<HorizontalListWidget>)this).addChild(headerWidget);
        final String commentText = this.review.getComment();
        if (commentText.length() != 0) {
            final ComponentWidget comment = ComponentWidget.text(commentText);
            comment.addId("review-comment");
            ((AbstractWidget<ComponentWidget>)this).addChild(comment);
        }
        final String format = ReviewWidget.DATE_FORMAT.format(this.review.getAddedAt());
        final ComponentWidget date = ComponentWidget.text(format);
        date.addId("review-date");
        ((AbstractWidget<ComponentWidget>)this).addChild(date);
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
    }
}
