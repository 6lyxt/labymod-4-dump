// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.event;

import java.util.Objects;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.item.Item;
import net.labymod.api.client.component.ComponentService;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;

public interface HoverEvent<T>
{
    default HoverEvent<Component> showText(@NotNull final Component text) {
        return ComponentService.hoverEvent(Action.SHOW_TEXT, text);
    }
    
    Action<T> action();
    
    T value();
    
    public static class Action<T>
    {
        public static final Action<Component> SHOW_TEXT;
        public static final Action<Item> SHOW_ITEM;
        public static final Action<Entity> SHOW_ENTITY;
        private final Class<T> type;
        private final String name;
        
        private Action(final String name, final Class<T> type) {
            this.name = name;
            this.type = type;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Class<T> getType() {
            return this.type;
        }
        
        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            final Action<?> action = (Action<?>)object;
            return Objects.equals(this.type, action.type) && Objects.equals(this.name, action.name);
        }
        
        @Override
        public int hashCode() {
            int result = (this.type != null) ? this.type.hashCode() : 0;
            result = 31 * result + ((this.name != null) ? this.name.hashCode() : 0);
            return result;
        }
        
        static {
            SHOW_TEXT = new Action<Component>("show_text", Component.class);
            SHOW_ITEM = new Action<Item>("show_item", Item.class);
            SHOW_ENTITY = new Action<Entity>("show_entity", Entity.class);
        }
    }
}
