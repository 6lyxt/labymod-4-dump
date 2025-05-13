// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.WheelWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetInnerBorderColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetInnerBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetSegmentBorderColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetSegmentSelectedColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetSegmentHighlightColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetSegmentBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetSegmentDistanceDegreesPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetInnerRadiusPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.WheelWidgetSelectablePropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class WheelWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> selectable;
    protected PropertyValueAccessor<?, ?, ?> innerRadius;
    protected PropertyValueAccessor<?, ?, ?> segmentDistanceDegrees;
    protected PropertyValueAccessor<?, ?, ?> segmentBackgroundColor;
    protected PropertyValueAccessor<?, ?, ?> segmentHighlightColor;
    protected PropertyValueAccessor<?, ?, ?> segmentSelectedColor;
    protected PropertyValueAccessor<?, ?, ?> segmentBorderColor;
    protected PropertyValueAccessor<?, ?, ?> innerBackgroundColor;
    protected PropertyValueAccessor<?, ?, ?> innerBorderColor;
    LssPropertyResetter WheelWidgetResetter;
    
    public WheelWidgetDirectPropertyValueAccessor() {
        this.selectable = new WheelWidgetSelectablePropertyValueAccessor();
        this.innerRadius = new WheelWidgetInnerRadiusPropertyValueAccessor();
        this.segmentDistanceDegrees = new WheelWidgetSegmentDistanceDegreesPropertyValueAccessor();
        this.segmentBackgroundColor = new WheelWidgetSegmentBackgroundColorPropertyValueAccessor();
        this.segmentHighlightColor = new WheelWidgetSegmentHighlightColorPropertyValueAccessor();
        this.segmentSelectedColor = new WheelWidgetSegmentSelectedColorPropertyValueAccessor();
        this.segmentBorderColor = new WheelWidgetSegmentBorderColorPropertyValueAccessor();
        this.innerBackgroundColor = new WheelWidgetInnerBackgroundColorPropertyValueAccessor();
        this.innerBorderColor = new WheelWidgetInnerBorderColorPropertyValueAccessor();
        this.WheelWidgetResetter = new WheelWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "selectable": {
                return this.selectable;
            }
            case "innerRadius": {
                return this.innerRadius;
            }
            case "segmentDistanceDegrees": {
                return this.segmentDistanceDegrees;
            }
            case "segmentBackgroundColor": {
                return this.segmentBackgroundColor;
            }
            case "segmentHighlightColor": {
                return this.segmentHighlightColor;
            }
            case "segmentSelectedColor": {
                return this.segmentSelectedColor;
            }
            case "segmentBorderColor": {
                return this.segmentBorderColor;
            }
            case "innerBackgroundColor": {
                return this.innerBackgroundColor;
            }
            case "innerBorderColor": {
                return this.innerBorderColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "selectable": {
                return true;
            }
            case "innerRadius": {
                return true;
            }
            case "segmentDistanceDegrees": {
                return true;
            }
            case "segmentBackgroundColor": {
                return true;
            }
            case "segmentHighlightColor": {
                return true;
            }
            case "segmentSelectedColor": {
                return true;
            }
            case "segmentBorderColor": {
                return true;
            }
            case "innerBackgroundColor": {
                return true;
            }
            case "innerBorderColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.WheelWidgetResetter;
    }
}
