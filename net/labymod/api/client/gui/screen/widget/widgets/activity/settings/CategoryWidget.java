// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

@AutoWidget
public class CategoryWidget extends ButtonWidget
{
    private final Setting category;
    
    public CategoryWidget(final Setting category) {
        this.category = category;
        this.component = category.displayName();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    public Setting category() {
        return this.category;
    }
}
