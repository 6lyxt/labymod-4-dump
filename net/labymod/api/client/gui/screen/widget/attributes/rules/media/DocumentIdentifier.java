// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.util.bounds.Rectangle;

public class DocumentIdentifier implements MediaIdentifier
{
    private static final String IDENTIFIER = "document";
    private Rectangle rectangle;
    
    public DocumentIdentifier() {
        this.rectangle = new PositionedBounds(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public String identifier() {
        return "document";
    }
    
    @Override
    public Rectangle rectangle() {
        return this.rectangle;
    }
    
    @Override
    public void updateRectangle(final Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
