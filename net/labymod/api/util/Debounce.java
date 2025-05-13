// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import java.util.Map;

public class Debounce
{
    private static final Map<String, Task> TASKS;
    
    public static void of(final String id, final long milliseconds, final Runnable runnable) {
        Task task = Debounce.TASKS.get(id);
        if (task != null) {
            task.cancel();
        }
        task = Task.builder(() -> {
            runnable.run();
            Debounce.TASKS.remove(id);
            return;
        }).delay(milliseconds, TimeUnit.MILLISECONDS).build();
        task.execute();
        Debounce.TASKS.put(id, task);
    }
    
    static {
        TASKS = new HashMap<String, Task>();
    }
}
