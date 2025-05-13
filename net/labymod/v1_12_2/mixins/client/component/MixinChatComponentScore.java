// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

import java.util.Objects;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.ScoreComponent;

@Mixin({ hl.class })
@Implements({ @Interface(iface = ScoreComponent.class, prefix = "scoreComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinChatComponentScore extends MixinChatComponentStyle<ScoreComponent> implements ScoreComponent
{
    @Shadow
    @Final
    @Mutable
    private String b;
    @Shadow
    @Final
    @Mutable
    private String c;
    
    @Shadow
    public abstract String e();
    
    @Intrinsic
    public String scoreComponent$getName() {
        return this.b;
    }
    
    @Intrinsic
    public String scoreComponent$getObjective() {
        return this.c;
    }
    
    @Override
    public ScoreComponent name(final String name) {
        this.b = name;
        return this;
    }
    
    @Override
    public ScoreComponent objective(final String objective) {
        this.c = objective;
        return this;
    }
    
    @Override
    public Component value() {
        return Component.text(this.e());
    }
    
    @Override
    public ScoreboardObjective getScoreboardObjective() {
        final chd integratedServer = bib.z().F();
        if (integratedServer == null) {
            return null;
        }
        if (integratedServer.M()) {
            final bhk scoreboard = integratedServer.C_().a(0).af();
            return (ScoreboardObjective)scoreboard.b(this.c);
        }
        return null;
    }
    
    @Override
    public ScoreComponent plainCopy() {
        return (ScoreComponent)new hl(this.b, this.c);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.b(), this.a, this.b, this.c);
    }
}
