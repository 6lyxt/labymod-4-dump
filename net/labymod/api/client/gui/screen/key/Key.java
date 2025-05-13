// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key;

import net.labymod.api.Laby;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.Collections;
import java.util.function.Function;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;

public class Key
{
    protected static final KeyMapper KEY_MAPPER;
    public static final Key NONE;
    public static final Key ESCAPE;
    public static final Key F1;
    public static final Key F2;
    public static final Key F3;
    public static final Key F4;
    public static final Key F5;
    public static final Key F6;
    public static final Key F7;
    public static final Key F8;
    public static final Key F9;
    public static final Key F10;
    public static final Key F11;
    public static final Key F12;
    public static final Key F13;
    public static final Key F14;
    public static final Key F15;
    public static final Key F16;
    public static final Key F17;
    public static final Key F18;
    public static final Key F19;
    public static final Key F20;
    public static final Key F21;
    public static final Key F22;
    public static final Key F23;
    public static final Key F24;
    public static final Key F25;
    public static final Key GRAVE;
    public static final Key NUM1;
    public static final Key NUM2;
    public static final Key NUM3;
    public static final Key NUM4;
    public static final Key NUM5;
    public static final Key NUM6;
    public static final Key NUM7;
    public static final Key NUM8;
    public static final Key NUM9;
    public static final Key NUM0;
    public static final Key MINUS;
    public static final Key EQUAL;
    public static final Key BACK;
    public static final Key TAB;
    public static final Key Q;
    public static final Key W;
    public static final Key E;
    public static final Key R;
    public static final Key T;
    public static final Key Y;
    public static final Key U;
    public static final Key I;
    public static final Key O;
    public static final Key P;
    public static final Key L_BRACKET;
    public static final Key R_BRACKET;
    public static final Key BACKSLASH;
    public static final Key CAPS_LOCK;
    public static final Key A;
    public static final Key S;
    public static final Key D;
    public static final Key F;
    public static final Key G;
    public static final Key H;
    public static final Key J;
    public static final Key K;
    public static final Key L;
    public static final Key SEMICOLON;
    public static final Key APOSTROPHE;
    public static final Key ENTER;
    public static final Key L_SHIFT;
    public static final Key Z;
    public static final Key X;
    public static final Key C;
    public static final Key V;
    public static final Key B;
    public static final Key N;
    public static final Key M;
    public static final Key COMMA;
    public static final Key PERIOD;
    public static final Key SLASH;
    public static final Key R_SHIFT;
    public static final Key L_CONTROL;
    public static final Key L_WIN;
    public static final Key L_ALT;
    public static final Key SPACE;
    public static final Key R_ALT;
    public static final Key R_WIN;
    public static final Key MENU;
    public static final Key R_CONTROL;
    public static final Key ARROW_LEFT;
    public static final Key ARROW_DOWN;
    public static final Key ARROW_RIGHT;
    public static final Key ARROW_UP;
    public static final Key INSERT;
    public static final Key HOME;
    public static final Key PAGE_UP;
    public static final Key DELETE;
    public static final Key END;
    public static final Key PAGE_DOWN;
    public static final Key PRINT;
    public static final Key SCROLL;
    public static final Key PAUSE;
    public static final Key NUM_LOCK;
    public static final Key DIVIDE;
    public static final Key MULTIPLY;
    public static final Key SUBTRACT;
    public static final Key ADD;
    public static final Key NUMPAD_EQUAL;
    public static final Key NUMPAD_ENTER;
    public static final Key DECIMAL;
    public static final Key NUMPAD0;
    public static final Key NUMPAD1;
    public static final Key NUMPAD2;
    public static final Key NUMPAD3;
    public static final Key NUMPAD4;
    public static final Key NUMPAD5;
    public static final Key NUMPAD6;
    public static final Key NUMPAD7;
    public static final Key NUMPAD8;
    public static final Key NUMPAD9;
    public static final Key WORLD_1;
    public static final Key WORLD_2;
    protected final String actualName;
    protected final String translationKey;
    protected String name;
    protected int id;
    protected char character;
    protected boolean unknown;
    protected boolean action;
    
    protected Key(String name, final String translationKeyPrefix) {
        this.id = -1;
        this.character = '\0';
        this.actualName = name;
        name = name.toLowerCase(Locale.ROOT);
        this.translationKey = translationKeyPrefix + ((name.length() == 1) ? ("key." + name) : name.replace("_", "."));
    }
    
