// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.concurrent;

public interface Completable<T>
{
    void addCompletableListener(final Runnable p0);
    
    boolean isLoading();
    
    void startLoading();
    
    void stopLoading();
    
    void stopLoadingOnError();
    
    T getCompleted();
    
    boolean hasResult();
    
    boolean hasError();
    
    void reset();
    
    void executeCompletableListeners(final T p0);
    
    void updateCompletable(final T p0);
}
