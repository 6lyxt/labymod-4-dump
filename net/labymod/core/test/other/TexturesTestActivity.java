// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.other;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.icon.Icon;
import java.lang.reflect.Field;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.GridWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
@Link("test/test-menu.lss")
public class TexturesTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final GridWidget<DivWidget> icons = new GridWidget<DivWidget>();
        icons.addId("icon-showcase");
        final Class<Textures> texturesClass = Textures.class;
        for (final Field declaredField : texturesClass.getDeclaredFields()) {
            this.addIcon(icons, declaredField);
        }
        for (final Class<?> declaredClass : texturesClass.getDeclaredClasses()) {
            for (final Field declaredField2 : declaredClass.getDeclaredFields()) {
                this.addIcon(icons, declaredField2);
            }
        }
        ((AbstractWidget<GridWidget<DivWidget>>)super.document()).addChild(icons);
    }
    
    private void addIcon(final GridWidget<DivWidget> icons, final Field field) {
        if (!field.getType().isAssignableFrom(Icon.class)) {
            return;
        }
        try {
            final Icon icon = (Icon)field.get(null);
            final DivWidget wrapper = new DivWidget();
            wrapper.addId("wrapper");
            final ComponentWidget iconNameWidget = ComponentWidget.text(field.getName());
            iconNameWidget.addId("icon-name");
            ((AbstractWidget<ComponentWidget>)wrapper).addChild(iconNameWidget);
            final IconWidget iconWidget = new IconWidget(icon);
            iconWidget.addId("test-view-icon");
            ((AbstractWidget<IconWidget>)wrapper).addChild(iconWidget);
            icons.addChild(wrapper);
        }
        catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
