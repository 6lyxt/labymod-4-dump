// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.key.mapper;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.I18n;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.Key;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;

public abstract class DefaultKeyMapper extends KeyMapper
{
    protected static final Int2ObjectArrayMap<Key> pressedKeys;
    protected static final Int2ObjectArrayMap<MouseButton> pressedMouseButtons;
    private static final StackWalker WALKER;
    private static final Logging ERROR_LOGGER;
    private static Key lastPressed;
    private static Key lastReleased;
    protected final Map<String, DefaultKey> keyCodes;
    
    public DefaultKeyMapper() {
        this.keyCodes = new HashMap<String, DefaultKey>();
    }
    
    public static void setLastPressed(final Key lastPressed) {
        DefaultKeyMapper.lastPressed = lastPressed;
    }
    
    public static Key lastPressed() {
        return (DefaultKeyMapper.lastPressed == null) ? Key.NONE : DefaultKeyMapper.lastPressed;
    }
    
    public static void setLastReleased(final Key lastReleased) {
        DefaultKeyMapper.lastReleased = lastReleased;
    }
    
    public static Key lastReleased() {
        return (DefaultKeyMapper.lastReleased == null) ? Key.NONE : DefaultKeyMapper.lastReleased;
    }
    
    public static Int2ObjectArrayMap<Key> getPressedKeys() {
        return DefaultKeyMapper.pressedKeys;
    }
    
    public static Int2ObjectArrayMap<MouseButton> getPressedMouseButtons() {
        return DefaultKeyMapper.pressedMouseButtons;
    }
    
    public static Key pressKey(final int keyCode) {
        Key key = (Key)DefaultKeyMapper.pressedKeys.get(keyCode);
        if (key != null) {
            return key;
        }
        key = KeyMapper.getKey(keyCode);
        DefaultKeyMapper.pressedKeys.put(keyCode, (Object)key);
        return key;
    }
    
    public static MouseButton pressMouse(final int keyCode) {
        MouseButton mouseButton = (MouseButton)DefaultKeyMapper.pressedMouseButtons.get(keyCode);
        if (mouseButton != null) {
            return mouseButton;
        }
        mouseButton = KeyMapper.getMouseButton(keyCode);
        DefaultKeyMapper.pressedMouseButtons.put(keyCode, (Object)mouseButton);
        return mouseButton;
    }
    
    public static boolean isKeyPressed(final int keyCode) {
        return DefaultKeyMapper.pressedKeys.containsKey(keyCode);
    }
    
    public static boolean isMousePressed(final int keyCode) {
        return DefaultKeyMapper.pressedMouseButtons.containsKey(keyCode);
    }
    
    public static Key releaseKey(final int keyCode) {
        final Key releasedKey = (Key)DefaultKeyMapper.pressedKeys.remove(keyCode);
        if (releasedKey != null) {
            return releasedKey;
        }
        final Key key = KeyMapper.getKey(keyCode);
        return key;
    }
    
    public static MouseButton releaseMouse(final int keyCode) {
        final MouseButton releasedMouse = (MouseButton)DefaultKeyMapper.pressedMouseButtons.remove(keyCode);
        if (releasedMouse != null) {
            return releasedMouse;
        }
        final MouseButton mouseButton = KeyMapper.getMouseButton(keyCode);
        return mouseButton;
    }
    
    @NotNull
    @Override
    public String getNameByKey(@NotNull final Key key) {
        final DefaultKey versionedKey = this.keyCodes.get(key.getActualName());
        if (versionedKey == null) {
            return key.getActualName();
        }
        if (!key.isAction()) {
            final char character = versionedKey.getCharacter();
            if (character != '\0' && character != ' ') {
                if (character == '\u00df') {
                    return "\u00df";
                }
                return Character.toString(character).toUpperCase(Locale.ROOT);
            }
        }
        final String translationKey = key.getTranslationKey();
        if (translationKey == null) {
            return key.getActualName();
        }
        final String translatedName = I18n.getTranslation(translationKey, new Object[0]);
        if (translatedName != null) {
            return translatedName;
        }
        return key.getActualName();
    }
    
    @Override
    public int getKeyCodeByKey(@NotNull final Key key) {
        return this.getKeyCodeByKey(this.keys, key, KeyCodeType.CURRENT);
    }
    
