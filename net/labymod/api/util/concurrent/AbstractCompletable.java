// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.concurrent;

import java.util.Objects;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collection;

public abstract class AbstractCompletable<T> implements Completable<T>
{
    private final T fallback;
    private final Collection<Runnable> completableListeners;
    private boolean loading;
    private boolean error;
    private T completed;
    
    public AbstractCompletable(final T fallback) {
        this.fallback = fallback;
        this.completableListeners = new HashSet<Runnable>();
        this.loading = true;
    }
    
    public AbstractCompletable(final T completed, final boolean isCompleted) {
        this.fallback = completed;
        this.completed = completed;
        this.completableListeners = new HashSet<Runnable>();
        this.loading = !isCompleted;
    }
    
    @Override
    public void addCompletableListener(final Runnable listener) {
        if (this.loading) {
            this.completableListeners.add(listener);
            return;
        }
        listener.run();
    }
    
    @Override
    public void startLoading() {
        this.loading = true;
    }
    
    @Override
    public void stopLoading() {
        this.loading = false;
        this.completableListeners.clear();
    }
    
    @Override
    public void stopLoadingOnError() {
        this.error = true;
        this.loading = false;
        for (final Runnable completableListener : this.completableListeners) {
            completableListener.run();
        }
        this.completableListeners.clear();
    }
    
    @Override
    public boolean isLoading() {
        return this.loading;
    }
    
    @Override
    public T getCompleted() {
        return (this.completed == null) ? this.fallback : this.completed;
    }
    
    @Override
    public boolean hasResult() {
        return this.completed != null;
    }
    
    @Override
    public boolean hasError() {
        return this.error;
    }
    
    @Override
    public void reset() {
        this.loading = true;
        this.completed = null;
        this.completableListeners.clear();
    }
    
    @Override
    public void executeCompletableListeners(final T completed) {
        if (Objects.equals(this.fallback, completed)) {
            return;
        }
        this.completed = completed;
        this.loading = false;
        for (final Runnable completableListener : this.completableListeners) {
            completableListener.run();
        }
        this.stopLoading();
    }
    
    @Override
    public void updateCompletable(final T completed) {
        if (this.loading) {
            this.executeCompletableListeners(completed);
        }
        else if (!Objects.equals(this.completed, completed)) {
            this.completed = completed;
        }
    }
}
