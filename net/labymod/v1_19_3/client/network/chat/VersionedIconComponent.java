// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.network.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.v1_19_3.client.component.VersionedIconContents;
import net.labymod.api.client.component.IconComponent;

public class VersionedIconComponent extends VersionedBaseComponent<IconComponent, VersionedIconContents> implements IconComponent
{
    public VersionedIconComponent(final tf holder) {
        super(holder);
    }
    
    @NotNull
    @Override
    public Icon getIcon() {
        return ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().icon();
    }
    
    @NotNull
    @Override
    public IconComponent setIcon(@NotNull final Icon icon) {
        ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().setIcon(icon);
        return this;
    }
    
    @Override
    public int getWidth() {
        return ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().width();
    }
    
    @Override
    public int getHeight() {
        return ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().height();
    }
    
    @Override
    public IconComponent setWidth(final int width) {
        ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().setWidth(width);
        return this;
    }
    
    @Override
    public IconComponent setHeight(final int height) {
        ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().setHeight(height);
        return this;
    }
    
    @NotNull
    @Override
    public String getPlaceholder() {
        return ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().placeholder();
    }
    
    @Override
    public IconComponent setPlaceholder(@NotNull final String placeholder) {
        ((VersionedBaseComponent<T, VersionedIconContents>)this).getContents().setPlaceholder(placeholder);
        return this;
    }
    
    @Override
    public IconComponent plainCopy() {
        return ComponentService.iconComponent(this.getIcon()).setWidth(this.getWidth()).setHeight(this.getHeight()).setPlaceholder(this.getPlaceholder());
    }
}
