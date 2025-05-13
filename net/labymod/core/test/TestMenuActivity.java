// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.test.widget.LazyGridWidgetTestActivity;
import net.labymod.core.test.other.ResourcePackDetailTestActivity;
import net.labymod.core.test.gfx.DynamicTextureTestActivity;
import net.labymod.core.test.other.TexturesTestActivity;
import net.labymod.core.test.other.ActivitiesTestActivity;
import net.labymod.core.test.gfx.CircleStencilTestActivity;
import net.labymod.core.test.overlay.StreamOverlayTestActivity;
import net.labymod.core.test.gfx.AnimatedIconTestActivity;
import net.labymod.core.test.styleorder.StyleOrderTestActivity;
import net.labymod.core.test.cave.ModelMinecraftTextTestActivity;
import net.labymod.core.test.cave.BackgroundWorldTestActivity;
import net.labymod.core.test.gfx.GFXImmediateTestActivity;
import net.labymod.core.test.component.ComponentTestActivity;
import net.labymod.core.test.performance.VerticalListTestActivity;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("test/test-menu.lss")
@AutoActivity
public class TestMenuActivity extends SimpleActivity
{
    private final Map<String, Supplier<TestActivity>> activities;
    
    public TestMenuActivity() {
        this.activities = new HashMap<String, Supplier<TestActivity>>();
        this.registerActivity("Performance VerticalList", (Supplier<TestActivity>)VerticalListTestActivity::new);
        this.registerActivity("Formular Test", (Supplier<TestActivity>)FormTestActivity::new);
        this.registerActivity("Components", (Supplier<TestActivity>)ComponentTestActivity::new);
        this.registerActivity("GFX Immediate", (Supplier<TestActivity>)GFXImmediateTestActivity::new);
        this.registerActivity("Rectangle", (Supplier<TestActivity>)RectangleTestActivity::new);
        this.registerActivity("Background World", (Supplier<TestActivity>)BackgroundWorldTestActivity::new);
        this.registerActivity("Fractured Logo", (Supplier<TestActivity>)ModelMinecraftTextTestActivity::new);
        this.registerActivity("StyleSheet Order Test", (Supplier<TestActivity>)StyleOrderTestActivity::new);
        this.registerActivity("Context Menu", (Supplier<TestActivity>)ContextMenuTestActivity::new);
        this.registerActivity("Animated Icons", (Supplier<TestActivity>)AnimatedIconTestActivity::new);
        this.registerActivity("TextInput", (Supplier<TestActivity>)TextInputTestActivity::new);
        this.registerActivity("Stream Overlay", (Supplier<TestActivity>)StreamOverlayTestActivity::new);
        this.registerActivity("Stencil Circle", (Supplier<TestActivity>)CircleStencilTestActivity::new);
        this.registerActivity("Activities", (Supplier<TestActivity>)ActivitiesTestActivity::new);
        this.registerActivity("View Textures", (Supplier<TestActivity>)TexturesTestActivity::new);
        this.registerActivity("Dynamic Texture", (Supplier<TestActivity>)DynamicTextureTestActivity::new);
        this.registerActivity("Resource Pack Details", (Supplier<TestActivity>)ResourcePackDetailTestActivity::new);
        this.registerActivity("Lazy Grid Widget", (Supplier<TestActivity>)LazyGridWidgetTestActivity::new);
    }
    
    public void registerActivity(final String name, final TestActivity activity) {
        this.registerActivity(name, () -> activity);
    }
    
    public void registerActivity(final String name, final Supplier<TestActivity> activitySupplier) {
        this.activities.put(name, activitySupplier);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>();
        final TilesGridWidget<Widget> gridWidget = new TilesGridWidget<Widget>();
        for (final Map.Entry<String, Supplier<TestActivity>> entry : this.activities.entrySet()) {
            final DivWidget entryContainer = new DivWidget();
            ((AbstractWidget<ComponentWidget>)entryContainer).addChild(ComponentWidget.text(entry.getKey()));
            entryContainer.addId("entries");
            entryContainer.setPressable(() -> this.displayScreen(entry.getValue().get()));
            gridWidget.addTile(entryContainer);
        }
        list.addChild(gridWidget);
        final DivWidget containerWidget = new DivWidget();
        containerWidget.addId("container");
        final ComponentWidget title = ComponentWidget.text("Test Menu");
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)containerWidget).addChild(title);
        final ScrollWidget scrollWidget = new ScrollWidget(list);
        ((AbstractWidget<ScrollWidget>)containerWidget).addChild(scrollWidget);
        ((AbstractWidget<DivWidget>)this.document).addChild(containerWidget);
    }
}
