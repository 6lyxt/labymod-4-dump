// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue;

import org.lwjgl.input.Keyboard;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import net.labymod.api.util.time.TimeUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.Display;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import java.nio.ByteBuffer;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.KeyboardInput;

public class EventQueueKeyboardInput implements KeyboardInput
{
    private static final byte[] KEY_DOWN_BUFFER;
    private static final EventQueue EVENT_QUEUE;
    private static final ByteBuffer TEMP_EVENT;
    private GLFWKeyCallbackI keyCallback;
    private GLFWCharCallbackI charCallback;
    private int retainedKey;
    private int retainedChar;
    private long retainedMillis;
    private byte retainedState;
    private boolean retainedRepeat;
    private boolean retainedEvent;
    private static final int[] GLFW_TO_LWJGL;
    
    @Override
    public void dispose() {
    }
    
    @Override
    public void create() {
        final long windowHandle = Display.getWindowHandle();
        this.keyCallback = this::onKey;
        this.charCallback = this::onChar;
        GLFW.glfwSetKeyCallback(windowHandle, this.keyCallback);
        GLFW.glfwSetCharCallback(windowHandle, this.charCallback);
        GLFW.glfwSetInputMode(windowHandle, 208900, 1);
    }
    
    private void onChar(final long windowHandle, final int codepoint) {
        if (this.retainedEvent && this.retainedChar != 0) {
            this.flushRetainedEvent();
        }
        if (!this.retainedEvent) {
            this.writeKeyboardEvent(0, (byte)0, codepoint, TimeUtil.getMillis(), true);
        }
        else {
            this.retainedChar = codepoint;
        }
    }
    
    private void onKey(final long windowHandle, int key, final int scancode, final int action, final int mods) {
        key = GLFWUtil.getNumpadActionKey(key, scancode, mods);
        final int translatedKey = this.translate(key);
        final boolean repeat = action == 2;
        this.flushRetainedEvent();
        this.retainedEvent = true;
        this.retainedKey = translatedKey;
        if (action == 1) {
            EventQueueKeyboardInput.KEY_DOWN_BUFFER[translatedKey] = 1;
        }
        else if (action == 0) {
            EventQueueKeyboardInput.KEY_DOWN_BUFFER[translatedKey] = 0;
        }
        this.retainedState = EventQueueKeyboardInput.KEY_DOWN_BUFFER[translatedKey];
        this.retainedMillis = TimeUtil.getMillis();
        this.retainedChar = 0;
        this.retainedRepeat = repeat;
    }
    
    private void flushRetainedEvent() {
        if (!this.retainedEvent) {
            return;
        }
        this.retainedEvent = false;
        this.writeKeyboardEvent(this.retainedKey, this.retainedState, this.retainedChar, this.retainedMillis, this.retainedRepeat);
    }
    
    @Override
    public void poll(final ByteBuffer buffer) {
        final int oldPosition = buffer.position();
        buffer.put(EventQueueKeyboardInput.KEY_DOWN_BUFFER);
        buffer.position(oldPosition);
    }
    
    @Override
    public void read(final ByteBuffer buffer) {
        this.flushRetainedEvent();
        EventQueueKeyboardInput.EVENT_QUEUE.fireEvents(buffer);
    }
    
    private int translate(final int key) {
        if (key == -1) {
            return EventQueueKeyboardInput.GLFW_TO_LWJGL[0];
        }
        if (key < EventQueueKeyboardInput.GLFW_TO_LWJGL.length) {
            return EventQueueKeyboardInput.GLFW_TO_LWJGL[key];
        }
        return key;
    }
    
    private void writeKeyboardEvent(final int keycode, final byte state, final int character, final long time, final boolean repeat) {
        EventQueueKeyboardInput.TEMP_EVENT.clear();
        EventQueueKeyboardInput.TEMP_EVENT.putInt(keycode);
        EventQueueKeyboardInput.TEMP_EVENT.put(state);
        EventQueueKeyboardInput.TEMP_EVENT.putInt(character);
        EventQueueKeyboardInput.TEMP_EVENT.putLong(time);
        EventQueueKeyboardInput.TEMP_EVENT.put((byte)(repeat ? 1 : 0));
        EventQueueKeyboardInput.TEMP_EVENT.flip();
        EventQueueKeyboardInput.EVENT_QUEUE.putEvent(EventQueueKeyboardInput.TEMP_EVENT);
    }
    
