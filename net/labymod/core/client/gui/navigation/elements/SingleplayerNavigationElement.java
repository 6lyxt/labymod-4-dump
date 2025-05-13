// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class SingleplayerNavigationElement extends ScreenNavigationElement
{
    public SingleplayerNavigationElement() {
        super(NamedScreen.SINGLEPLAYER.create());
    }
    
    @Override
    public String getWidgetId() {
        return "singleplayer";
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.singleplayer", new Component[0]);
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.SINGLEPLAYER;
    }
    
    @Override
    public boolean isVisible() {
        return !Laby.labyAPI().minecraft().isSingleplayer() && Laby.labyAPI().config().appearance().navigation().showSingleplayer().get();
    }
    
    @Override
    public void onUpdate(final ScreenInstance instance) {
        final GameScreen gameScreen = GameScreenRegistry.from(instance);
        if (gameScreen != null) {
            this.screen = instance;
        }
    }
    
    @Override
    public boolean isScreen(final Object raw) {
        final GameScreen gameScreen = GameScreenRegistry.from(raw);
        return gameScreen == NamedScreen.SINGLEPLAYER;
    }
    
    @Override
    public boolean isFallback() {
        return true;
    }
}
