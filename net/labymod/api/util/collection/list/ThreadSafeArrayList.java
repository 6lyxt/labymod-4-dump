// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection.list;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.Collections;
import net.labymod.api.util.ThreadSafe;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class ThreadSafeArrayList<T> extends ArrayList<T>
{
    private static boolean debug;
    private final boolean forceDebug;
    private List<StackTraceElement> stacktrace;
    private boolean operationRunning;
    private int modCountBeforeLastOperation;
    private int modCountAfterLastOperation;
    
    public ThreadSafeArrayList(final boolean forceDebug) {
        this.stacktrace = null;
        this.operationRunning = false;
        this.forceDebug = forceDebug;
    }
    
    public ThreadSafeArrayList(final int initialCapacity, final boolean forceDebug) {
        super(initialCapacity);
        this.stacktrace = null;
        this.operationRunning = false;
        this.forceDebug = forceDebug;
    }
    
    public ThreadSafeArrayList() {
        this(false);
    }
    
    public ThreadSafeArrayList(final int initialCapacity) {
        this(initialCapacity, false);
    }
    
    public static boolean isDebug() {
        return ThreadSafeArrayList.debug;
    }
    
    public static void setDebug(final boolean debug) {
        ThreadSafeArrayList.debug = debug;
    }
    
    @Override
    public void trimToSize() {
        this.wrap(() -> super.trimToSize());
    }
    
    @Override
    public void ensureCapacity(final int minCapacity) {
        this.wrap(() -> super.ensureCapacity(minCapacity));
    }
    
    @Override
    public boolean add(final T t) {
        return this.wrap(() -> super.add((T)t));
    }
    
    @Override
    public void add(final int index, final T element) {
        this.wrap(() -> super.add(index, (T)element));
    }
    
    @Override
    public T remove(final int index) {
        return this.wrap(() -> super.remove(index));
    }
    
    @Override
    public T removeFirst() {
        return this.wrap(() -> super.removeFirst());
    }
    
    @Override
    public T removeLast() {
        return this.wrap(() -> super.removeLast());
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.wrap(() -> super.remove(o));
    }
    
    @Override
    public void clear() {
        this.wrap(() -> super.clear());
    }
    
    @Override
    public boolean addAll(final Collection<? extends T> c) {
        return this.wrap(() -> super.addAll(c));
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends T> c) {
        return this.wrap(() -> super.addAll(index, c));
    }
    
    @Override
    protected void removeRange(final int fromIndex, final int toIndex) {
        this.wrap(() -> super.removeRange(fromIndex, toIndex));
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        return this.wrap(() -> super.removeAll(c));
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.wrap(() -> super.retainAll(c));
    }
    
    private void storeStackTrace() {
        ThreadSafe.ensureRenderThread();
        if (ThreadSafeArrayList.debug || this.forceDebug) {
            final List<StackTraceElement> frames = new ArrayList<StackTraceElement>(32);
            StackWalker.getInstance(Collections.emptySet(), 32).forEach(stackFrame -> frames.add(stackFrame.toStackTraceElement()));
            this.stacktrace = frames;
        }
        this.operationRunning = true;
        this.modCountBeforeLastOperation = this.modCount;
    }
    
    private void clearStackTrace() {
        this.operationRunning = false;
        this.modCountAfterLastOperation = this.modCount;
    }
    
    private void wrap(final Runnable runnable) {
        this.storeStackTrace();
        try {
            runnable.run();
        }
        finally {
            this.clearStackTrace();
        }
    }
    
    private <E> E wrap(final Supplier<E> supplier) {
        this.storeStackTrace();
        try {
            return supplier.get();
        }
        finally {
            this.clearStackTrace();
        }
    }
    
    private Object[] elementData() {
        return this.toArray();
    }
    
    public Exception createException() {
        final Exception exception = new Exception("Operation running: " + this.operationRunning + ", modCountBeforeLastOperation: " + this.modCountBeforeLastOperation + ", modCountAfterLastOperation: " + this.modCountAfterLastOperation);
        if ((ThreadSafeArrayList.debug || this.forceDebug) && this.stacktrace != null) {
            exception.setStackTrace(this.stacktrace.toArray(new StackTraceElement[0]));
        }
        else {
            exception.setStackTrace(new StackTraceElement[0]);
        }
        return exception;
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        if (ThreadSafeArrayList.debug || this.forceDebug) {
            return new Itr();
        }
        return super.iterator();
    }
    
    @Override
    public boolean removeIf(final Predicate<? super T> filter) {
        return this.wrap(() -> super.removeIf(filter));
    }
    
    @Override
    public void replaceAll(final UnaryOperator<T> operator) {
        this.wrap(() -> super.replaceAll(operator));
    }
    
    @Override
    public void sort(final Comparator<? super T> c) {
        this.wrap(() -> super.sort(c));
    }
    
    static {
        ThreadSafeArrayList.debug = false;
    }
    
    private class Itr implements Iterator<T>
    {
        int cursor;
        int lastRet;
        int expectedModCount;
        
        Itr() {
            this.lastRet = -1;
            this.expectedModCount = ThreadSafeArrayList.this.modCount;
        }
        
        @Override
        public boolean hasNext() {
            return this.cursor != ThreadSafeArrayList.this.size();
        }
        
        @Override
        public T next() {
            this.checkForComodification();
            final int i = this.cursor;
            if (i >= ThreadSafeArrayList.this.size()) {
                throw new NoSuchElementException();
            }
            final Object[] elementData = ThreadSafeArrayList.this.elementData();
            if (i >= elementData.length) {
                throw new ConcurrentModificationException(ThreadSafeArrayList.this.createException());
            }
            this.cursor = i + 1;
            final Object[] array = elementData;
            final int lastRet = i;
            this.lastRet = lastRet;
            return (T)array[lastRet];
        }
        
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                ThreadSafeArrayList.this.remove(this.lastRet);
                this.cursor = this.lastRet;
                this.lastRet = -1;
                this.expectedModCount = ThreadSafeArrayList.this.modCount;
            }
            catch (final IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException(ThreadSafeArrayList.this.createException());
            }
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super T> action) {
            Objects.requireNonNull(action);
            final int size = ThreadSafeArrayList.this.size();
            int i = this.cursor;
            if (i < size) {
                final Object[] es = ThreadSafeArrayList.this.elementData();
                if (i >= es.length) {
                    throw new ConcurrentModificationException(ThreadSafeArrayList.this.createException());
                }
                while (i < size && ThreadSafeArrayList.this.modCount == this.expectedModCount) {
                    action.accept((Object)es[i]);
                    ++i;
                }
                this.cursor = i;
                this.lastRet = i - 1;
                this.checkForComodification();
            }
        }
        
        final void checkForComodification() {
            if (ThreadSafeArrayList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException(ThreadSafeArrayList.this.createException());
            }
        }
    }
}
