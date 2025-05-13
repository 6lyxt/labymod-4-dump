// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client;

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

@Mixin({ ffk.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private fes bN;
    @Shadow
    public String ac;
    @Shadow
    public boolean aa;
    @Shadow
    public String Z;
    @Shadow
    @Final
    public ffe H;
    @Shadow
    @Final
    public ffe G;
    @Shadow
    @Final
    private Set<cmz> aQ;
    @Shadow
    @Final
    private ffj<Double> aV;
    @Shadow
    @Final
    private ffj<Double> aU;
    @Shadow
    @Final
    private ffj<Boolean> ak;
    @Shadow
    @Final
    private ffj<Integer> au;
    @Shadow
    @Final
    private ffj<cmw> aF;
    @Shadow
    @Final
    private ffj<btk> aR;
    @Shadow
    @Final
    private ffj<Double> aT;
    @Shadow
    @Final
    private ffj<Double> aS;
    @Shadow
    @Final
    private ffj<Double> aG;
    @Shadow
    @Final
    private ffj<Double> aL;
    @Shadow
    @Final
    private ffj<Double> aH;
    @Shadow
    @Final
    private ffj<Boolean> bh;
    @Shadow
    @Final
    private ffj<Boolean> bi;
    @Shadow
    @Final
    private ffj<Boolean> bj;
    @Shadow
    @Final
    private ffj<Boolean> bB;
    @Shadow
    @Final
    private ffj<Boolean> bC;
    @Shadow
    @Final
    private ffj<Integer> bO;
    @Shadow
    public boolean Y;
    @Shadow
    @Final
    public ffe C;
    @Shadow
    @Final
    public ffe B;
    @Shadow
    @Final
    private File bM;
    @Shadow
    @Final
    private ffj<feq> ba;
    @Shadow
    public boolean ad;
    @Shadow
    public gvb r;
    @Shadow
    @Final
    private ffj<Integer> aq;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void ax();
    
    @Shadow
    public abstract ffj<Boolean> ab();
    
    @Shadow
    public abstract void shadow$av();
    
    @Shadow
    public abstract ffj<Boolean> Y();
    
    @Shadow
    public abstract ffj<Boolean> c();
    
    @Shadow
    public abstract ffj<Integer> e();
    
    @Shadow
    public abstract ffj<Integer> f();
    
    @Shadow
    public abstract int aD();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bM);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ad = false;
        this.r = gvb.f;
        this.aq.a((Object)OptionsUtil.getRenderDistance((int)this.aq.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bM));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bM));
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
        return switch (this.bN) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final cmz part : cmz.values()) {
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
        for (final cmz part : this.aQ) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.ax();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ac;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((cmw)this.aF.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aR.c() == btk.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aR.a((Object)((hand == MainHand.LEFT) ? btk.a : btk.b));
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
        return (boolean)this.bB.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bB.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bN = fes.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.ab().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bC.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bO.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.Y;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return ffg.Q().aP().d();
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$av();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((feq)this.ba.c()) {
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
        return this.aD();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.f().c();
    }
}