    protected Key(final String name) {
        this(name, "labymod.keys.keyboard.");
    }
    
    protected Key action() {
        this.action = true;
        return this;
    }
    
    protected Key register() {
        KeyMapper.registerKey(this);
        return this;
    }
    
    public String getName() {
        if (this.name == null) {
            this.name = KeyMapper.getKeyName(this);
        }
        return this.name;
    }
    
    public String getTranslationKey() {
        return this.translationKey;
    }
    
    public String getActualName() {
        return this.actualName;
    }
    
    public int getId() {
        if (this.id == -1) {
            this.id = KeyMapper.getKeyCode(this);
        }
        return this.id;
    }
    
    public boolean isAction() {
        return this.action;
    }
    
    public boolean isUnknown() {
        return this.unknown;
    }
    
    public boolean isPressed() {
        return KeyMapper.isPressed(this);
    }
    
    public static Key get(final int keyCode) {
        Key key = KeyMapper.getKey(keyCode);
        if (key != null) {
            return key;
        }
        key = new Key("UNKNOWN_" + keyCode);
        key.unknown = true;
        key.id = keyCode;
        Key.KEY_MAPPER.register(key, keyCode, keyCode);
        return key;
    }
    
    @Nullable
    public static Key getByName(@NotNull final String name) {
        return KeyMapper.getKey(name);
    }
    
    public static String concat(final Collection<Key> keys) {
        final List<Key> toSort = new ArrayList<Key>();
        toSort.addAll(keys);
        toSort.sort(Collections.reverseOrder(Comparator.comparing((Function<? super Key, ? extends Comparable>)Key::getId)));
        final StringJoiner joiner = new StringJoiner(" + ");
        for (final Key key : toSort) {
            if (key == null) {
                continue;
            }
            final String keyName = key.getName();
            joiner.add(keyName);
        }
        return joiner.toString();
    }
    
    @Override
    public String toString() {
        return this.actualName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Key key = (Key)o;
        return this.actualName.equals(key.actualName) && this.action == key.action && this.getId() == key.getId();
    }
    
    @Override
    public int hashCode() {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = 31 * result + (this.action ? 1 : 0);
        return result;
    }
    
    public static boolean isCharacter(final char character) {
        return character != '§' && character >= ' ' && character != '\u007f';
    }
    
