// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.overlay;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.event.client.gui.hud.HudWidgetUpdateRequestEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.VanillaOptionsSaveEvent;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.event.client.gui.hud.HudWidgetMovedEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.hud.HudWidgetRegisterEvent;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetRendererWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;

@AutoActivity
public class HudWidgetOverlay extends IngameOverlayActivity
{
    private static final String REASON_MOVED = "moved";
    private static final ModifyReason UPDATE_RENDERER_BOUNDS;
    private HudWidgetRendererWidget rendererWidget;
    private boolean previousItemInOffhand;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        ((AbstractWidget<Widget>)(this.rendererWidget = new HudWidgetRendererWidget())).addId("renderer");
        this.rendererWidget.bounds().set(window.absoluteBounds(), HudWidgetOverlay.UPDATE_RENDERER_BOUNDS);
        ((AbstractWidget<HudWidgetRendererWidget>)this.document).addChild(this.rendererWidget);
    }
    
    @Subscribe
    public void onHudWidgetRegister(final HudWidgetRegisterEvent event) {
        if (this.rendererWidget != null) {
            this.rendererWidget.addHudWidget(event.hudWidget());
        }
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        if (this.rendererWidget != null) {
            this.rendererWidget.updateHudWidgets();
        }
    }
    
    @Subscribe
    public void onHudWidgetUpdate(final HudWidgetMovedEvent event) {
        if (this.rendererWidget != null) {
            this.rendererWidget.reinitializeHudWidget(event.hudWidget(), "moved");
        }
    }
    
    @Subscribe
    public void onConfigurationSave(final ConfigurationSaveEvent event) {
        this.reload();
    }
    
    @Subscribe
    public void onVanillaOptionsSave(final VanillaOptionsSaveEvent event) {
        if (event.getPhase() == Phase.PRE) {
            this.reload();
        }
    }
    
    @Subscribe
    public void onHudWidgetUpdateRequest(final HudWidgetUpdateRequestEvent event) {
        if (this.rendererWidget != null) {
            this.rendererWidget.reinitializeHudWidget(event.hudWidget(), event.getReason());
        }
    }
    
    @Override
    public void tick() {
        final Player clientPlayer = this.labyAPI.minecraft().getClientPlayer();
        if (clientPlayer != null) {
            final ItemStack offhandItemStack = clientPlayer.getOffHandItemStack();
            final boolean itemInOffhand = offhandItemStack != null && !offhandItemStack.isAir();
            if (this.previousItemInOffhand != itemInOffhand) {
                for (final HudWidgetDropzone dropzone : NamedHudWidgetDropzones.ITEMS) {
                    final HudWidget<?> hudWidget = this.rendererWidget.getHudWidgetInDropzone(dropzone);
                    if (hudWidget != null) {
                        this.rendererWidget.updateHudWidget(hudWidget.firstWidget());
                    }
                }
                this.previousItemInOffhand = itemInOffhand;
            }
        }
        super.tick();
    }
    
    @Override
    public boolean isVisible() {
        return this.labyAPI.config().ingame().hudWidgets().get();
    }
    
    @Override
    public boolean isAcceptingInput() {
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final ScreenWrapper screen = window.currentScreen();
        return NamedScreen.CHAT_INPUT.isScreen(screen) || NamedScreen.CHAT_INPUT_IN_BED.isScreen(screen);
    }
    
    static {
        UPDATE_RENDERER_BOUNDS = ModifyReason.of("updateRendererBounds");
    }
}
