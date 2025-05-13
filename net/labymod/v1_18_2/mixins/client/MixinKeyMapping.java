// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client;

import java.util.Iterator;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.options.InputMappingResolver;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Supplier;
import java.util.Set;
import org.spongepowered.asm.mixin.Final;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.client.options.MinecraftInputMapping;

@Implements({ @Interface(iface = MinecraftInputMapping.class, prefix = "input$", remap = Interface.Remap.NONE) })
@Mixin({ dyo.class })
public abstract class MixinKeyMapping implements MinecraftInputMapping
{
    @Shadow
    private dsh.a o;
    @Shadow
    @Final
    private static Map<String, dyo> h;
    @Shadow
    private boolean p;
    @Shadow
    private int q;
    @Shadow
    @Final
    private static Set<String> j;
    @Shadow
    @Final
    private static Map<String, Integer> k;
    private boolean labyMod$hidden;
    private Supplier<Widget> labyMod$replacement;
    
    @Shadow
    public abstract boolean d();
    
    @Shadow
    public abstract String g();
    
    @Shadow
    public abstract String e();
    
    @Shadow
    protected abstract void m();
    
    @Shadow
    public abstract void a(final boolean p0);
    
    @Intrinsic
    public boolean input$isDown() {
        return this.d();
    }
    
    @Intrinsic
    public String input$getName() {
        return this.g();
    }
    
    @Intrinsic
    public String input$getCategory() {
        return this.e();
    }
    
    @Inject(method = { "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V" }, at = { @At("RETURN") })
    private void labyMod$fireRegisterKeybindingEvent(final String name, final dsh.b type, final int key, final String category, final CallbackInfo ci) {
        final RegisterKeybindingEvent event = Laby.fireEvent(new RegisterKeybindingEvent(name, category));
        if (event.isCancelled()) {
            this.labyMod$hidden = true;
        }
        else if (event.getReplacement() != null) {
            this.labyMod$replacement = event.getReplacement();
        }
    }
    
    @Inject(method = { "isDown" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$disableKeybind(final CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced(this)) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Redirect(method = { "resetMapping" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object labyMod$disableKeybind(final Map instance, final Object k, final Object v) {
        if (!MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping)v)) {
            return instance.put(k, v);
        }
        return null;
    }
    
    @Inject(method = { "same" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$same(final dyo mapping, final CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping)mapping)) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "<clinit>" }, at = { @At("TAIL") })
    private static void labyMod$createKeySupplier(final CallbackInfo ci) {
        InputMappingResolver.setResolver(name -> (MinecraftInputMapping)MixinKeyMapping.h.get(name));
    }
    
    @Override
    public int getKeyCode() {
        return this.o.b();
    }
    
    @NotNull
    @Override
    public Key key() {
        final dsh.b type = this.o.a();
        Key key;
        if (type == dsh.b.c) {
            key = KeyMapper.getMouseButton(this.getKeyCode());
        }
        else {
            key = KeyMapper.getKey(this.getKeyCode());
        }
        return (key == null) ? Key.NONE : key;
    }
    
    @Override
    public boolean isMouse() {
        return this.o.a() == dsh.b.c;
    }
    
    @Override
    public void unpress() {
        this.m();
    }
    
    @Override
    public void press() {
        this.a(true);
    }
    
    @Override
    public boolean isActuallyDown() {
        final dsh.b type = this.o.a();
        if (type == dsh.b.c) {
            return DefaultKeyMapper.isMousePressed(this.getKeyCode());
        }
        return DefaultKeyMapper.isKeyPressed(this.getKeyCode());
    }
    
    @Override
    public boolean isHidden() {
        return this.labyMod$hidden;
    }
    
    @Override
    public Widget getReplacement() {
        return (this.labyMod$replacement != null) ? this.labyMod$replacement.get() : null;
    }
    
    @Override
    public boolean hasReplacement() {
        return this.labyMod$replacement != null;
    }
    
    @Override
    public void addCategory(final String category) {
        if (MixinKeyMapping.k.containsKey(category)) {
            return;
        }
        int lastSortOrder = 0;
        for (final Integer sortOrder : MixinKeyMapping.k.values()) {
            if (lastSortOrder < sortOrder) {
                lastSortOrder = sortOrder;
            }
        }
        MixinKeyMapping.k.put(category, ++lastSortOrder);
    }
    
    public void updateDown(final boolean down) {
        this.p = down;
    }
    
    public void resetClickCount() {
        this.q = 0;
    }
}
