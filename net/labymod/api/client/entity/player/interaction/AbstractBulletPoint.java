// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.interaction;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public abstract class AbstractBulletPoint implements BulletPoint
{
    private final Component title;
    private final Icon icon;
    
    protected AbstractBulletPoint(final Component title) {
        this(title, null);
    }
    
    protected AbstractBulletPoint(final Component title, final Icon icon) {
        this.title = title;
        this.icon = icon;
    }
    
    @Override
    public Component getTitle() {
        return this.title;
    }
    
    @Override
    public Icon getIcon() {
        return this.icon;
    }
}
