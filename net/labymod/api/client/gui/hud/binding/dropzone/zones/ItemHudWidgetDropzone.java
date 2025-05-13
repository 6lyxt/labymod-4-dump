// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import java.util.Locale;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;

public class ItemHudWidgetDropzone extends HotbarWidgetDropzone
{
    private final Type type;
    
    public ItemHudWidgetDropzone(final Type type) {
        super(type.getId(), type.isLeft());
        this.type = type;
    }
    
    @Override
    public float getX(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        final float offset = this.getOffset(renderer, hudWidgetSize);
        return renderer.getArea().getCenterX() + offset;
    }
    
    @Override
    public float getY(final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        int index = this.type.getYIndex();
        if (!renderer.isEditor() && Laby.labyAPI().hudWidgetRegistry().globalHudWidgetConfig().itemGravity().get()) {
            for (Type type = this.type; type != null; type = type.below()) {
                final HudWidget<?> hudWidget = renderer.getHudWidgetInDropzone(type.getId());
                if (hudWidget == null || !hudWidget.isVisibleInGame()) {
                    ++index;
                }
            }
        }
        final float offset = (hudWidgetSize.getScaledHeight() + 1.0f) * 3.0f + 1.0f;
        return renderer.getArea().getBottom() - offset + index * (hudWidgetSize.getScaledHeight() + 1.0f);
    }
    
    public Type type() {
        return this.type;
    }
    
    @Override
    public HudWidgetDropzone copy() {
        return new ItemHudWidgetDropzone(this.type);
    }
    
    public enum Type
    {
        TOP_LEFT(true, 0), 
        TOP_RIGHT(false, 0), 
        MIDDLE_LEFT(true, 1), 
        MIDDLE_RIGHT(false, 1), 
        BOTTOM_LEFT(true, 2), 
        BOTTOM_RIGHT(false, 2);
        
        private static final Type[] VALUES;
        private final boolean left;
        private final int yIndex;
        
        private Type(final boolean left, final int yIndex) {
            this.left = left;
            this.yIndex = yIndex;
        }
        
        public Type below() {
            return (this.ordinal() > 3) ? null : Type.VALUES[this.ordinal() + 2];
        }
        
        public boolean isLeft() {
            return this.left;
        }
        
        public int getYIndex() {
            return this.yIndex;
        }
        
        public String getId() {
            return "item_" + this.name().toLowerCase(Locale.ROOT);
        }
        
        static {
            VALUES = values();
        }
    }
}
