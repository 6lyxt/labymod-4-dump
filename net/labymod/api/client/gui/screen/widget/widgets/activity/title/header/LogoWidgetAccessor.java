// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.title.header;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface LogoWidgetAccessor extends Widget
{
    @Nullable
    Widget getMinecraftWidget();
    
    @Nullable
    Widget getEditionWidget();
    
    @Nullable
    SplashWidgetAccessor getSplashWidget();
}
