// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client;

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
import org.spongepowered.asm.mixin.Final;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.MinecraftOptions;

@Mixin({ dvt.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private dvb bn;
    @Shadow
    public String bd;
    @Shadow
    public bkc q;
    @Shadow
    public double C;
    @Shadow
    public double D;
    @Shadow
    public double E;
    @Shadow
    public double F;
    @Shadow
    public double r;
    @Shadow
    public double t;
    @Shadow
    public double s;
    @Shadow
    public boolean T;
    @Shadow
    public boolean U;
    @Shadow
    public boolean V;
    @Shadow
    public boolean aV;
    @Shadow
    public double aW;
    @Shadow
    public String aU;
    @Shadow
    public boolean g;
    @Shadow
    public int k;
    @Shadow
    public Set<bkf> bk;
    @Shadow
    public atp y;
    @Shadow
    public dvu bb;
    @Shadow
    @Final
    public dvm ay;
    @Shadow
    @Final
    public dvm ax;
    @Shadow
    public boolean ah;
    @Shadow
    public boolean ai;
    @Shadow
    public boolean aQ;
    @Shadow
    @Final
    public dvm at;
    @Shadow
    @Final
    public dvm as;
    @Shadow
    @Final
    private File bm;
    @Shadow
    public boolean aR;
    @Shadow
    public duz J;
    @Shadow
    public fbq K;
    @Shadow
    public int i;
    @Shadow
    public boolean af;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void c();
    
    @Shadow
    public abstract void shadow$b();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bm);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.K = fbq.f;
        this.i = OptionsUtil.getRenderDistance(this.i);
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bm));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bm));
    }
    
    @Redirect(method = { "save" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return this.k;
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
        return switch (this.bn) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final bkf part : bkf.values()) {
            if ((value & part.a()) == part.a()) {
                this.bk.add(part);
            }
            else {
                this.bk.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final bkf part : this.bk) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void sendOptionsToServer() {
        this.c();
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.bd;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch (this.q) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.y == atp.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.y = ((hand == MainHand.LEFT) ? atp.a : atp.b);
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor(this.F * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor(this.E * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor(this.D * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return this.C;
    }
    
    @Override
    public double getChatTextOpacity() {
        return this.r * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return this.t;
    }
    
    @Override
    public double getChatLineSpacing() {
        return this.s;
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return this.T;
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return this.U;
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return this.V;
    }
    
    @Override
    public String getLastKnownServer() {
        return this.aU;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.aU = address;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.aV;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.aV = smooth;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return this.g;
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.g = darkMode;
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.ax;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.ay;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.at;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.as;
    }
    
    @Override
    public boolean isFullscreen() {
        return this.ah;
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.ah = fullscreen;
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bn = dvb.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return this.ai;
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.ai = bobbing;
    }
    
    @Override
    public double getFov() {
        return this.aW;
    }
    
    @Override
    public boolean isHideGUI() {
        return this.aQ;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.aR;
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$b();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch (this.J) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return this.af;
    }
    
    @Override
    public int getRenderDistance() {
        return this.i;
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.getRenderDistance();
    }
}
