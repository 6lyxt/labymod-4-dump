// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.icon;

import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.Laby;
import java.util.Iterator;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.vector.FloatMatrix4;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.render.matrix.Stack;

public class IconBatch
{
    private final Stack stack;
    private final Map<String, List<BatchIcon>> icons;
    
    public IconBatch() {
        this.stack = Stack.create(new ArrayStackProvider());
        this.icons = new HashMap<String, List<BatchIcon>>();
    }
    
    public void addIcon(final Stack stack, final Icon icon, final float x, final float y, final float width, final float height, final int rgb) {
        final FloatMatrix4 m = stack.getProvider().getPosition().copy();
        final ResourceLocation location = icon.getResourceLocation();
        String key = "";
        if (location != null) {
            key = location.getNamespace() + ":" + location.getPath();
        }
        this.icons.computeIfAbsent(key, l -> new ArrayList()).add(new BatchIcon(icon, m, x, y, width, height, rgb));
    }
    
    public void renderBatch(final Stack stack) {
        if (this.icons.isEmpty()) {
            return;
        }
        final boolean ancient = PlatformEnvironment.isAncientOpenGL();
        if (ancient) {
            stack.push();
            stack.identity();
        }
        final Stack customStack = this.stack;
        for (final Map.Entry<String, List<BatchIcon>> entry : this.icons.entrySet()) {
            this.renderBatch(customStack, entry.getValue().get(0).icon().getResourceLocation(), entry.getValue());
        }
        this.icons.clear();
        if (ancient) {
            stack.pop();
        }
    }
    
    private void renderBatch(final Stack stack, final ResourceLocation location, final List<BatchIcon> icons) {
        if (location == null) {
            for (final BatchIcon icon : icons) {
                stack.getProvider().getPosition().set(icon.matrix);
                icon.icon.render(stack, icon.x, icon.y, icon.width, icon.height, false, icon.rgb);
            }
            return;
        }
        final ResourceRenderContext batch = Laby.references().resourceRenderContext().begin(stack, location);
        for (final BatchIcon icon2 : icons) {
            stack.getProvider().getPosition().set(icon2.matrix);
            icon2.icon.render(batch, icon2.x, icon2.y, icon2.width, icon2.height, false, icon2.rgb);
        }
        batch.uploadToBuffer();
    }
    
    record BatchIcon(Icon icon, FloatMatrix4 matrix, float x, float y, float width, float height, int rgb) {}
}