    static {
        KEY_MAPPER = Laby.references().keyMapper();
        NONE = new Key("NONE").register();
        ESCAPE = new Key("ESCAPE").register().action();
        F1 = new Key("F_1").register().action();
        F2 = new Key("F_2").register().action();
        F3 = new Key("F_3").register().action();
        F4 = new Key("F_4").register().action();
        F5 = new Key("F_5").register().action();
        F6 = new Key("F_6").register().action();
        F7 = new Key("F_7").register().action();
        F8 = new Key("F_8").register().action();
        F9 = new Key("F_9").register().action();
        F10 = new Key("F_10").register().action();
        F11 = new Key("F_11").register().action();
        F12 = new Key("F_12").register().action();
        F13 = new Key("F_13").register().action();
        F14 = new Key("F_14").register().action();
        F15 = new Key("F_15").register().action();
        F16 = new Key("F_16").register().action();
        F17 = new Key("F_17").register().action();
        F18 = new Key("F_18").register().action();
        F19 = new Key("F_19").register().action();
        F20 = new Key("F_20").register().action();
        F21 = new Key("F_21").register().action();
        F22 = new Key("F_22").register().action();
        F23 = new Key("F_23").register().action();
        F24 = new Key("F_24").register().action();
        F25 = new Key("F_25").register().action();
        GRAVE = new Key("GRAVE").register();
        NUM1 = new Key("NUM_1").register();
        NUM2 = new Key("NUM_2").register();
        NUM3 = new Key("NUM_3").register();
        NUM4 = new Key("NUM_4").register();
        NUM5 = new Key("NUM_5").register();
        NUM6 = new Key("NUM_6").register();
        NUM7 = new Key("NUM_7").register();
        NUM8 = new Key("NUM_8").register();
        NUM9 = new Key("NUM_9").register();
        NUM0 = new Key("NUM_0").register();
        MINUS = new Key("MINUS").register();
        EQUAL = new Key("EQUAL").register();
        BACK = new Key("BACK").register().action();
        TAB = new Key("TAB").register().action();
        Q = new Key("Q").register();
        W = new Key("W").register();
        E = new Key("E").register();
        R = new Key("R").register();
        T = new Key("T").register();
        Y = new Key("Y").register();
        U = new Key("U").register();
        I = new Key("I").register();
        O = new Key("O").register();
        P = new Key("P").register();
        L_BRACKET = new Key("LEFT_BRACKET").register();
        R_BRACKET = new Key("RIGHT_BRACKET").register();
        BACKSLASH = new Key("BACKSLASH").register();
        CAPS_LOCK = new Key("CAPSLOCK").register().action();
        A = new Key("A").register();
        S = new Key("S").register();
        D = new Key("D").register();
        F = new Key("F").register();
        G = new Key("G").register();
        H = new Key("H").register();
        J = new Key("J").register();
        K = new Key("K").register();
        L = new Key("L").register();
        SEMICOLON = new Key("SEMICOLON").register();
        APOSTROPHE = new Key("APOSTROPHE").register();
        ENTER = new Key("ENTER").register().action();
        L_SHIFT = new Key("LEFT_SHIFT").register().action();
        Z = new Key("Z").register();
        X = new Key("X").register();
        C = new Key("C").register();
        V = new Key("V").register();
        B = new Key("B").register();
        N = new Key("N").register();
        M = new Key("M").register();
        COMMA = new Key("COMMA").register();
        PERIOD = new Key("PERIOD").register();
        SLASH = new Key("SLASH").register();
        R_SHIFT = new Key("RIGHT_SHIFT").register().action();
        L_CONTROL = new Key("LEFT_CONTROL").register().action();
        L_WIN = new Key("LEFT_WIN").register().action();
        L_ALT = new Key("LEFT_ALT").register().action();
        SPACE = new Key("SPACE").register();
        R_ALT = new Key("RIGHT_ALT").register().action();
        R_WIN = new Key("RIGHT_WIN").register().action();
        MENU = new Key("MENU").register().action();
        R_CONTROL = new Key("RIGHT_CONTROL").register().action();
        ARROW_LEFT = new Key("ARROW_LEFT").register().action();
        ARROW_DOWN = new Key("ARROW_DOWN").register().action();
        ARROW_RIGHT = new Key("ARROW_RIGHT").register().action();
        ARROW_UP = new Key("ARROW_UP").register().action();
        INSERT = new Key("INSERT").register().action();
        HOME = new Key("HOME").register().action();
        PAGE_UP = new Key("PAGE_UP").register().action();
        DELETE = new Key("DELETE").register().action();
        END = new Key("END").register().action();
        PAGE_DOWN = new Key("PAGE_DOWN").register().action();
        PRINT = new Key("PRINT").register().action();
        SCROLL = new Key("SCROLL").register().action();
        PAUSE = new Key("PAUSE").register().action();
        NUM_LOCK = new Key("NUMPAD_LOCK").register().action();
        DIVIDE = new Key("NUMPAD_DIVIDE").register();
        MULTIPLY = new Key("NUMPAD_MULTIPLY").register();
        SUBTRACT = new Key("NUMPAD_SUBTRACT").register();
        ADD = new Key("NUMPAD_ADD").register();
        NUMPAD_EQUAL = new Key("NUMPAD_EQUAL").register();
        NUMPAD_ENTER = new Key("NUMPAD_ENTER").register().action();
        DECIMAL = new Key("DUMPAD_DECIMAL").register();
        NUMPAD0 = new Key("NUMPAD_0").register();
        NUMPAD1 = new Key("NUMPAD_1").register();
        NUMPAD2 = new Key("NUMPAD_2").register();
        NUMPAD3 = new Key("NUMPAD_3").register();
        NUMPAD4 = new Key("NUMPAD_4").register();
        NUMPAD5 = new Key("NUMPAD_5").register();
        NUMPAD6 = new Key("NUMPAD_6").register();
        NUMPAD7 = new Key("NUMPAD_7").register();
        NUMPAD8 = new Key("NUMPAD_8").register();
        NUMPAD9 = new Key("NUMPAD_9").register();
        WORLD_1 = new Key("WORLD_1").register();
        WORLD_2 = new Key("WORLD_2").register();
    }
}
