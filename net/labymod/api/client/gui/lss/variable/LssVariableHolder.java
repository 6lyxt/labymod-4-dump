// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.variable;

import net.labymod.api.util.time.TimeUtil;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public interface LssVariableHolder
{
    @Nullable
    default LssVariableHolder getParentVariableHolder() {
        return null;
    }
    
    Map<String, LssVariable> getLssVariables();
    
    void updateLssVariable(final LssVariable p0);
    
    void forceUpdateLssVariable(final LssVariable p0);
    
    default LssVariable getVariable(@NotNull final String key) {
        final LssVariable variable = this.getLssVariables().get(key);
        if (variable != null) {
            return variable;
        }
        final LssVariableHolder parent = this.getParentVariableHolder();
        return (parent != null) ? parent.getVariable(key) : null;
    }
    
    default void setVariable(@NotNull final String key, @NotNull final String value) {
        final Map<String, LssVariable> lssVariables = this.getLssVariables();
        LssVariable variable = lssVariables.get(key);
        if (variable != null && Objects.equals(value, variable.value())) {
            return;
        }
        variable = new LssVariable(this, key, value, TimeUtil.getCurrentTimeMillis());
        lssVariables.put(key, variable);
        this.updateLssVariable(variable);
    }
    
    default void setVariable(@NotNull final String key, @NotNull final Number value) {
        this.setVariable(key, String.valueOf(value));
    }
    
    default void setVariable(@NotNull final String key, final boolean value) {
        this.setVariable(key, String.valueOf(value));
    }
    
    default void clearVariable(@NotNull final String key) {
        final LssVariable variable = this.getLssVariables().remove(key);
        if (variable != null) {
            this.updateLssVariable(variable);
        }
    }
}
