// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.debug;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gfx.GFXCapabilityEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import java.util.Iterator;
import net.labymod.api.client.gfx.GFXCapabilities;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gfx.GFXVersion;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/debug/opengl-info-view.lss")
public final class OpenGLInformationActivity extends Activity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final GFXCapabilities capabilities = this.labyAPI.gfxRenderPipeline().gfx().capabilities();
        final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>();
        list.addId("content-list");
        for (final GFXVersion version : capabilities.getVersions()) {
            list.addChild(this.createContentWidget(version.toString(), version.isSupported()));
        }
        capabilities.forEach(entry -> list.addChild(this.createContentWidget(entry.key(), entry.value())));
        final ScrollWidget scrollWidget = new ScrollWidget(list);
        ((AbstractWidget<ScrollWidget>)super.document).addChild(scrollWidget);
    }
    
    private Widget createContentWidget(final String key, final Object value) {
        return this.createContentWidget(key, value.toString());
    }
    
    private Widget createContentWidget(final String key, final String value) {
        final HorizontalListWidget contentWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)contentWidget).addId("content-entry");
        contentWidget.addEntry(ComponentWidget.text(key));
        contentWidget.addEntry(ComponentWidget.text(value).addId("right"));
        return contentWidget;
    }
}
