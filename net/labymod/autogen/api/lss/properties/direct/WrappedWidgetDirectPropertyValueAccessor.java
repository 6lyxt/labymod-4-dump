// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.WrappedWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetBoxSizingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetRendererPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetAlignmentYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetAlignmentXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedWidgetPriorityLayerPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class WrappedWidgetDirectPropertyValueAccessor extends StyledWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> priorityLayer;
    protected PropertyValueAccessor<?, ?, ?> alignmentX;
    protected PropertyValueAccessor<?, ?, ?> alignmentY;
    protected PropertyValueAccessor<?, ?, ?> renderer;
    protected PropertyValueAccessor<?, ?, ?> boxSizing;
    LssPropertyResetter WrappedWidgetResetter;
    
    public WrappedWidgetDirectPropertyValueAccessor() {
        this.priorityLayer = new WrappedWidgetPriorityLayerPropertyValueAccessor();
        this.alignmentX = new WrappedWidgetAlignmentXPropertyValueAccessor();
        this.alignmentY = new WrappedWidgetAlignmentYPropertyValueAccessor();
        this.renderer = new WrappedWidgetRendererPropertyValueAccessor();
        this.boxSizing = new WrappedWidgetBoxSizingPropertyValueAccessor();
        this.WrappedWidgetResetter = new WrappedWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "priorityLayer": {
                return this.priorityLayer;
            }
            case "alignmentX": {
                return this.alignmentX;
            }
            case "alignmentY": {
                return this.alignmentY;
            }
            case "renderer": {
                return this.renderer;
            }
            case "boxSizing": {
                return this.boxSizing;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "priorityLayer": {
                return true;
            }
            case "alignmentX": {
                return true;
            }
            case "alignmentY": {
                return true;
            }
            case "renderer": {
                return true;
            }
            case "boxSizing": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.WrappedWidgetResetter;
    }
}
