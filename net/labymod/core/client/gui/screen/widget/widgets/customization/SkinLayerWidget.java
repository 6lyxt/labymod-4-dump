// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.customization;

import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class SkinLayerWidget extends HorizontalListWidget
{
    private final SkinLayer layer;
    private final Icon icon;
    private final Component component;
    
    public SkinLayerWidget(final SkinLayer layer) {
        this.layer = layer;
        this.icon = Icon.sprite32(Textures.SpriteCustomization.TEXTURE, layer.ordinal() % 4, layer.ordinal() / 4);
        this.component = Component.translatable(String.format(Locale.ROOT, "labymod.activity.customization.layers.layer.%s", layer.getLocaleId()), new Component[0]);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.icon != null) {
            this.addEntry(new IconWidget(this.icon)).alignment().set(HorizontalAlignment.LEFT);
        }
        if (this.component != null) {
            final ComponentWidget componentWidget = ComponentWidget.component(this.component);
            this.addEntry(componentWidget).alignment().set(HorizontalAlignment.LEFT);
        }
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        final boolean enabled = this.layer.isEnabled(options.getModelParts());
        final boolean partlyEnabled = this.layer.isPartlyEnabled(options.getModelParts());
        final CheckBoxWidget widget = new CheckBoxWidget();
        widget.setState(enabled ? CheckBoxWidget.State.CHECKED : (partlyEnabled ? CheckBoxWidget.State.PARTLY : CheckBoxWidget.State.UNCHECKED));
        widget.addId("checkbox");
        widget.setActionListener(() -> {
            int value = options.getModelParts();
            if (partlyEnabled) {
                value |= this.layer.getBitMask();
            }
            options.setModelParts(value ^ this.layer.getBitMask());
            options.sendOptionsToServer();
            options.save();
            this.reInitialize();
            this.callActionListeners();
            return;
        });
        this.addEntry(widget).alignment().set(HorizontalAlignment.RIGHT);
    }
}
