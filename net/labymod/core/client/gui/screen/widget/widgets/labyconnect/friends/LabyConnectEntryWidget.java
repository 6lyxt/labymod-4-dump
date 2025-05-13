// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public abstract class LabyConnectEntryWidget extends SimpleWidget
{
    protected ParentScreen contentDisplay;
    
    public LabyConnectEntryWidget(final ParentScreen contentDisplay) {
        this.contentDisplay = contentDisplay;
        this.addId("labyconnect-entry");
        this.lazy = true;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    public void updateContentDisplay(final ParentScreen contentDisplay) {
        this.contentDisplay = contentDisplay;
    }
    
    public void displayContentActivity(final Activity activity) {
        this.contentDisplay.displayScreen(activity);
    }
    
    public abstract void select();
}
