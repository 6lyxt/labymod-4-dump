// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions;

import net.labymod.core.labymodnet.models.CosmeticOption;

public abstract class CustomWidgetOption extends WidgetOption
{
    protected final CosmeticOption option;
    
    protected CustomWidgetOption(final CosmeticOption option, final String optionName, final int optionIndex) {
        super(optionName, optionIndex);
        this.option = option;
    }
}
