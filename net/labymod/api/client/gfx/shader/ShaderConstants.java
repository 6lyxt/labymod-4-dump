// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.Map;

public class ShaderConstants
{
    public static final ShaderConstants EMPTY;
    private final Map<CharSequence, Supplier<CharSequence>> definedConstantMap;
    private final List<String> definedConstants;
    
    protected ShaderConstants(final Map<CharSequence, Supplier<CharSequence>> definedConstantMap) {
        this.definedConstantMap = definedConstantMap;
        this.definedConstants = new ArrayList<String>();
        this.rebuild();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public Collection<String> getDefinedConstants() {
        return this.definedConstants;
    }
    
    public void rebuild() {
        this.definedConstants.clear();
        for (Map.Entry<CharSequence, Supplier<CharSequence>> entry : this.definedConstantMap.entrySet()) {
            final CharSequence key = entry.getKey();
            final Supplier<CharSequence> value = entry.getValue();
            if (value == null) {
                this.definedConstants.add("#define " + String.valueOf(key));
            }
            else {
                this.definedConstants.add("#define " + String.valueOf(key) + " " + String.valueOf(value.get()));
            }
        }
    }
    
    static {
        EMPTY = builder().build();
    }
    
    public static class Builder
    {
        private final Map<CharSequence, Supplier<CharSequence>> constants;
        
        public Builder() {
            this.constants = new HashMap<CharSequence, Supplier<CharSequence>>();
        }
        
        public Builder addConstant(final CharSequence name) {
            return this.addConstant(name, null);
        }
        
        public Builder addConstant(final CharSequence name, @Nullable final Supplier<CharSequence> value) {
            this.constants.put(name, value);
            return this;
        }
        
        public <T extends ShaderConstants> T build() {
            return this.build(ShaderConstants::new);
        }
        
        public <T extends ShaderConstants> T build(final Function<Map<CharSequence, Supplier<CharSequence>>, T> shaderConstantsConstructor) {
            return shaderConstantsConstructor.apply(this.constants);
        }
    }
}
