// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.DirectConnectActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class MultiplayerNavigationElement extends ScreenNavigationElement
{
    public MultiplayerNavigationElement() {
        super(NamedScreen.MULTIPLAYER.create());
    }
    
    @Override
    public String getWidgetId() {
        return "multiplayer";
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.multiplayer", new Component[0]);
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.MULTIPLAYER;
    }
    
    @Override
    public boolean isScreen(final Object raw) {
        return super.isScreen(raw) || raw instanceof MultiplayerActivity || raw instanceof DirectConnectActivity;
    }
    
    @Override
    public boolean isFallback() {
        return true;
    }
}
