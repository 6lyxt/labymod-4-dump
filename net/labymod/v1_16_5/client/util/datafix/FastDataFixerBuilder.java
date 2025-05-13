// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.util.datafix;

import com.mojang.datafixers.DataFixer;
import java.util.concurrent.Executor;
import com.mojang.datafixers.DataFixerBuilder;

public class FastDataFixerBuilder extends DataFixerBuilder
{
    private static final Executor EXECUTOR;
    
    public FastDataFixerBuilder() {
        super(2730);
    }
    
    public DataFixer build(final Executor executor) {
        return super.build(FastDataFixerBuilder.EXECUTOR);
    }
    
    static {
        EXECUTOR = (executor -> {});
    }
}
