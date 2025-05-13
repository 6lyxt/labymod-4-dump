// 
// Decompiled by Procyon v0.6.0
// 

package org.lwjgl.input;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.lwjgl.BufferUtils;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue.EventQueueKeyboardInput;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput;
import java.nio.ByteBuffer;

public class Keyboard
{
    public static final int EVENT_SIZE = 18;
    public static final int KEYBOARD_SIZE = 349;
    private static final int BUFFER_SIZE = 50;
    private static KeyEvent current_event;
    private static KeyEvent temp_event;
    private static ByteBuffer key_down_buffer;
    private static boolean created;
    private static boolean repeat_enabled;
    private static ByteBuffer readBuffer;
    private static KeyboardInput keyboardInput;
    private static final String[] KEY_NAMES;
    private static final Object2IntMap<String> NAME_TO_KEY;
    public static final int CHAR_NONE = 0;
    public static final int KEY_NONE;
    public static final int KEY_SPACE;
    public static final int KEY_APOSTROPHE;
    public static final int KEY_COMMA;
    public static final int KEY_MINUS;
    public static final int KEY_PERIOD;
    public static final int KEY_SLASH;
    public static final int KEY_0;
    public static final int KEY_1;
    public static final int KEY_2;
    public static final int KEY_3;
    public static final int KEY_4;
    public static final int KEY_5;
    public static final int KEY_6;
    public static final int KEY_7;
    public static final int KEY_8;
    public static final int KEY_9;
    public static final int KEY_SEMICOLON;
    public static final int KEY_EQUALS;
    public static final int KEY_A;
    public static final int KEY_B;
    public static final int KEY_C;
    public static final int KEY_D;
    public static final int KEY_E;
    public static final int KEY_F;
    public static final int KEY_G;
    public static final int KEY_H;
    public static final int KEY_I;
    public static final int KEY_J;
    public static final int KEY_K;
    public static final int KEY_L;
    public static final int KEY_M;
    public static final int KEY_N;
    public static final int KEY_O;
    public static final int KEY_P;
    public static final int KEY_Q;
    public static final int KEY_R;
    public static final int KEY_S;
    public static final int KEY_T;
    public static final int KEY_U;
    public static final int KEY_V;
    public static final int KEY_W;
    public static final int KEY_X;
    public static final int KEY_Y;
    public static final int KEY_Z;
    public static final int KEY_LBRACKET;
    public static final int KEY_BACKSLASH;
    public static final int KEY_RBRACKET;
    public static final int KEY_GRAVE;
    public static final int KEY_WORLD_1;
    public static final int KEY_WORLD_2;
    public static final int KEY_ESCAPE;
    public static final int KEY_RETURN;
    public static final int KEY_TAB;
    public static final int KEY_BACK;
    public static final int KEY_INSERT;
    public static final int KEY_DELETE;
    public static final int KEY_RIGHT;
    public static final int KEY_LEFT;
    public static final int KEY_DOWN;
    public static final int KEY_UP;
    public static final int KEY_PRIOR;
    public static final int KEY_NEXT;
    public static final int KEY_HOME;
    public static final int KEY_END;
    public static final int KEY_CAPITAL;
    public static final int KEY_SCROLL;
    public static final int KEY_NUMLOCK;
    public static final int KEY_PRINT_SCREEN;
    public static final int KEY_PAUSE;
    public static final int KEY_F1;
    public static final int KEY_F2;
    public static final int KEY_F3;
    public static final int KEY_F4;
    public static final int KEY_F5;
    public static final int KEY_F6;
    public static final int KEY_F7;
    public static final int KEY_F8;
    public static final int KEY_F9;
    public static final int KEY_F10;
    public static final int KEY_F11;
    public static final int KEY_F12;
    public static final int KEY_F13;
    public static final int KEY_F14;
    public static final int KEY_F15;
    public static final int KEY_F16;
    public static final int KEY_F17;
    public static final int KEY_F18;
    public static final int KEY_F19;
    public static final int KEY_F20;
    public static final int KEY_F21;
    public static final int KEY_F22;
    public static final int KEY_F23;
    public static final int KEY_F24;
    public static final int KEY_F25;
    public static final int KEY_NUMPAD0;
    public static final int KEY_NUMPAD1;
    public static final int KEY_NUMPAD2;
    public static final int KEY_NUMPAD3;
    public static final int KEY_NUMPAD4;
    public static final int KEY_NUMPAD5;
    public static final int KEY_NUMPAD6;
    public static final int KEY_NUMPAD7;
    public static final int KEY_NUMPAD8;
    public static final int KEY_NUMPAD9;
    public static final int KEY_DECIMAL;
    public static final int KEY_DIVIDE;
    public static final int KEY_MULTIPLY;
    public static final int KEY_SUBTRACT;
    public static final int KEY_ADD;
    public static final int KEY_NUMPADENTER;
    public static final int KEY_NUMPADEQUALS;
    public static final int KEY_LSHIFT;
    public static final int KEY_LCONTROL;
    public static final int KEY_LMENU;
    public static final int KEY_LMETA;
    public static final int KEY_RSHIFT;
    public static final int KEY_RCONTROL;
    public static final int KEY_RMENU;
    public static final int KEY_RMETA;
    public static final int KEY_MENU;
    
