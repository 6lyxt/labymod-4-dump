// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue;

import java.nio.IntBuffer;
import net.labymod.api.util.time.TimeUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.Display;
import net.labymod.api.util.Buffers;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import java.nio.ByteBuffer;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput;

public class EventQueueMouseInput implements MouseInput
{
    private static final String DISABLE_RAW_INPUT = "org.lwjgl.input.Mouse.disableRawInput";
    private final byte[] buttonStates;
    private final EventQueue eventQueue;
    private final ByteBuffer tempBuffer;
    private int lastX;
    private int lastY;
    private int accumulatedX;
    private int accumulatedY;
    private int accumulatedZ;
    private boolean grabbed;
    private boolean insideWindow;
    private GLFWMouseButtonCallbackI mouseButtonCallback;
    private GLFWCursorPosCallbackI cursorPosCallback;
    private GLFWCursorEnterCallbackI cursorEnterCallback;
    private GLFWScrollCallbackI scrollCallback;
    
    public EventQueueMouseInput() {
        this.buttonStates = new byte[this.getButtonCount()];
        this.eventQueue = new EventQueue(22);
        this.tempBuffer = Buffers.createByteBuffer(22);
    }
    
    @Override
    public void create() {
        final long windowHandle = Display.getWindowHandle();
        this.setRawMouseMotion(windowHandle, true);
        this.mouseButtonCallback = this::onMouseButton;
        this.cursorPosCallback = this::onCursorPos;
        this.cursorEnterCallback = this::onCursorEnter;
        this.scrollCallback = this::onScroll;
        GLFW.glfwSetMouseButtonCallback(windowHandle, this.mouseButtonCallback);
        GLFW.glfwSetCursorPosCallback(windowHandle, this.cursorPosCallback);
        GLFW.glfwSetCursorEnterCallback(windowHandle, this.cursorEnterCallback);
        GLFW.glfwSetScrollCallback(windowHandle, this.scrollCallback);
    }
    
    private void onScroll(final long window, final double xOffset, final double yOffset) {
        this.accumulatedZ += (int)yOffset;
        this.writeMouseEvent((byte)(-1), (byte)0, (int)yOffset, TimeUtil.getNanoTime());
    }
    
    private void onCursorPos(final long window, final double newX, final double newY) {
        final int x = (int)newX;
        final int y = Display.getHeight() - 1 - (int)newY;
        final int deltaX = x - this.lastX;
        final int deltaY = y - this.lastY;
        if (deltaX != 0 || deltaY != 0) {
            this.accumulatedX += deltaX;
            this.accumulatedY += deltaY;
            this.lastX = x;
            this.lastY = y;
            final long time = TimeUtil.getNanoTime();
            if (this.grabbed) {
                this.writeMouseEvent((byte)(-1), (byte)0, deltaX, deltaY, 0, time);
            }
            else {
                this.writeMouseEvent((byte)(-1), (byte)0, x, y, 0, time);
            }
        }
    }
    
    private void onCursorEnter(final long window, final boolean entered) {
        this.insideWindow = entered;
    }
    
    private void onMouseButton(final long window, final int button, final int action, final int mods) {
        final byte state = (byte)((action == 1) ? 1 : 0);
        this.writeMouseEvent((byte)button, state, 0, TimeUtil.getNanoTime());
        if (button < this.buttonStates.length) {
            this.buttonStates[button] = state;
        }
    }
    
    @Override
    public void poll(final IntBuffer positionBuffer, final ByteBuffer buttonBuffer) {
        if (this.grabbed) {
            positionBuffer.put(0, this.accumulatedX);
            positionBuffer.put(1, this.accumulatedY);
        }
        else {
            positionBuffer.put(0, this.lastX);
            positionBuffer.put(1, this.lastY);
        }
        positionBuffer.put(2, this.accumulatedZ);
        final int accumulatedX = 0;
        this.accumulatedZ = accumulatedX;
        this.accumulatedY = accumulatedX;
        this.accumulatedX = accumulatedX;
        for (int i = 0; i < this.buttonStates.length; ++i) {
            buttonBuffer.put(i, this.buttonStates[i]);
        }
    }
    
    @Override
    public void read(final ByteBuffer buffer) {
        this.eventQueue.fireEvents(buffer);
    }
    
    @Override
    public void setCursorPosition(final int x, final int y) {
        final long windowHandle = Display.getWindowHandle();
        GLFW.glfwSetCursorPos(windowHandle, (double)x, (double)y);
        this.onCursorPos(windowHandle, x, y);
    }
    
    @Override
    public void grab(final boolean grab) {
        GLFW.glfwSetInputMode(Display.getWindowHandle(), 208897, grab ? 212995 : 212993);
        this.grabbed = grab;
        this.reset();
    }
    
    @Override
    public boolean isInsideWindow() {
        return this.insideWindow;
    }
    
    @Override
    public int getButtonCount() {
        return 8;
    }
    
    @Override
    public void setRawMouseInput(final boolean rawMouseInput) {
        this.setRawMouseMotion(Display.getWindowHandle(), rawMouseInput);
    }
    
    @Override
    public void dispose() {
    }
    
    private void setRawMouseMotion(final long handle, final boolean state) {
        if (GLFW.glfwRawMouseMotionSupported() && !Boolean.getBoolean("org.lwjgl.input.Mouse.disableRawInput")) {
            GLFW.glfwSetInputMode(handle, 208901, (int)(state ? 1 : 0));
        }
    }
    
    private void writeMouseEvent(final byte button, final byte state, final int delta, final long time) {
        if (this.grabbed) {
            this.writeMouseEvent(button, state, 0, 0, delta, time);
        }
        else {
            this.writeMouseEvent(button, state, this.lastX, this.lastY, delta, time);
        }
    }
    
    private void writeMouseEvent(final byte button, final byte state, final int x, final int y, final int delta, final long time) {
        this.tempBuffer.clear();
        this.tempBuffer.put(button);
        this.tempBuffer.put(state);
        this.tempBuffer.putInt(x);
        this.tempBuffer.putInt(y);
        this.tempBuffer.putInt(delta);
        this.tempBuffer.putLong(time);
        this.tempBuffer.flip();
        this.eventQueue.putEvent(this.tempBuffer);
    }
    
    private void reset() {
        this.eventQueue.clear();
        final int n = 0;
        this.accumulatedY = n;
        this.accumulatedX = n;
    }
}
