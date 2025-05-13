// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.window.debug.util;

import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.List;

public class VersionedWidget
{
    private final List<VersionedWidget> children;
    private final Class<?> clazz;
    private final Widget wrapped;
    private final boolean anonymous;
    
    public VersionedWidget(final Class<?> vanillaClass, final Widget wrapped) {
        this.children = new ArrayList<VersionedWidget>();
        this.wrapped = wrapped;
        if (vanillaClass != null && vanillaClass.isAnonymousClass()) {
            final Class<?> superclass = vanillaClass.getSuperclass();
            if (superclass != null && superclass != Object.class) {
                this.clazz = superclass;
                this.anonymous = true;
                return;
            }
        }
        this.anonymous = false;
        this.clazz = vanillaClass;
    }
    
    public VersionedWidget(final Class<?> vanillaClass) {
        this(vanillaClass, null);
    }
    
    protected VersionedWidget() {
        this(null, null);
    }
    
    public List<VersionedWidget> getChildren() {
        return this.children;
    }
    
    public String getIdentifier() {
        String vanillaName = this.clazz.getSimpleName();
        if (this.anonymous) {
            vanillaName += " (anonymous)";
        }
        if (this.wrapped == null) {
            return vanillaName + " (vanilla)";
        }
        return this.wrapped.getTypeName() + " (from " + vanillaName;
    }
    
    public Widget getWrapped() {
        return this.wrapped;
    }
    
    public void addChild(final VersionedWidget child) {
        this.children.add(child);
    }
}
