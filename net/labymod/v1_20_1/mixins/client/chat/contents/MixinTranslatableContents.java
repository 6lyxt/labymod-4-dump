// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.chat.contents;

import net.labymod.v1_20_1.client.network.chat.MutableComponentAccessor;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_20_1.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_20_1.client.util.WatchableTranslatableComponentArgumentsList;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.client.network.chat.contents.TranslatableContentsAccessor;

@Mixin({ ug.class })
@Implements({ @Interface(iface = TranslatableContentsAccessor.class, prefix = "translatableAccessor$", remap = Interface.Remap.NONE) })
public abstract class MixinTranslatableContents implements TranslatableContentsAccessor
{
    private List<Component> labyMod$arguments;
    @Mutable
    @Shadow
    @Final
    private Object[] g;
    @Shadow
    @Nullable
    private qm h;
    
    @Shadow
    public abstract String shadow$a();
    
    @Shadow
    public abstract String shadow$b();
    
    @Inject(method = { "<init>(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V" }, at = { @At("TAIL") })
    public void labyMod$initializeWithArguments(final CallbackInfo ci) {
        this.labyMod$mapArguments();
    }
    
    @Override
    public List<Component> getLabyArguments() {
        return this.labyMod$arguments;
    }
    
    @Intrinsic
    public String translatableAccessor$getKey() {
        return this.shadow$a();
    }
    
    @Intrinsic
    public String translatableAccessor$getFallback() {
        return this.shadow$b();
    }
    
    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> this.g, arguments -> {
            this.g = arguments;
            this.labyMod$cleanupArguments();
            this.h = null;
        });
    }
    
    private void labyMod$cleanupArguments() {
        for (int i = 0; i < this.g.length; ++i) {
            final Object argument = this.g[i];
            if (argument instanceof VersionedBaseComponent) {
                final VersionedBaseComponent<?, ?> component = (VersionedBaseComponent<?, ?>)argument;
                this.g[i] = component.getHolder();
            }
        }
    }
    
    private void labyMod$mapArguments() {
        if (this.g instanceof Component[]) {
            final Object[] args = new Object[this.g.length];
            if (this.g.length != 0) {
                System.arraycopy(this.g, 0, args, 0, this.g.length);
            }
            this.g = args;
        }
        final WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<Component>(this.labyMod$watchableArgumentsList());
        for (final Object argument : this.g) {
            if (argument instanceof final MutableComponentAccessor accessor) {
                watchableArrayList.addUnwatched(accessor.getLabyComponent());
            }
            else if (argument instanceof final Component component) {
                watchableArrayList.addUnwatched(component);
            }
            else if (argument instanceof final String string) {
                watchableArrayList.addUnwatched(Component.text(string));
            }
            else {
                watchableArrayList.addUnwatched(Component.text(String.valueOf(argument)));
            }
        }
        this.labyMod$cleanupArguments();
        this.labyMod$arguments = watchableArrayList;
    }
}
