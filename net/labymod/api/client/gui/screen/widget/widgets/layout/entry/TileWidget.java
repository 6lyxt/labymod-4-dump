// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.entry;

import net.labymod.api.client.gui.screen.ScreenContext;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
@Deprecated(forRemoval = true, since = "4.0.6")
public class TileWidget<T extends Widget> extends WrappedWidget
{
    private final int widthUnits;
    private final int heightUnits;
    
    public TileWidget(@NotNull final T childWidget, final int widthUnits, final int heightUnits) {
        super(childWidget);
        this.widthUnits = widthUnits;
        this.heightUnits = heightUnits;
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (!this.isVisible()) {
            return;
        }
        super.render(context);
    }
    
    public int getWidthUnits() {
        return this.widthUnits;
    }
    
    public int getHeightUnits() {
        return this.heightUnits;
    }
    
    @NotNull
    @Override
    public T childWidget() {
        return (T)this.childWidget;
    }
}
