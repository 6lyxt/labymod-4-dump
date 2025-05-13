// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.StyleHelper;

@Singleton
@Implements(StyleHelper.class)
public class DefaultStyleHelper implements StyleHelper
{
    @Override
    public Selector createSelector(final String rawSelector) {
        return new DefaultSelector(rawSelector);
    }
}
