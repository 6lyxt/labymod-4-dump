// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type.list;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;

public interface ListSettingConfig
{
    @NotNull
    default Component entryDisplayName() {
        return ListSetting.defaultEntryName();
    }
    
    @NotNull
    default Component newEntryTitle() {
        return Component.translatable("labymod.ui.settings.list.entry", new Component[0]);
    }
    
    default boolean isInvalid() {
        return false;
    }
}
