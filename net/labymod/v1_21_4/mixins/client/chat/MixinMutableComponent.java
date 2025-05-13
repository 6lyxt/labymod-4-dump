// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_21_4.client.network.chat.VersionedIconComponent;
import net.labymod.v1_21_4.client.component.VersionedIconContents;
import net.labymod.v1_21_4.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_4.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_21_4.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.network.chat.MutableComponentAccessor;

@Mixin({ xd.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, wp
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<wp> d;
    @Shadow
    @Final
    @Mutable
    private wq c;
    @Shadow
    private tl g;
    
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
    public void setContents(final wq contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final wq contents, final List<wp> $$1, final xm $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<wp>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final xd holder = (xd)this;
        if (contents instanceof ya) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final xw.a literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
        }
        else if (contents instanceof xt) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof xx) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == xw.c) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof xv));
        }
    }
    
    public xd f() {
        wq contents = this.c;
        if (contents instanceof final xw.a literalContents) {
            contents = (wq)new xw.a(literalContents.b());
        }
        else if (contents instanceof final ya translatableContents) {
            final Object[] args = translatableContents.d();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (wq)new ya(translatableContents.b(), translatableContents.c(), arguments);
        }
        final List<wp> siblings = new ArrayList<wp>();
        for (final wp sibling : this.d) {
            siblings.add((wp)sibling.f());
        }
        return new xd(contents, (List)siblings, this.a());
    }
}
