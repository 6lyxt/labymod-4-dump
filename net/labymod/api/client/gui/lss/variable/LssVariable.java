// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.variable;

import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;

public class LssVariable
{
    private static final WidgetModifier WIDGET_MODIFIER;
    private final LssVariableHolder holder;
    private final String key;
    private final String value;
    private final long timestamp;
    
    public LssVariable(@NotNull final LssVariableHolder holder, @NotNull final String key, @NotNull final String value, final long timestamp) {
        if (!LssVariable.WIDGET_MODIFIER.isVariableKey(key)) {
            throw new IllegalArgumentException("Invalid variable key: '" + key + "', must start with '--' followed by a char other than '-'");
        }
        this.holder = holder;
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
    }
    
    @NotNull
    public LssVariableHolder holder() {
        return this.holder;
    }
    
    @NotNull
    public String key() {
        return this.key;
    }
    
    @NotNull
    public String value() {
        return this.value;
    }
    
    public long timestamp() {
        return this.timestamp;
    }
    
    static {
        WIDGET_MODIFIER = Laby.references().widgetModifier();
    }
}
