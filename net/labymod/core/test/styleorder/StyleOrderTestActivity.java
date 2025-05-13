// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.styleorder;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
@Link("test/style/style-test-activity.lss")
public class StyleOrderTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final StyleOrderTestWidget styled = new StyleOrderTestWidget();
        styled.addId("styled");
        ((AbstractWidget<StyleOrderTestWidget>)this.document).addChild(styled);
        final DivWidget normal = new DivWidget();
        normal.addId("normal");
        ((AbstractWidget<DivWidget>)this.document).addChild(normal);
    }
}
