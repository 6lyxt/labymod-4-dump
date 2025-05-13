// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Rectangle;

public class ScreenIdentifier implements MediaIdentifier
{
    private static final String IDENTIFIER = "screen";
    
    @Override
    public String identifier() {
        return "screen";
    }
    
    @Override
    public Rectangle rectangle() {
        return Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds();
    }
    
    @Override
    public void updateRectangle(final Rectangle rectangle) {
    }
}
