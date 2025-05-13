// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class LabyModNavigationElement extends ScreenNavigationElement
{
    public LabyModNavigationElement() {
        super(new LabyModActivity());
    }
    
    @Override
    public String getWidgetId() {
        return "labymod";
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.labymod", new Component[0]);
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.LABYMOD;
    }
    
    @Override
    public boolean shouldDocumentHandleEscape() {
        return true;
    }
}
