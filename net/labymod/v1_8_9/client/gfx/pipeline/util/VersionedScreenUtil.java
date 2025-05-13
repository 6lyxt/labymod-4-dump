// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx.pipeline.util;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.v1_8_9.client.VersionedMinecraft;
import org.lwjgl.input.Mouse;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;

public final class VersionedScreenUtil
{
    private static int lastMouseX;
    private static int lastMouseY;
    
    @Deprecated
    public static void drawScreen(final axu screen, final int mouseX, final int mouseY, final float tickDelta) {
        handleMouseDragged();
        final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(screen);
        ScreenUtil.wrapRender(VersionedStackProvider.DEFAULT_STACK, tickDelta, () -> screen.a(mouseX, mouseY, tickDelta));
        screenCustomFontStack.pop(screen);
    }
    
    public static void drawScreen(final Operation<Void> original, final axu screen, final int mouseX, final int mouseY, final float tickDelta) {
        handleMouseDragged();
        final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(screen);
        ScreenUtil.wrapRender(VersionedStackProvider.DEFAULT_STACK, tickDelta, () -> original.call(new Object[] { screen, mouseX, mouseY, tickDelta }));
        screenCustomFontStack.pop(screen);
    }
    
    private static void handleMouseDragged() {
        final int rawMouseX = Mouse.getX();
        final int rawMouseY = Mouse.getY();
        if (rawMouseX == VersionedScreenUtil.lastMouseX && rawMouseY == VersionedScreenUtil.lastMouseY) {
            return;
        }
        final ave minecraft = ave.A();
        final MouseButton mouseButton = ((VersionedMinecraft)minecraft).getCurrentEventButton();
        if (VersionedScreenUtil.lastMouseX != -1 && VersionedScreenUtil.lastMouseY != -1 && mouseButton != null) {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            final double widthFactor = window.getScaledWidth() / (double)minecraft.d;
            final double heightFactor = window.getScaledHeight() / (double)minecraft.e;
            final double deltaX = (rawMouseX - VersionedScreenUtil.lastMouseX) * widthFactor;
            final double deltaY = -(rawMouseY - VersionedScreenUtil.lastMouseY) * heightFactor;
            Laby.labyAPI().minecraft().updateMouse(rawMouseX * widthFactor, window.getScaledHeight() - rawMouseY * heightFactor, mouse -> ((VersionedMinecraft)minecraft).handleMouseDragged(mouse, mouseButton, deltaX, deltaY));
        }
        VersionedScreenUtil.lastMouseX = rawMouseX;
        VersionedScreenUtil.lastMouseY = rawMouseY;
    }
    
    static {
        VersionedScreenUtil.lastMouseX = -1;
        VersionedScreenUtil.lastMouseY = -1;
    }
}
