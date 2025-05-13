// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.property;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Map;

public interface DirectPropertyValueAccessorRegisterBulk
{
    void register(final Map<Class<? extends Widget>, DirectPropertyValueAccessor> p0);
}
