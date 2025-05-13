// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.world.item;

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

@Mixin({ bmb.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    @Shadow
    private int f;
    @Shadow
    private md i;
    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new NbtDataComponentContainer(() -> (NBTTagCompound)this.i);
    }
    
    @Shadow
    public abstract blx b();
    
    @Shadow
    public abstract int g();
    
    @Shadow
    public abstract boolean F();
    
    @Shadow
    public abstract int k();
    
    @Shadow
    public abstract nr r();
    
    @Shadow
    public abstract md p();
    
    @Shadow
    public abstract md o();
    
    @Shadow
    public abstract bmb shadow$i();
    
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final bfw player, final bnl flag, final CallbackInfoReturnable<List<nr>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<nr> lines = (List<nr>)cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = djz.C().k.p ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, player != null && player.b_()));
        lines.clear();
        for (final Component line : event.getTooltipLines()) {
            lines.add((nr)componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue((Object)lines);
    }
    
    @NotNull
    @Override
    public Item getAsItem() {
        return (Item)this.b();
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.g();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.k();
    }
    
    @Override
    public boolean isSword() {
        final blx item = this.b();
        return item == bmd.kl || item == bmd.kq || item == bmd.kA || item == bmd.kv || item == bmd.kF || item == bmd.kK;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.b() instanceof bkh;
    }
    
    @Override
    public boolean isFood() {
        return this.F();
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public boolean isFishingTool() {
        return this.b() == bmd.mi || this.b() == bmd.pk;
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
        return this.f;
    }
    
    @Override
    public void setSize(final int size) {
        this.f = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.r());
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
        this.p();
        return this.getDataComponentContainer();
    }
    
    @Nullable
    @Override
    public NBTTagCompound getNBTTag() {
        return (NBTTagCompound)this.o();
    }
    
    @NotNull
    @Override
    public NBTTagCompound getOrCreateNBTTag() {
        return (NBTTagCompound)this.p();
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return bmb.b((bmb)itemStack, (bmb)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$i();
    }
}
