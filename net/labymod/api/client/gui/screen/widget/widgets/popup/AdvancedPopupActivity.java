// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.popup;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Activity;

@AutoActivity
public class AdvancedPopupActivity extends Activity
{
    private final AdvancedPopupWidget popupWidget;
    
    protected AdvancedPopupActivity(@NotNull final AdvancedPopupWidget popupWidget) {
        Objects.requireNonNull(popupWidget, "popup");
        this.popupWidget = popupWidget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<AdvancedPopupWidget>)this.document).addChild(this.popupWidget);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ESCAPE) {
            this.popupWidget.popup().close();
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return true;
    }
}
