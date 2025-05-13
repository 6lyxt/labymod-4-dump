// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_21_1.client.network.chat.VersionedIconComponent;
import net.labymod.v1_21_1.client.component.VersionedIconContents;
import net.labymod.v1_21_1.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_1.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_21_1.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor;

@Mixin({ xn.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, wz
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<wz> d;
    @Shadow
    @Final
    @Mutable
    private xa c;
    @Shadow
    private tw g;
    
    public MixinMutableComponent() {
        this.labyMod$children = new ArrayList<Component>();
    }
    
    @Override
    public VersionedBaseComponent<? extends Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }
    
    @Override
    public List<Component> getChildren() {
        return Collections.unmodifiableList((List<? extends Component>)this.labyMod$children);
    }
    
    @Override
    public void setContents(final xa contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final xa contents, final List<wz> $$1, final xw $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<wz>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final xn holder = (xn)this;
        if (contents instanceof yk) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final yg.a literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
        }
        else if (contents instanceof yd) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof yh) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == yg.c) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof yf));
        }
    }
    
    public xn f() {
        xa contents = this.c;
        if (contents instanceof final yg.a literalContents) {
            contents = (xa)new yg.a(literalContents.b());
        }
        else if (contents instanceof final yk translatableContents) {
            final Object[] args = translatableContents.d();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (xa)new yk(translatableContents.b(), translatableContents.c(), arguments);
        }
        final List<wz> siblings = new ArrayList<wz>();
        for (final wz sibling : this.d) {
            siblings.add((wz)sibling.f());
        }
        return new xn(contents, (List)siblings, this.a());
    }
}
