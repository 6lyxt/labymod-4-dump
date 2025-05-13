// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import java.util.UUID;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.BossBarProgressHandler;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.BossBar;

@Mixin({ bjj.class })
public abstract class MixinBossInfo extends tt implements BossBar
{
    private final BossBarProgressHandler labyMod$progressHandler;
    private Component labyMod$displayName;
    private BossBarColor labyMod$bossBarColor;
    private BossBarOverlay labyMod$bossBarOverlay;
    
    public MixinBossInfo(final UUID lvt_1_1_, final hh lvt_2_1_, final tt.a lvt_3_1_, final tt.b lvt_4_1_) {
        super(lvt_1_1_, lvt_2_1_, lvt_3_1_, lvt_4_1_);
        this.labyMod$progressHandler = new DynamicBossBarProgressHandler(this::a, this::f);
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labymod$init(final ik packet, final CallbackInfo ci) {
        this.init();
    }
    
    public void init() {
        this.labyMod$displayName = Laby.references().componentMapper().fromMinecraftComponent(this.e());
        this.labyMod$bossBarColor = this.labyMod$toLabyModColor(this.g());
        this.labyMod$bossBarOverlay = this.labyMod$toLabyModOverlay(this.h());
    }
    
    public void a(final hh lvt_1_1_) {
        super.a(lvt_1_1_);
        this.init();
    }
    
    public void a(final tt.a lvt_1_1_) {
        super.a(lvt_1_1_);
        this.init();
    }
    
    public void a(final tt.b lvt_1_1_) {
        super.a(lvt_1_1_);
        this.init();
    }
    
    @NotNull
    public UUID getIdentifier() {
        return this.d();
    }
    
    public Component displayName() {
        return this.labyMod$displayName;
    }
    
    public BossBarColor bossBarColor() {
        return this.labyMod$bossBarColor;
    }
    
    public BossBarOverlay bossBarOverlay() {
        return this.labyMod$bossBarOverlay;
    }
    
    public BossBarProgressHandler progressHandler() {
        return this.labyMod$progressHandler;
    }
    
    private BossBarColor labyMod$toLabyModColor(final tt.a color) {
        return switch (color) {
            default -> throw new MatchException(null, null);
            case a -> BossBarColor.PINK;
            case b -> BossBarColor.BLUE;
            case c -> BossBarColor.RED;
            case d -> BossBarColor.GREEN;
            case e -> BossBarColor.YELLOW;
            case f -> BossBarColor.PURPLE;
            case g -> BossBarColor.WHITE;
        };
    }
    
    private BossBarOverlay labyMod$toLabyModOverlay(final tt.b overlay) {
        return switch (overlay) {
            default -> throw new MatchException(null, null);
            case a -> BossBarOverlay.PROGRESS;
            case b -> BossBarOverlay.NOTCHED_6;
            case c -> BossBarOverlay.NOTCHED_10;
            case d -> BossBarOverlay.NOTCHED_12;
            case e -> BossBarOverlay.NOTCHED_20;
        };
    }
}
