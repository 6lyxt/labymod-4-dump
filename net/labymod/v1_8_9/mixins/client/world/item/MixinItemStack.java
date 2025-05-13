// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.world.item;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.component.data.DataComponentContainer;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import net.labymod.v1_8_9.client.world.item.VersionedAirItem;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.font.ComponentMapper;
import java.util.Iterator;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.Component;
import java.util.ArrayList;
import net.labymod.api.event.Event;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;
import net.labymod.api.Laby;
import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.component.data.NbtDataComponentContainer;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.ItemStack;

@Mixin({ zx.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    private ResourceLocation labyMod$defaultAirLocation;
    private int labyMod$lastItemSlot;
    @Shadow
    private zw d;
    @Shadow
    public int c;
    @Shadow
    public int b;
    @Shadow
    private int f;
    @Shadow
    private dn e;
    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new NbtDataComponentContainer(() -> (NBTTagCompound)this.e);
    }
    
    @Shadow
    public abstract zw b();
    
    @Shadow
    public abstract String shadow$q();
    
    @Shadow
    public abstract int h();
    
    @Shadow
    public abstract aba m();
    
    @Shadow
    protected abstract boolean d(final zx p0);
    
    @Shadow
    public abstract dn o();
    
    @Shadow
    public abstract void d(final dn p0);
    
    @Shadow
    public abstract zx shadow$k();
    
    @Inject(method = { "getTooltip" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final wn player, final boolean advanced, final CallbackInfoReturnable<List<String>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<String> lines = (List<String>)cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = ave.A().t.y ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final LegacyComponentSerializer serializer = Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer();
        final List<Component> mappedLines = new ArrayList<Component>(lines.size());
        for (final String line : lines) {
            mappedLines.add(serializer.deserialize(line));
        }
        boolean b = false;
        Label_0177: {
            if (player instanceof final Player apiPlayer) {
                if (apiPlayer.gameMode() == GameMode.CREATIVE) {
                    b = true;
                    break Label_0177;
                }
            }
            b = false;
        }
        final boolean creative = b;
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, creative));
        lines.clear();
        final ComponentMapper componentMapper = Laby.references().componentMapper();
        for (final Component line2 : event.getTooltipLines()) {
            final eu mappedMinecraftComponent = (eu)componentMapper.toMinecraftComponent(line2);
            lines.add(mappedMinecraftComponent.d());
        }
        cir.setReturnValue((Object)lines);
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
    
    @NotNull
    @Override
    public Item getAsItem() {
        final Item item = (Item)this.b();
        return (item != null) ? item : VersionedAirItem.AIR;
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.h();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.c;
    }
    
    @Override
    public boolean isSword() {
        return this.d == zy.m || this.d == zy.q || this.d == zy.l || this.d == zy.B || this.d == zy.u;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.d instanceof yo;
    }
    
    @Override
    public boolean isFood() {
        final aba action = this.m();
        return action == aba.b || action == aba.c;
    }
    
    @Override
    public boolean isFishingTool() {
        return this.d == zy.aR || this.d == zy.bY;
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public int getSize() {
        return this.b;
    }
    
    @Override
    public void setSize(final int size) {
        this.b = size;
    }
    
    @Redirect(method = { "getIsItemStackEqual" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemStackEqual(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean labyMod$fixEqualsCheck(final zx instance, final zx other) {
        return this.labyMod$equals(this.d(other), (zx)this, other);
    }
    
    private boolean labyMod$equals(final boolean legacyResult, final zx thisStack, final zx otherStack) {
        if (!Laby.labyAPI().config().multiplayer().classicPvP().oldEquip().enabled().get()) {
            return legacyResult;
        }
        final boolean equalsStackSize = thisStack.b == otherStack.b;
        final boolean equalsItem = thisStack.b() == otherStack.b();
        final boolean equalsNBT = thisStack.o() != null || otherStack.o() == null;
        final boolean equalsNBTCompound = thisStack.o() == null || thisStack.o().equals((Object)otherStack.o());
        final boolean equals = equalsStackSize && equalsItem && equalsNBT && equalsNBTCompound;
        final boolean isBowItem = otherStack.b() instanceof yt || thisStack.b() instanceof yt;
        final boolean isFlintAndSteelItem = otherStack.b() instanceof zr || thisStack.b() instanceof zr;
        boolean result = (isBowItem || isFlintAndSteelItem) ? (equals || legacyResult) : legacyResult;
        if (ave.A().h != null) {
            final int slot = ave.A().h.bi.c;
            if (slot != this.labyMod$lastItemSlot) {
                result = false;
            }
            if (thisStack.equals(otherStack)) {
                this.labyMod$lastItemSlot = slot;
            }
        }
        return result;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer().deserialize(this.shadow$q());
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
        this.getOrCreateNBTTag();
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
        dn tag = this.o();
        if (tag == null) {
            this.d(tag = new dn());
        }
        return (NBTTagCompound)tag;
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return zx.b((zx)itemStack, (zx)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$k();
    }
    
    @Override
    public int getLegacyItemData() {
        return this.f;
    }
    
    @Override
    public void setLegacyItemData(final int legacyData) {
        this.f = legacyData;
    }
}
