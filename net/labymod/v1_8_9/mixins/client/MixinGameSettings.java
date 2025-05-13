// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.options.AttackIndicatorPosition;
import net.labymod.core.client.options.InputMappingResolver;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.options.ChatVisibility;
import java.util.Locale;
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
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.options.MinecraftInputMapping;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import java.io.File;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.MinecraftOptions;

@Mixin({ avh.class })
public abstract class MixinGameSettings implements MinecraftOptions
{
    private static final ChatScreenUpdateEvent VISIBILITY_CHAT_SCREEN_UPDATE_EVENT;
    private static final ChatScreenUpdateEvent COLORS_CHAT_SCREEN_UPDATE_EVENT;
    @Shadow
    public int aB;
    @Shadow
    public String aN;
    @Shadow
    public wn.b m;
    @Shadow
    public float E;
    @Shadow
    public float F;
    @Shadow
    public float G;
    @Shadow
    public float H;
    @Shadow
    public float q;
    @Shadow
    public boolean n;
    @Shadow
    public boolean o;
    @Shadow
    public boolean p;
    @Shadow
    public boolean aG;
    @Shadow
    public Set<wo> ba;
    @Shadow
    public String aF;
    @Shadow
    public int g;
    @Shadow
    public avb ai;
    @Shadow
    public avb ag;
    @Shadow
    public boolean s;
    @Shadow
    public boolean d;
    @Shadow
    public float aI;
    @Shadow
    public boolean aA;
    @Shadow
    public avb ad;
    @Shadow
    public avb ae;
    @Shadow
    public boolean aC;
    @Shadow
    public int c;
    @Shadow
    @Final
    private File bc;
    @Shadow
    public int aL;
    
    @Inject(method = { "isKeyDown" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$checkForHiddenKeys(final avb key, final CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping)key)) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Shadow
    public abstract void c();
    
    @Shadow
    public abstract void b();
    
    @Inject(method = { "loadOptions" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bc);
    }
    
    @Inject(method = { "loadOptions" }, at = { @At("RETURN") })
    private void labyMod$loadOptionsReturn(final CallbackInfo ci) {
        this.c = OptionsUtil.getRenderDistance(this.c);
    }
    
    @Inject(method = { "saveOptions" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bc));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "saveOptions" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bc));
    }
    
    @Redirect(method = { "saveOptions" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return this.g;
    }
    
    @Override
    public int getChatBackgroundColor() {
        return this.getBackgroundColorWithOpacity(Integer.MIN_VALUE);
    }
    
    @Override
    public int getBackgroundColorWithOpacity(final int baseColor) {
        return baseColor;
    }
    
    @Override
    public Perspective perspective() {
        if (this.aB == 0) {
            return Perspective.FIRST_PERSON;
        }
        if (this.aB == 1) {
            return Perspective.THIRD_PERSON_BACK;
        }
        if (this.aB == 2) {
            return Perspective.THIRD_PERSON_FRONT;
        }
        throw new RuntimeException("No perspective set");
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final wo part : this.ba) {
            mask |= part.a();
        }
        return mask;
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final wo part : wo.values()) {
            if ((value & part.a()) == part.a()) {
                this.ba.add(part);
            }
            else {
                this.ba.remove(part);
            }
        }
    }
    
    @Override
    public void sendOptionsToServer() {
        this.c();
    }
    
    @Override
    public boolean isEyeProtectionActive() {
        return true;
    }
    
    @Override
    public void setEyeProtectionActive(final boolean darkMode) {
    }
    
    @Override
    public String getCurrentLanguage() {
        return this.aN.toLowerCase(Locale.US);
    }
    
    @Override
    public String getLastKnownServer() {
        return this.aF;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.aF = address;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        switch (this.m) {
            case b: {
                return ChatVisibility.COMMANDS_ONLY;
            }
            case c: {
                return ChatVisibility.HIDDEN;
            }
            default: {
                return ChatVisibility.SHOWN;
            }
        }
    }
    
    @Override
    public MainHand mainHand() {
        return MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
    }
    
    @Override
    public float getChatHeightOpen() {
        return (float)Math.floor(this.H * 160.0 + 20.0);
    }
    
    @Override
    public float getChatHeightClosed() {
        return (float)Math.floor(this.G * 160.0 + 20.0);
    }
    
    @Override
    public float getChatWidth() {
        return (float)Math.floor(this.F * 280.0 + 40.0);
    }
    
    @Override
    public double getChatScale() {
        return this.E;
    }
    
    @Override
    public double getChatTextOpacity() {
        return this.q * 0.9f + 0.1f;
    }
    
    @Override
    public double getTextBackgroundOpacity() {
        return this.getChatTextOpacity() * 0.5;
    }
    
    @Override
    public double getChatLineSpacing() {
        return 0.0;
    }
    
    @Override
    public boolean isChatColorsEnabled() {
        return this.n;
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return this.o;
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return this.p;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.aG;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.aG = smooth;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.ag;
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.ai;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.ad;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.ae;
    }
    
    @Override
    public boolean isFullscreen() {
        return this.s;
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.s = fullscreen;
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.aB = perspective.ordinal();
    }
    
    @Override
    public boolean isBobbing() {
        return this.d;
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.d = bobbing;
    }
    
    @Override
    public double getFov() {
        return this.aI;
    }
    
    @Override
    public boolean isHideGUI() {
        return this.aA;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.aC;
    }
    
    @Override
    public void save() {
        this.b();
    }
    
    @Override
    public AttackIndicatorPosition attackIndicatorPosition() {
        return AttackIndicatorPosition.OFF;
    }
    
    @Insert(method = { "setOptionValue" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;chatVisibility:Lnet/minecraft/entity/player/EntityPlayer$EnumChatVisibility;", shift = At.Shift.AFTER))
    private void labyMod$refreshChatForChatVisibility(final avh.a option, final int $$, final InsertInfo ci) {
        Laby.fireEvent(MixinGameSettings.VISIBILITY_CHAT_SCREEN_UPDATE_EVENT);
    }
    
    @Insert(method = { "setOptionValue" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;chatColours:Z", shift = At.Shift.AFTER))
    private void labyMod$refreshChatForChatColors(final avh.a option, final int $$, final InsertInfo ci) {
        Laby.fireEvent(MixinGameSettings.COLORS_CHAT_SCREEN_UPDATE_EVENT);
    }
    
    @WrapOperation(method = { "setOptionValue" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;guiScale:I", ordinal = 1) })
    private void labyMod$setGuiScale(final avh instance, final int newValue, final Operation<Void> original, @Local(argsOnly = true) int value) {
        final int guiScale = this.aL + value;
        original.call(new Object[] { instance, 0 });
        final avr scaledResolution = new avr(ave.A());
        if (guiScale > scaledResolution.e()) {
            return;
        }
        original.call(new Object[] { instance, guiScale });
    }
    
    @Inject(method = { "getKeyBinding" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$changeKeyBinding(final avh.a option, final CallbackInfoReturnable<String> cir) {
        if (option == avh.a.n && this.aL != 0) {
            cir.setReturnValue(bnq.a(option.d(), new Object[0]) + ": " + this.aL);
        }
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return false;
    }
    
    @Override
    public int getRenderDistance() {
        return this.c;
    }
    
    @Override
    public int getActualRenderDistance() {
        return this.getRenderDistance();
    }
    
    static {
        VISIBILITY_CHAT_SCREEN_UPDATE_EVENT = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.VISIBILITY);
        COLORS_CHAT_SCREEN_UPDATE_EVENT = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.COLORS);
    }
}
