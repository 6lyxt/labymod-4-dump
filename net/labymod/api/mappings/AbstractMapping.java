// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings;

public abstract class AbstractMapping implements Mapping
{
    private final String original;
    private final String mapped;
    
    protected AbstractMapping(final String original, final String mapped) {
        this.original = original;
        this.mapped = mapped;
    }
    
    @Override
    public String getOriginal() {
        return this.original;
    }
    
    @Override
    public String getMapped() {
        return this.mapped;
    }
}
