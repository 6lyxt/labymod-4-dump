// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class MenuNavigationElement extends ScreenNavigationElement
{
    public MenuNavigationElement() {
        super(NamedScreen.INGAME_MENU.create());
    }
    
    @Override
    public String getWidgetId() {
        return "menu";
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.menu", new Component[0]);
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.WORKBENCH;
    }
    
    @Override
    public boolean isVisible() {
        return Laby.labyAPI().minecraft().isIngame();
    }
}
