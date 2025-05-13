// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_20_2.client.network.chat.VersionedIconComponent;
import net.labymod.v1_20_2.client.component.VersionedIconContents;
import net.labymod.v1_20_2.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_20_2.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_20_2.client.network.chat.VersionedTextComponent;
import net.labymod.v1_20_2.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_20_2.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_20_2.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.client.network.chat.MutableComponentAccessor;

@Mixin({ ty.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, tl
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<tl> d;
    @Shadow
    @Final
    @Mutable
    private tm c;
    @Shadow
    private qr g;
    
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
    public void setContents(final tm contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final tm contents, final List<tl> $$1, final uh $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<tl>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final ty holder = (ty)this;
        if (contents instanceof uv) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final uq literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.a().isEmpty());
        }
        else if (contents instanceof uo) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof us) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == tm.a) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof ur));
        }
    }
    
    public ty e() {
        tm contents = this.c;
        if (contents instanceof final uq literalContents) {
            contents = (tm)new uq(literalContents.a());
        }
        else if (contents instanceof final uv translatableContents) {
            final Object[] args = translatableContents.c();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (tm)new uv(translatableContents.a(), translatableContents.b(), arguments);
        }
        final List<tl> siblings = new ArrayList<tl>();
        for (final tl sibling : this.d) {
            siblings.add((tl)sibling.e());
        }
        return new ty(contents, (List)siblings, this.a());
    }
}
