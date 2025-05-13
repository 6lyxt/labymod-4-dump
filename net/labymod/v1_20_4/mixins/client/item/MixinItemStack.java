// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.item;

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

@Mixin({ cmy.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    @Shadow
    private int x;
    @Shadow
    private sn A;
    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new NbtDataComponentContainer(() -> (NBTTagCompound)this.A);
    }
    
    @Shadow
    public abstract cmt d();
    
    @Shadow
    public abstract int k();
    
    @Shadow
    public abstract boolean M();
    
    @Shadow
    public abstract int r();
    
    @Shadow
    public abstract vf y();
    
    @Shadow
    public abstract sn w();
    
    @Shadow
    public abstract sn v();
    
    @Shadow
    public abstract cmy shadow$p();
    
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final cfi player, final coq flag, final CallbackInfoReturnable<List<vf>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<vf> lines = (List<vf>)cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = evi.O().m.m ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.b()));
        lines.clear();
        for (final Component line : event.getTooltipLines()) {
            lines.add((vf)componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue((Object)lines);
    }
    
    @NotNull
    @Override
    public Item getAsItem() {
        return (Item)this.d();
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.k();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.r();
    }
    
    @Override
    public boolean isSword() {
        final cmt item = this.d();
        return item == cnb.oI || item == cnb.oN || item == cnb.oX || item == cnb.oS || item == cnb.pc || item == cnb.ph;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.d() instanceof ckr;
    }
    
    @Override
    public boolean isFood() {
        return this.M();
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public boolean isFishingTool() {
        return this.d() == cnb.qS || this.d() == cnb.nQ;
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
        return this.x;
    }
    
    @Override
    public void setSize(final int size) {
        this.x = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.y());
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
        this.w();
        return this.getDataComponentContainer();
    }
    
    @Nullable
    @Override
    public NBTTagCompound getNBTTag() {
        return (NBTTagCompound)this.v();
    }
    
    @NotNull
    @Override
    public NBTTagCompound getOrCreateNBTTag() {
        return (NBTTagCompound)this.w();
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return cmy.a((cmy)itemStack, (cmy)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$p();
    }
}
