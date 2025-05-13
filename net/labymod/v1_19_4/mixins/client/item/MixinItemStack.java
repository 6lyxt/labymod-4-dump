// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.item;

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
import net.labymod.api.Laby;
import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.component.data.NbtDataComponentContainer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.ItemStack;

@Mixin({ cfv.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    @Shadow
    private int s;
    @Shadow
    private re v;
    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new NbtDataComponentContainer(() -> (NBTTagCompound)this.v);
    }
    
    @Shadow
    public abstract cfq c();
    
    @Shadow
    public abstract int j();
    
    @Shadow
    public abstract boolean L();
    
    @Shadow
    public abstract int q();
    
    @Shadow
    public abstract tj x();
    
    @Shadow
    public abstract re v();
    
    @Shadow
    public abstract re u();
    
    @Shadow
    public abstract cfv shadow$o();
    
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final bym player, final chl flag, final CallbackInfoReturnable<List<tj>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<tj> lines = (List<tj>)cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = emh.N().m.m ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.b()));
        lines.clear();
        for (final Component line : event.getTooltipLines()) {
            lines.add((tj)componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue((Object)lines);
    }
    
    @NotNull
    @Override
    public Item getAsItem() {
        return (Item)this.c();
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.j();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.q();
    }
    
    @Override
    public boolean isSword() {
        final cfq item = this.c();
        return item == cfy.nT || item == cfy.nY || item == cfy.oi || item == cfy.od || item == cfy.on || item == cfy.os;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.c() instanceof cdq;
    }
    
    @Override
    public boolean isFood() {
        return this.L();
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public boolean isFishingTool() {
        return this.c() == cfy.qd || this.c() == cfy.nb;
    }
    
    @Override
    public ResourceLocation getIdentifier() {
        return this.getAsItem().getIdentifier();
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.getAsItem().getMaximumStackSize();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.getAsItem().getMaximumDamage();
    }
    
    @Override
    public int getSize() {
        return this.s;
    }
    
    @Override
    public void setSize(final int size) {
        this.s = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.x());
    }
    
    @NotNull
    @Override
    public DataComponentContainer getDataComponentContainer() {
        final NBTTagCompound wrapped = this.labyMod$dataComponentContainer.getWrapped();
        return (wrapped == null) ? DataComponentContainer.EMPTY : this.labyMod$dataComponentContainer;
    }
    
    @NotNull
    @Override
    public DataComponentContainer getOrCreateDataComponentContainer() {
        this.v();
        return this.getDataComponentContainer();
    }
    
    @Nullable
    @Override
    public NBTTagCompound getNBTTag() {
        return (NBTTagCompound)this.u();
    }
    
    @NotNull
    @Override
    public NBTTagCompound getOrCreateNBTTag() {
        return (NBTTagCompound)this.v();
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return cfv.b((cfv)itemStack, (cfv)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$o();
    }
}
