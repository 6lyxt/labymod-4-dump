// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_19_3.client.network.chat.VersionedIconComponent;
import net.labymod.v1_19_3.client.component.VersionedIconContents;
import net.labymod.v1_19_3.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_19_3.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_19_3.client.network.chat.VersionedTextComponent;
import net.labymod.v1_19_3.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_19_3.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_19_3.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.network.chat.MutableComponentAccessor;

@Mixin({ tf.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, ss
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<ss> d;
    @Shadow
    @Final
    @Mutable
    private st c;
    @Shadow
    private qk g;
    
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
    public void setContents(final st contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final st contents, final List<ss> $$1, final to $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<ss>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final tf holder = (tf)this;
        if (contents instanceof uc) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final tx literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.a().isEmpty());
        }
        else if (contents instanceof tv) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof tz) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == st.a) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof ty));
        }
    }
    
    public tf e() {
        st contents = this.c;
        if (contents instanceof final tx literalContents) {
            contents = (st)new tx(literalContents.a());
        }
        else if (contents instanceof final uc translatableContents) {
            final Object[] args = translatableContents.b();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (st)new uc(translatableContents.a(), arguments);
        }
        final List<ss> siblings = new ArrayList<ss>();
        for (final ss sibling : this.d) {
            siblings.add((ss)sibling.e());
        }
        return new tf(contents, (List)siblings, this.a());
    }
}
