// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old;

import org.jetbrains.annotations.Nullable;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import net.labymod.core.main.animation.old.animations.HitboxOldAnimation;
import net.labymod.core.main.animation.old.animations.GeneralItemPostureOldAnimation;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.main.animation.old.animations.HeadRotationOldAnimation;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.animation.old.animations.legacy.LegacySneakingOldAnimation;
import net.labymod.core.main.animation.old.animations.SneakingOldAnimation;
import net.labymod.core.main.animation.old.animations.EquipOldAnimation;
import net.labymod.core.main.animation.old.animations.legacy.LegacySwordOldAnimation;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.main.animation.old.animations.FoodOldAnimation;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import net.labymod.core.main.animation.old.animations.BlockBuildOldAnimation;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import net.labymod.core.main.animation.old.animations.BowOldAnimation;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.event.EventBus;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class OldAnimationRegistry
{
    private final Map<String, OldAnimation> animations;
    private final EventBus eventBus;
    
    @Inject
    public OldAnimationRegistry(final EventBus eventBus) {
        this.animations = new HashMap<String, OldAnimation>();
        (this.eventBus = eventBus).registerListener(new OldAnimationListener(this));
    }
    
    public void registerAnimations() {
        this.register(new BowOldAnimation());
        this.register(new FishingRodOldAnimation());
        this.register(new BlockBuildOldAnimation());
        this.register(new HeartOldAnimation());
        this.register(new FoodOldAnimation());
        this.register(new InventoryLayoutOldAnimation());
        this.register(new SwordOldAnimation());
        this.register(new LegacySwordOldAnimation());
        this.register(new EquipOldAnimation());
        this.register(new SneakingOldAnimation());
        this.register(new LegacySneakingOldAnimation());
        this.register(new RangeOldAnimation());
        this.register(new SlowdownOldAnimation());
        this.register(new HeadRotationOldAnimation());
        this.register(new BackwardsOldAnimation());
        this.register(new GeneralItemPostureOldAnimation());
        this.register(new HitboxOldAnimation());
        this.register(new DamageOldAnimation());
    }
    
    public void register(final OldAnimation animation) {
        this.animations.put(animation.getName(), animation);
        this.eventBus.registerListener(animation);
    }
    
    @Nullable
    public <T extends OldAnimation> T get(final String name) {
        final OldAnimation oldAnimation = this.animations.get(name);
        return (T)((oldAnimation == null) ? null : oldAnimation);
    }
}
