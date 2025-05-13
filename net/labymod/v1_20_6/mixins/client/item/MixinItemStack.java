// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.item;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Event;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;
import java.util.ArrayList;
import java.util.Collection;
import net.labymod.api.Laby;
import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.v1_20_6.client.component.data.VersionedDataComponentContainer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.ItemStack;

@Mixin({ cur.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    @Shadow
    private int o;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer;
    @Deprecated
    private NBTTagCompound labyMod$dummyTag;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new VersionedDataComponentContainer(this::a);
    }
    
    @Shadow
    public abstract cum g();
    
    @Shadow
    public abstract int n();
    
    @Shadow
    public abstract int u();
    
    @Shadow
    public abstract xp x();
    
    @Shadow
    public abstract cur shadow$s();
    
    @Shadow
    public abstract ki a();
    
    @Shadow
    public abstract int j();
    
    @Shadow
    public abstract int o();
    
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final cum.b context, final cmz player, final cwk flag, final CallbackInfoReturnable<List<xp>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<xp> lines = new ArrayList<xp>((Collection<? extends xp>)cir.getReturnValue());
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = ffh.Q().m.m ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.b()));
        lines.clear();
        for (final Component line : event.getTooltipLines()) {
            lines.add((xp)componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue((Object)lines);
    }
    
    @NotNull
    @Override
    public Item getAsItem() {
        return (Item)this.g();
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.n();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.u();
    }
    
    @Override
    public boolean isSword() {
        final cum item = this.g();
        return item == cuu.oL || item == cuu.oQ || item == cuu.pa || item == cuu.oV || item == cuu.pf || item == cuu.pk;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.g() instanceof csp;
    }
    
    @Override
    public boolean isFood() {
        final cpt foodProperties = (cpt)this.a().a(km.v);
        return foodProperties != null;
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public boolean isFishingTool() {
        return this.g() == cuu.qV || this.g() == cuu.nR;
    }
    
    @Override
    public ResourceLocation getIdentifier() {
        return this.getAsItem().getIdentifier();
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.j();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.o();
    }
    
    @Override
    public int getSize() {
        return this.o;
    }
    
    @Override
    public void setSize(final int size) {
        this.o = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.x());
    }
    
    @NotNull
    @Override
    public DataComponentContainer getDataComponentContainer() {
        final VersionedDataComponentContainer container = this.labyMod$dataComponentContainer;
        final ki wrapped = container.getWrapped();
        if (wrapped == ki.a) {
            return DataComponentContainer.EMPTY;
        }
        return container;
    }
    
    @NotNull
    @Override
    public DataComponentContainer getOrCreateDataComponentContainer() {
        return this.getDataComponentContainer();
    }
    
    @Nullable
    @Override
    public NBTTagCompound getNBTTag() {
        return null;
    }
    
    @NotNull
    @Override
    public NBTTagCompound getOrCreateNBTTag() {
        if (this.labyMod$dummyTag == null) {
            this.labyMod$dummyTag = (NBTTagCompound)new us();
        }
        return this.labyMod$dummyTag;
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return cur.a((cur)itemStack, (cur)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$s();
    }
}
