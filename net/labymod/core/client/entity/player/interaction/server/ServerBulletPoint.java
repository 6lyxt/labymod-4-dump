// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.server;

import net.labymod.core.main.LabyMod;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Textures;
import net.labymod.api.util.HashUtil;
import java.nio.charset.StandardCharsets;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.serverapi.core.model.feature.InteractionMenuEntry;
import net.labymod.core.client.entity.player.interaction.DefaultInteractionMenuRegistry;
import net.labymod.api.client.entity.player.interaction.BulletPoint;

public class ServerBulletPoint implements BulletPoint
{
    private static final DefaultInteractionMenuRegistry REGISTRY;
    private final InteractionMenuEntry entry;
    private final Component title;
    private final Icon icon;
    
    public ServerBulletPoint(final InteractionMenuEntry entry) {
        this.entry = entry;
        this.title = Laby.references().labyModProtocolService().mapComponent(entry.displayName());
        final String iconUrl = entry.getIconUrl();
        this.icon = ((iconUrl == null) ? null : Icon.completable(Laby.references().textureRepository().loadCacheResourceAsync("labymod", HashUtil.md5Hex(iconUrl.getBytes(StandardCharsets.UTF_8)), iconUrl, Textures.EMPTY)));
    }
    
    @Override
    public Component getTitle() {
        return this.title;
    }
    
    @Override
    public Icon getIcon() {
        return this.icon;
    }
    
    @Override
    public void execute(final Player player) {
        final LabyAPI labyAPI = Laby.labyAPI();
        final String value = ServerBulletPoint.REGISTRY.replacePlaceholders(this.entry.getValue(), player);
        switch (this.entry.type()) {
            case CLIPBOARD: {
                labyAPI.minecraft().chatExecutor().copyToClipboard(value);
                break;
            }
            case RUN_COMMAND: {
                labyAPI.minecraft().chatExecutor().chat(value.startsWith("/") ? value : ("/" + value), false);
                break;
            }
            case SUGGEST_COMMAND: {
                labyAPI.minecraft().openChat(value.startsWith("/") ? value : ("/" + value));
                break;
            }
            case OPEN_BROWSER: {
                OperatingSystem.getPlatform().openUrl(value);
                break;
            }
        }
    }
    
    static {
        REGISTRY = (DefaultInteractionMenuRegistry)LabyMod.references().interactionMenuRegistry();
    }
}
