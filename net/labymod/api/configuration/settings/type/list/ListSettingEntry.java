// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type.list;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.settings.type.SettingElement;

public class ListSettingEntry extends SettingElement
{
    private final Component displayName;
    private final int listIndex;
    
    public ListSettingEntry(final ListSetting holder, final int listIndex) {
        this(holder, Component.empty(), null, listIndex);
    }
    
    public ListSettingEntry(final ListSetting holder, final String displayName, final int listIndex) {
        this(holder, displayName, null, listIndex);
    }
    
    public ListSettingEntry(final ListSetting holder, final Component displayName, final int listIndex) {
        this(holder, displayName, null, listIndex);
    }
    
    public ListSettingEntry(final ListSetting holder, final String displayName, final String customTranslation, final int listIndex) {
        this(holder, Component.text(displayName), customTranslation, listIndex);
    }
    
    public ListSettingEntry(final ListSetting holder, final Component displayName, final String customTranslation, final int listIndex) {
        super("entry", null, customTranslation, new String[0]);
        this.parent = holder;
        this.displayName = displayName;
        this.listIndex = listIndex;
    }
    
    @Override
    public boolean hasAdvancedButton() {
        return true;
    }
    
    @Override
    public Component displayName() {
        return this.displayName;
    }
    
    public int listIndex() {
        return this.listIndex;
    }
    
    public void remove() {
        ((ListSetting)this.parent).remove(this);
    }
}
