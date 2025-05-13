// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.badge;

import javax.inject.Inject;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.entity.player.badge.PositionType;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class BadgeService
{
    @Inject
    public BadgeService(final BadgeRegistry badgeRegistry) {
        badgeRegistry.register("labymod_rank", PositionType.LEFT_TO_NAME, new RankBadgeRenderer());
        badgeRegistry.register("language_flag", PositionType.OVERWRITE_PING, new LanguageFlagBadgeRenderer());
    }
}
