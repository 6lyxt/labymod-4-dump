// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;

@AutoActivity
@Link("test/rectangle.lss")
public class RectangleTestActivity extends TestActivity
{
    private static final ModifyReason REASON;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final float width = 10.0f;
        final float height = 1.0f;
        final float offset = 0.0f;
        final DivWidget first = new DivWidget();
        first.addId("first");
        first.bounds().setPosition(offset, offset, RectangleTestActivity.REASON);
        first.bounds().setSize(width, height, RectangleTestActivity.REASON);
        ((AbstractWidget<DivWidget>)this.document).addChild(first);
        final DivWidget second = new DivWidget();
        second.addId("second");
        second.bounds().setPosition(offset, offset + height, RectangleTestActivity.REASON);
        second.bounds().setSize(width, height, RectangleTestActivity.REASON);
        ((AbstractWidget<DivWidget>)this.document).addChild(second);
    }
    
    static {
        REASON = ModifyReason.of("RectangleTestActivity");
    }
}
