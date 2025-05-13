// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@AutoActivity
public class WorkInProgressActivity extends SimpleActivity
{
    private final String branchName;
    
    public WorkInProgressActivity(final String branchName) {
        this.branchName = branchName;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final HorizontalListWidget list = new HorizontalListWidget();
        ((AbstractWidget<Widget>)list).addId("centered");
        list.priorityLayer().set(PriorityLayer.VERY_FRONT);
        final ComponentWidget componentWidget = ComponentWidget.component(Component.text("Work in progress", NamedTextColor.RED));
        componentWidget.addId("centered");
        list.addEntry(componentWidget);
        final IconWidget pepeSad = new IconWidget(Textures.SpriteCommon.PEPE_SAD);
        pepeSad.addId("pepesad");
        list.addEntry(pepeSad);
        ((AbstractWidget<HorizontalListWidget>)this.document).addChild(list);
    }
}
