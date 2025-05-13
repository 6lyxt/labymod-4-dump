// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

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

@Mixin({ eqz.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private eqh bJ;
    @Shadow
    public String ad;
    @Shadow
    public boolean ab;
    @Shadow
    public String aa;
    @Shadow
    @Final
    public eqt I;
    @Shadow
    @Final
    public eqt H;
    @Shadow
    @Final
    private Set<cbv> aN;
    @Shadow
    @Final
    private eqy<Double> aS;
    @Shadow
    @Final
    private eqy<Double> aR;
    @Shadow
    @Final
    private eqy<Boolean> am;
    @Shadow
    @Final
    private eqy<Integer> au;
    @Shadow
    @Final
    private eqy<cbs> aF;
    @Shadow
    @Final
    private eqy<bja> aO;
    @Shadow
    @Final
    private eqy<Double> aQ;
    @Shadow
    @Final
    private eqy<Double> aP;
    @Shadow
    @Final
    private eqy<Double> aG;
    @Shadow
    @Final
    private eqy<Double> aI;
    @Shadow
    @Final
    private eqy<Double> aH;
    @Shadow
    @Final
    private eqy<Boolean> be;
    @Shadow
    @Final
    private eqy<Boolean> bf;
    @Shadow
    @Final
    private eqy<Boolean> bg;
    @Shadow
    @Final
    private eqy<Boolean> bx;
    @Shadow
    @Final
    private eqy<Boolean> by;
    @Shadow
    @Final
    private eqy<Integer> bK;
    @Shadow
    public boolean Z;
    @Shadow
    @Final
    public eqt D;
    @Shadow
    @Final
    public eqt C;
    @Shadow
    @Final
    private File bI;
    @Shadow
    @Final
    private eqy<eqf> aX;
    @Shadow
    public boolean ae;
    @Shadow
    public gev r;
    @Shadow
    @Final
    private eqy<Integer> aq;
    @Shadow
    @Final
    private eqy<Integer> ar;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void at();
    
    @Shadow
    public abstract eqy<Boolean> X();
    
    @Shadow
    public abstract void shadow$ar();
    
    @Shadow
    public abstract eqy<Boolean> U();
    
    @Shadow
    public abstract int az();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bI);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ae = false;
        this.r = gev.f;
        this.aq.a((Object)OptionsUtil.getRenderDistance((int)this.aq.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bI));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bI));
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
        return switch (this.bJ) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final cbv part : cbv.values()) {
            if ((value & part.a()) == part.a()) {
                this.aN.add(part);
            }
            else {
                this.aN.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final cbv part : this.aN) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.at();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ad;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((cbs)this.aF.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aO.c() == bja.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aO.a((Object)((hand == MainHand.LEFT) ? bja.a : bja.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aS.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aR.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aQ.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aP.c();
    }
    
    @Override
    public double getChatTextOpacity() {
        return (double)this.aG.c() * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return (double)this.aI.c();
    }
    
    @Override
    public double getChatLineSpacing() {
        return (double)this.aH.c();
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return (boolean)this.be.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bf.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bg.c();
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
        return (boolean)this.bx.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bx.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bJ = eqh.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.X().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.by.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bK.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.Z;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return eqv.O().aN().d();
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$ar();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((eqf)this.aX.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.U().c();
    }
    
    @Override
    public int getRenderDistance() {
        return (int)this.aq.c();
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.az();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.ar.c();
    }
}
