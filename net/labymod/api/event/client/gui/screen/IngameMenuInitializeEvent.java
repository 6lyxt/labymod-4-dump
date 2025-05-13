// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import java.util.Iterator;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import java.util.ArrayList;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.labymod.api.event.Event;

public class IngameMenuInitializeEvent implements Event
{
    private List<PauseMenuButton> buttons;
    
    public void addButton(@NotNull final PauseMenuButton button) {
        Objects.requireNonNull(button, "Button cannot be null");
        if (this.buttons == null) {
            this.buttons = new ArrayList<PauseMenuButton>();
        }
        this.buttons.add(button);
    }
    
    public void addLeftButton(@NotNull final Component text, @Nullable final Icon icon, @Nullable final Runnable onClick) {
        this.addButton(new PauseMenuButton(PauseMenuButtonPosition.LEFT, text, icon, onClick));
    }
    
    public void addRightButton(@NotNull final Component text, @Nullable final Icon icon, @Nullable final Runnable onClick) {
        this.addButton(new PauseMenuButton(PauseMenuButtonPosition.RIGHT, text, icon, onClick));
    }
    
    public void addLeftButton(@NotNull final Component text, @Nullable final Runnable onClick) {
        this.addLeftButton(text, null, onClick);
    }
    
    public void addRightButton(@NotNull final Component text, @Nullable final Runnable onClick) {
        this.addRightButton(text, null, onClick);
    }
    
    public void forEachRightButton(@NotNull final Consumer<PauseMenuButton> consumer) {
        this.forEachButton(PauseMenuButtonPosition.RIGHT, consumer);
    }
    
    public void forEachLeftButton(@NotNull final Consumer<PauseMenuButton> consumer) {
        this.forEachButton(PauseMenuButtonPosition.LEFT, consumer);
    }
    
    public void forEachButton(@NotNull final PauseMenuButtonPosition position, @NotNull final Consumer<PauseMenuButton> consumer) {
        Objects.requireNonNull(position, "Position cannot be null");
        Objects.requireNonNull(consumer, "Consumer cannot be null");
        if (this.buttons == null) {
            return;
        }
        for (final PauseMenuButton button : this.buttons) {
            if (button.position() == position) {
                consumer.accept(button);
            }
        }
    }
    
    public enum PauseMenuButtonPosition
    {
        LEFT, 
        RIGHT;
    }
    
    public static class PauseMenuButton
    {
        private final PauseMenuButtonPosition position;
        private final Component text;
        private final Icon icon;
        private final Runnable onClick;
        
        public PauseMenuButton(@NotNull final PauseMenuButtonPosition position, @NotNull final Component text, @Nullable final Icon icon, @Nullable final Runnable onClick) {
            Objects.requireNonNull(position, "Position cannot be null");
            Objects.requireNonNull(text, "Text cannot be null");
            this.position = position;
            this.text = text;
            this.icon = icon;
            this.onClick = onClick;
        }
        
        @NotNull
        public PauseMenuButtonPosition position() {
            return this.position;
        }
        
        @NotNull
        public Component text() {
            return this.text;
        }
        
        @Nullable
        public Icon getIcon() {
            return this.icon;
        }
        
        @Nullable
        public Runnable onClick() {
            return this.onClick;
        }
    }
}
