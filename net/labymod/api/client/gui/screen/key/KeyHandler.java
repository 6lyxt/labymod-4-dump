// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key;

import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.Laby;

public final class KeyHandler
{
    public static boolean isControlDown() {
        return isLeftControlDown() || isRightControlDown();
    }
    
    public static boolean isRightControlDown() {
        return isMacOS() ? isKeyPressed(Key.R_WIN) : isKeyPressed(Key.R_CONTROL);
    }
    
    public static boolean isLeftControlDown() {
        return isMacOS() ? isKeyPressed(Key.L_WIN) : isKeyPressed(Key.L_CONTROL);
    }
    
    public static boolean isAltDown() {
        return isKeyPressed(Key.L_ALT) || isKeyPressed(Key.R_ALT);
    }
    
    public static boolean isShiftDown() {
        return isKeyPressed(Key.L_SHIFT) || isKeyPressed(Key.R_SHIFT);
    }
    
    public static boolean isSelectAll(final Key key) {
        return key == Key.A && isControlDown() && !isShiftDown() && !isAltDown();
    }
    
    public static boolean isSelectLeft(final Key key) {
        return key == Key.HOME && !isControlDown() && isShiftDown() && !isAltDown();
    }
    
    public static boolean isSelectRight(final Key key) {
        return key == Key.END && !isControlDown() && isShiftDown() && !isAltDown();
    }
    
    public static boolean isCopy(final Key key) {
        return key == Key.C && isControlDown() && !isShiftDown() && !isAltDown();
    }
    
    public static boolean isPaste(final Key key) {
        return key == Key.V && isControlDown() && !isShiftDown() && !isAltDown();
    }
    
    public static boolean isCut(final Key key) {
        return key == Key.X && isControlDown() && !isShiftDown() && !isAltDown();
    }
    
    public static boolean isControl(final Key key) {
        return key == (isMacOS() ? Key.L_WIN : Key.L_CONTROL) || key == (isMacOS() ? Key.R_WIN : Key.R_CONTROL);
    }
    
    public static boolean isEnter(final Key key) {
        return key == Key.NUMPAD_ENTER || key == Key.ENTER;
    }
    
    private static boolean isMacOS() {
        return Laby.labyAPI().minecraft().isMacOS();
    }
    
    private static boolean isKeyPressed(final Key key) {
        return KeyMapper.isPressed(key);
    }
}
