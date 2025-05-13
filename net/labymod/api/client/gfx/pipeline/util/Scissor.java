// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.util;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.ArrayDeque;
import java.util.Deque;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.debug.Renderdoc;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.GFXBridge;

public final class Scissor
{
    private final GFXBridge gfx;
    private final ScissorStack scissorStack;
    private boolean worldEnvironment;
    
    public Scissor(final GFXRenderPipeline renderPipeline) {
        this.gfx = renderPipeline.gfx();
        this.scissorStack = (Renderdoc.isLoaded() ? new RenderdocScissorStack(this.gfx) : new ScissorStack());
    }
    
    public void push(final Stack stack, final float x, final float y, final float width, final float height) {
        this.push(stack, Rectangle.relative(x, y, width, height));
    }
    
    public void push(final Stack stack, final Rectangle rectangle) {
        this.push(stack, rectangle, true);
    }
    
    public void push(final Stack stack, final float x, final float y, final float width, final float height, final boolean useFloatingPoint) {
        this.push(stack, Rectangle.relative(x, y, width, height), useFloatingPoint);
    }
    
    public void push(final Stack stack, Rectangle rectangle, final boolean useFloatingPoint) {
        float left = rectangle.getLeft();
        float top = rectangle.getTop();
        float right = rectangle.getRight();
        float bottom = rectangle.getBottom();
        if (!useFloatingPoint) {
            left = (float)(int)left;
            top = (float)(int)top;
            right = (float)(int)right;
            bottom = (float)(int)bottom;
        }
        rectangle = Rectangle.absolute(left, top, right, bottom);
        final FloatVector4 vec = stack.transformVector(rectangle, false);
        rectangle = Rectangle.absolute(rectangle.getLeft() + vec.getX(), rectangle.getTop() + vec.getY(), rectangle.getRight() + vec.getZ(), rectangle.getBottom() + vec.getW());
        this.push(rectangle);
    }
    
    public void push(final Stack stack, final float y, final float height) {
        this.push(stack, Rectangle.relative(0.0f, y, (float)Laby.labyAPI().minecraft().minecraftWindow().getScaledWidth(), height));
    }
    
    public void push(final Rectangle rectangle) {
        this.applyScissor(this.scissorStack.push(rectangle));
    }
    
    public void pop() {
        this.applyScissor(this.scissorStack.pop());
    }
    
    public void enableScissorWorld() {
        this.worldEnvironment = false;
    }
    
    public void disableScissorWorld() {
        this.worldEnvironment = true;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void enableScissor() {
        this.worldEnvironment = false;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void disableScissor() {
        this.worldEnvironment = true;
    }
    
    private void applyScissor(final Rectangle rectangle) {
        if (this.worldEnvironment) {
            return;
        }
        final Blaze3DBufferSource blaze3DBufferSource = this.gfx.blaze3DBufferSource();
        blaze3DBufferSource.endBuffer();
        if (rectangle == null) {
            this.gfx.disableScissor();
            return;
        }
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        final int rawHeight = window.getRawHeight();
        final float scale = window.getScale();
        final float x = rectangle.getLeft() * scale;
        final float y = rawHeight - rectangle.getBottom() * scale;
        final float width = rectangle.getWidth() * scale;
        final float height = rectangle.getHeight() * scale;
        this.gfx.enableScissor();
        this.gfx.scissor((int)x, (int)y, Math.max(0, (int)width), Math.max(0, (int)height));
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void apply(final Stack stack, final float x, final float y, final float width, final float height, final Runnable renderer) {
        this.apply(stack, x, y, width, height, true, renderer);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void apply(final Stack stack, final int x, final int y, final int width, final int height, final Runnable renderer) {
        this.apply(stack, (float)x, (float)y, (float)width, (float)height, false, renderer);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void apply(final Stack stack, final float y, final float height, final Runnable renderer) {
        this.apply(stack, 0.0f, y, (float)Laby.labyAPI().minecraft().minecraftWindow().getScaledWidth(), height, renderer);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void apply(final Stack stack, final Rectangle rectangle, final Runnable renderer) {
        this.apply(stack, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), renderer);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void apply(final Stack stack, final Rectangle rectangle, final boolean useFloatingPointPosition, final Runnable renderer) {
        this.apply(stack, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), useFloatingPointPosition, renderer);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void apply(final Stack stack, final float x, final float y, final float width, final float height, final boolean useFloatingPointPosition, final Runnable renderer) {
        try {
            this.push(stack, Rectangle.relative(x, y, width, height), useFloatingPointPosition);
            renderer.run();
        }
        finally {
            this.pop();
        }
    }
    
    static class ScissorStack
    {
        private final Deque<Rectangle> stack;
        
        ScissorStack() {
            this.stack = new ArrayDeque<Rectangle>();
        }
        
        public Rectangle push(final Rectangle rectangle) {
            final Rectangle latestRectangle = this.stack.peek();
            if (latestRectangle == null) {
                this.stack.addLast(rectangle);
                return rectangle;
            }
            final Rectangle intersectionRectangle = Objects.requireNonNullElseGet(rectangle.intersection(latestRectangle), () -> Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f));
            this.stack.addLast(intersectionRectangle);
            return intersectionRectangle;
        }
        
        @Nullable
        public Rectangle pop() {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException("Scissor stack underflow");
            }
            this.stack.removeLast();
            return this.stack.peekLast();
        }
    }
    
    static class RenderdocScissorStack extends ScissorStack
    {
        private final GFXBridge gfx;
        private int counter;
        
        public RenderdocScissorStack(final GFXBridge gfx) {
            this.gfx = gfx;
        }
        
        @Override
        public Rectangle push(final Rectangle rectangle) {
            final Rectangle pushedRectangle = super.push(rectangle);
            this.gfx.glPushDebugGroup(this.counter++, "Scissor (" + String.valueOf(pushedRectangle));
            return pushedRectangle;
        }
        
        @Nullable
        @Override
        public Rectangle pop() {
            final Rectangle pop = super.pop();
            this.gfx.glPopDebugGroup();
            --this.counter;
            return pop;
        }
    }
}
