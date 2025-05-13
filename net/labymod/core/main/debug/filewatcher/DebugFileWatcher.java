// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug.filewatcher;

import java.util.Objects;
import net.labymod.api.LabyAPI;
import java.nio.file.WatchKey;
import net.labymod.api.Laby;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.Iterator;
import java.util.List;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Path;
import net.labymod.api.Constants;
import java.io.IOException;
import java.nio.file.FileSystems;
import net.labymod.core.main.LabyMod;
import java.nio.file.WatchService;

public class DebugFileWatcher
{
    private final WatchablePathManager watchablePathManager;
    private WatchService watchService;
    
    public DebugFileWatcher() {
        this.watchablePathManager = LabyMod.references().watchablePathManager();
    }
    
    public void collectDirectories() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        }
        catch (final IOException exception) {
            exception.printStackTrace();
            return;
        }
        final List<Path> directories = Constants.SystemProperties.getFiles(Constants.SystemProperties.HOT_FILE_RELOADING_DIRECTORIES);
        for (final Path directory : directories) {
            if (IOUtil.exists(directory)) {
                if (!IOUtil.isDirectory(directory)) {
                    continue;
                }
                registerWatcher(directory, this.watchService);
                try {
                    Files.walkFileTree(directory, new FileWatcherVisitor(this.watchService));
                }
                catch (final IOException exception2) {
                    exception2.printStackTrace();
                }
            }
        }
    }
    
    public void startFileWatcherThread() {
        final WatchService watchService = this.watchService;
        if (watchService == null) {
            return;
        }
        final Thread fileWatcherServiceThread = new DebugFileWatcherThread(this.watchablePathManager, watchService);
        fileWatcherServiceThread.start();
    }
    
    public void close() throws IOException {
        final WatchService watchService = this.watchService;
        if (watchService == null) {
            return;
        }
        watchService.close();
    }
    
    static void registerWatcher(final Path directory, final WatchService watchService) {
        try {
            directory.register(watchService, (WatchEvent.Kind<?>[])new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE });
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
    
    private static class FileWatcherVisitor extends SimpleFileVisitor<Path>
    {
        private final WatchService watchService;
        
        public FileWatcherVisitor(final WatchService watchService) {
            this.watchService = watchService;
        }
        
        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            DebugFileWatcher.registerWatcher(dir, this.watchService);
            return FileVisitResult.CONTINUE;
        }
    }
    
    private static class DebugFileWatcherThread extends Thread
    {
        private final WatchablePathManager watchablePathManager;
        private final WatchService watchService;
        private boolean running;
        
        public DebugFileWatcherThread(final WatchablePathManager watchablePathManager, final WatchService watchService) {
            this.watchablePathManager = watchablePathManager;
            this.watchService = watchService;
            this.setName("FileWatcherService");
        }
        
        @Override
        public synchronized void start() {
            this.running = true;
            super.start();
        }
        
        @Override
        public void run() {
            super.run();
            while (this.running) {
                this.executeWatchService();
            }
        }
        
        @Override
        public void interrupt() {
            super.interrupt();
            this.running = false;
        }
        
        private void executeWatchService() {
            WatchKey key;
            try {
                key = this.watchService.take();
            }
            catch (final ClosedWatchServiceException exception) {
                this.running = false;
                return;
            }
            catch (final InterruptedException exception2) {
                return;
            }
            for (final WatchEvent<?> pollEvent : key.pollEvents()) {
                if (pollEvent.context() instanceof Path) {
                    final Path watchableDirectory = (Path)key.watchable();
                    final Path path = watchableDirectory.resolve((Path)pollEvent.context());
                    final LabyAPI labyAPI = Laby.labyAPI();
                    if (labyAPI == null) {
                        continue;
                    }
                    if (labyAPI.minecraft() == null) {
                        continue;
                    }
                    final WatchablePath.WatchableEventType eventType = this.getEventType(pollEvent.kind());
                    if (eventType == WatchablePath.WatchableEventType.UNKNOWN) {
                        continue;
                    }
                    labyAPI.minecraft().executeOnRenderThread(() -> this.watchablePathManager.onUpdate(path, eventType));
                }
            }
            key.reset();
        }
        
        private WatchablePath.WatchableEventType getEventType(final WatchEvent.Kind<?> watchEventKind) {
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.ENTRY_MODIFY)) {
                return WatchablePath.WatchableEventType.MODIFY;
            }
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.ENTRY_CREATE)) {
                return WatchablePath.WatchableEventType.CREATE;
            }
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.ENTRY_DELETE)) {
                return WatchablePath.WatchableEventType.DELETE;
            }
            if (Objects.equals(watchEventKind, StandardWatchEventKinds.OVERFLOW)) {
                return WatchablePath.WatchableEventType.OVERFLOW;
            }
            return WatchablePath.WatchableEventType.UNKNOWN;
        }
    }
}
