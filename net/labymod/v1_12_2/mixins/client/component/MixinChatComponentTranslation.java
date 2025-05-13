// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

import net.labymod.v1_12_2.client.util.WatchableTranslatableComponentArgumentsList;
import org.spongepowered.asm.mixin.Intrinsic;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.TranslatableComponent;

@Mixin({ hp.class })
@Implements({ @Interface(iface = TranslatableComponent.class, prefix = "translatableComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinChatComponentTranslation extends MixinChatComponentStyle<TranslatableComponent> implements TranslatableComponent
{
    private List<Component> labyMod$arguments;
    @Mutable
    @Shadow
    @Final
    private Object[] e;
    @Shadow
    private long g;
    @Shadow
    @Final
    private String d;
    
    @Shadow
    public abstract String shadow$i();
    
    @Inject(method = { "<init>(Ljava/lang/String;[Ljava/lang/Object;)V" }, at = { @At("TAIL") })
    public void labyMod$initializeWithArguments(final CallbackInfo ci) {
        final WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<Component>(this.labyMod$watchableArgumentsList());
        for (final Object argument : this.e) {
            if (argument instanceof he) {
                watchableArrayList.addUnwatched((Component)argument);
            }
            else {
                watchableArrayList.addUnwatched(Component.text(String.valueOf(argument)));
            }
        }
        this.labyMod$arguments = watchableArrayList;
    }
    
    @Override
    public TranslatableComponent plainCopy() {
        return (TranslatableComponent)new hp(this.d, this.e);
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
        return this.arguments(CollectionHelper.asUnmodifiableList(arguments));
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
    
    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> this.e, arguments -> {
            this.e = arguments;
            this.g = 0L;
        });
    }
}
