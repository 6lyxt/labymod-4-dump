// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key.mapper;

import net.labymod.api.Constants;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import java.util.Objects;
import net.labymod.api.client.gui.screen.key.InputType;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public abstract class KeyMapper
{
    public static final char DEFAULT_CHAR = '\0';
    protected final Map<String, Key> keys;
    protected final Map<String, MouseButton> mouseButtons;
    protected static final Logging DEBUG_LOGGER;
    
    public static void registerKey(@NotNull final Key key) {
        Laby.references().keyMapper().register(key);
    }
    
    @NotNull
    public static String getKeyName(@NotNull final Key key) {
        return Laby.references().keyMapper().getNameByKey(key);
    }
    
    @Nullable
    public static Key getKey(@NotNull final String name) {
        return Laby.references().keyMapper().getKeyByName(name);
    }
    
    @Nullable
    public static Key getKey(final int keyCode) {
        return Laby.references().keyMapper().getKeyByKeyCode(keyCode);
    }
    
    @Nullable
    public static Key getKey(final int keyCode, @NotNull final KeyCodeType type) {
        return Laby.references().keyMapper().getKeyByKeyCode(keyCode, type);
    }
    
    @Nullable
    public static MouseButton getMouseButton(final int keyCode) {
        return Laby.references().keyMapper().getMouseButtonByKeyCode(keyCode);
    }
    
    @Nullable
    public static MouseButton getMouseButton(final int keyCode, @NotNull final KeyCodeType type) {
        return Laby.references().keyMapper().getMouseButtonByKeyCode(keyCode, type);
    }
    
    public static int getKeyCode(@NotNull final Key key) {
        if (key instanceof final MouseButton mouseButton) {
            return Laby.references().keyMapper().getKeyCodeByMouseButton(mouseButton);
        }
        return Laby.references().keyMapper().getKeyCodeByKey(key);
    }
    
    public static int getKeyCode(@NotNull final Key key, @NotNull final KeyCodeType type) {
        if (key instanceof final MouseButton mouseButton) {
            return Laby.references().keyMapper().getKeyCodeByMouseButton(mouseButton, type);
        }
        return Laby.references().keyMapper().getKeyCodeByKey(key, type);
    }
    
    @Deprecated
    public static int getMouseButton(@NotNull final MouseButton mouseButton) {
        return Laby.references().keyMapper().getKeyCodeByMouseButton(mouseButton);
    }
    
    public static char getCharacter(@NotNull final Key key) {
        return Laby.references().keyMapper().getChar(key);
    }
    
    public static boolean isPressed(@NotNull final Key key) {
        return Laby.references().keyMapper().isKeyPressed(key);
    }
    
    public static InputType getInputType(@NotNull final Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (key.isAction() || (KeyHandler.isControlDown() && !KeyHandler.isAltDown())) {
            return InputType.ACTION;
        }
        return InputType.CHARACTER;
    }
    
    protected KeyMapper() {
        this.keys = new HashMap<String, Key>();
        this.mouseButtons = new HashMap<String, MouseButton>();
    }
    
    public void register(@NotNull final Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        final String actualName = key.getActualName();
        if (this.keys.containsKey(actualName) || this.mouseButtons.containsKey(actualName)) {
            throw new IllegalArgumentException("Key " + actualName + " is already registered!");
        }
        if (key instanceof final MouseButton mouseButton) {
            this.mouseButtons.put(actualName, mouseButton);
        }
        else {
            this.keys.put(actualName, key);
        }
    }
    
    @Nullable
    public Key getKeyByName(@NotNull final String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        final Key mouseButton = this.mouseButtons.get(name);
        if (mouseButton != null) {
            return mouseButton;
        }
        return this.keys.get(name);
    }
    
    @NotNull
    public abstract String getNameByKey(@NotNull final Key p0);
    
    public abstract int getKeyCodeByKey(@NotNull final Key p0);
    
    public abstract int getKeyCodeByKey(@NotNull final Key p0, @NotNull final KeyCodeType p1);
    
    public abstract int getKeyCodeByMouseButton(@NotNull final MouseButton p0);
    
    public abstract int getKeyCodeByMouseButton(@NotNull final MouseButton p0, @NotNull final KeyCodeType p1);
    
    @Nullable
    public abstract Key getKeyByKeyCode(final int p0);
    
    @Nullable
    public abstract Key getKeyByKeyCode(final int p0, @NotNull final KeyCodeType p1);
    
    @Nullable
    public abstract MouseButton getMouseButtonByKeyCode(final int p0);
    
    @Nullable
    public abstract MouseButton getMouseButtonByKeyCode(final int p0, @NotNull final KeyCodeType p1);
    
    public abstract char getChar(@NotNull final Key p0);
    
    public abstract boolean isKeyPressed(@NotNull final Key p0);
    
    public abstract void initialize();
    
    public abstract void register(@NotNull final Key p0, final int p1, final int p2);
    
    static {
        DEBUG_LOGGER = Logging.create(KeyMapper.class, Constants.SystemProperties.getKeymapping());
    }
    
    public enum KeyCodeType
    {
        CURRENT, 
        LWJGL, 
        GLFW;
    }
}
