// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_20_1.client.network.chat.VersionedIconComponent;
import net.labymod.v1_20_1.client.component.VersionedIconContents;
import net.labymod.v1_20_1.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedTextComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_20_1.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_20_1.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.client.network.chat.MutableComponentAccessor;

@Mixin({ tj.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, sw
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<sw> d;
    @Shadow
    @Final
    @Mutable
    private sx c;
    @Shadow
    private qm g;
    
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
    public void setContents(final sx contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final sx contents, final List<sw> $$1, final ts $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<sw>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final tj holder = (tj)this;
        if (contents instanceof ug) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final ub literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.a().isEmpty());
        }
        else if (contents instanceof tz) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof ud) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == sx.a) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof uc));
        }
    }
    
    public tj e() {
        sx contents = this.c;
        if (contents instanceof final ub literalContents) {
            contents = (sx)new ub(literalContents.a());
        }
        else if (contents instanceof final ug translatableContents) {
            final Object[] args = translatableContents.c();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (sx)new ug(translatableContents.a(), translatableContents.b(), arguments);
        }
        final List<sw> siblings = new ArrayList<sw>();
        for (final sw sibling : this.d) {
            siblings.add((sw)sibling.e());
        }
        return new tj(contents, (List)siblings, this.a());
    }
}
