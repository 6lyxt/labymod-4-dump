// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaBulletEntryContainerRenderer extends VanillaBackgroundRenderer
{
    public VanillaBulletEntryContainerRenderer() {
        this.name = "BulletEntryContainer";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPre(widget, context);
        final int scale = 2;
        final int partWidth = 6;
        final Bounds bounds = widget.bounds();
        final BatchResourceRenderer resourceRenderer = this.resourceRenderer.beginBatch(context.stack(), Textures.SpriteCommon.TEXTURE);
        resourceRenderer.pos(bounds.getX(), bounds.getY()).size((float)partWidth, bounds.getHeight()).sprite((float)(32 * scale), (float)(80 * scale), (float)(partWidth * scale), (float)(16 * scale)).build();
        resourceRenderer.pos(bounds.getRight() - partWidth, bounds.getY()).size((float)partWidth, bounds.getHeight()).sprite((float)((48 - partWidth) * scale), (float)(80 * scale), (float)(partWidth * scale), (float)(16 * scale)).build();
        final int repeatPartWidth = 16 - partWidth * 2;
        final int times = (int)(MathHelper.ceil(bounds.getWidth() - partWidth * 2) / (float)repeatPartWidth);
        for (int i = 0; i < times; ++i) {
            resourceRenderer.pos(bounds.getX() + partWidth + i * repeatPartWidth, bounds.getY()).size(repeatPartWidth + 0.1f, bounds.getHeight()).sprite((float)((32 + partWidth) * scale), (float)(80 * scale), (float)(repeatPartWidth * scale), (float)(16 * scale)).build();
        }
        final float leftOverWidth = bounds.getWidth() - (times * repeatPartWidth + partWidth * 2);
        if (leftOverWidth > 0.0f) {
            resourceRenderer.pos(bounds.getRight() - partWidth - leftOverWidth, bounds.getY()).size(leftOverWidth, 16.0f).sprite((float)((32 + partWidth) * scale), (float)(80 * scale), leftOverWidth * scale, (float)(16 * scale)).build();
        }
        resourceRenderer.upload();
    }
}
