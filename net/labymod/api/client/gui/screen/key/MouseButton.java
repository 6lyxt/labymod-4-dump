// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;

public class MouseButton extends Key
{
    public static final Key LEFT;
    public static final Key RIGHT;
    public static final Key MIDDLE;
    public static final Key M4;
    public static final Key M5;
    public static final Key M6;
    public static final Key M7;
    public static final Key M8;
    
    protected MouseButton(final String name) {
        super(name, "labymod.keys.mouse.");
    }
    
    @NotNull
    public static MouseButton get(final int keyCode) {
        MouseButton mouseButton = KeyMapper.getMouseButton(keyCode);
        if (mouseButton != null) {
            return mouseButton;
        }
        mouseButton = new MouseButton("M" + (keyCode + 1));
        mouseButton.unknown = true;
        mouseButton.id = keyCode;
        Laby.references().keyMapper().register(mouseButton, keyCode, keyCode);
        return mouseButton;
    }
    
    public boolean isLeft() {
        return this == MouseButton.LEFT;
    }
    
    public boolean isRight() {
        return this == MouseButton.RIGHT;
    }
    
    public boolean isMiddle() {
        return this == MouseButton.MIDDLE;
    }
    
    static {
        LEFT = new MouseButton("LEFT").register().action();
        RIGHT = new MouseButton("RIGHT").register().action();
        MIDDLE = new MouseButton("MIDDLE").register().action();
        M4 = new MouseButton("M4").register().action();
        M5 = new MouseButton("M5").register().action();
        M6 = new MouseButton("M6").register().action();
        M7 = new MouseButton("M7").register().action();
        M8 = new MouseButton("M8").register().action();
    }
}