    @Override
    public int getKeyCodeByKey(@NotNull final Key key, @NotNull final KeyCodeType type) {
        if (type == KeyCodeType.CURRENT) {
            return this.getKeyCodeByKey(key);
        }
        return this.getKeyCodeByKey(this.keys, key, type);
    }
    
    @Override
    public int getKeyCodeByMouseButton(@NotNull final MouseButton mouseButton) {
        return this.getKeyCodeByKey(this.mouseButtons, mouseButton, KeyCodeType.CURRENT);
    }
    
    @Override
    public int getKeyCodeByMouseButton(@NotNull final MouseButton mouseButton, @NotNull final KeyCodeType type) {
        if (type == KeyCodeType.CURRENT) {
            return this.getKeyCodeByMouseButton(mouseButton);
        }
        return this.getKeyCodeByKey(this.mouseButtons, mouseButton, KeyCodeType.CURRENT);
    }
    
    @Nullable
    @Override
    public Key getKeyByKeyCode(final int keyCode) {
        return this.getKeyByKeyCode(this.keys, keyCode, KeyCodeType.CURRENT);
    }
    
    @Nullable
    @Override
    public Key getKeyByKeyCode(final int keyCode, @NotNull final KeyCodeType type) {
        if (type == KeyCodeType.CURRENT) {
            return this.getKeyByKeyCode(keyCode);
        }
        return this.getKeyByKeyCode(this.keys, keyCode, type);
    }
    
    @Nullable
    @Override
    public MouseButton getMouseButtonByKeyCode(final int keyCode) {
        return this.getKeyByKeyCode(this.mouseButtons, keyCode, KeyCodeType.CURRENT);
    }
    
    @Nullable
    @Override
    public MouseButton getMouseButtonByKeyCode(final int keyCode, @NotNull final KeyCodeType type) {
        if (type == KeyCodeType.CURRENT) {
            return this.getMouseButtonByKeyCode(keyCode);
        }
        return this.getKeyByKeyCode(this.mouseButtons, keyCode, type);
    }
    
    @Override
    public char getChar(@NotNull final Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        final DefaultKey versionedKey = this.keyCodes.get(key.getActualName());
        if (versionedKey == null) {
            return '\0';
        }
        return versionedKey.getCharacter();
    }
    
    @Override
    public boolean isKeyPressed(@NotNull final Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        if (key instanceof MouseButton) {
            return isMousePressed(key.getId());
        }
        return isKeyPressed(key.getId());
    }
    
    @Override
    public void register(@NotNull final Key key, final int glfwKeyCode, final int lwjglKeyCode) {
        Objects.requireNonNull(key, "Key cannot be null");
        final String actualName = key.getActualName();
        if (this.keyCodes.containsKey(actualName)) {
            throw new IllegalArgumentException("Key " + actualName + " is already registered");
        }
        final DefaultKey versionedKey = this.createKey(key, glfwKeyCode, lwjglKeyCode);
        if (key instanceof final MouseButton mouseButton) {
            this.mouseButtons.putIfAbsent(actualName, mouseButton);
            DefaultKeyMapper.DEBUG_LOGGER.info("Registered mouse button {} with key code {}", actualName, key);
        }
        else {
            this.keys.putIfAbsent(actualName, key);
            DefaultKeyMapper.DEBUG_LOGGER.info("Registered keyboard key {} with key code {}", actualName, glfwKeyCode);
        }
        this.keyCodes.put(actualName, versionedKey);
    }
    
