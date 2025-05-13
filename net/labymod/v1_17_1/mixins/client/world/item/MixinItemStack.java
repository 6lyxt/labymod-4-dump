// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.world.item;

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

@Mixin({ bqq.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    @Shadow
    private int r;
    @Shadow
    private na u;
    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new NbtDataComponentContainer(() -> (NBTTagCompound)this.u);
    }
    
    @Shadow
    public abstract bqm c();
    
    @Shadow
    public abstract int h();
    
    @Shadow
    public abstract boolean J();
    
    @Shadow
    public abstract int o();
    
    @Shadow
    public abstract os v();
    
    @Shadow
    public abstract na t();
    
    @Shadow
    public abstract na s();
    
    @Shadow
    public abstract bqq shadow$m();
    
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final bke player, final bsd flag, final CallbackInfoReturnable<List<os>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<os> lines = (List<os>)cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = dvp.C().l.w ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, player != null && player.f()));
        lines.clear();
        for (final Component line : event.getTooltipLines()) {
            lines.add((os)componentMapper.toMinecraftComponent(line));
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
        return this.h();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.o();
    }
    
    @Override
    public boolean isSword() {
        final bqm item = this.c();
        return item == bqs.mx || item == bqs.mC || item == bqs.mM || item == bqs.mH || item == bqs.mR || item == bqs.mW;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.c() instanceof bou;
    }
    
    @Override
    public boolean isFood() {
        return this.J();
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public boolean isFishingTool() {
        return this.c() == bqs.or || this.c() == bqs.lR;
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
        return this.r;
    }
    
    @Override
    public void setSize(final int size) {
        this.r = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.v());
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
        this.t();
        return this.getDataComponentContainer();
    }
    
    @Nullable
    @Override
    public NBTTagCompound getNBTTag() {
        return (NBTTagCompound)this.s();
    }
    
    @NotNull
    @Override
    public NBTTagCompound getOrCreateNBTTag() {
        return (NBTTagCompound)this.t();
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return bqq.b((bqq)itemStack, (bqq)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$m();
    }
}
