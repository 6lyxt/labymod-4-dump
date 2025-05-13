// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity;

import net.labymod.api.util.bounds.Rectangle;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class Document extends AbstractWidget<Widget>
{
    private static final AttributeState ROOT;
    private final Activity activity;
    
    public Document(final Activity activity) {
        this.activity = activity;
        this.addId("body");
        super.setStaticAttributeState(Document.ROOT);
    }
    
    public Activity activity() {
        return this.activity;
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.handleChildSize(this);
    }
    
    private void handleChildSize(final Widget widget) {
        for (final Widget child : widget.getChildren()) {
            this.handleChildSize(child);
        }
        widget.handleAttributes();
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        this.updateMediaRules(this);
        super.onBoundsChanged(previousRect, newRect);
    }
    
    private void updateMediaRules(final Widget widget) {
        for (final Widget child : widget.getChildren()) {
            child.applyMediaRules(false);
            this.updateMediaRules(child);
        }
    }
    
    static {
        ROOT = AttributeState.staticState("ROOT", 0);
    }
}
