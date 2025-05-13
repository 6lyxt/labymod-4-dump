// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.hotkey;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.settings.creator.SettingEntry;
import net.labymod.api.configuration.settings.creator.MemberInspector;

public interface HotkeyFactory
{
    boolean hasSettingAnnotation(final MemberInspector p0);
    
    @Nullable
    Hotkey create(final MemberInspector p0, final String p1, final SettingEntry p2);
}
