// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_21_3.client.network.chat.VersionedIconComponent;
import net.labymod.v1_21_3.client.component.VersionedIconContents;
import net.labymod.v1_21_3.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_21_3.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_21_3.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_3.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_3.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_21_3.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.network.chat.MutableComponentAccessor;

@Mixin({ yj.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, xv
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<xv> d;
    @Shadow
    @Final
    @Mutable
    private xw c;
    @Shadow
    private us g;
    
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
    public void setContents(final xw contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final xw contents, final List<xv> $$1, final ys $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<xv>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final yj holder = (yj)this;
        if (contents instanceof zg) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final zc.a literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
        }
        else if (contents instanceof yz) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof zd) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == zc.c) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof zb));
        }
    }
    
    public yj f() {
        xw contents = this.c;
        if (contents instanceof final zc.a literalContents) {
            contents = (xw)new zc.a(literalContents.b());
        }
        else if (contents instanceof final zg translatableContents) {
            final Object[] args = translatableContents.d();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (xw)new zg(translatableContents.b(), translatableContents.c(), arguments);
        }
        final List<xv> siblings = new ArrayList<xv>();
        for (final xv sibling : this.d) {
            siblings.add((xv)sibling.f());
        }
        return new yj(contents, (List)siblings, this.a());
    }
}
