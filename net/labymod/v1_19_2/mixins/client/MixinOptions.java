// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client;

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

@Mixin({ efy.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private efg bI;
    @Shadow
    public String ag;
    @Shadow
    public boolean af;
    @Shadow
    public String ae;
    @Shadow
    @Final
    public efs J;
    @Shadow
    @Final
    public efs I;
    @Shadow
    @Final
    private Set<bud> aL;
    @Shadow
    @Final
    private efx<Double> aQ;
    @Shadow
    @Final
    private efx<Double> aP;
    @Shadow
    @Final
    private efx<Boolean> ao;
    @Shadow
    @Final
    private efx<Integer> aw;
    @Shadow
    @Final
    private efx<bua> aH;
    @Shadow
    @Final
    private efx<bbx> aM;
    @Shadow
    @Final
    private efx<Double> aO;
    @Shadow
    @Final
    private efx<Double> aN;
    @Shadow
    @Final
    private efx<Double> aI;
    @Shadow
    @Final
    private efx<Double> aK;
    @Shadow
    @Final
    private efx<Double> aJ;
    @Shadow
    @Final
    private efx<Boolean> ba;
    @Shadow
    @Final
    private efx<Boolean> bb;
    @Shadow
    @Final
    private efx<Boolean> bc;
    @Shadow
    @Final
    private efx<Boolean> bs;
    @Shadow
    @Final
    private efx<Boolean> bt;
    @Shadow
    @Final
    private efx<Integer> bJ;
    @Shadow
    public boolean aa;
    @Shadow
    @Final
    public efs E;
    @Shadow
    @Final
    public efs D;
    @Shadow
    @Final
    private File bH;
    @Shadow
    public boolean ab;
    @Shadow
    @Final
    private efx<efe> aU;
    @Shadow
    public fom s;
    @Shadow
    @Final
    private efx<Integer> as;
    @Shadow
    @Final
    private efx<Integer> at;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void ak();
    
    @Shadow
    public abstract efx<Boolean> S();
    
    @Shadow
    public abstract void shadow$aj();
    
    @Shadow
    public abstract efx<Boolean> P();
    
    @Shadow
    public abstract int aq();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bH);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.s = fom.f;
        this.as.a((Object)OptionsUtil.getRenderDistance((int)this.as.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bH));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bH));
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
        return switch (this.bI) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final bud part : bud.values()) {
            if ((value & part.a()) == part.a()) {
                this.aL.add(part);
            }
            else {
                this.aL.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final bud part : this.aL) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.ak();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ag;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((bua)this.aH.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aM.c() == bbx.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aM.a((Object)((hand == MainHand.LEFT) ? bbx.a : bbx.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aQ.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aP.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aO.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aN.c();
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
        return (boolean)this.ba.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bb.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bc.c();
    }
    
    @Override
    public String getLastKnownServer() {
        return this.ae;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.af;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.af = smooth;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.ae = address;
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
        return (MinecraftInputMapping)this.I;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.J;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.E;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.D;
    }
    
    @Override
    public boolean isFullscreen() {
        return (boolean)this.bs.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bs.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bI = efg.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.S().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bt.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bJ.c();
    }
    
    @Override
    public boolean isHideGUI() {
        return this.aa;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.ab;
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$aj();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((efe)this.aU.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.P().c();
    }
    
    @Override
    public int getRenderDistance() {
        return (int)this.as.c();
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.aq();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.at.c();
    }
}
