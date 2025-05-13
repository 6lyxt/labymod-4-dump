// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference;

import net.labymod.api.reference.exception.CircularDependencyException;
import java.util.Collections;
import net.labymod.api.reference.util.ReferenceUtil;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.concurrent.locks.Lock;
import java.util.List;

public class NullableSingletonReference<T> extends Reference<T>
{
    private static final int STACK_TRACE_DEPTH = 32;
    private State state;
    private List<StackTraceElement> creatingStackTraces;
    private T reference;
    private final Lock lock;
    
    public NullableSingletonReference(final Class<T> referenceClass, final Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
        this.state = State.NONE;
        this.creatingStackTraces = new ArrayList<StackTraceElement>();
        this.lock = new ReentrantLock();
    }
    
    public T get() {
        if (this.state == State.CREATED) {
            return this.reference;
        }
        try {
            this.lock.lock();
            if (this.state == State.CREATING) {
                throw this.createCircularDependencyException();
            }
            if (this.reference == null) {
                this.createReference();
            }
            return this.reference;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    private void createReference() {
        try {
            this.creatingStackTraces = this.createStackTrace(32);
            this.state = State.CREATING;
            this.reference = this.referenceSupplier.get();
            this.state = State.CREATED;
            this.creatingStackTraces = null;
        }
        catch (final Throwable throwable) {
            ReferenceUtil.onError(throwable);
        }
    }
    
    private List<StackTraceElement> createStackTrace(final int depth) {
        final List<StackTraceElement> frames = new ArrayList<StackTraceElement>(depth);
        StackWalker.getInstance(Collections.emptySet(), depth).forEach(stackFrame -> frames.add(stackFrame.toStackTraceElement()));
        return frames;
    }
    
    private CircularDependencyException createCircularDependencyException() {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Circular dependency detected for ").append(this.referenceClass.getName()).append("!");
        if (this.creatingStackTraces == null) {
            messageBuilder.append(" (no previous stacktrace)");
        }
        final CircularDependencyException rootException = new CircularDependencyException(messageBuilder.toString());
        if (this.creatingStackTraces != null) {
            final CircularDependencyException cause = new CircularDependencyException("Previous stacktrace:");
            cause.setStackTrace(this.creatingStackTraces.toArray(new StackTraceElement[0]));
            rootException.initCause(cause);
        }
        return rootException;
    }
    
    enum State
    {
        NONE, 
        CREATING, 
        CREATED;
    }
}
