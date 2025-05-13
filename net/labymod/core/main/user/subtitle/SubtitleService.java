// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.subtitle;

import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.serverapi.core.model.display.Subtitle;
import javax.inject.Inject;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.entity.player.tag.PositionType;
import java.util.HashMap;
import net.labymod.api.LabyAPI;
import java.util.UUID;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class SubtitleService
{
    private final Map<UUID, SubtitleComponent> subtitles;
    
    @Inject
    public SubtitleService(final LabyAPI labyAPI) {
        this.subtitles = new HashMap<UUID, SubtitleComponent>();
        labyAPI.tagRegistry().register("subtitle", PositionType.BELOW_NAME, new SubtitleTag(this));
        labyAPI.eventBus().registerListener(this);
    }
    
    public void addSubtitle(final Subtitle subtitle) {
        this.subtitles.put(subtitle.getUniqueId(), new SubtitleComponent(subtitle));
    }
    
    public void removeSubtitle(final Subtitle subtitle) {
        this.subtitles.remove(subtitle.getUniqueId());
    }
    
    public void removeSubtitle(final UUID uniqueId) {
        this.subtitles.remove(uniqueId);
    }
    
    public SubtitleComponent getSubtitleOf(final UUID uuid) {
        return this.subtitles.get(uuid);
    }
    
    @Subscribe
    public void networkDisconnect(final NetworkDisconnectEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.subtitles.clear();
    }
    
    @Subscribe
    public void onSubServerSwitch(final SubServerSwitchEvent event) {
        this.subtitles.clear();
    }
}
