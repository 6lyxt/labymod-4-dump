// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.menu;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.test.cave.BackgroundWorldTestActivity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultAdvancedConfig;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gui.screen.widget.widgets.title.MainMenuWidget;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/main-menu.lss")
@AutoActivity
public class MainMenuActivity extends SimpleActivity
{
    private static boolean handledFadeIn;
    private final AbstractBootLogoRenderer logo;
    private final DynamicBackgroundController world;
    private MainMenuWidget mainMenuWidget;
    
    public MainMenuActivity() {
        final DefaultReferenceStorage references = LabyMod.references();
        this.logo = references.bootLogoController().renderer();
        this.world = references.dynamicBackgroundController();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean fadeIn = !MainMenuActivity.handledFadeIn && Laby.labyAPI().config().appearance().fadeOutAnimation().get() == FadeOutAnimationType.FADING && MinecraftVersions.V1_16.orNewer();
        (this.mainMenuWidget = new MainMenuWidget(this.logo, this.world, fadeIn)).addId("main-menu");
        ((AbstractWidget<MainMenuWidget>)this.document).addChild(this.mainMenuWidget);
        if (!this.world.isOpeningPlayed()) {
            this.world.playOpening();
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        this.logo.updateProgress(1.0f, this.logo.isResourcesPreloaded());
        super.render(context);
        MainMenuActivity.handledFadeIn = true;
        if (((DefaultAdvancedConfig)this.labyAPI.config().other().advanced()).devTools().get() && KeyHandler.isControlDown() && KeyHandler.isShiftDown() && this.labyAPI.minecraft().isKeyPressed(Key.O)) {
            this.labyAPI.minecraft().minecraftWindow().displayScreen(new BackgroundWorldTestActivity());
        }
    }
    
    @Override
    public boolean shouldRenderBackground() {
        return !DynamicBackgroundController.isEnabled();
    }
    
    @Override
    public boolean renderBackground(final ScreenContext context) {
        final boolean isFancyTheme = Laby.labyAPI().themeService().currentTheme() instanceof FancyTheme;
        return !isFancyTheme;
    }
    
    @Override
    public boolean displayPreviousScreen() {
        return false;
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.PLAY) {
            ThreadSafe.executeOnRenderThread(() -> {
                if (this.mainMenuWidget != null) {
                    this.mainMenuWidget.onLabyConnectPlay();
                }
            });
        }
    }
}
