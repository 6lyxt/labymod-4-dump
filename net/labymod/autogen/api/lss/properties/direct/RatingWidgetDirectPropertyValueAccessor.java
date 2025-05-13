// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.RatingWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetHoverColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetEmptyColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetFillColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetSpaceBetweenRatingsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetDisplayEmptyRatingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetDisplayExactRatingPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class RatingWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> displayExactRating;
    protected PropertyValueAccessor<?, ?, ?> displayEmptyRating;
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenRatings;
    protected PropertyValueAccessor<?, ?, ?> fillColor;
    protected PropertyValueAccessor<?, ?, ?> emptyColor;
    protected PropertyValueAccessor<?, ?, ?> hoverColor;
    LssPropertyResetter RatingWidgetResetter;
    
    public RatingWidgetDirectPropertyValueAccessor() {
        this.displayExactRating = new RatingWidgetDisplayExactRatingPropertyValueAccessor();
        this.displayEmptyRating = new RatingWidgetDisplayEmptyRatingPropertyValueAccessor();
        this.spaceBetweenRatings = new RatingWidgetSpaceBetweenRatingsPropertyValueAccessor();
        this.fillColor = new RatingWidgetFillColorPropertyValueAccessor();
        this.emptyColor = new RatingWidgetEmptyColorPropertyValueAccessor();
        this.hoverColor = new RatingWidgetHoverColorPropertyValueAccessor();
        this.RatingWidgetResetter = new RatingWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "displayExactRating": {
                return this.displayExactRating;
            }
            case "displayEmptyRating": {
                return this.displayEmptyRating;
            }
            case "spaceBetweenRatings": {
                return this.spaceBetweenRatings;
            }
            case "fillColor": {
                return this.fillColor;
            }
            case "emptyColor": {
                return this.emptyColor;
            }
            case "hoverColor": {
                return this.hoverColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "displayExactRating": {
                return true;
            }
            case "displayEmptyRating": {
                return true;
            }
            case "spaceBetweenRatings": {
                return true;
            }
            case "fillColor": {
                return true;
            }
            case "emptyColor": {
                return true;
            }
            case "hoverColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.RatingWidgetResetter;
    }
}
