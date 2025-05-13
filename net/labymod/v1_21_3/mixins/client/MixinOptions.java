// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client;

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

@Mixin({ fmk.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private flq bU;
    @Shadow
    public String ab;
    @Shadow
    public boolean Z;
    @Shadow
    public String Y;
    @Shadow
    @Final
    public fme G;
    @Shadow
    @Final
    public fme F;
    @Shadow
    @Final
    private Set<cpy> aU;
    @Shadow
    @Final
    private fmj<Double> aZ;
    @Shadow
    @Final
    private fmj<Double> aY;
    @Shadow
    @Final
    private fmj<Boolean> aj;
    @Shadow
    @Final
    private fmj<Integer> at;
    @Shadow
    @Final
    private fmj<cpu> aH;
    @Shadow
    @Final
    private fmj<bwa> aV;
    @Shadow
    @Final
    private fmj<Double> aX;
    @Shadow
    @Final
    private fmj<Double> aW;
    @Shadow
    @Final
    private fmj<Double> aI;
    @Shadow
    @Final
    private fmj<Double> aN;
    @Shadow
    @Final
    private fmj<Double> aJ;
    @Shadow
    @Final
    private fmj<Boolean> bn;
    @Shadow
    @Final
    private fmj<Boolean> bo;
    @Shadow
    @Final
    private fmj<Boolean> bp;
    @Shadow
    @Final
    private fmj<Boolean> bI;
    @Shadow
    @Final
    private fmj<Boolean> bJ;
    @Shadow
    @Final
    private fmj<Integer> bV;
    @Shadow
    public boolean X;
    @Shadow
    @Final
    public fme B;
    @Shadow
    @Final
    public fme A;
    @Shadow
    @Final
    private File bT;
    @Shadow
    @Final
    private fmj<flo> be;
    @Shadow
    public boolean ac;
    @Shadow
    public hhr r;
    @Shadow
    @Final
    private fmj<Integer> ap;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void aB();
    
    @Shadow
    public abstract fmj<Boolean> ae();
    
    @Shadow
    public abstract void shadow$az();
    
    @Shadow
    public abstract fmj<Boolean> ab();
    
    @Shadow
    public abstract fmj<Boolean> c();
    
    @Shadow
    public abstract fmj<Integer> e();
    
    @Shadow
    public abstract fmj<Integer> f();
    
    @Shadow
    public abstract int aH();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bT);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ac = false;
        this.r = hhr.f;
        this.ap.a((Object)OptionsUtil.getRenderDistance((int)this.ap.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bT));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bT));
    }
    
    @Redirect(method = { "save" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return (int)this.at.c();
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
        return switch (this.bU) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final cpy part : cpy.values()) {
            if ((value & part.a()) == part.a()) {
                this.aU.add(part);
            }
            else {
                this.aU.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final cpy part : this.aU) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.aB();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ab;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((cpu)this.aH.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aV.c() == bwa.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aV.a((Object)((hand == MainHand.LEFT) ? bwa.a : bwa.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aZ.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aY.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aX.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aW.c();
    }
    
    @Override
    public double getChatTextOpacity() {
        return (double)this.aI.c() * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return (double)this.aN.c();
    }
    
    @Override
    public double getChatLineSpacing() {
        return (double)this.aJ.c();
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return (boolean)this.bn.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bo.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bp.c();
    }
    
    @Override
    public String getLastKnownServer() {
        return this.Y;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.Z;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.Z = smooth;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.Y = address;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return (boolean)this.aj.c();
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.aj.a((Object)darkMode);
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.F;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.G;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.B;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.A;
    }
    
    @Override
    public boolean isFullscreen() {
        return (boolean)this.bI.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bI.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bU = flq.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.ae().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bJ.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bV.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.X;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return fmg.Q().aQ().d();
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$az();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((flo)this.be.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.ab().c();
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
        return this.aH();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.f().c();
    }
}
