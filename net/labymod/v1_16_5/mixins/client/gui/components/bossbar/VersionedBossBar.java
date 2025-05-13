// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.components.bossbar;

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
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.BossBar;

@Mixin({ dls.class })
@Implements({ @Interface(iface = BossBar.class, prefix = "bossBar$", remap = Interface.Remap.NONE) })
public abstract class VersionedBossBar extends aok implements BossBar
{
    private final BossBarProgressHandler labyMod$progressHandler;
    private Component labyMod$displayName;
    private BossBarColor labyMod$bossBarColor;
    private BossBarOverlay labyMod$bossBarOverlay;
    
    private VersionedBossBar(final UUID param0, final nr param1, final aok.a param2, final aok.b param3) {
        super(param0, param1, param2, param3);
        this.labyMod$progressHandler = new DynamicBossBarProgressHandler(this::a, this::k);
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$init(final oz packet, final CallbackInfo ci) {
        this.labyMod$init();
    }
    
    public void labyMod$init() {
        this.labyMod$displayName = Laby.references().componentMapper().fromMinecraftComponent(this.j());
        this.labyMod$bossBarColor = BossBarColor.getByName(this.l().b());
        this.labyMod$bossBarOverlay = BossBarOverlay.getByName(this.m().a());
    }
    
    public void a(final aok.a lvt_1_1_) {
        super.a(lvt_1_1_);
        this.labyMod$init();
    }
    
    public void a(final nr lvt_1_1_) {
        super.a(lvt_1_1_);
        this.labyMod$init();
    }
    
    public void a(final aok.b lvt_1_1_) {
        super.a(lvt_1_1_);
        this.labyMod$init();
    }
    
    @NotNull
    public UUID getIdentifier() {
        return this.i();
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
