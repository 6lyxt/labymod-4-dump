// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.options.AttackIndicatorPosition;
import net.labymod.core.client.options.InputMappingResolver;
import net.labymod.api.client.options.MinecraftInputMapping;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import java.io.File;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.options.MinecraftOptions;

@Mixin({ bid.class })
public abstract class MixinGameSettings implements MinecraftOptions
{
    private static final ChatScreenUpdateEvent VISIBILITY_CHAT_SCREEN_UPDATE_EVENT;
    private static final ChatScreenUpdateEvent COLORS_CHAT_SCREEN_UPDATE_EVENT;
    @Shadow
    public int aw;
    @Shadow
    public String aJ;
    @Shadow
    public aed.b o;
    @Shadow
    public float G;
    @Shadow
    public float H;
    @Shadow
    public float I;
    @Shadow
    public float J;
    @Shadow
    public float s;
    @Shadow
    public boolean p;
    @Shadow
    public boolean q;
    @Shadow
    public boolean r;
    @Shadow
    public boolean aB;
    @Shadow
    public Set<aee> aT;
    @Shadow
    public String aA;
    @Shadow
    public int i;
    @Shadow
    public bhy ae;
    @Shadow
    public bhy ad;
    @Shadow
    public boolean u;
    @Shadow
    public boolean f;
    @Shadow
    public float aD;
    @Shadow
    public boolean av;
    @Shadow
    public bhy Y;
    @Shadow
    public bhy Z;
    @Shadow
    @Final
    private File aV;
    @Shadow
    public boolean ax;
    @Shadow
    public vo C;
    @Shadow
    public int e;
    @Shadow
    public int aG;
    
    @Shadow
    public abstract void c();
    
    @Shadow
    public abstract void b();
    
    @Inject(method = { "loadOptions" }, at = { @At("HEAD") })
    private void labyMod$loadOptions(final CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.aV);
    }
    
    @Inject(method = { "loadOptions" }, at = { @At("RETURN") })
    private void labyMod$loadOptionsReturn(final CallbackInfo ci) {
        this.e = OptionsUtil.getRenderDistance(this.e);
    }
    
    @Inject(method = { "saveOptions" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$saveOptionsPre(final CallbackInfo ci) {
        final VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.aV));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "saveOptions" }, at = { @At("TAIL") })
    private void labyMod$saveOptionsPost(final CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.aV));
    }
    
    @Redirect(method = { "saveOptions" }, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(final Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }
    
    @Override
    public int getFrameLimit() {
        return this.i;
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
        if (this.aw == 0) {
            return Perspective.FIRST_PERSON;
        }
        if (this.aw == 1) {
            return Perspective.THIRD_PERSON_BACK;
        }
        if (this.aw == 2) {
            return Perspective.THIRD_PERSON_FRONT;
        }
        throw new RuntimeException("No perspective set");
    }
    
    @Override
    public void setModelParts(final int value) {
        for (final aee part : aee.values()) {
            if ((value & part.a()) == part.a()) {
                this.aT.add(part);
            }
            else {
                this.aT.remove(part);
            }
        }
    }
    
    @Override
    public int getModelParts() {
        int mask = 0;
        for (final aee part : this.aT) {
            mask |= part.a();
        }
        return mask;
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
        return this.aJ.toLowerCase(Locale.US);
    }
    
    @Override
    public String getLastKnownServer() {
        return this.aA;
    }
    
    @Override
    public void setLastKnownServer(final String address) {
        this.aA = address;
    }
    
    @Override
    public ChatVisibility chatVisibility() {
        switch (this.o) {
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
        return (this.C == vo.a) ? MainHand.LEFT : MainHand.RIGHT;
    }
    
    @Override
    public void setMainHand(final MainHand hand) {
        this.C = ((hand == MainHand.LEFT) ? vo.a : vo.b);
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
        return this.s * 0.9f + 0.1f;
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
        return this.p;
    }
    
    @Override
    public boolean isChatLinksEnabled() {
        return this.q;
    }
    
    @Override
    public boolean isChatLinkConfirmationEnabled() {
        return this.r;
    }
    
    @Override
    public boolean isSmoothCamera() {
        return this.aB;
    }
    
    @Override
    public void setSmoothCamera(final boolean smooth) {
        this.aB = smooth;
    }
    
    @Override
    public MinecraftInputMapping getInputMapping(final String name) {
        return InputMappingResolver.resolve(name);
    }
    
    @Override
    public MinecraftInputMapping useItemInput() {
        return (MinecraftInputMapping)this.ad;
    }
    
    @Override
    public MinecraftInputMapping attackInput() {
        return (MinecraftInputMapping)this.ae;
    }
    
    @Override
    public MinecraftInputMapping sneakInput() {
        return (MinecraftInputMapping)this.Y;
    }
    
    @Override
    public MinecraftInputMapping sprintInput() {
        return (MinecraftInputMapping)this.Z;
    }
    
    @Override
    public boolean isFullscreen() {
        return this.u;
    }
    
    @Override
    public void setFullscreen(final boolean fullscreen) {
        this.u = fullscreen;
    }
    
    @Override
    public void setPerspective(final Perspective perspective) {
        this.aw = perspective.ordinal();
    }
    
    @Override
    public boolean isBobbing() {
        return this.f;
    }
    
    @Override
    public void setBobbing(final boolean bobbing) {
        this.f = bobbing;
    }
    
    @Override
    public double getFov() {
        return this.aD;
    }
    
    @Override
    public boolean isHideGUI() {
        return this.av;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.ax;
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
    private void labyMod$refreshChatForChatVisibility(final bid.a option, final int $$, final InsertInfo ci) {
        Laby.fireEvent(MixinGameSettings.VISIBILITY_CHAT_SCREEN_UPDATE_EVENT);
    }
    
    @Insert(method = { "setOptionValue" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;chatColours:Z", shift = At.Shift.AFTER))
    private void labyMod$refreshChatForChatColors(final bid.a option, final int $$, final InsertInfo ci) {
        Laby.fireEvent(MixinGameSettings.COLORS_CHAT_SCREEN_UPDATE_EVENT);
    }
    
    @Inject(method = { "isKeyDown" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$checkForHiddenKeys(final bhy key, final CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping)key)) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @WrapOperation(method = { "setOptionValue" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;guiScale:I", ordinal = 1) })
    private void labyMod$setGuiScale(final bid instance, final int newValue, final Operation<Void> original, @Local(argsOnly = true) int value) {
        final int guiScale = this.aG + value;
        original.call(new Object[] { instance, 0 });
        final bit scaledResolution = new bit(bib.z());
        if (guiScale > scaledResolution.e()) {
            return;
        }
        original.call(new Object[] { instance, guiScale });
    }
    
    @Inject(method = { "getKeyBinding" }, at = { @At("HEAD") }, cancellable = true)
    public void labyMod$changeKeyBinding(final bid.a option, final CallbackInfoReturnable<String> cir) {
        if (option == bid.a.n && this.aG != 0) {
            cir.setReturnValue(cey.a(option.d(), new Object[0]) + ": " + this.aG);
        }
    }
    
    @Override
    public boolean isBackgroundForChatOnly() {
        return false;
    }
    
    @Override
    public int getRenderDistance() {
        return this.e;
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
