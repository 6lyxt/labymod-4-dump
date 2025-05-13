// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client;

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

@Mixin({ evm.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private euu bL;
    @Shadow
    public String ad;
    @Shadow
    public boolean ab;
    @Shadow
    public String aa;
    @Shadow
    @Final
    public evg I;
    @Shadow
    @Final
    public evg H;
    @Shadow
    @Final
    private Set<cfj> aP;
    @Shadow
    @Final
    private evl<Double> aU;
    @Shadow
    @Final
    private evl<Double> aT;
    @Shadow
    @Final
    private evl<Boolean> am;
    @Shadow
    @Final
    private evl<Integer> aw;
    @Shadow
    @Final
    private evl<cfg> aH;
    @Shadow
    @Final
    private evl<bmf> aQ;
    @Shadow
    @Final
    private evl<Double> aS;
    @Shadow
    @Final
    private evl<Double> aR;
    @Shadow
    @Final
    private evl<Double> aI;
    @Shadow
    @Final
    private evl<Double> aK;
    @Shadow
    @Final
    private evl<Double> aJ;
    @Shadow
    @Final
    private evl<Boolean> bg;
    @Shadow
    @Final
    private evl<Boolean> bh;
    @Shadow
    @Final
    private evl<Boolean> bi;
    @Shadow
    @Final
    private evl<Boolean> bz;
    @Shadow
    @Final
    private evl<Boolean> bA;
    @Shadow
    @Final
    private evl<Integer> bM;
    @Shadow
    public boolean Z;
    @Shadow
    @Final
    public evg D;
    @Shadow
    @Final
    public evg C;
    @Shadow
    @Final
    private File bK;
    @Shadow
    @Final
    private evl<eus> aZ;
    @Shadow
    public boolean ae;
    @Shadow
    public gkj r;
    @Shadow
    @Final
    private evl<Integer> as;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void au();
    
    @Shadow
    public abstract evl<Boolean> Y();
    
    @Shadow
    public abstract void shadow$as();
    
    @Shadow
    public abstract evl<Boolean> V();
    
    @Shadow
    public abstract evl<Boolean> c();
    
    @Shadow
    public abstract evl<Integer> e();
    
    @Shadow
    public abstract evl<Integer> f();
    
    @Shadow
    public abstract int aA();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bK);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ae = false;
        this.r = gkj.f;
        this.as.a((Object)OptionsUtil.getRenderDistance((int)this.as.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bK));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bK));
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
        return switch (this.bL) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final cfj part : cfj.values()) {
            if ((value & part.a()) == part.a()) {
                this.aP.add(part);
            }
            else {
                this.aP.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final cfj part : this.aP) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.au();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ad;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((cfg)this.aH.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aQ.c() == bmf.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aQ.a((Object)((hand == MainHand.LEFT) ? bmf.a : bmf.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aU.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aT.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aS.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aR.c();
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
        return (boolean)this.bg.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bh.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bi.c();
    }
    
    @Override
    public String getLastKnownServer() {
        return this.aa;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.ab;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.ab = smooth;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.aa = address;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return (boolean)this.am.c();
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.am.a((Object)darkMode);
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
        return (boolean)this.bz.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bz.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bL = euu.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.Y().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bA.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bM.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.Z;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return evi.O().aN().d();
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$as();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((eus)this.aZ.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.V().c();
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
        return this.aA();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.f().c();
    }
}
