// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace;

import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.labyconnect.LabyConnect;
import java.net.URI;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;

public class RPlaceOverlayCommand extends Command
{
    public RPlaceOverlayCommand() {
        super("place-overlay", new String[] { "placeoverlay", "rplace-overlay", "rplaceoverlay" });
        this.translationKey("labymod.command.command.rplaceoverlay");
    }
    
    @Override
    public boolean execute(final String prefix, final String[] arguments) {
        final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        if (!labyConnect.isAuthenticated()) {
            this.displayMessage(Component.translatable("labymod.activity.screenshotBrowser.viewer.notConnected", NamedTextColor.RED));
            return true;
        }
        final RPlaceRegistry registry = LabyMod.references().rPlaceRegistry();
        if (!registry.isOnTargetLobby()) {
            this.displayTranslatable("wrongServer", NamedTextColor.RED, new Component[0]);
            return true;
        }
        if (!registry.isEnabled()) {
            this.displayTranslatable("disabled", NamedTextColor.RED, new Component[0]);
            return true;
        }
        if (arguments.length == 1 && arguments[0].equals("clear")) {
            this.displayTranslatable("cleared", NamedTextColor.RED, new Component[0]);
            registry.clear(false);
            return true;
        }
        if (arguments.length < 3) {
            this.displaySyntax();
            return true;
        }
        final String url = arguments[0];
        final ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        final int playerX = (player == null) ? 0 : ((int)player.position().getX());
        final int playerZ = (player == null) ? 0 : ((int)player.position().getZ());
        final int x = arguments[1].equals("~") ? playerX : Integer.parseInt(arguments[1]);
        final int z = arguments[2].equals("~") ? playerZ : Integer.parseInt(arguments[2]);
        int size = -1;
        if (arguments.length > 3) {
            size = Integer.parseInt(arguments[3]);
        }
        try {
            final PixelArt art = registry.registerFromUrl(URI.create(url).toURL(), x, z, size, false);
            this.displayTranslatable("added", NamedTextColor.GREEN, Component.text(url, NamedTextColor.YELLOW), Component.text(art.getWidth(), NamedTextColor.YELLOW), Component.text(art.getHeight(), NamedTextColor.YELLOW), Component.text(art.getCenterX(), NamedTextColor.YELLOW), Component.text(art.getCenterZ(), NamedTextColor.YELLOW), Component.text("/place-overlay clear", NamedTextColor.GRAY));
        }
        catch (final Throwable e) {
            this.displayTranslatable("invalid", NamedTextColor.RED, Component.text(e.getMessage(), NamedTextColor.YELLOW));
        }
        return true;
    }
}
