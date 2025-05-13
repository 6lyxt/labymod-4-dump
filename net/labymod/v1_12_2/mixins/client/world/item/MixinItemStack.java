// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world.item;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.client.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.render.font.ComponentMapper;
import java.util.Iterator;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.event.EventBus;
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

@Mixin({ aip.class })
@Implements({ @Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE) })
public abstract class MixinItemStack implements ItemStack
{
    private ResourceLocation labyMod$defaultAirLocation;
    private int labyMod$lastItemSlot;
    @Shadow
    private ain e;
    @Shadow
    public int d;
    @Shadow
    public int c;
    @Shadow
    private int h;
    @Shadow
    private fy f;
    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer;
    
    public MixinItemStack() {
        this.labyMod$dataComponentContainer = new NbtDataComponentContainer(() -> (NBTTagCompound)this.f);
    }
    
    @Shadow
    public abstract ain c();
    
    @Shadow
    public abstract String shadow$r();
    
    @Shadow
    public abstract int i();
    
    @Shadow
    public abstract aip shadow$l();
    
    @Shadow
    public abstract akc n();
    
    @Shadow
    protected abstract boolean d(final aip p0);
    
    @Shadow
    public abstract fy p();
    
    @Shadow
    public abstract void b(final fy p0);
    
    @Inject(method = { "getTooltip" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$fireTooltipEvent(final aed player, final akb flag, final CallbackInfoReturnable<List<String>> cir) {
        final EventBus eventBus = Laby.labyAPI().eventBus();
        final List<String> lines = (List<String>)cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue((Object)lines);
            return;
        }
        final ItemStackTooltipEvent.TooltipType type = bib.z().t.z ? ItemStackTooltipEvent.TooltipType.ADVANCED : ItemStackTooltipEvent.TooltipType.NORMAL;
        final LegacyComponentSerializer serializer = Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer();
        final List<Component> mappedLines = new ArrayList<Component>(lines.size());
        for (final String line : lines) {
            mappedLines.add(serializer.deserialize(line));
        }
        final ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, player != null && player.z()));
        lines.clear();
        final ComponentMapper componentMapper = Laby.references().componentMapper();
        for (final Component line2 : event.getTooltipLines()) {
            final hh mappedMinecraftComponent = (hh)componentMapper.toMinecraftComponent(line2);
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
        return (Item)this.c();
    }
    
    @Override
    public int getCurrentDamageValue() {
        return this.i();
    }
    
    @Override
    public int getUseDuration(final LivingEntity entity) {
        return this.d;
    }
    
    @Override
    public boolean isSword() {
        return this.e == air.p || this.e == air.t || this.e == air.o || this.e == air.E || this.e == air.x;
    }
    
    @Override
    public boolean isItem() {
        return !this.isBlock();
    }
    
    @Override
    public boolean isBlock() {
        return this.e instanceof ahb;
    }
    
    @Override
    public boolean isFood() {
        final akc action = this.n();
        return action == akc.b || action == akc.c;
    }
    
    @Override
    public boolean isFishingTool() {
        return this.e == air.aZ || this.e == air.cj;
    }
    
    @Override
    public boolean isAir() {
        return this.getAsItem().isAir();
    }
    
    @Override
    public int getSize() {
        return this.c;
    }
    
    @Override
    public void setSize(final int size) {
        this.c = size;
    }
    
    @Override
    public Component getDisplayName() {
        return Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer().deserialize(this.shadow$r());
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
        return (NBTTagCompound)this.p();
    }
    
    @NotNull
    @Override
    public NBTTagCompound getOrCreateNBTTag() {
        fy tag = this.p();
        if (tag == null) {
            this.b(tag = new fy());
        }
        return (NBTTagCompound)tag;
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return aip.b((aip)itemStack, (aip)this);
    }
    
    @Intrinsic
    public ItemStack itemStack$copy() {
        return (ItemStack)this.shadow$l();
    }
    
    @Override
    public int getLegacyItemData() {
        return this.h;
    }
    
    @Override
    public void setLegacyItemData(final int legacyData) {
        this.h = legacyData;
    }
}
