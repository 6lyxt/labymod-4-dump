// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import java.io.IOException;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;

public interface ResourceLoader
{
    @Deprecated
    StyleSheet getOrLoadStyleSheet(@Nullable final LinkMetaList p0, final ThemeFile p1) throws IOException;
}
