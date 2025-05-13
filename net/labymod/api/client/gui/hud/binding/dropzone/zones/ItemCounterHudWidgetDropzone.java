// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import java.util.Locale;

public class ItemCounterHudWidgetDropzone extends HotbarWidgetDropzone
{
    private final boolean rightBound;
    
    public ItemCounterHudWidgetDropzone(final boolean rightBound) {
        super(String.format(Locale.ROOT, "item_counter_%s", rightBound ? "right" : "left"), rightBound);
        this.rightBound = rightBound;
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final float itemOffset = this.getItemOffset(renderer);
        final float hotbarOffset = this.getOffset(renderer, hudWidgetSize);
        return renderer.getArea().getCenterX() + hotbarOffset + itemOffset;
    }
    
    private float getItemOffset(final HudWidgetRendererAccessor renderer) {
        float itemHudWidgetWidth = 0.0f;
        for (final HudWidgetDropzone zone : NamedHudWidgetDropzones.ITEMS) {
            final ItemHudWidgetDropzone itemZone = (ItemHudWidgetDropzone)zone;
            if (itemZone.type().isLeft() == this.rightBound) {
                final ItemHudWidget<?> hudWidget = (ItemHudWidget)renderer.getRelevantHudWidgetForDropzone(zone);
                if (hudWidget != null) {
                    final HudWidgetWidget widget = renderer.getWidget(hudWidget);
                    float width = widget.scaledBounds().getWidth();
                    final RenderableComponent component = hudWidget.getRenderableItemName(renderer.isEditor());
                    if (component != null) {
                        width += component.getWidth();
                    }
                    itemHudWidgetWidth = Math.max(itemHudWidgetWidth, width + 4.0f);
                }
            }
        }
        return itemHudWidgetWidth * (this.rightBound ? -1 : 1);
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        return renderer.getArea().getBottom() - hudWidgetSize.getScaledHeight() - 2.0f;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new ItemCounterHudWidgetDropzone(this.rightBound);
    }
}
