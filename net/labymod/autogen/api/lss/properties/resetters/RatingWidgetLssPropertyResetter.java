// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class RatingWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final RatingWidget ratingWidget) {
            if (ratingWidget.displayExactRating() != null) {
                ratingWidget.displayExactRating().reset();
            }
            if (((RatingWidget)widget).displayEmptyRating() != null) {
                ((RatingWidget)widget).displayEmptyRating().reset();
            }
            if (((RatingWidget)widget).spaceBetweenRatings() != null) {
                ((RatingWidget)widget).spaceBetweenRatings().reset();
            }
            if (((RatingWidget)widget).fillColor() != null) {
                ((RatingWidget)widget).fillColor().reset();
            }
            if (((RatingWidget)widget).emptyColor() != null) {
                ((RatingWidget)widget).emptyColor().reset();
            }
            if (((RatingWidget)widget).hoverColor() != null) {
                ((RatingWidget)widget).hoverColor().reset();
            }
        }
        super.reset(widget);
    }
}
