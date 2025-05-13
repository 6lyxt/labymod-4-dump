// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.animation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Collection;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;

public class EmoteAnimationMeta
{
    public static AnimationMeta<Boolean> START_ANIMATION;
    public static final AnimationMeta<Boolean> TRIGGER_EMOTE;
    public static final AnimationMeta<Boolean> BLOCK_SNEAKING;
    public static final AnimationMeta<Boolean> BLOCK_HEAD_ANIMATION;
    private static final Collection<AnimationMeta<?>> WITH_DEFAULTS;
    
    public static Collection<AnimationMeta<?>> withDefaults() {
        return EmoteAnimationMeta.WITH_DEFAULTS;
    }
    
    static {
        EmoteAnimationMeta.START_ANIMATION = new AnimationMeta<Boolean>("start_animation", "s_a", Boolean::parseBoolean);
        TRIGGER_EMOTE = new AnimationMeta<Boolean>("trigger_emote", "t_e", Boolean::parseBoolean);
        BLOCK_SNEAKING = new AnimationMeta<Boolean>("block_sneaking", "b_s", Boolean::parseBoolean);
        BLOCK_HEAD_ANIMATION = new AnimationMeta<Boolean>("block_head_animation", "b_h_a", Boolean::parseBoolean);
        final Collection<AnimationMeta<?>> withDefaults = new HashSet<AnimationMeta<?>>(AnimationMeta.defaults());
        withDefaults.add(EmoteAnimationMeta.TRIGGER_EMOTE);
        withDefaults.add(EmoteAnimationMeta.BLOCK_SNEAKING);
        withDefaults.add(EmoteAnimationMeta.BLOCK_HEAD_ANIMATION);
        WITH_DEFAULTS = Collections.unmodifiableCollection((Collection<? extends AnimationMeta<?>>)withDefaults);
    }
}
