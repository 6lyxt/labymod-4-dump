// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client;

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

@Mixin({ ejj.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private eis bG;
    @Shadow
    public String ag;
    @Shadow
    public boolean af;
    @Shadow
    public String ae;
    @Shadow
    @Final
    public ejd J;
    @Shadow
    @Final
    public ejd I;
    @Shadow
    @Final
    private Set<bwq> aM;
    @Shadow
    @Final
    private eji<Double> aR;
    @Shadow
    @Final
    private eji<Double> aQ;
    @Shadow
    @Final
    private eji<Boolean> ao;
    @Shadow
    @Final
    private eji<Integer> aw;
    @Shadow
    @Final
    private eji<bwn> aH;
    @Shadow
    @Final
    private eji<beb> aN;
    @Shadow
    @Final
    private eji<Double> aP;
    @Shadow
    @Final
    private eji<Double> aO;
    @Shadow
    @Final
    private eji<Double> aI;
    @Shadow
    @Final
    private eji<Double> aK;
    @Shadow
    @Final
    private eji<Double> aJ;
    @Shadow
    @Final
    private eji<Boolean> bb;
    @Shadow
    @Final
    private eji<Boolean> bc;
    @Shadow
    @Final
    private eji<Boolean> bd;
    @Shadow
    @Final
    private eji<Boolean> bu;
    @Shadow
    @Final
    private eji<Boolean> bv;
    @Shadow
    @Final
    private eji<Integer> bH;
    @Shadow
    public boolean aa;
    @Shadow
    @Final
    public ejd E;
    @Shadow
    @Final
    public ejd D;
    @Shadow
    @Final
    private File bF;
    @Shadow
    public boolean ab;
    @Shadow
    @Final
    private eji<eiq> aU;
    @Shadow
    public ftv s;
    @Shadow
    @Final
    private eji<Integer> as;
    @Shadow
    @Final
    private eji<Integer> at;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void am();
    
    @Shadow
    public abstract eji<Boolean> U();
    
    @Shadow
    public abstract void shadow$al();
    
    @Shadow
    public abstract eji<Boolean> R();
    
    @Shadow
    public abstract int as();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bF);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.s = ftv.f;
        this.as.a((Object)OptionsUtil.getRenderDistance((int)this.as.c()));
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bF));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bF));
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
        return switch (this.bG) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final bwq part : bwq.values()) {
            if ((value & part.a()) == part.a()) {
                this.aM.add(part);
            }
            else {
                this.aM.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final bwq part : this.aM) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.am();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.ag;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch ((bwn)this.aH.c()) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.aN.c() == beb.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.aN.a((Object)((hand == MainHand.LEFT) ? beb.a : beb.b));
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor((double)this.aR.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor((double)this.aQ.c() * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor((double)this.aP.c() * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return (double)this.aO.c();
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
        return (boolean)this.bb.c();
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return (boolean)this.bc.c();
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return (boolean)this.bd.c();
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
        return (boolean)this.bu.c();
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.bu.a((Object)fullscreen);
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bG = eis.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return (boolean)this.U().c();
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.bv.a((Object)bobbing);
    }
    
    @Override
    public double getFov() {
        return (int)this.bH.c();
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
        this.shadow$al();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch ((eiq)this.aU.c()) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return (boolean)this.R().c();
    }
    
    @Override
    public int getRenderDistance() {
        return (int)this.as.c();
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.as();
    }
    
    @Override
    public int getSimulationDistance() {
        return (int)this.at.c();
    }
}
