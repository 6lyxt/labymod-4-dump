// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client;

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

@Mixin({ dkd.class })
@Implements({ @Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE) })
public abstract class MixinOptions implements MinecraftOptions
{
    @Shadow
    private djl be;
    @Shadow
    public String aV;
    @Shadow
    public bfu j;
    @Shadow
    public double v;
    @Shadow
    public double w;
    @Shadow
    public double x;
    @Shadow
    public double y;
    @Shadow
    public double k;
    @Shadow
    public double m;
    @Shadow
    public double l;
    @Shadow
    public boolean L;
    @Shadow
    public boolean M;
    @Shadow
    public boolean N;
    @Shadow
    public boolean aN;
    @Shadow
    public double aO;
    @Shadow
    public String aM;
    @Shadow
    public int d;
    @Shadow
    public Set<bfx> bb;
    @Shadow
    public aqi r;
    @Shadow
    public dke aT;
    @Shadow
    @Final
    public djw aq;
    @Shadow
    @Final
    public djw ap;
    @Shadow
    public boolean Z;
    @Shadow
    public boolean aa;
    @Shadow
    public boolean aI;
    @Shadow
    @Final
    public djw al;
    @Shadow
    @Final
    public djw ak;
    @Shadow
    @Final
    private File bd;
    @Shadow
    public boolean aJ;
    @Shadow
    public dji C;
    @Shadow
    public eog D;
    @Shadow
    public int b;
    @Shadow
    public boolean X;
    
    @Shadow
    public abstract int a(final int p0);
    
    @Shadow
    public abstract void c();
    
    @Shadow
    public abstract void shadow$b();
    
    @Inject(method = { "load" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bd);
    }
    
    @Inject(method = { "load" }, at = { @At("RETURN") })
    private void labyMod$forceOptions(final CallbackInfo ci) {
        this.D = eog.f;
        this.b = OptionsUtil.getRenderDistance(this.b);
    }
    
    @Inject(method = { "save" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bd));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "save" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bd));
    }
    
    @Redirect(method = { "save" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return this.d;
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
        switch (this.be) {
            case a: {
                return Perspective.FIRST_PERSON;
            }
            case b: {
                return Perspective.THIRD_PERSON_BACK;
            }
            case c: {
                return Perspective.THIRD_PERSON_FRONT;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.be));
            }
        }
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final bfx part : bfx.values()) {
            if ((value & part.a()) == part.a()) {
                this.bb.add(part);
            }
            else {
                this.bb.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final bfx part : this.bb) {
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
        return this.aV;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        switch (this.j) {
            case a: {
                return ChatVisibility.SHOWN;
            }
            case b: {
                return ChatVisibility.COMMANDS_ONLY;
            }
            case c: {
                return ChatVisibility.HIDDEN;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.j));
            }
        }
    }
    
    @Override
    public MainHand mainHand() {
        return (this.r == aqi.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.r = ((hand == MainHand.LEFT) ? aqi.a : aqi.b);
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor(this.y * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor(this.x * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor(this.w * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return this.v;
    }
    
    @Override
    public double getChatTextOpacity() {
        return this.k * 0.8999999761581421 + 0.10000000149011612;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return this.m;
    }
    
    @Override
    public double getChatLineSpacing() {
        return this.l;
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return this.L;
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return this.M;
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return this.N;
    }
    
    @Override
    public String getLastKnownServer() {
        return this.aM;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.aM = address;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.aN;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.aN = smooth;
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return false;
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.ap;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.aq;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.al;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.ak;
    }
    
    @Override
    public boolean isFullscreen() {
        return this.Z;
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.Z = fullscreen;
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.be = djl.values()[perspective.ordinal()];
    }
    
    @Override
    public boolean isBobbing() {
        return this.aa;
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.aa = bobbing;
    }
    
    @Override
    public double getFov() {
        return this.aO;
    }
    
    @Override
    public boolean isHideGUI() {
        return this.aI;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.aJ;
    }
    
    @Intrinsic
    public void options$save() {
        this.shadow$b();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        switch (this.C) {
            case a: {
                return AttackIndicatorPosition.OFF;
            }
            case b: {
                return AttackIndicatorPosition.CROSSHAIR;
            }
            case c: {
                return AttackIndicatorPosition.HOTBAR;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.C));
            }
        }
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return this.X;
    }
    
    @Override
    public int getRenderDistance() {
        return this.b;
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.getRenderDistance();
    }
}
