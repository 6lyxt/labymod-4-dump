// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import java.util.Iterator;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.math.vector.FloatVector2;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public abstract class ServerEntryWidget extends AbstractWidget<Widget>
{
    protected ServerInfoWidget.Movable movable;
    protected Consumer<ServerInfoWidget.Movable> moveHandler;
    private float mouseClickX;
    private float mouseClickY;
    private final AnimationData animationData;
    private final FloatVector2 draggingPivot;
    
    public ServerEntryWidget() {
        this.mouseClickX = -1.0f;
        this.mouseClickY = -1.0f;
        this.animationData = new AnimationData();
        this.draggingPivot = new FloatVector2();
        this.movable = ServerInfoWidget.Movable.NO;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        this.mouseClickX = (float)mouse.getX();
        this.mouseClickY = (float)mouse.getY();
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        mouse.transform(this.bounds().rectangle(BoundsType.OUTER), () -> {
            final float ox = this.draggingPivot.getX();
            final float oy = this.draggingPivot.getY();
            final float x = MathHelper.lerp((float)mouse.getX(), ox);
            final float y = MathHelper.lerp((float)mouse.getY(), oy);
            this.draggingPivot.set(x, y);
            return;
        });
        super.renderWidget(context);
        final Widget iconWidget = this.getIconWidget();
        final boolean mouseMoved = this.mouseClickX != -1.0f && this.mouseClickY != -1.0f && (this.mouseClickX != mouse.getX() || this.mouseClickY != mouse.getY());
        if (iconWidget != null && this.isHovered() && (!this.isDragging() || !mouseMoved)) {
            for (final Widget child : iconWidget.getChildren()) {
                child.render(context);
            }
        }
    }
    
    public void setMovable(final ServerInfoWidget.Movable movable, final Consumer<ServerInfoWidget.Movable> moveHandler) {
        this.movable = movable;
        this.moveHandler = moveHandler;
    }
    
    protected void addSelectionWidgets(final IconWidget parent) {
        if (this.movable == ServerInfoWidget.Movable.NO) {
            return;
        }
        final DivWidget overlay = new DivWidget();
        overlay.addId("overlay");
        ((AbstractWidget<DivWidget>)parent).addChild(overlay);
        if (this.movable == ServerInfoWidget.Movable.UP || this.movable == ServerInfoWidget.Movable.ALL) {
            final SelectionWidget upWidget = new SelectionWidget(SelectionWidget.SelectionIcon.UP);
            upWidget.setPressable(() -> {
                if (this.moveHandler != null) {
                    this.moveHandler.accept(ServerInfoWidget.Movable.UP);
                }
                return;
            });
            ((AbstractWidget<SelectionWidget>)parent).addChild(upWidget);
        }
        if (this.movable == ServerInfoWidget.Movable.DOWN || this.movable == ServerInfoWidget.Movable.ALL) {
            final SelectionWidget downWidget = new SelectionWidget(SelectionWidget.SelectionIcon.DOWN);
            downWidget.setPressable(() -> {
                if (this.moveHandler != null) {
                    this.moveHandler.accept(ServerInfoWidget.Movable.DOWN);
                }
                return;
            });
            ((AbstractWidget<SelectionWidget>)parent).addChild(downWidget);
        }
        final SelectionWidget addWidget = new SelectionWidget(SelectionWidget.SelectionIcon.ADD);
        addWidget.setPressable(() -> {
            if (this.moveHandler != null) {
                this.moveHandler.accept(ServerInfoWidget.Movable.ADD);
            }
            return;
        });
        ((AbstractWidget<SelectionWidget>)parent).addChild(addWidget);
    }
    
    public abstract int getListIndex();
    
    protected Widget getIconWidget() {
        return null;
    }
    
    public void applyAnimation(final Widget parent) {
        if (parent.isHovered()) {
            this.animationData.startMoveIn();
        }
        else {
            this.animationData.startMoveOut();
        }
        final float progress = this.animationData.getProgress();
        this.setScale(progress);
    }
    
    @Override
    public int getSortingValue() {
        return this.getListIndex();
    }
    
    @Override
    public FloatVector2 getPivot() {
        return this.isDragging() ? this.draggingPivot : super.getPivot();
    }
    
    private static class AnimationData
    {
        private boolean moveOutStarted;
        private long moveOutTime;
        private boolean moveInStarted;
        private long moveInTime;
        
        public void startMoveOut() {
            if (!this.moveOutStarted) {
                this.moveOutStarted = true;
                this.moveOutTime = TimeUtil.getCurrentTimeMillis() + 1000L;
                this.moveInStarted = false;
            }
        }
        
        public void startMoveIn() {
            if (!this.moveInStarted) {
                this.moveInStarted = true;
                this.moveInTime = (this.moveOutStarted ? (TimeUtil.getCurrentTimeMillis() + 1000L) : TimeUtil.getCurrentTimeMillis());
                this.moveOutStarted = false;
            }
        }
        
        public float getProgress() {
            long timePassed = 0L;
            if (this.moveOutStarted) {
                timePassed = this.moveOutTime - TimeUtil.getCurrentTimeMillis();
            }
            if (this.moveInStarted) {
                timePassed = this.moveInTime - TimeUtil.getCurrentTimeMillis();
            }
            if (timePassed == -1L) {
                return 1.0f;
            }
            float progress = MathHelper.clamp(timePassed / 1250.0f, 0.0f, 1.0f);
            progress = (float)CubicBezier.EASE_IN_OUT.curve(progress);
            progress = MathHelper.clamp(progress, 0.5f, 1.0f);
            if (this.moveInStarted) {
                progress = 1.5f - progress;
            }
            return progress;
        }
    }
}
