// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.callback;

public class InsertInfoReturnable<T> extends InsertInfo
{
    private T returnValue;
    
    public InsertInfoReturnable(final boolean cancellable) {
        super(cancellable);
    }
    
    public T getReturnValue() {
        return this.returnValue;
    }
    
    public void setReturnValue(final T returnValue) {
        this.returnValue = returnValue;
        this.cancel();
    }
}