    public static void create() {
        create(new EventQueueKeyboardInput());
    }
    
    public static void create(final KeyboardInput input) {
        if (Keyboard.created) {
            return;
        }
        (Keyboard.keyboardInput = input).create();
        Keyboard.created = true;
        Keyboard.readBuffer = ByteBuffer.allocate(900);
        reset();
    }
    
    public static boolean next() {
        if (!Keyboard.created) {
            throw new IllegalStateException("Keyboard must be created before you can read events");
        }
        boolean result;
        while ((result = readNext(Keyboard.current_event)) && Keyboard.current_event.repeat && !Keyboard.repeat_enabled) {}
        return result;
    }
    
    public static void poll() {
        if (!Keyboard.created) {
            throw new IllegalStateException("Keyboard not created");
        }
        Keyboard.keyboardInput.poll(Keyboard.key_down_buffer);
        read();
    }
    
    public static String getKeyName(final int keyCode) {
        return Keyboard.KEY_NAMES[keyCode];
    }
    
    public static int getKeyIndex(final String name) {
        return Keyboard.NAME_TO_KEY.getOrDefault((Object)name, Keyboard.KEY_NONE);
    }
    
    public static int getNumKeyboardEvents() {
        if (Keyboard.created) {
            throw new IllegalStateException("Keyboard must be created before you can read events");
        }
        final int oldPosition = Keyboard.readBuffer.position();
        int eventCount = 0;
        while (readNext(Keyboard.temp_event) && (!Keyboard.temp_event.repeat || Keyboard.repeat_enabled)) {
            ++eventCount;
        }
        Keyboard.readBuffer.position(oldPosition);
        return eventCount;
    }
    
    public static int getKeyCount() {
        return Keyboard.NAME_TO_KEY.size();
    }
    
    public static char getEventCharacter() {
        return (char)Keyboard.current_event.character;
    }
    
    public static int getEventKey() {
        return Keyboard.current_event.key;
    }
    
    public static boolean getEventKeyState() {
        return Keyboard.current_event.state;
    }
    
    public static long getEventNanoseconds() {
        return Keyboard.current_event.time;
    }
    
    public static boolean isRepeatEvent() {
        return Keyboard.current_event.repeat;
    }
    
    public static boolean isKeyDown(final int key) {
        if (!isCreated()) {
            throw new IllegalStateException("Keyboard must be created before your can query key state");
        }
        return Keyboard.key_down_buffer.get(key) != 0;
    }
    
    public static boolean isCreated() {
        return Keyboard.created;
    }
    
    public static void destroy() {
        if (!isCreated()) {
            return;
        }
        Keyboard.created = false;
        Keyboard.keyboardInput.dispose();
        reset();
    }
    
    public static void enableRepeatEvents(final boolean enable) {
        Keyboard.repeat_enabled = enable;
    }
    
    public static boolean areRepeatEventsEnabled() {
        return Keyboard.repeat_enabled;
    }
    
