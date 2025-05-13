// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.chat;

import java.util.Iterator;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_19_4.client.network.chat.VersionedIconComponent;
import net.labymod.v1_19_4.client.component.VersionedIconContents;
import net.labymod.v1_19_4.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_19_4.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_19_4.client.network.chat.VersionedTextComponent;
import net.labymod.v1_19_4.client.network.chat.VersionedTranslatableComponent;
import java.util.Collection;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_19_4.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_19_4.client.network.chat.VersionedBaseComponent;
import net.labymod.api.client.component.Component;
import java.util.List;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_4.client.network.chat.MutableComponentAccessor;

@Mixin({ tw.class })
@Implements({ @Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE) })
public abstract class MixinMutableComponent implements MutableComponentAccessor, tj
{
    private final List<Component> labyMod$children;
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;
    @Shadow
    @Mutable
    @Final
    private List<tj> d;
    @Shadow
    @Final
    @Mutable
    private tk c;
    @Shadow
    private qz g;
    
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
    public void setContents(final tk contents) {
        this.c = contents;
        this.g = null;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    public void labyMod$createLabyComponent(final tk contents, final List<tj> $$1, final uf $$2, final CallbackInfo ci) {
        (this.d = new WatchableArrayList<tj>(new WatchableComponentSiblingList(this.labyMod$children))).addAll($$1);
        final tw holder = (tw)this;
        if (contents instanceof ut) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
        }
        else if (contents instanceof final uo literalContents) {
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.a().isEmpty());
        }
        else if (contents instanceof um) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
        }
        else if (contents instanceof uq) {
            this.labyMod$component = new VersionedScoreComponent(holder);
        }
        else if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        }
        else if (contents == tk.a) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        }
        else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof up));
        }
    }
    
    public tw e() {
        tk contents = this.c;
        if (contents instanceof final uo literalContents) {
            contents = (tk)new uo(literalContents.a());
        }
        else if (contents instanceof final ut translatableContents) {
            final Object[] args = translatableContents.c();
            final Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            contents = (tk)new ut(translatableContents.a(), translatableContents.b(), arguments);
        }
        final List<tj> siblings = new ArrayList<tj>();
        for (final tj sibling : this.d) {
            siblings.add((tj)sibling.e());
        }
        return new tw(contents, (List)siblings, this.a());
    }
}
