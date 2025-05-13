// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.title;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.Event;

public class TitleScreenLogoInitializeEvent implements Event
{
    private final String type;
    @Nullable
    private Widget minecraftWidget;
    @Nullable
    private Widget editionWidget;
    
    public TitleScreenLogoInitializeEvent(final String type, @Nullable final Widget minecraftWidget, @Nullable final Widget editionWidget) {
        this.type = type;
        this.minecraftWidget = minecraftWidget;
        this.editionWidget = editionWidget;
    }
    
    public String getType() {
        return this.type;
    }
    
    @Nullable
    public Widget getMinecraftWidget() {
        return this.minecraftWidget;
    }
    
    @Nullable
    public Widget getEditionWidget() {
        return this.editionWidget;
    }
    
    public void setMinecraftWidget(@Nullable final Widget minecraftWidget) {
        this.minecraftWidget = minecraftWidget;
    }
    
    public void setEditionWidget(@Nullable final Widget editionWidget) {
        this.editionWidget = editionWidget;
    }
}
