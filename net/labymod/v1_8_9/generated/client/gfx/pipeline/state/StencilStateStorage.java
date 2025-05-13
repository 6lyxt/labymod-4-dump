// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.data.StencilFuncData;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class StencilStateStorage implements StateStorage<bfl.n>
{
    private int zpass;
    private int fail;
    private StencilFuncData stencilFuncData;
    private int mask;
    private int zfail;
    
    public StencilStateStorage() {
        this.stencilFuncData = new StencilFuncData();
    }
    
    @Override
    public void store(final bfl.n state) {
        this.stencilFuncData.setFunc(state.a.a);
        this.stencilFuncData.setRef(state.a.b);
        this.stencilFuncData.setMask(state.a.c);
        this.mask = state.b;
        this.fail = state.c;
        this.zfail = state.d;
        this.zpass = state.e;
    }
    
    @Override
    public void restore() {
    }
    
    @Override
    public StencilStateStorage copy() {
        final StencilStateStorage newObject = new StencilStateStorage();
        newObject.zpass = this.zpass;
        newObject.fail = this.fail;
        newObject.stencilFuncData = this.stencilFuncData.copy();
        newObject.mask = this.mask;
        newObject.zfail = this.zfail;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "StencilStateStorage[zpass=" + this.zpass + ", fail=" + this.fail + ", stencilFuncData=" + String.valueOf(this.stencilFuncData) + ", mask=" + this.mask + ", zfail=" + this.zfail;
    }
}
