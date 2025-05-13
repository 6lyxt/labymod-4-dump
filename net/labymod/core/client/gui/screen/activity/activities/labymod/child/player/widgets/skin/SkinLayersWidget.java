// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin;

import net.labymod.api.client.gui.screen.widget.widgets.layout.TreeWidget;
import net.labymod.core.client.gui.screen.widget.widgets.customization.SkinLayerWidget;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class SkinLayersWidget extends AbstractWidget<Widget>
{
    private final Runnable updateModel;
    
    public SkinLayersWidget(final Runnable updateModel) {
        this.updateModel = (() -> {
            this.reInitialize();
            updateModel.run();
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>();
        list.addId("layer-list");
        final SkinLayerWidget hat = new SkinLayerWidget(SkinLayer.HAT);
        hat.setActionListener(this.updateModel);
        list.addChild(hat);
        final SkinLayerWidget jacket = new SkinLayerWidget(SkinLayer.JACKET);
        jacket.setActionListener(this.updateModel);
        final TreeWidget jacketTree = new TreeWidget(jacket);
        final SkinLayerWidget leftSleeve = new SkinLayerWidget(SkinLayer.LEFT_SLEEVE);
        leftSleeve.setActionListener(this.updateModel);
        ((AbstractWidget<SkinLayerWidget>)jacketTree).addChild(leftSleeve);
        final SkinLayerWidget jacketBase = new SkinLayerWidget(SkinLayer.JACKET_BASE);
        jacketBase.setActionListener(this.updateModel);
        ((AbstractWidget<SkinLayerWidget>)jacketTree).addChild(jacketBase);
        final SkinLayerWidget rightSleeve = new SkinLayerWidget(SkinLayer.RIGHT_SLEEVE);
        rightSleeve.setActionListener(this.updateModel);
        ((AbstractWidget<SkinLayerWidget>)jacketTree).addChild(rightSleeve);
        list.addChild(jacketTree);
        final SkinLayerWidget pants = new SkinLayerWidget(SkinLayer.PANTS);
        pants.setActionListener(this.updateModel);
        final TreeWidget pantsTree = new TreeWidget(pants);
        final SkinLayerWidget leftPants = new SkinLayerWidget(SkinLayer.LEFT_PANTS_LEG);
        leftPants.setActionListener(this.updateModel);
        ((AbstractWidget<SkinLayerWidget>)pantsTree).addChild(leftPants);
        final SkinLayerWidget rightPants = new SkinLayerWidget(SkinLayer.RIGHT_PANTS_LEG);
        rightPants.setActionListener(this.updateModel);
        ((AbstractWidget<SkinLayerWidget>)pantsTree).addChild(rightPants);
        list.addChild(pantsTree);
        final SkinLayerWidget widget = new SkinLayerWidget(SkinLayer.CAPE);
        widget.setActionListener(this.updateModel);
        list.addChild(widget);
        ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(list);
    }
}
