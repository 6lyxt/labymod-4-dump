// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.component;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.icon.Icon;

public class VersionedIconComponent extends fa
{
    private final Icon icon;
    private int width;
    private int height;
    private String placeholder;
    
    public VersionedIconComponent(final Icon icon) {
        super("");
        this.icon = icon;
    }
    
    @NotNull
    public Icon icon() {
        return this.icon;
    }
    
    public int width() {
        return this.width;
    }
    
    public int height() {
        return this.height;
    }
    
    @NotNull
    public String placeholder() {
        return this.placeholder;
    }
    
    public fa h() {
        final VersionedIconComponent component = new VersionedIconComponent(this.icon);
        component.width = this.width;
        component.height = this.height;
        component.placeholder = this.placeholder;
        component.a(this.b().m());
        for (final eu sibling : this.a()) {
            component.a(sibling.f());
        }
        return component;
    }
    
    public String toString() {
        return "IconComponent{icon=" + String.valueOf(this.icon) + ", width=" + this.width + ", height=" + this.height + ", placeholder='" + this.placeholder + "', siblings=" + String.valueOf(this.a) + ", style=" + String.valueOf(this.b());
    }
}
