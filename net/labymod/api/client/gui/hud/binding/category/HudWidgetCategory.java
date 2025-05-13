// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.category;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.binding.HudWidgetBinding;

public class HudWidgetCategory extends HudWidgetBinding
{
    public static final HudWidgetCategory INGAME;
    public static final HudWidgetCategory ITEM;
    public static final HudWidgetCategory SYSTEM;
    public static final HudWidgetCategory SERVICE;
    public static final HudWidgetCategory MISCELLANEOUS;
    
    public HudWidgetCategory(@NotNull final Object holder, @NotNull final String id) {
        super(holder, id);
    }
    
    public HudWidgetCategory(@NotNull final String id) {
        super(id);
    }
    
    @NotNull
    public Component title() {
        return ((BaseComponent<Component>)Component.translatable(this.namespace + ".hudWidgetCategory." + this.id + ".name", new Component[0])).decorate(TextDecoration.BOLD);
    }
    
    @NotNull
    public Component description() {
        return Component.translatable(this.namespace + ".hudWidgetCategory." + this.id + ".description", new Component[0]);
    }
    
    static {
        INGAME = new HudWidgetCategory("ingame");
        ITEM = new HudWidgetCategory("item");
        SYSTEM = new HudWidgetCategory("system");
        SERVICE = new HudWidgetCategory("service");
        MISCELLANEOUS = new HudWidgetCategory("miscellaneous");
    }
}
