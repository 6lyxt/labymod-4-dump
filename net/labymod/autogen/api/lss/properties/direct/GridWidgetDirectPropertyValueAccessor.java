// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.GridWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetAutoPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetIteratePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetMaxRowHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetMaxColumnWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetRowsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetColumnsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.GridWidgetOutlineThicknessPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class GridWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> outlineThickness;
    protected PropertyValueAccessor<?, ?, ?> columns;
    protected PropertyValueAccessor<?, ?, ?> rows;
    protected PropertyValueAccessor<?, ?, ?> maxColumnWidth;
    protected PropertyValueAccessor<?, ?, ?> maxRowHeight;
    protected PropertyValueAccessor<?, ?, ?> iterate;
    protected PropertyValueAccessor<?, ?, ?> auto;
    LssPropertyResetter GridWidgetResetter;
    
    public GridWidgetDirectPropertyValueAccessor() {
        this.outlineThickness = new GridWidgetOutlineThicknessPropertyValueAccessor();
        this.columns = new GridWidgetColumnsPropertyValueAccessor();
        this.rows = new GridWidgetRowsPropertyValueAccessor();
        this.maxColumnWidth = new GridWidgetMaxColumnWidthPropertyValueAccessor();
        this.maxRowHeight = new GridWidgetMaxRowHeightPropertyValueAccessor();
        this.iterate = new GridWidgetIteratePropertyValueAccessor();
        this.auto = new GridWidgetAutoPropertyValueAccessor();
        this.GridWidgetResetter = new GridWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "outlineThickness": {
                return this.outlineThickness;
            }
            case "columns": {
                return this.columns;
            }
            case "rows": {
                return this.rows;
            }
            case "maxColumnWidth": {
                return this.maxColumnWidth;
            }
            case "maxRowHeight": {
                return this.maxRowHeight;
            }
            case "iterate": {
                return this.iterate;
            }
            case "auto": {
                return this.auto;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "outlineThickness": {
                return true;
            }
            case "columns": {
                return true;
            }
            case "rows": {
                return true;
            }
            case "maxColumnWidth": {
                return true;
            }
            case "maxRowHeight": {
                return true;
            }
            case "iterate": {
                return true;
            }
            case "auto": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.GridWidgetResetter;
    }
}
