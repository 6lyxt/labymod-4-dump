// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.abort;

import net.labymod.api.client.entity.Entity;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import java.util.HashSet;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import java.util.Collection;
import net.labymod.api.client.entity.player.Player;

public class PlayerEmoteAbortWatcher implements EmoteAbortWatcher<Player>
{
    private final Collection<AbortAction> defaultAbortActions;
    
    public PlayerEmoteAbortWatcher(final Collection<AbortAction> defaultAbortActions) {
        this.defaultAbortActions = defaultAbortActions;
    }
    
    @Override
    public boolean shouldAbort(final EmoteAnimationStorage animationStorage, final Player player, final float partialTicks) {
        final EmoteItem emote = animationStorage.getEmote();
        final Collection<AbortAction> abortActions = emote.getAbortActions();
        final Collection<AbortAction> ignoredAbortActions = emote.getIgnoredAbortActions();
        final Collection<String> suspendedParts = new HashSet<String>();
        for (final AbortAction abortAction : AbortAction.VALUES) {
            final boolean active = !ignoredAbortActions.contains(abortAction) && (abortActions.contains(abortAction) || this.defaultAbortActions.contains(abortAction));
            final boolean met = abortAction.isMet(player, partialTicks);
            if (met) {
                animationStorage.actionMet(abortAction);
                if (active) {
                    return true;
                }
            }
            if (abortAction.affectsParts()) {
                final boolean unsuspendDelay = !met && animationStorage.getSuspendedParts().containsAll(abortAction.getAffectedParts()) && animationStorage.isActionMetDelay(abortAction);
                if (met || unsuspendDelay) {
                    suspendedParts.addAll(abortAction.getAffectedParts());
                }
            }
        }
        animationStorage.updateSuspendedParts(suspendedParts);
        return false;
    }
}