    static {
        KEY_DOWN_BUFFER = new byte[349];
        EVENT_QUEUE = new EventQueue(18);
        TEMP_EVENT = ByteBuffer.allocate(18);
        (GLFW_TO_LWJGL = new int[349])[0] = Keyboard.KEY_NONE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[32] = Keyboard.KEY_SPACE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[39] = Keyboard.KEY_APOSTROPHE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[44] = Keyboard.KEY_COMMA;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[45] = Keyboard.KEY_MINUS;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[46] = Keyboard.KEY_PERIOD;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[47] = Keyboard.KEY_SLASH;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[48] = Keyboard.KEY_0;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[49] = Keyboard.KEY_1;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[50] = Keyboard.KEY_2;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[51] = Keyboard.KEY_3;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[52] = Keyboard.KEY_4;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[53] = Keyboard.KEY_5;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[54] = Keyboard.KEY_6;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[55] = Keyboard.KEY_7;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[56] = Keyboard.KEY_8;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[57] = Keyboard.KEY_9;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[59] = Keyboard.KEY_SEMICOLON;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[61] = Keyboard.KEY_EQUALS;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[65] = Keyboard.KEY_A;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[66] = Keyboard.KEY_B;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[67] = Keyboard.KEY_C;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[68] = Keyboard.KEY_D;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[69] = Keyboard.KEY_E;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[70] = Keyboard.KEY_F;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[71] = Keyboard.KEY_G;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[72] = Keyboard.KEY_H;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[73] = Keyboard.KEY_I;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[74] = Keyboard.KEY_J;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[75] = Keyboard.KEY_K;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[76] = Keyboard.KEY_L;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[77] = Keyboard.KEY_M;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[78] = Keyboard.KEY_N;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[79] = Keyboard.KEY_O;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[80] = Keyboard.KEY_P;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[81] = Keyboard.KEY_Q;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[82] = Keyboard.KEY_R;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[83] = Keyboard.KEY_S;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[84] = Keyboard.KEY_T;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[85] = Keyboard.KEY_U;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[86] = Keyboard.KEY_V;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[87] = Keyboard.KEY_W;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[88] = Keyboard.KEY_X;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[89] = Keyboard.KEY_Y;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[90] = Keyboard.KEY_Z;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[91] = Keyboard.KEY_LBRACKET;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[92] = Keyboard.KEY_BACKSLASH;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[93] = Keyboard.KEY_RBRACKET;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[96] = Keyboard.KEY_GRAVE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[161] = Keyboard.KEY_WORLD_1;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[162] = Keyboard.KEY_WORLD_2;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[256] = Keyboard.KEY_ESCAPE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[257] = Keyboard.KEY_RETURN;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[258] = Keyboard.KEY_TAB;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[259] = Keyboard.KEY_BACK;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[260] = Keyboard.KEY_INSERT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[261] = Keyboard.KEY_DELETE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[262] = Keyboard.KEY_RIGHT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[263] = Keyboard.KEY_LEFT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[264] = Keyboard.KEY_DOWN;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[265] = Keyboard.KEY_UP;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[266] = Keyboard.KEY_PRIOR;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[267] = Keyboard.KEY_NEXT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[268] = Keyboard.KEY_HOME;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[269] = Keyboard.KEY_END;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[280] = Keyboard.KEY_CAPITAL;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[281] = Keyboard.KEY_SCROLL;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[282] = Keyboard.KEY_NUMLOCK;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[283] = Keyboard.KEY_PRINT_SCREEN;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[284] = Keyboard.KEY_PAUSE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[290] = Keyboard.KEY_F1;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[291] = Keyboard.KEY_F2;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[292] = Keyboard.KEY_F3;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[293] = Keyboard.KEY_F4;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[294] = Keyboard.KEY_F5;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[295] = Keyboard.KEY_F6;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[296] = Keyboard.KEY_F7;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[297] = Keyboard.KEY_F8;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[298] = Keyboard.KEY_F9;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[299] = Keyboard.KEY_F10;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[300] = Keyboard.KEY_F11;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[301] = Keyboard.KEY_F12;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[302] = Keyboard.KEY_F13;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[303] = Keyboard.KEY_F14;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[304] = Keyboard.KEY_F15;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[305] = Keyboard.KEY_F16;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[306] = Keyboard.KEY_F17;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[307] = Keyboard.KEY_F18;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[308] = Keyboard.KEY_F19;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[309] = Keyboard.KEY_F20;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[310] = Keyboard.KEY_F21;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[311] = Keyboard.KEY_F22;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[312] = Keyboard.KEY_F23;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[313] = Keyboard.KEY_F24;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[314] = Keyboard.KEY_F25;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[320] = Keyboard.KEY_NUMPAD0;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[321] = Keyboard.KEY_NUMPAD1;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[322] = Keyboard.KEY_NUMPAD2;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[323] = Keyboard.KEY_NUMPAD3;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[324] = Keyboard.KEY_NUMPAD4;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[325] = Keyboard.KEY_NUMPAD5;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[326] = Keyboard.KEY_NUMPAD6;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[327] = Keyboard.KEY_NUMPAD7;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[328] = Keyboard.KEY_NUMPAD8;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[329] = Keyboard.KEY_NUMPAD9;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[330] = Keyboard.KEY_DECIMAL;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[331] = Keyboard.KEY_DIVIDE;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[332] = Keyboard.KEY_MULTIPLY;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[333] = Keyboard.KEY_SUBTRACT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[334] = Keyboard.KEY_ADD;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[335] = Keyboard.KEY_NUMPADENTER;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[336] = Keyboard.KEY_NUMPADEQUALS;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[340] = Keyboard.KEY_LSHIFT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[341] = Keyboard.KEY_LCONTROL;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[342] = Keyboard.KEY_LMENU;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[343] = Keyboard.KEY_LMETA;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[344] = Keyboard.KEY_RSHIFT;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[345] = Keyboard.KEY_RCONTROL;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[346] = Keyboard.KEY_RMENU;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[347] = Keyboard.KEY_RMETA;
        EventQueueKeyboardInput.GLFW_TO_LWJGL[348] = Keyboard.KEY_MENU;
    }
}
