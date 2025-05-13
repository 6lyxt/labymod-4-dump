// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_19_2.client.network.chat.VersionedIconComponent;
import net.labymod.v1_19_2.client.component.VersionedIconContents;
import net.labymod.v1_19_2.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_19_2.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_19_2.client.network.chat.VersionedTextComponent;
import net.labymod.v1_19_2.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_19_2.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_19_2.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.client.network.chat.MutableComponentAccessor;

@Mixin({ sb.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, rq
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<rq> d;
    @Shadow
    @Final
    @Mutable
    private rr c;
    @Shadow
    private pe g;
    
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
    public void setContents(final rr contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final rr contents, final List<rq> $$1, final sj $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<rq>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final sb holder = (sb)this;
        if (contents instanceof sx) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final ss literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.a().isEmpty());
        }
        else if (contents instanceof sq) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof su) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == rr.a) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof st));
        }
    }
    
    public sb e() {
        rr contents = this.c;
        if (contents instanceof final ss literalContents) {
            contents = (rr)new ss(literalContents.a());
        }
        else if (contents instanceof final sx translatableContents) {
            final Object[] args = translatableContents.b();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (rr)new sx(translatableContents.a(), arguments);
        }
        final List<rq> siblings = new ArrayList<rq>();
        for (final rq sibling : this.d) {
            siblings.add((rq)sibling.e());
        }
        return new sb(contents, (List)siblings, this.a());
    }
}
