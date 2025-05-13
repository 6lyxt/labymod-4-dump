// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import java.util.Objects;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.navigation.elements.ScreenFactoryNavigationElement;

public class OptionsNavigationElement extends ScreenFactoryNavigationElement
{
    public OptionsNavigationElement() {
        final NamedScreen options = NamedScreen.OPTIONS;
        Objects.requireNonNull(options);
        super(options::create);
    }
    
    @Override
    public String getWidgetId() {
        return "options";
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("labymod.ui.navigation.options", new Component[0]);
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.SETTINGS;
    }
    
    @Override
    public boolean shouldDocumentHandleEscape() {
        return true;
    }
    
    @Override
    public void onUpdate(final ScreenInstance instance) {
        final GameScreen gameScreen = GameScreenRegistry.from(instance);
        if (gameScreen != null && instance != this.screen) {
            this.screen = instance;
        }
    }
    
    @Override
    public void onCloseScreen() {
    }
    
    @Override
    public boolean isScreen(final Object raw) {
        final GameScreen gameScreen = GameScreenRegistry.from(raw);
        return gameScreen != null && gameScreen.isOptions();
    }
    
    @Override
    public boolean isVisible() {
        return Laby.labyAPI().config().appearance().navigation().showOptions().get();
    }
    
    @Override
    public boolean isFallback() {
        return true;
    }
}