    @Override
    public void initialize() {
        this.register(Key.NONE, -1, 0);
        this.register(MouseButton.LEFT, 0, 0);
        this.register(MouseButton.RIGHT, 1, 1);
        this.register(MouseButton.MIDDLE, 2, 2);
        this.register(MouseButton.M4, 3, 3);
        this.register(MouseButton.M5, 4, 4);
        this.register(MouseButton.M6, 5, 5);
        this.register(MouseButton.M7, 6, 6);
        this.register(MouseButton.M8, 7, 7);
        this.register(Key.ESCAPE, 256, 1);
        this.register(Key.F1, 290, 59);
        this.register(Key.F2, 291, 60);
        this.register(Key.F3, 292, 61);
        this.register(Key.F4, 293, 62);
        this.register(Key.F5, 294, 63);
        this.register(Key.F6, 295, 64);
        this.register(Key.F7, 296, 65);
        this.register(Key.F8, 297, 66);
        this.register(Key.F9, 298, 67);
        this.register(Key.F10, 299, 68);
        this.register(Key.F11, 300, 87);
        this.register(Key.F12, 301, 88);
        this.register(Key.F13, 302, 100);
        this.register(Key.F14, 303, 101);
        this.register(Key.F15, 304, 102);
        this.register(Key.F16, 305, 103);
        this.register(Key.F17, 306, 104);
        this.register(Key.F18, 307, 105);
        this.register(Key.F19, 308, 113);
        this.register(Key.F20, 309, Integer.MIN_VALUE);
        this.register(Key.F21, 310, Integer.MIN_VALUE);
        this.register(Key.F22, 311, Integer.MIN_VALUE);
        this.register(Key.F23, 312, Integer.MIN_VALUE);
        this.register(Key.F24, 313, Integer.MIN_VALUE);
        this.register(Key.F25, 314, Integer.MIN_VALUE);
        this.register(Key.GRAVE, 96, 41);
        this.register(Key.NUM1, 49, 2);
        this.register(Key.NUM2, 50, 3);
        this.register(Key.NUM3, 51, 4);
        this.register(Key.NUM4, 52, 5);
        this.register(Key.NUM5, 53, 6);
        this.register(Key.NUM6, 54, 7);
        this.register(Key.NUM7, 55, 8);
        this.register(Key.NUM8, 56, 9);
        this.register(Key.NUM9, 57, 10);
        this.register(Key.NUM0, 48, 11);
        this.register(Key.MINUS, 45, 12);
        this.register(Key.EQUAL, 61, 13);
        this.register(Key.BACK, 259, 14);
        this.register(Key.TAB, 258, 15);
        this.register(Key.Q, 81, 16);
        this.register(Key.W, 87, 17);
        this.register(Key.E, 69, 18);
        this.register(Key.R, 82, 19);
        this.register(Key.T, 84, 20);
        this.register(Key.Y, 89, 21);
        this.register(Key.U, 85, 22);
        this.register(Key.I, 73, 23);
        this.register(Key.O, 79, 24);
        this.register(Key.P, 80, 25);
        this.register(Key.L_BRACKET, 91, 26);
        this.register(Key.R_BRACKET, 93, 27);
        this.register(Key.BACKSLASH, 92, 43);
        this.register(Key.CAPS_LOCK, 280, 58);
        this.register(Key.A, 65, 30);
        this.register(Key.S, 83, 31);
        this.register(Key.D, 68, 32);
        this.register(Key.F, 70, 33);
        this.register(Key.G, 71, 34);
        this.register(Key.H, 72, 35);
        this.register(Key.J, 74, 36);
        this.register(Key.K, 75, 37);
        this.register(Key.L, 76, 38);
        this.register(Key.SEMICOLON, 59, 39);
        this.register(Key.APOSTROPHE, 39, 40);
        this.register(Key.ENTER, 257, 28);
        this.register(Key.L_SHIFT, 340, 42);
        this.register(Key.Z, 90, 44);
        this.register(Key.X, 88, 45);
        this.register(Key.C, 67, 46);
        this.register(Key.V, 86, 47);
        this.register(Key.B, 66, 48);
        this.register(Key.N, 78, 49);
        this.register(Key.M, 77, 50);
        this.register(Key.COMMA, 44, 51);
        this.register(Key.PERIOD, 46, 52);
        this.register(Key.SLASH, 47, 53);
        this.register(Key.R_SHIFT, 344, 54);
        this.register(Key.L_CONTROL, 341, 29);
        this.register(Key.L_WIN, 343, 219);
        this.register(Key.L_ALT, 342, 56);
        this.register(Key.SPACE, 32, 57);
        this.register(Key.R_ALT, 346, 184);
        this.register(Key.R_WIN, 347, 220);
        this.register(Key.MENU, 348, 221);
        this.register(Key.R_CONTROL, 345, 157);
        this.register(Key.ARROW_LEFT, 263, 203);
        this.register(Key.ARROW_DOWN, 264, 208);
        this.register(Key.ARROW_RIGHT, 262, 205);
        this.register(Key.ARROW_UP, 265, 200);
        this.register(Key.INSERT, 260, 210);
        this.register(Key.HOME, 268, 199);
        this.register(Key.PAGE_UP, 266, 201);
        this.register(Key.DELETE, 261, 211);
        this.register(Key.END, 269, 207);
        this.register(Key.PAGE_DOWN, 267, 209);
        this.register(Key.PRINT, 283, 183);
        this.register(Key.SCROLL, 281, 70);
        this.register(Key.PAUSE, 284, 197);
        this.register(Key.NUM_LOCK, 282, 69);
        this.register(Key.DIVIDE, 331, 181);
        this.register(Key.MULTIPLY, 332, 55);
        this.register(Key.SUBTRACT, 333, 74);
        this.register(Key.ADD, 334, 78);
        this.register(Key.NUMPAD_EQUAL, 336, 141);
        this.register(Key.NUMPAD_ENTER, 335, 156);
        this.register(Key.DECIMAL, 330, 83);
        this.register(Key.NUMPAD0, 320, 82);
        this.register(Key.NUMPAD1, 321, 79);
        this.register(Key.NUMPAD2, 322, 80);
        this.register(Key.NUMPAD3, 323, 81);
        this.register(Key.NUMPAD4, 324, 75);
        this.register(Key.NUMPAD5, 325, 76);
        this.register(Key.NUMPAD6, 326, 77);
        this.register(Key.NUMPAD7, 327, 71);
        this.register(Key.NUMPAD8, 328, 72);
        this.register(Key.NUMPAD9, 329, 73);
        this.register(Key.WORLD_1, 161, 167);
        this.register(Key.WORLD_2, 162, 316);
    }
    
