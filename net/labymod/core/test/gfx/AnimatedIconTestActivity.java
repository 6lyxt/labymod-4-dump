// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.gfx;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.AnimatedIcon;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.test.TestActivity;

@Link("test/test-menu.lss")
@AutoActivity
public class AnimatedIconTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final AnimatedResourceLocation.Builder builder = Laby.references().resourceLocationFactory().builder();
        final AnimatedResourceLocation animatedResourceLocation = builder.resourceLocations("labymod", "textures/spinner/spinner", 30).delay(33L).build();
        final IconWidget widget = new IconWidget(AnimatedIcon.of(animatedResourceLocation));
        widget.addId("animated-icon");
        ((AbstractWidget<IconWidget>)this.document).addChild(widget);
    }
}
