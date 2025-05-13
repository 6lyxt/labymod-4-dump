// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_16_5.client.util.WatchableTranslatableComponentArgumentsList;
import org.spongepowered.asm.mixin.Intrinsic;
import java.util.Collection;
import java.util.Arrays;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.TranslatableComponent;

@Mixin({ of.class })
@Implements({ @Interface(iface = TranslatableComponent.class, prefix = "translatableComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinTranslatableComponent extends MixinBaseComponent<TranslatableComponent> implements TranslatableComponent
{
    private List<Component> labyMod$arguments;
    @Final
    @Shadow
    @Mutable
    private Object[] h;
    @Shadow
    @Nullable
    private ly i;
    
    @Shadow
    public abstract String shadow$i();
    
    @Shadow
    public abstract of shadow$h();
    
    @Shadow
    public abstract String i();
    
    @Inject(method = { "<init>(Ljava/lang/String;[Ljava/lang/Object;)V" }, at = { @At("TAIL") })
    public void labyMod$initializeWithArguments(final CallbackInfo ci) {
        this.labyMod$mapArguments();
    }
    
    @Inject(method = { "<init>(Ljava/lang/String;)V" }, at = { @At("TAIL") })
    public void labyMod$initializeWithoutArguments(final CallbackInfo ci) {
        this.labyMod$mapArguments();
    }
    
    @Override
    public List<Component> getArguments() {
        return this.labyMod$arguments;
    }
    
    @Override
    public TranslatableComponent argument(final Component argument) {
        this.labyMod$arguments.add(argument);
        return this;
    }
    
    @Override
    public TranslatableComponent arguments(final Component... arguments) {
        return this.arguments(Arrays.asList(arguments));
    }
    
    @Override
    public TranslatableComponent arguments(final Collection<Component> arguments) {
        this.labyMod$arguments.addAll(arguments);
        return this;
    }
    
    @Intrinsic
    public String translatableComponent$getKey() {
        return this.shadow$i();
    }
    
    @Intrinsic
    public TranslatableComponent translatableComponent$plainCopy() {
        return (TranslatableComponent)this.shadow$h();
    }
    
    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> this.h, arguments -> {
            this.h = arguments;
            this.i = null;
        });
    }
    
    private void labyMod$mapArguments() {
        final WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<Component>(this.labyMod$watchableArgumentsList());
        for (final Object argument : this.h) {
            if (argument instanceof nn) {
                watchableArrayList.addUnwatched((Component)argument);
            }
            else {
                watchableArrayList.addUnwatched(Component.text(String.valueOf(argument)));
            }
        }
        this.labyMod$arguments = watchableArrayList;
    }
}
