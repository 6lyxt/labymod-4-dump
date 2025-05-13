// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Laby;
import java.util.function.Consumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.icon.IconBatch;

public class FontIconRenderListeners
{
    public static final FontIconRenderListener NORMAL_2D;
    public static final FontIconRenderListener NORMAL_3D;
    private static final IconBatch BATCH;
    private static final FontIconRenderListener BATCH_LISTENER;
    
    public static void batch(final Stack stack, final Consumer<FontIconRenderListener> renderer) {
        batch(stack, null, renderer);
    }
    
    public static void batch3d(final Stack stack, final Consumer<FontIconRenderListener> renderer) {
        batch(stack, FontIconRenderListeners.NORMAL_3D, renderer);
    }
    
    public static void batch(final Stack stack, final FontIconRenderListener batchCustomizer, final Consumer<FontIconRenderListener> renderer) {
        renderer.accept(FontIconRenderListeners.BATCH_LISTENER);
        if (batchCustomizer != null) {
            batchCustomizer.preRender();
        }
        FontIconRenderListeners.BATCH.renderBatch(stack);
        if (batchCustomizer != null) {
            batchCustomizer.postRender();
        }
    }
    
    static {
        NORMAL_2D = new FontIconRenderListener() {};
        NORMAL_3D = new FontIconRenderListener() {
            @Override
            public void preRender() {
                Laby.gfx().storeBlaze3DStates();
                Laby.gfx().enableDepth();
            }
            
            @Override
            public void postRender() {
                Laby.gfx().restoreBlaze3DStates();
            }
        };
        BATCH = new IconBatch();
        BATCH_LISTENER = new FontIconRenderListener() {
            @Override
            public void render(final Stack stack, final Icon icon, final float x, final float y, final float width, final float height, final int rgb) {
                FontIconRenderListeners.BATCH.addIcon(stack, icon, x, y, width, height, rgb);
            }
        };
    }
}
