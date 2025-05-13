// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.gui.components.bossbar;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import java.util.UUID;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.BossBarProgressHandler;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.BossBar;

@Mixin({ fja.class })
@Implements({ @Interface(iface = BossBar.class, prefix = "bossBar$", remap = Interface.Remap.NONE) })
public abstract class VersionedBossBar extends bqh implements BossBar
{
    private final BossBarProgressHandler labyMod$progressHandler;
    private Component labyMod$displayName;
    private BossBarColor labyMod$bossBarColor;
    private BossBarOverlay labyMod$bossBarOverlay;
    
    @Shadow
    public abstract void a(final float p0);
    
    private VersionedBossBar(final UUID param0, final wz param1, final bqh.a param2, final bqh.b param3) {
        super(param0, param1, param2, param3);
        this.labyMod$progressHandler = new DynamicBossBarProgressHandler(this::a, this::j);
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$init(final UUID $$0, final wz $$1, final float $$2, final bqh.a $$3, final bqh.b $$4, final boolean $$5, final boolean $$6, final boolean $$7, final CallbackInfo ci) {
        this.labyMod$init();
    }
    
    private void labyMod$init() {
        this.labyMod$displayName = Laby.references().componentMapper().fromMinecraftComponent(this.i());
        this.labyMod$bossBarColor = BossBarColor.getByName(this.k().b());
        this.labyMod$bossBarOverlay = BossBarOverlay.getByName(this.l().a());
    }
    
    public void a(final wz $$0) {
        super.a($$0);
        this.labyMod$init();
    }
    
    public void a(final bqh.a $$0) {
        super.a($$0);
        this.labyMod$init();
    }
    
    public void a(final bqh.b $$0) {
        super.a($$0);
        this.labyMod$init();
    }
    
    @NotNull
    public UUID getIdentifier() {
        return this.h();
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
}
