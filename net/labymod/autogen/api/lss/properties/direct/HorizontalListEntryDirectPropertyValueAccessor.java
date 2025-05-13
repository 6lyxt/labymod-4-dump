// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.HorizontalListEntryLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntrySkipFillPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryIgnoreHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryIgnoreWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryAlignmentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListEntryFlexPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class HorizontalListEntryDirectPropertyValueAccessor extends WrappedWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> flex;
    protected PropertyValueAccessor<?, ?, ?> alignment;
    protected PropertyValueAccessor<?, ?, ?> ignoreWidth;
    protected PropertyValueAccessor<?, ?, ?> ignoreHeight;
    protected PropertyValueAccessor<?, ?, ?> skipFill;
    LssPropertyResetter HorizontalListEntryResetter;
    
    public HorizontalListEntryDirectPropertyValueAccessor() {
        this.flex = new HorizontalListEntryFlexPropertyValueAccessor();
        this.alignment = new HorizontalListEntryAlignmentPropertyValueAccessor();
        this.ignoreWidth = new HorizontalListEntryIgnoreWidthPropertyValueAccessor();
        this.ignoreHeight = new HorizontalListEntryIgnoreHeightPropertyValueAccessor();
        this.skipFill = new HorizontalListEntrySkipFillPropertyValueAccessor();
        this.HorizontalListEntryResetter = new HorizontalListEntryLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "flex": {
                return this.flex;
            }
            case "alignment": {
                return this.alignment;
            }
            case "ignoreWidth": {
                return this.ignoreWidth;
            }
            case "ignoreHeight": {
                return this.ignoreHeight;
            }
            case "skipFill": {
                return this.skipFill;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "flex": {
                return true;
            }
            case "alignment": {
                return true;
            }
            case "ignoreWidth": {
                return true;
            }
            case "ignoreHeight": {
                return true;
            }
            case "skipFill": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.HorizontalListEntryResetter;
    }
}
