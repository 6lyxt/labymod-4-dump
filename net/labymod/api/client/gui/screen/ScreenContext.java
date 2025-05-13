// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import java.util.function.Function;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.Minecraft;

public class ScreenContext
{
    private final Minecraft minecraft;
    private Stack stack;
    private float tickDelta;
    private Runnable flushGraphicsAction;
    private MutableMouse mouse;
    
    private ScreenContext() {
        this.minecraft = Laby.labyAPI().minecraft();
    }
    
    public static ScreenContext create() {
        return new ScreenContext();
    }
    
    public void pushStack() {
        this.stack.push();
    }
    
    public void translate(final float x, final float y, final float z) {
        this.stack.translate(x, y, z);
    }
    
    public void rotateX(final float angle) {
        this.stack.rotate(angle, 1.0f, 0.0f, 0.0f);
    }
    
    public void rotateY(final float angle) {
        this.stack.rotate(angle, 0.0f, 1.0f, 0.0f);
    }
    
    public void rotateZ(final float angle) {
        this.stack.rotate(angle, 0.0f, 0.0f, 1.0f);
    }
    
    public void rotateRadiansX(final float value) {
        this.stack.rotateRadians(value, 1.0f, 0.0f, 0.0f);
    }
    
    public void rotateRadiansY(final float value) {
        this.stack.rotateRadians(value, 0.0f, 1.0f, 0.0f);
    }
    
    public void rotateRadiansZ(final float value) {
        this.stack.rotateRadians(value, 0.0f, 0.0f, 1.0f);
    }
    
    public void scale(final float x, final float y, final float z) {
        this.stack.scale(x, y, z);
    }
    
    public void popStack() {
        this.stack.pop();
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public MutableMouse mouse() {
        return this.mouse;
    }
    
    public void setMouse(final MutableMouse mouse) {
        this.mouse = mouse;
    }
    
    public float getTickDelta() {
        return this.tickDelta;
    }
    
    public void setFlushGraphicsAction(final Runnable flushGraphicsAction) {
        this.flushGraphicsAction = flushGraphicsAction;
    }
    
    public void executeFlushGraphics() {
        if (this.flushGraphicsAction != null) {
            this.flushGraphicsAction.run();
        }
    }
    
    public void runInContext(final Stack stack, final MutableMouse mouse, final float tickDelta, final Consumer<ScreenContext> action) {
        final Stack prevStack = this.stack();
        final MutableMouse prevMouse = this.mouse();
        final float prevTickDelta = this.getTickDelta();
        try {
            this.stack = stack;
            this.mouse = mouse;
            this.tickDelta = tickDelta;
            action.accept(this);
        }
        finally {
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
        }
    }
    
    public void runInContext(final Stack stack, final int mouseX, final int mouseY, final float tickDelta, final Consumer<ScreenContext> action) {
        final Stack prevStack = this.stack();
        final MutableMouse prevMouse = this.mouse();
        final float prevTickDelta = this.getTickDelta();
        try {
            this.stack = stack;
            this.tickDelta = tickDelta;
            this.minecraft.updateMouse(mouseX, mouseY, mouse -> {
                this.mouse = mouse;
                action.accept(this);
            });
        }
        finally {
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
        }
    }
    
    public boolean runInContextWithState(final Stack stack, final MutableMouse mouse, final float tickDelta, final Function<ScreenContext, Boolean> state) {
        final Stack prevStack = this.stack();
        final MutableMouse prevMouse = this.mouse();
        final float prevTickDelta = this.getTickDelta();
        try {
            this.stack = stack;
            this.mouse = mouse;
            this.tickDelta = tickDelta;
            return state.apply(this);
        }
        finally {
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
        }
    }
}
