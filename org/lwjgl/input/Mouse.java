// 
// Decompiled by Procyon v0.6.0
// 

package org.lwjgl.input;

import org.lwjgl.BufferUtils;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue.EventQueueMouseInput;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.MouseInput;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Mouse
{
    public static final int EVENT_SIZE = 22;
    private static int x;
    private static int y;
    private static int absolute_x;
    private static int absolute_y;
    private static int dx;
    private static int dy;
    private static int dwheel;
    private static int buttonCount;
    private static int event_x;
    private static int event_y;
    private static int event_dx;
    private static int event_dy;
    private static int last_event_raw_x;
    private static int last_event_raw_y;
    private static int event_dwheel;
    private static long event_nanos;
    private static int grab_x;
    private static int grab_y;
    private static boolean grabbed;
    private static int eventButton;
    private static boolean eventState;
    private static boolean hasWheel;
    private static IntBuffer coord_buffer;
    private static ByteBuffer buttonBuffer;
    private static ByteBuffer readBuffer;
    private static boolean created;
    private static MouseInput mouseInput;
    
    public static void create() {
        create(new EventQueueMouseInput());
    }
    
    public static void create(final MouseInput mouse) {
        if (Mouse.created) {
            return;
        }
        (Mouse.mouseInput = mouse).create();
        Mouse.created = true;
        Mouse.buttonCount = Mouse.mouseInput.getButtonCount();
        Mouse.buttonBuffer = BufferUtils.createByteBuffer(Mouse.buttonCount);
        Mouse.coord_buffer = BufferUtils.createIntBuffer(3);
        (Mouse.readBuffer = BufferUtils.createByteBuffer(1100)).limit(0);
        setGrabbed(isGrabbed());
    }
    
    public static void setCursorPosition(final int newX, final int newY) {
        isCreatedOrThrown();
        Mouse.event_x = newX;
        Mouse.x = newX;
        Mouse.event_y = newY;
        Mouse.y = newY;
        if (!isGrabbed()) {
            Mouse.mouseInput.setCursorPosition(Mouse.x, Mouse.y);
        }
        else {
            Mouse.grab_x = newX;
            Mouse.grab_y = newY;
        }
    }
    
    private static void isCreatedOrThrown() {
        if (isCreated()) {
            return;
        }
        throw new IllegalStateException("Mouse is not created!");
    }
    
    public static int getDWheel() {
        final int result = Mouse.dwheel;
        Mouse.dwheel = 0;
        return result;
    }
    
    public static int getEventDWheel() {
        return Mouse.event_dwheel;
    }
    
    public static int getEventDX() {
        return Mouse.event_dx;
    }
    
    public static int getEventDY() {
        return Mouse.event_dy;
    }
    
    public static int getEventX() {
        return Mouse.event_x;
    }
    
    public static int getEventY() {
        return Mouse.event_y;
    }
    
    public static int getEventButton() {
        return Mouse.eventButton;
    }
    
    public static boolean getEventButtonState() {
        return Mouse.eventState;
    }
    
    public static boolean next() {
        isCreatedOrThrown();
        if (!Mouse.readBuffer.hasRemaining()) {
            return false;
        }
        Mouse.eventButton = Mouse.readBuffer.get();
        Mouse.eventState = (Mouse.readBuffer.get() != 0);
        if (isGrabbed()) {
            Mouse.event_dx = Mouse.readBuffer.getInt();
            Mouse.event_dy = Mouse.readBuffer.getInt();
            Mouse.event_x += Mouse.event_dx;
            Mouse.event_y += Mouse.event_dy;
            Mouse.last_event_raw_x = Mouse.event_x;
            Mouse.last_event_raw_y = Mouse.event_y;
        }
        else {
            final int newEventX = Mouse.readBuffer.getInt();
            final int newEventY = Mouse.readBuffer.getInt();
            Mouse.event_dx = newEventX - Mouse.last_event_raw_x;
            Mouse.event_dy = newEventY - Mouse.last_event_raw_y;
            Mouse.event_x = newEventX;
            Mouse.event_y = newEventY;
            Mouse.last_event_raw_x = newEventX;
            Mouse.last_event_raw_y = newEventY;
        }
        Mouse.event_dwheel = Mouse.readBuffer.getInt();
        Mouse.event_nanos = Mouse.readBuffer.getLong();
        return true;
    }
    
    public static boolean isButtonDown(final int button) {
        isCreatedOrThrown();
        return button < Mouse.buttonCount && button >= 0 && Mouse.buttonBuffer.get(button) != 0;
    }
    
    public static long getEventNanoseconds() {
        return Mouse.event_nanos;
    }
    
    public static boolean isCreated() {
        return Mouse.created;
    }
    
    public static void poll() {
        isCreatedOrThrown();
        Mouse.mouseInput.poll(Mouse.coord_buffer, Mouse.buttonBuffer);
        final int xPos = Mouse.coord_buffer.get(0);
        final int yPos = Mouse.coord_buffer.get(1);
        final int wheelDelta = Mouse.coord_buffer.get(2);
        if (isGrabbed()) {
            Mouse.dx += xPos;
            Mouse.dy += yPos;
            Mouse.x += xPos;
            Mouse.y += yPos;
            Mouse.absolute_x += xPos;
            Mouse.absolute_y += yPos;
        }
        else {
            Mouse.dx = xPos - Mouse.absolute_x;
            Mouse.dy = yPos - Mouse.absolute_y;
            Mouse.absolute_x = (Mouse.x = xPos);
            Mouse.absolute_y = (Mouse.y = yPos);
        }
        Mouse.dwheel += wheelDelta;
        read();
    }
    
    private static void read() {
        Mouse.readBuffer.compact();
        Mouse.mouseInput.read(Mouse.readBuffer);
        Mouse.readBuffer.flip();
    }
    
    public static int getDX() {
        final int result = Mouse.dx;
        Mouse.dx = 0;
        return result;
    }
    
    public static int getDY() {
        final int result = Mouse.dy;
        Mouse.dy = 0;
        return result;
    }
    
    public static int getX() {
        return Mouse.x;
    }
    
    public static int getY() {
        return Mouse.y;
    }
    
    public static void setGrabbed(final boolean grabbed) {
        final boolean lastGrabbedState = Mouse.grabbed;
        Mouse.grabbed = grabbed;
        if (!isCreated()) {
            return;
        }
        Mouse.mouseInput.grab(grabbed);
        if (grabbed && !lastGrabbedState) {
            Mouse.grab_x = Mouse.x;
            Mouse.grab_y = Mouse.y;
        }
        else if (!grabbed && lastGrabbedState) {
            Mouse.mouseInput.setCursorPosition(Mouse.grab_x, Mouse.grab_y);
        }
        poll();
        Mouse.event_x = Mouse.x;
        Mouse.event_y = Mouse.y;
        Mouse.last_event_raw_x = Mouse.x;
        Mouse.last_event_raw_y = Mouse.y;
        resetMouse();
    }
    
    private static void resetMouse() {
        Mouse.dx = (Mouse.dy = (Mouse.dwheel = 0));
        Mouse.readBuffer.position(Mouse.readBuffer.limit());
    }
    
    public static boolean isGrabbed() {
        return Mouse.grabbed;
    }
    
    public static boolean hasWheel() {
        return Mouse.hasWheel;
    }
    
    public static void destroy() {
        if (!Mouse.created) {
            return;
        }
        Mouse.created = false;
        Mouse.coord_buffer = null;
        Mouse.buttonBuffer = null;
        Mouse.mouseInput.dispose();
    }
    
    public static boolean isInsideWindow() {
        return Mouse.mouseInput.isInsideWindow();
    }
    
    public static void setRawMouseInput(final boolean rawMouseInput) {
        Mouse.mouseInput.setRawMouseInput(rawMouseInput);
    }
    
    static {
        Mouse.hasWheel = true;
        Mouse.created = false;
    }
}
