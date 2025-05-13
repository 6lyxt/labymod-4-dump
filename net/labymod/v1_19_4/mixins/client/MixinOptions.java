// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client;

import net.labymod.api.client.options.AttackIndicatorPosition;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.core.client.options.InputMappingResolver;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.options.ChatVisibility;
import java.util.Iterator;
import net.labymod.api.client.options.Perspective;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.core.client.options.OptionsTranslator;
import java.io.PrintWriter;
import java.io.Writer;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.VanillaOptionsSaveEvent;
import net.labymod.core.client.options.OptionsUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.File;
import java.util.Set;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.MinecraftOptions;

@Mixin({ eml.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private elu bK;
    @Shadow
    public String af;
    @Shadow
    public boolean ae;
    @Shadow
    public String ad;
    @Shadow
    @Final
    public emf I;
    @Shadow
    @Final
    public emf H;
    @Shadow
    @Final
    private Set<byn> aO;
    @Shadow
    @Final
    private emk<Double> aT;
    @Shadow
    @Final
    private emk<Double> aS;
    @Shadow
    @Final
    private emk<Boolean> ao;
    @Shadow
    @Final
    private emk<Integer> aw;
    @Shadow
    @Final
    private emk<byk> aH;
    @Shadow
    @Final
    private emk<bfr> aP;
    @Shadow
    @Final
    private emk<Double> aR;
    @Shadow
    @Final
    private emk<Double> aQ;
    @Shadow
    @Final
    private emk<Double> aI;
    @Shadow
    @Final
    private emk<Double> aK;
    @Shadow
    @Final
    private emk<Double> aJ;
    @Shadow
    @Final
    private emk<Boolean> bf;
    @Shadow
    @Final
    private emk<Boolean> bg;
    @Shadow
    @Final
    private emk<Boolean> bh;
    @Shadow
    @Final
    private emk<Boolean> by;
    @Shadow
    @Final
    private emk<Boolean> bz;
    @Shadow
    @Final
    private emk<Integer> bL;
    @Shadow
    public boolean Z;
    @Shadow
    @Final
    public emf D;
    @Shadow
    @Final
    public emf C;
    @Shadow
    @Final
    private File bJ;
    @Shadow
    public boolean aa;
    @Shadow
    @Final
    private emk<els> aY;
    @Shadow
    public boolean ag;
    @Shadow
    public fyn r;
    @Shadow
    @Final
    private emk<Integer> as;
    @Shadow
    @Final
    private emk<Integer> at;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void ar();
    
    @Shadow
    public abstract emk<Boolean> W();
    
    @Shadow
    public abstract void shadow$aq();
    
    @Shadow
    public abstract emk<Boolean> T();
    
    @Shadow
    public abstract int ax();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bJ);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ag = false;
        this.r = fyn.f;
        this.as.a((Object)OptionsUtil.getRenderDistance((int)this.as.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bJ));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bJ));
    }
    
    @Redirect(method = { "save" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return (int)this.aw.c();
    }
    
    @Override
    public int getChatBackgroundColor() {
        return this.getBackgroundColorWithOpacity(Integer.MIN_VALUE);
    }
    
    @Override
    public int getBackgroundColorWithOpacity(final int baseColor) {
        return this.a(baseColor);
    }
    
    @Override
    public Perspective perspective() {
        return switch (this.bK) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final byn part : byn.values()) {
            if ((value & part.a()) == part.a()) {
                this.aO.add(part);
            }
            else {
                this.aO.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final byn part : this.aO) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.ar();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.af;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((byk)this.aH.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aP.c() == bfr.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aP.a((Object)((hand == MainHand.LEFT) ? bfr.a : bfr.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aT.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aS.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aR.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aQ.c();
    }
    
    @Override
    public double getChatTextOpacity() {
        return (double)this.aI.c() * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return (double)this.aK.c();
    }
    
    @Override
    public double getChatLineSpacing() {
        return (double)this.aJ.c();
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return (boolean)this.bf.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bg.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bh.c();
    }
    
    @Override
    public String getLastKnownServer() {
        return this.ad;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.ae;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.ae = smooth;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.ad = address;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return (boolean)this.ao.c();
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.ao.a((Object)darkMode);
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.H;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.I;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.D;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.C;
    }
    
    @Override
    public boolean isFullscreen() {
        return (boolean)this.by.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.by.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bK = elu.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.W().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bz.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bL.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.Z;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.aa;
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$aq();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((els)this.aY.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.T().c();
    }
    
    @Override
    public int getRenderDistance() {
        return (int)this.as.c();
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.ax();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.at.c();
    }
}
