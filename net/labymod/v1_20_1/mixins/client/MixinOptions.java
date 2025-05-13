// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client;

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

@Mixin({ enr.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private ena bL;
    @Shadow
    public String ag;
    @Shadow
    public boolean ae;
    @Shadow
    public String ad;
    @Shadow
    @Final
    public enl I;
    @Shadow
    @Final
    public enl H;
    @Shadow
    @Final
    private Set<byp> aP;
    @Shadow
    @Final
    private enq<Double> aU;
    @Shadow
    @Final
    private enq<Double> aT;
    @Shadow
    @Final
    private enq<Boolean> ap;
    @Shadow
    @Final
    private enq<Integer> ax;
    @Shadow
    @Final
    private enq<bym> aI;
    @Shadow
    @Final
    private enq<bft> aQ;
    @Shadow
    @Final
    private enq<Double> aS;
    @Shadow
    @Final
    private enq<Double> aR;
    @Shadow
    @Final
    private enq<Double> aJ;
    @Shadow
    @Final
    private enq<Double> aL;
    @Shadow
    @Final
    private enq<Double> aK;
    @Shadow
    @Final
    private enq<Boolean> bg;
    @Shadow
    @Final
    private enq<Boolean> bh;
    @Shadow
    @Final
    private enq<Boolean> bi;
    @Shadow
    @Final
    private enq<Boolean> bz;
    @Shadow
    @Final
    private enq<Boolean> bA;
    @Shadow
    @Final
    private enq<Integer> bM;
    @Shadow
    public boolean Z;
    @Shadow
    @Final
    public enl D;
    @Shadow
    @Final
    public enl C;
    @Shadow
    @Final
    private File bK;
    @Shadow
    public boolean aa;
    @Shadow
    @Final
    private enq<emy> aZ;
    @Shadow
    public boolean ah;
    @Shadow
    public gah r;
    @Shadow
    @Final
    private enq<Integer> at;
    @Shadow
    @Final
    private enq<Integer> au;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void ar();
    
    @Shadow
    public abstract enq<Boolean> W();
    
    @Shadow
    public abstract void shadow$aq();
    
    @Shadow
    public abstract enq<Boolean> T();
    
    @Shadow
    public abstract int ax();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bK);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.ah = false;
        this.r = gah.f;
        this.at.a((Object)OptionsUtil.getRenderDistance((int)this.at.c()));
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
        return (int)this.ax.c();
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
        for (final byp part : byp.values()) {
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
        for (final byp part : this.aP) {
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
        return this.ag;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((bym)this.aI.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aQ.c() == bft.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aQ.a((Object)((hand == MainHand.LEFT) ? bft.a : bft.b));
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
        return (double)this.aJ.c() * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return (double)this.aL.c();
    }
    
    @Override
    public double getChatLineSpacing() {
        return (double)this.aK.c();
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
        return (boolean)this.ap.c();
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.ap.a((Object)darkMode);
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
        this.bL = ena.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.W().c();
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
        return this.aa;
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$aq();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((emy)this.aZ.c()) {
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
        return (int)this.at.c();
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.ax();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.au.c();
    }
}
