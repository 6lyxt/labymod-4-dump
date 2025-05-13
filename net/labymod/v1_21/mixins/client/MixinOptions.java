// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client;

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

@Mixin({ fgs.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private ffz bO;
    @Shadow
    public String ac;
    @Shadow
    public boolean aa;
    @Shadow
    public String Z;
    @Shadow
    @Final
    public fgm H;
    @Shadow
    @Final
    public fgm G;
    @Shadow
    @Final
    private Set<cmy> aQ;
    @Shadow
    @Final
    private fgr<Double> aV;
    @Shadow
    @Final
    private fgr<Double> aU;
    @Shadow
    @Final
    private fgr<Boolean> ak;
    @Shadow
    @Final
    private fgr<Integer> au;
    @Shadow
    @Final
    private fgr<cmv> aF;
    @Shadow
    @Final
    private fgr<btg> aR;
    @Shadow
    @Final
    private fgr<Double> aT;
    @Shadow
    @Final
    private fgr<Double> aS;
    @Shadow
    @Final
    private fgr<Double> aG;
    @Shadow
    @Final
    private fgr<Double> aL;
    @Shadow
    @Final
    private fgr<Double> aH;
    @Shadow
    @Final
    private fgr<Boolean> bh;
    @Shadow
    @Final
    private fgr<Boolean> bi;
    @Shadow
    @Final
    private fgr<Boolean> bj;
    @Shadow
    @Final
    private fgr<Boolean> bC;
    @Shadow
    @Final
    private fgr<Boolean> bD;
    @Shadow
    @Final
    private fgr<Integer> bP;
    @Shadow
    public boolean Y;
    @Shadow
    @Final
    public fgm C;
    @Shadow
    @Final
    public fgm B;
    @Shadow
    @Final
    private File bN;
    @Shadow
    @Final
    private fgr<ffx> ba;
    @Shadow
    public boolean ad;
    @Shadow
    public gwk r;
    @Shadow
    @Final
    private fgr<Integer> aq;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void ay();
    
    @Shadow
    public abstract fgr<Boolean> ab();
    
    @Shadow
    public abstract void shadow$aw();
    
    @Shadow
    public abstract fgr<Boolean> Y();
    
    @Shadow
    public abstract fgr<Boolean> c();
    
    @Shadow
    public abstract fgr<Integer> e();
    
    @Shadow
    public abstract fgr<Integer> f();
    
    @Shadow
    public abstract int aE();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bN);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ad = false;
        this.r = gwk.f;
        this.aq.a((Object)OptionsUtil.getRenderDistance((int)this.aq.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bN));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bN));
    }
    
    @Redirect(method = { "save" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return (int)this.au.c();
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
        return switch (this.bO) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final cmy part : cmy.values()) {
            if ((value & part.a()) == part.a()) {
                this.aQ.add(part);
            }
            else {
                this.aQ.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final cmy part : this.aQ) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.ay();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ac;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((cmv)this.aF.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aR.c() == btg.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aR.a((Object)((hand == MainHand.LEFT) ? btg.a : btg.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aV.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aU.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aT.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aS.c();
    }
    
    @Override
    public double getChatTextOpacity() {
        return (double)this.aG.c() * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return (double)this.aL.c();
    }
    
    @Override
    public double getChatLineSpacing() {
        return (double)this.aH.c();
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return (boolean)this.bh.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bi.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bj.c();
    }
    
    @Override
    public String getLastKnownServer() {
        return this.Z;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.aa;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.aa = smooth;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.Z = address;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return (boolean)this.ak.c();
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.ak.a((Object)darkMode);
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.G;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.H;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.C;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.B;
    }
    
    @Override
    public boolean isFullscreen() {
        return (boolean)this.bC.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bC.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bO = ffz.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.ab().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bD.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bP.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.Y;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return fgo.Q().aN().d();
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$aw();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((ffx)this.ba.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.Y().c();
    }
    
    @Override
    public boolean isHideSplashTexts() {
        return (boolean)this.c().c();
    }
    
    @Override
    public int getRenderDistance() {
        return (int)this.e().c();
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.aE();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.f().c();
    }
}
