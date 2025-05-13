// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.FoldingWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.FoldingWidgetPreviewExpandPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.FoldingWidgetContentOffsetPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class FoldingWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> contentOffset;
    protected PropertyValueAccessor<?, ?, ?> previewExpand;
    LssPropertyResetter FoldingWidgetResetter;
    
    public FoldingWidgetDirectPropertyValueAccessor() {
        this.contentOffset = new FoldingWidgetContentOffsetPropertyValueAccessor();
        this.previewExpand = new FoldingWidgetPreviewExpandPropertyValueAccessor();
        this.FoldingWidgetResetter = new FoldingWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "contentOffset": {
                return this.contentOffset;
            }
            case "previewExpand": {
                return this.previewExpand;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "contentOffset": {
                return true;
            }
            case "previewExpand": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.FoldingWidgetResetter;
    }
}
