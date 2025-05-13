// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.item;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Intrinsic;
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
import net.labymod.v1_21_5.client.component.data.VersionedDataComponentContainer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.ItemStack;

@Mixin({ dak.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    @Shadow
    private int q;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer;
    @Deprecated
    private NBTTagCompound labyMod$dummyTag;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new VersionedDataComponentContainer(this::a);
    }
    
    @Shadow
    public abstract dag h();
    
    @Shadow
    public abstract int o();
    
    @Shadow
    public abstract int a(final byf p0);
    
    @Shadow
    public abstract xg y();
    
    @Shadow
    public abstract dak shadow$v();
    
    @Shadow
    public abstract ki a();
    
    @Shadow
    public abstract int k();
    
    @Shadow
    public abstract int p();
    
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final dag.b context, final csi player, final dbz flag, final CallbackInfoReturnable<List<xg>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<xg> lines = new ArrayList<xg>((Collection<? extends xg>)cir.getReturnValue());
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = fqq.Q().n.m ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.b()));
        lines.clear();
        for (final Component line : event.getTooltipLines()) {
            lines.add((xg)componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue((Object)lines);
    }
    
    @NotNull
    @Override
    public Item getAsItem() {
        return (Item)this.h();
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.o();
    }
    
    @Intrinsic
    public int itemStack$getUseDuration(final LivingEntity entity) {
        return this.a((byf)entity);
    }
    
    @Override
    public boolean isSword() {
        final dag item = this.h();
        return item == dao.pA || item == dao.pF || item == dao.pP || item == dao.pK || item == dao.pU || item == dao.pZ;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.h() instanceof cys;
    }
    
    @Override
    public boolean isFood() {
        final cvy foodProperties = (cvy)this.a().a(kl.v);
        return foodProperties != null;
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public boolean isFishingTool() {
        return this.h() == dao.sd || this.h() == dao.oA;
    }
    
    @Override
    public ResourceLocation getIdentifier() {
        return this.getAsItem().getIdentifier();
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.k();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.p();
    }
    
    @Override
    public int getSize() {
        return this.q;
    }
    
    @Override
    public void setSize(final int size) {
        this.q = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.y());
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
            this.labyMod$dummyTag = (NBTTagCompound)new ua();
        }
        return this.labyMod$dummyTag;
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return dak.a((dak)itemStack, (dak)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$v();
    }
}
