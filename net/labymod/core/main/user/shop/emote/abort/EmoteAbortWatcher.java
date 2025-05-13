// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.abort;

import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.api.client.entity.Entity;

public interface EmoteAbortWatcher<E extends Entity>
{
    boolean shouldAbort(final EmoteAnimationStorage p0, final E p1, final float p2);
}
