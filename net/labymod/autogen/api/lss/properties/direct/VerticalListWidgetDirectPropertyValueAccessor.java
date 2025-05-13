// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.VerticalListWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetListOrderPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetListAlignmentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetRenderOutOfBoundsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetSelectablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetSqueezeHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VerticalListWidgetOverwriteWidthPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class VerticalListWidgetDirectPropertyValueAccessor extends SessionedListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> overwriteWidth;
    protected PropertyValueAccessor<?, ?, ?> squeezeHeight;
    protected PropertyValueAccessor<?, ?, ?> selectable;
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> renderOutOfBounds;
    protected PropertyValueAccessor<?, ?, ?> listAlignment;
    protected PropertyValueAccessor<?, ?, ?> listOrder;
    LssPropertyResetter VerticalListWidgetResetter;
    
    public VerticalListWidgetDirectPropertyValueAccessor() {
        this.overwriteWidth = new VerticalListWidgetOverwriteWidthPropertyValueAccessor();
        this.squeezeHeight = new VerticalListWidgetSqueezeHeightPropertyValueAccessor();
        this.selectable = new VerticalListWidgetSelectablePropertyValueAccessor();
        this.spaceBetweenEntries = new VerticalListWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.renderOutOfBounds = new VerticalListWidgetRenderOutOfBoundsPropertyValueAccessor();
        this.listAlignment = new VerticalListWidgetListAlignmentPropertyValueAccessor();
        this.listOrder = new VerticalListWidgetListOrderPropertyValueAccessor();
        this.VerticalListWidgetResetter = new VerticalListWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "overwriteWidth": {
                return this.overwriteWidth;
            }
            case "squeezeHeight": {
                return this.squeezeHeight;
            }
            case "selectable": {
                return this.selectable;
            }
            case "spaceBetweenEntries": {
                return this.spaceBetweenEntries;
            }
            case "renderOutOfBounds": {
                return this.renderOutOfBounds;
            }
            case "listAlignment": {
                return this.listAlignment;
            }
            case "listOrder": {
                return this.listOrder;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "overwriteWidth": {
                return true;
            }
            case "squeezeHeight": {
                return true;
            }
            case "selectable": {
                return true;
            }
            case "spaceBetweenEntries": {
                return true;
            }
            case "renderOutOfBounds": {
                return true;
            }
            case "listAlignment": {
                return true;
            }
            case "listOrder": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.VerticalListWidgetResetter;
    }
}
