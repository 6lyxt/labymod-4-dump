// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.state.data;

public final class StencilFuncData
{
    private int func;
    private int ref;
    private int mask;
    
    public StencilFuncData() {
    }
    
    public StencilFuncData(final int func, final int ref, final int mask) {
        this.func = func;
        this.ref = ref;
        this.mask = mask;
    }
    
    public int getFunc() {
        return this.func;
    }
    
    public void setFunc(final int func) {
        this.func = func;
    }
    
    public int getRef() {
        return this.ref;
    }
    
    public void setRef(final int ref) {
        this.ref = ref;
    }
    
    public int getMask() {
        return this.mask;
    }
    
    public void setMask(final int mask) {
        this.mask = mask;
    }
    
    public StencilFuncData copy() {
        return new StencilFuncData(this.func, this.ref, this.mask);
    }
}
