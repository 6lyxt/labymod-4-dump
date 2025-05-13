// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public abstract class AbstractFileDialogs implements FileDialogs
{
    protected ExecutorService executor;
    
    public AbstractFileDialogs() {
        this.executor = Executors.newSingleThreadExecutor();
    }
}