    protected abstract DefaultKey createKey(final Key p0, final int p1, final int p2);
    
    private static void printStacktrace(final String message, final Object... arguments) {
        final StringBuilder stackTraceBuilder = new StringBuilder();
        final List<StackWalker.StackFrame> frames = DefaultKeyMapper.WALKER.walk((Function<? super Stream<StackWalker.StackFrame>, ? extends List<StackWalker.StackFrame>>)Stream::toList);
        if (frames.size() < 2) {
            return;
        }
        for (final StackWalker.StackFrame frame : frames) {
            stackTraceBuilder.append('\t').append(frame.toString()).append('\n');
        }
        DefaultKeyMapper.ERROR_LOGGER.warn(String.format(Locale.ROOT, message, arguments) + " - Stacktrace: \n" + String.valueOf(stackTraceBuilder), new Object[0]);
    }
    
    private <T extends Key> T getKeyByKeyCode(final Map<String, T> map, final int keyCode, final KeyCodeType type) {
        DefaultKey key = null;
        for (final DefaultKey value : this.keyCodes.values()) {
            int keyCodeToMatch = 0;
            switch (type) {
                case LWJGL: {
                    keyCodeToMatch = value.getLwjglKeyCode();
                    break;
                }
                case GLFW: {
                    keyCodeToMatch = value.getGlfwKeyCode();
                    break;
                }
                default: {
                    keyCodeToMatch = value.getKeyCode();
                    break;
                }
            }
            if (map.containsKey(value.getKeyName()) && keyCodeToMatch == keyCode) {
                key = value;
                break;
            }
        }
        if (key == null) {
            return null;
        }
        return map.get(key.getKeyName());
    }
    
    private <T extends Key> int getKeyCodeByKey(final Map<String, T> map, final T key, final KeyCodeType type) {
        Objects.requireNonNull(key, "Key cannot be null");
        final String actualName = key.getActualName();
        if (!map.containsKey(actualName)) {
            return 0;
        }
        final DefaultKey versionedKey = this.keyCodes.get(actualName);
        if (versionedKey == null) {
            return 0;
        }
        switch (type) {
            case LWJGL: {
                return versionedKey.getLwjglKeyCode();
            }
            case GLFW: {
                return versionedKey.getGlfwKeyCode();
            }
            default: {
                return versionedKey.getKeyCode();
            }
        }
    }
    
    static {
        pressedKeys = new Int2ObjectArrayMap();
        pressedMouseButtons = new Int2ObjectArrayMap();
        WALKER = StackWalker.getInstance();
        ERROR_LOGGER = Logging.create(KeyMapper.class);
    }
}
