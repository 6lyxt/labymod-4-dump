// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.state.optifine;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

@Deprecated
public class OptiFineShaderStateStorage implements StateStorage<Void>
{
    private final IntSupplier programIdSupplier;
    private final IntConsumer useProgramConsumer;
    private int programId;
    
    public OptiFineShaderStateStorage(final IntSupplier programIdSupplier, final IntConsumer useProgramConsumer) {
        this.programIdSupplier = programIdSupplier;
        this.useProgramConsumer = useProgramConsumer;
    }
    
    @Override
    public void store(final Void unused) {
        this.programId = this.programIdSupplier.getAsInt();
    }
    
    @Override
    public void restore() {
        this.useProgramConsumer.accept(this.programId);
    }
    
    @Override
    public StateStorage<Void> copy() {
        final OptiFineShaderStateStorage newStateStorage = new OptiFineShaderStateStorage(this.programIdSupplier, this.useProgramConsumer);
        newStateStorage.programId = this.programId;
        return newStateStorage;
    }
}
