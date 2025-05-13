// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Textures;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.HudWidgetWindowWidget;
import net.labymod.api.client.gui.screen.activity.Activity;

public abstract class HudWindowActivity extends Activity
{
    private static final int ANIMATION_DURATION_TICKS = 4;
    protected final HudWidgetWindowWidget window;
    private int trashOpacity;
    private int prevTrashOpacity;
    
    public HudWindowActivity(final HudWidgetWindowWidget window) {
        this.trashOpacity = 0;
        this.prevTrashOpacity = 0;
        this.window = window;
    }
    
    @Override
    public void render(final ScreenContext context) {
        final float opacity = MathHelper.lerp((float)this.trashOpacity, (float)this.prevTrashOpacity) / 4.0f;
        ((Document)this.document).opacity().set(1.0f - opacity);
        super.render(context);
        if (opacity > 0.0f) {
            Laby.gfx().clearDepth();
            final float size = this.bounds().getWidth() / 4.0f;
            final Icon icon = (this.trashOpacity > 3) ? Textures.SpriteWidgetEditor.TRASH_FRAME_0 : ((this.trashOpacity < 2) ? Textures.SpriteWidgetEditor.TRASH_FRAME_1 : Textures.SpriteWidgetEditor.TRASH_FRAME_2);
            this.labyAPI.renderPipeline().setAlpha(opacity, () -> icon.render(context.stack(), this.bounds().getCenterX() - size / 2.0f, this.bounds().getCenterY() - size / 2.0f, size));
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        this.prevTrashOpacity = this.trashOpacity;
        if (this.isTrashVisible()) {
            ++this.trashOpacity;
            if (this.trashOpacity > 4) {
                this.trashOpacity = 4;
            }
        }
        else {
            --this.trashOpacity;
            if (this.trashOpacity < 0) {
                this.trashOpacity = 0;
            }
        }
    }
    
    public boolean isTrashVisible() {
        return this.canDropHudWidget() && this.window.isHudWidgetOnTrashArea();
    }
    
    public abstract boolean canDropHudWidget();
}
