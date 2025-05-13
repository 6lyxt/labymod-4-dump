// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.component;

import net.labymod.api.client.component.TextComponent;
import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.labymod.api.client.gui.icon.Icon;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.v1_8_9.client.component.VersionedIconComponent;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.IconComponent;

@Mixin({ VersionedIconComponent.class })
@Implements({ @Interface(iface = IconComponent.class, prefix = "iconComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinIconComponent extends MixinChatComponentStyle<IconComponent> implements IconComponent
{
    @Final
    @Shadow(remap = false)
    @Mutable
    private Icon icon;
    @Shadow(remap = false)
    private int width;
    @Shadow(remap = false)
    private int height;
    @Shadow(remap = false)
    private String placeholder;
    
    @NotNull
    @Intrinsic
    public Icon iconComponent$getIcon() {
        return this.icon;
    }
    
    @NotNull
    @Intrinsic
    public IconComponent iconComponent$setIcon(@NotNull final Icon icon) {
        this.icon = icon;
        return this;
    }
    
    @Intrinsic
    public int iconComponent$getWidth() {
        return this.width;
    }
    
    @Intrinsic
    public int iconComponent$getHeight() {
        return this.height;
    }
    
    @NotNull
    @Intrinsic
    public IconComponent iconComponent$setWidth(final int width) {
        this.width = width;
        return this;
    }
    
    @NotNull
    @Intrinsic
    public IconComponent iconComponent$setHeight(final int height) {
        this.height = height;
        return this;
    }
    
    @NotNull
    @Intrinsic
    public String iconComponent$getPlaceholder() {
        return this.placeholder;
    }
    
    @Intrinsic
    public IconComponent iconComponent$setPlaceholder(@NotNull final String placeholder) {
        this.placeholder = placeholder;
        ((TextComponent)this).text(placeholder);
        return this;
    }
    
    @Intrinsic
    public IconComponent iconComponent$plainCopy() {
        final IconComponent component = (IconComponent)new VersionedIconComponent(this.icon);
        component.setWidth(this.width);
        component.setHeight(this.height);
        component.setPlaceholder(this.placeholder);
        return component;
    }
}
