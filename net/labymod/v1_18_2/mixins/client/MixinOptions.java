// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client;

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

@Mixin({ dyv.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private dyc bv;
    @Shadow
    public String bj;
    @Shadow
    public boh u;
    @Shadow
    public double G;
    @Shadow
    public double H;
    @Shadow
    public double I;
    @Shadow
    public double J;
    @Shadow
    public double v;
    @Shadow
    public double x;
    @Shadow
    public double w;
    @Shadow
    public boolean X;
    @Shadow
    public boolean Y;
    @Shadow
    public boolean Z;
    @Shadow
    public boolean bb;
    @Shadow
    public double bc;
    @Shadow
    public String ba;
    @Shadow
    public boolean h;
    @Shadow
    public int n;
    @Shadow
    public Set<bok> bs;
    @Shadow
    public axt C;
    @Shadow
    public dyw bh;
    @Shadow
    @Final
    public dyo aE;
    @Shadow
    @Final
    public dyo aD;
    @Shadow
    public boolean al;
    @Shadow
    public boolean am;
    @Shadow
    public boolean aW;
    @Shadow
    @Final
    public dyo az;
    @Shadow
    @Final
    public dyo ay;
    @Shadow
    @Final
    private File bu;
    @Shadow
    public boolean aX;
    @Shadow
    public dya N;
    @Shadow
    public ffc O;
    @Shadow
    public int k;
    @Shadow
    public boolean aj;
    @Shadow
    public int l;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void c();
    
    @Shadow
    public abstract void shadow$b();
    
    @Shadow
    public abstract int i();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bu);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.O = ffc.f;
        this.k = OptionsUtil.getRenderDistance(this.k);
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bu));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bu));
    }
    
    @Redirect(method = { "save" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return this.n;
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
        return switch (this.bv) {
            default -> throw new MatchException(null, null);
            case a -> Perspective.FIRST_PERSON;
            case b -> Perspective.THIRD_PERSON_BACK;
            case c -> Perspective.THIRD_PERSON_FRONT;
        };
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final bok part : bok.values()) {
            if ((value & part.a()) == part.a()) {
                this.bs.add(part);
            }
            else {
                this.bs.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final bok part : this.bs) {
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
        return this.bj;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        return switch (this.u) {
            default -> throw new MatchException(null, null);
            case a -> ChatVisibility.SHOWN;
            case c -> ChatVisibility.HIDDEN;
            case b -> ChatVisibility.COMMANDS_ONLY;
        };
    }
    
    @Override
    public MainHand mainHand() {
        return (this.C == axt.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.C = ((hand == MainHand.LEFT) ? axt.a : axt.b);
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor(this.J * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor(this.I * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor(this.H * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return this.G;
    }
    
    @Override
    public double getChatTextOpacity() {
        return this.v * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return this.x;
    }
    
    @Override
    public double getChatLineSpacing() {
        return this.w;
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return this.X;
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return this.Y;
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return this.Z;
    }
    
    @Override
    public String getLastKnownServer() {
        return this.ba;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.ba = address;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.bb;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.bb = smooth;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return this.h;
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
        this.h = darkMode;
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.aD;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.aE;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.az;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.ay;
    }
    
    @Override
    public boolean isFullscreen() {
        return this.al;
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.al = fullscreen;
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.bv = dyc.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return this.am;
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.am = bobbing;
    }
    
    @Override
    public double getFov() {
        return this.bc;
    }
    
    @Override
    public boolean isHideGUI() {
        return this.aW;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.aX;
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$b();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return switch (this.N) {
            default -> throw new MatchException(null, null);
            case b -> AttackIndicatorPosition.CROSSHAIR;
            case c -> AttackIndicatorPosition.HOTBAR;
            case a -> AttackIndicatorPosition.OFF;
        };
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return this.aj;
    }
    
    @Override
    public int getRenderDistance() {
        return this.k;
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.i();
    }
    
    @Override
    public int getSimulationDistance() {
        return this.l;
    }
}
