// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.revision;

import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.widget.widgets.RevisionPopupWidget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetScreenOverlay;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.Laby;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.revision.SimpleRevision;

public class PopupRevision extends SimpleRevision
{
    private final String bannerPath;
    private boolean showPopUp;
    private boolean showToNewUsers;
    
    public PopupRevision(final String namespace, final SemanticVersion version, final String releaseDate, final String bannerPath, final boolean showToNewUsers) {
        super(namespace, version, releaseDate);
        this.showPopUp = false;
        this.bannerPath = bannerPath;
        this.showToNewUsers = showToNewUsers;
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    @Subscribe
    public void onActivityOpen(final ActivityOpenEvent event) {
        if (!this.showPopUp) {
            return;
        }
        final Activity activity = event.activity();
        if (!(activity instanceof MultiplayerActivity) && !(activity instanceof SingleplayerOverlay)) {
            return;
        }
        final WidgetScreenOverlay overlay = Laby.labyAPI().screenOverlayHandler().screenOverlay(WidgetScreenOverlay.class);
        if (overlay == null) {
            return;
        }
        Laby.labyAPI().minecraft().executeNextTick(() -> {
            try {
                final Icon icon = this.getIcon(this.bannerPath);
                final RevisionPopupWidget popupWidget = new RevisionPopupWidget(icon);
                popupWidget.displayInOverlay();
            }
            catch (final Throwable e) {
                e.printStackTrace();
            }
            this.showPopUp = false;
        });
    }
    
    private Icon getIcon(final String path) throws IOException {
        final ResourceLocation resourceLocation = Laby.references().resourceLocationFactory().create(this.getNamespace(), path);
        final Icon icon = Icon.texture(resourceLocation);
        final GameImage image = Laby.references().gameImageProvider().getImage(resourceLocation);
        icon.resolution(image.getWidth(), image.getHeight());
        image.close();
        return icon;
    }
    
    public void visit(final boolean isNewUser) {
        if (isNewUser && !this.showToNewUsers) {
            return;
        }
        this.showPopUp = true;
    }
}