    private static void reset() {
        Keyboard.readBuffer.limit(0);
        for (int index = 0; index < Keyboard.key_down_buffer.remaining(); ++index) {
            Keyboard.key_down_buffer.put(index, (byte)0);
        }
        Keyboard.current_event.reset();
    }
    
    private static void read() {
        Keyboard.readBuffer.compact();
        Keyboard.keyboardInput.read(Keyboard.readBuffer);
        Keyboard.readBuffer.flip();
    }
    
    private static boolean readNext(final KeyEvent event) {
        if (!Keyboard.readBuffer.hasRemaining()) {
            return false;
        }
        event.key = Keyboard.readBuffer.getInt();
        event.state = (Keyboard.readBuffer.get() != 0);
        event.character = Keyboard.readBuffer.getInt();
        event.time = Keyboard.readBuffer.getLong();
        event.repeat = (Keyboard.readBuffer.get() == 1);
        return true;
    }
    
    private static int register(final String name, final int lwjglCode) {
        Keyboard.KEY_NAMES[lwjglCode] = name;
        Keyboard.NAME_TO_KEY.put((Object)name, lwjglCode);
        return lwjglCode;
    }
    
    static {
        Keyboard.current_event = new KeyEvent();
        Keyboard.temp_event = new KeyEvent();
        Keyboard.key_down_buffer = BufferUtils.createByteBuffer(349);
        KEY_NAMES = new String[349];
        NAME_TO_KEY = (Object2IntMap)new Object2IntOpenHashMap(253);
        KEY_NONE = register("NONE", 0);
        KEY_SPACE = register("SPACE", 57);
        KEY_APOSTROPHE = register("APOSTROPHE", 40);
        KEY_COMMA = register("COMMA", 51);
        KEY_MINUS = register("MINUS", 12);
        KEY_PERIOD = register("PERIOD", 52);
        KEY_SLASH = register("SLASH", 53);
        KEY_0 = register("0", 11);
        KEY_1 = register("1", 2);
        KEY_2 = register("2", 3);
        KEY_3 = register("3", 4);
        KEY_4 = register("4", 5);
        KEY_5 = register("5", 6);
        KEY_6 = register("6", 7);
        KEY_7 = register("7", 8);
        KEY_8 = register("8", 9);
        KEY_9 = register("9", 10);
        KEY_SEMICOLON = register("SEMICOLON", 39);
        KEY_EQUALS = register("EQUALS", 13);
        KEY_A = register("A", 30);
        KEY_B = register("B", 48);
        KEY_C = register("C", 46);
        KEY_D = register("D", 32);
        KEY_E = register("E", 18);
        KEY_F = register("F", 33);
        KEY_G = register("G", 34);
        KEY_H = register("H", 35);
        KEY_I = register("I", 23);
        KEY_J = register("J", 36);
        KEY_K = register("K", 37);
        KEY_L = register("L", 38);
        KEY_M = register("M", 50);
        KEY_N = register("N", 49);
        KEY_O = register("O", 24);
        KEY_P = register("P", 25);
        KEY_Q = register("Q", 16);
        KEY_R = register("R", 19);
        KEY_S = register("S", 31);
        KEY_T = register("T", 20);
        KEY_U = register("U", 22);
        KEY_V = register("V", 47);
        KEY_W = register("W", 17);
        KEY_X = register("X", 45);
        KEY_Y = register("Y", 21);
        KEY_Z = register("Z", 44);
        KEY_LBRACKET = register("LBRACKET", 26);
        KEY_BACKSLASH = register("BACKSLASH", 43);
        KEY_RBRACKET = register("RBRACKET", 27);
        KEY_GRAVE = register("GRAVE", 41);
        KEY_WORLD_1 = register("WORLD_1", 167);
        KEY_WORLD_2 = register("WORLD_2", 316);
        KEY_ESCAPE = register("ESCAPE", 1);
        KEY_RETURN = register("RETURN", 28);
        KEY_TAB = register("TAB", 15);
        KEY_BACK = register("BACK", 14);
        KEY_INSERT = register("INSERT", 210);
        KEY_DELETE = register("DELETE", 211);
        KEY_RIGHT = register("RIGHT", 205);
        KEY_LEFT = register("LEFT", 203);
        KEY_DOWN = register("DOWN", 208);
        KEY_UP = register("UP", 200);
        KEY_PRIOR = register("PRIOR", 201);
        KEY_NEXT = register("NEXT", 209);
        KEY_HOME = register("HOME", 199);
        KEY_END = register("END", 207);
        KEY_CAPITAL = register("CAPITAL", 58);
        KEY_SCROLL = register("SCROLL", 70);
        KEY_NUMLOCK = register("NUMLOCK", 69);
        KEY_PRINT_SCREEN = register("PRINT_SCREEN", 28);
        KEY_PAUSE = register("PAUSE", 197);
        KEY_F1 = register("F1", 59);
        KEY_F2 = register("F2", 60);
        KEY_F3 = register("F3", 61);
        KEY_F4 = register("F4", 62);
        KEY_F5 = register("F5", 63);
        KEY_F6 = register("F6", 64);
        KEY_F7 = register("F7", 65);
        KEY_F8 = register("F8", 66);
        KEY_F9 = register("F9", 67);
        KEY_F10 = register("F10", 68);
        KEY_F11 = register("F11", 87);
        KEY_F12 = register("F12", 88);
        KEY_F13 = register("F13", 100);
        KEY_F14 = register("F14", 101);
        KEY_F15 = register("F15", 102);
        KEY_F16 = register("F16", 103);
        KEY_F17 = register("F17", 104);
        KEY_F18 = register("F18", 105);
        KEY_F19 = register("F19", 113);
        KEY_F20 = register("F20", 309);
        KEY_F21 = register("F21", 310);
        KEY_F22 = register("F22", 311);
        KEY_F23 = register("F23", 312);
        KEY_F24 = register("F24", 313);
        KEY_F25 = register("F25", 314);
        KEY_NUMPAD0 = register("NUMPAD0", 82);
        KEY_NUMPAD1 = register("NUMPAD1", 79);
        KEY_NUMPAD2 = register("NUMPAD2", 80);
        KEY_NUMPAD3 = register("NUMPAD3", 81);
        KEY_NUMPAD4 = register("NUMPAD4", 75);
        KEY_NUMPAD5 = register("NUMPAD5", 76);
        KEY_NUMPAD6 = register("NUMPAD6", 77);
        KEY_NUMPAD7 = register("NUMPAD7", 71);
        KEY_NUMPAD8 = register("NUMPAD8", 72);
        KEY_NUMPAD9 = register("NUMPAD9", 73);
        KEY_DECIMAL = register("DECIMAL", 83);
        KEY_DIVIDE = register("DIVIDE", 181);
        KEY_MULTIPLY = register("MULTIPLY", 55);
        KEY_SUBTRACT = register("SUBTRACT", 74);
        KEY_ADD = register("ADD", 78);
        KEY_NUMPADENTER = register("NUMPADENTER", 156);
        KEY_NUMPADEQUALS = register("NUMPADEQUALS", 141);
        KEY_LSHIFT = register("LSHIFT", 42);
        KEY_LCONTROL = register("LCONTROL", 29);
        KEY_LMENU = register("LMENU", 56);
        KEY_LMETA = register("LMETA", 219);
        KEY_RSHIFT = register("RSHIFT", 54);
        KEY_RCONTROL = register("RCONTROL", 157);
        KEY_RMENU = register("RMENU", 184);
        KEY_RMETA = register("RMETA", 220);
        KEY_MENU = register("MENU", 348);
    }
    
    private static final class KeyEvent
    {
        private int character;
        private int key;
        private boolean state;
        private boolean repeat;
        private long time;
        
        private void reset() {
            this.character = 0;
            this.key = 0;
            this.state = false;
            this.repeat = false;
        }
        
        @Override
        public String toString() {
            return "KeyEvent{character=" + this.character + ", key=" + this.key + ", state=" + this.state + ", repeat=" + this.repeat + ", time=" + this.time;
        }
    }
}
