// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot;

import java.util.concurrent.TimeUnit;
import java.util.Random;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.api.Laby;
import java.util.stream.Stream;
import java.util.Collections;
import net.labymod.api.util.time.TimeUtil;
import java.io.IOException;
import java.util.function.Predicate;
import java.nio.file.Files;
import java.nio.file.FileVisitOption;
import net.labymod.api.util.io.IOUtil;
import java.util.concurrent.atomic.AtomicLong;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.Constants;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import net.labymod.api.event.EventBus;
import net.labymod.core.client.screenshot.meta.ScreenshotMetaProvider;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.nio.file.Path;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class ScreenshotBrowser
{
    private static final Path DEBUG_DIRECTORY;
    private final QueuedExecutor executor;
    private final ScheduledExecutorService indexExecutor;
    private final List<ScreenshotBrowserNotifier> notifiers;
    private final List<Screenshot> screenshots;
    private final Map<Long, ScreenshotSection> sectionMap;
    private final List<ScreenshotSection> sections;
    private final ScreenshotMetaProvider metaProvider;
    private ScreenshotBrowserNotifier.IndexState state;
    
    @Inject
    public ScreenshotBrowser(final EventBus eventBus) {
        this.executor = new QueuedExecutor(10);
        this.indexExecutor = Executors.newScheduledThreadPool(1);
        this.notifiers = new ArrayList<ScreenshotBrowserNotifier>();
        this.screenshots = new ArrayList<Screenshot>();
        this.sectionMap = new HashMap<Long, ScreenshotSection>();
        this.sections = new ArrayList<ScreenshotSection>();
        this.metaProvider = new ScreenshotMetaProvider();
        this.state = ScreenshotBrowserNotifier.IndexState.NOT_INITIALIZED;
        this.refresh();
        eventBus.registerListener(this);
    }
    
    public void refresh() {
        this.purge();
        this.indexScreenshotsInDirectoryAsync(Constants.Files.SCREENSHOT_DIRECTORY);
    }
    
    public void purge() {
        final List<ScreenshotSection> prevSections = new ArrayList<ScreenshotSection>(this.sections);
        this.screenshots.clear();
        this.sectionMap.clear();
        this.sections.clear();
        for (final ScreenshotBrowserNotifier notifier : this.notifiers) {
            for (final ScreenshotSection section : prevSections) {
                notifier.onSectionRemoved(section);
            }
        }
    }
    
    @Subscribe
    public void onWriteScreenshot(final WriteScreenshotEvent event) {
        if (event.getPhase() != Phase.POST) {
            return;
        }
        this.indexExecutor.execute(() -> {
            this.updateState(ScreenshotBrowserNotifier.IndexState.INDEXING, 0.0f);
            this.index(event.getDestination().toPath());
            this.updateState(ScreenshotBrowserNotifier.IndexState.FINALIZING, 1.0f);
            this.finalizeScreenshots();
            this.updateState(ScreenshotBrowserNotifier.IndexState.IDLE, 1.0f);
        });
    }
    
    public void indexScreenshotsInDirectoryAsync(final Path directory) {
        this.indexExecutor.execute(() -> {
            try {
                this.indexScreenshotsInDirectory(directory);
            }
            catch (final Throwable t) {
                t.printStackTrace();
            }
        });
    }
    
    private void indexScreenshotsInDirectory(final Path directory) {
        if (this.state != ScreenshotBrowserNotifier.IndexState.NOT_INITIALIZED && this.state != ScreenshotBrowserNotifier.IndexState.IDLE) {
            return;
        }
        this.updateState(ScreenshotBrowserNotifier.IndexState.PREPARING, 0.0f);
        this.metaProvider.load();
        final AtomicLong current = new AtomicLong(1L);
        final AtomicLong total = new AtomicLong();
        final boolean exists = IOUtil.exists(directory);
        if (exists) {
            try (final Stream<Path> files = Files.walk(directory, new FileVisitOption[0])) {
                total.set(files.filter(this::excludeDebugDirectory).count());
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        this.updateState(ScreenshotBrowserNotifier.IndexState.INDEXING, 0.0f);
        final AtomicLong timeLastFlush = new AtomicLong(TimeUtil.getMillis());
        if (exists) {
            try (final Stream<Path> files2 = Files.walk(directory, new FileVisitOption[0])) {
                files2.filter(this::excludeDebugDirectory).sorted(Collections.reverseOrder()).forEach(path -> {
                    this.index(path);
                    final long currentLong = current.getAndIncrement();
                    final long totalLong = total.get();
                    final float progress = currentLong / (float)totalLong;
                    this.updateState(ScreenshotBrowserNotifier.IndexState.INDEXING, progress);
                    final long durationSinceFlush = TimeUtil.getMillis() - timeLastFlush.get();
                    if (durationSinceFlush > 1000L) {
                        timeLastFlush.set(TimeUtil.getMillis());
                        this.flushDirtySections();
                    }
                    return;
                });
            }
            catch (final IOException e2) {
                e2.printStackTrace();
            }
        }
        this.updateState(ScreenshotBrowserNotifier.IndexState.FINALIZING, 1.0f);
        this.finalizeScreenshots();
        this.updateState(ScreenshotBrowserNotifier.IndexState.IDLE, 1.0f);
    }
    
    private void finalizeScreenshots() {
        this.screenshots.sort((s1, s2) -> Long.compare(s2.getMeta().getTimestamp(), s1.getMeta().getTimestamp()));
        this.sections.sort((s1, s2) -> Long.compare(s2.getTimestamp(), s1.getTimestamp()));
        int previousYear = -1;
        for (final ScreenshotSection section : this.sections) {
            final int year = section.getYear();
            section.setBeginningOfYear(year != previousYear);
            previousYear = year;
        }
        this.flushDirtySections();
    }
    
    private void flushDirtySections() {
        this.metaProvider.flush();
        Laby.labyAPI().minecraft().executeNextTick(() -> {
            final ScreenshotSection[] array = this.sectionMap.values().toArray(new ScreenshotSection[0]);
            int i = 0;
            for (int length = array.length; i < length; ++i) {
                final ScreenshotSection section = array[i];
                if (section.isDirty()) {
                    section.setDirty(false);
                    this.notifiers.iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final ScreenshotBrowserNotifier notifier = iterator.next();
                        notifier.onSectionChanged(section);
                    }
                }
            }
        });
    }
    
    private Screenshot index(final Path path) {
        final Screenshot screenshot = new Screenshot(path);
        if (screenshot.isValid()) {
            screenshot.initialize(this.metaProvider);
            this.screenshots.add(screenshot);
            final ScreenshotSection section = this.sectionMap.computeIfAbsent(screenshot.getMeta().getMonthTimestamp(), timestamp -> {
                final ScreenshotSection newSection = new ScreenshotSection(timestamp);
                this.sections.add(newSection);
                Laby.labyAPI().minecraft().executeNextTick(() -> {
                    this.notifiers.iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final ScreenshotBrowserNotifier notifier = iterator.next();
                        notifier.onSectionAdded(newSection);
                    }
                    return;
                });
                return newSection;
            });
            section.push(screenshot);
            return screenshot;
        }
        return null;
    }
    
    public void removeScreenshot(final Screenshot screenshot) {
        this.screenshots.remove(screenshot);
        final long sectionIndex = screenshot.getMeta().getMonthTimestamp();
        final ScreenshotSection section = this.sectionMap.get(sectionIndex);
        if (section != null) {
            section.remove(screenshot);
            if (section.isEmpty()) {
                this.sections.remove(section);
                this.sectionMap.remove(sectionIndex);
                Laby.labyAPI().minecraft().executeNextTick(() -> {
                    this.notifiers.iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final ScreenshotBrowserNotifier notifier = iterator.next();
                        notifier.onSectionRemoved(section);
                    }
                });
            }
            else {
                Laby.labyAPI().minecraft().executeNextTick(() -> {
                    this.notifiers.iterator();
                    final Iterator iterator2;
                    while (iterator2.hasNext()) {
                        final ScreenshotBrowserNotifier notifier2 = iterator2.next();
                        notifier2.onSectionChanged(section);
                    }
                });
            }
        }
    }
    
    public int getIndexOf(final Screenshot screenshot) {
        return this.screenshots.indexOf(screenshot);
    }
    
    public ScreenshotSection getSectionOf(final Screenshot screenshot) {
        final ScreenshotMeta meta = screenshot.getMeta();
        return (meta == null) ? null : this.sectionMap.get(meta.getMonthTimestamp());
    }
    
    public Screenshot getScreenshot(final Path path) {
        final Path name = path.getFileName();
        for (final Screenshot screenshot : this.screenshots) {
            if (screenshot.getPath().getFileName().equals(name)) {
                return screenshot;
            }
        }
        return null;
    }
    
    public ScreenshotSection getLatestSection() {
        return this.sections.isEmpty() ? null : this.sections.get(0);
    }
    
    public List<Screenshot> getScreenshots() {
        return this.screenshots;
    }
    
    public Map<Long, ScreenshotSection> getSectionMap() {
        return this.sectionMap;
    }
    
    public List<ScreenshotSection> getSections() {
        return this.sections;
    }
    
    public QueuedExecutor getExecutor() {
        return this.executor;
    }
    
    public ScreenshotBrowserNotifier.IndexState getState() {
        return this.state;
    }
    
    public void subscribe(final ScreenshotBrowserNotifier notifier) {
        this.notifiers.add(notifier);
    }
    
    public void unsubscribe(final ScreenshotBrowserNotifier notifier) {
        this.notifiers.remove(notifier);
    }
    
    private void updateState(final ScreenshotBrowserNotifier.IndexState state, final float progress) {
        this.state = state;
        for (final ScreenshotBrowserNotifier notifier : this.notifiers) {
            notifier.onIndexProgress(state, progress);
        }
    }
    
    private boolean excludeDebugDirectory(final Path path) {
        return !path.startsWith(ScreenshotBrowser.DEBUG_DIRECTORY);
    }
    
    static {
        DEBUG_DIRECTORY = Path.of("screenshots", "debug");
    }
    
    public static class QueuedExecutor
    {
        private final Random random;
        private final List<Task> tasks;
        
        public QueuedExecutor(final int threads) {
            this.random = new Random();
            this.tasks = new ArrayList<Task>();
            final ScheduledExecutorService executor = Executors.newScheduledThreadPool(threads);
            for (int i = 0; i < threads; ++i) {
                executor.scheduleWithFixedDelay(this::handleQueue, 0L, 1L, TimeUnit.MILLISECONDS);
            }
        }
        
        private void handleQueue() {
            try {
                Task task = null;
                synchronized (this.tasks) {
                    if (!this.tasks.isEmpty()) {
                        task = this.tasks.remove(this.random.nextInt(this.tasks.size()));
                    }
                }
                if (task != null) {
                    task.runnable.run();
                }
            }
            catch (final Throwable t) {
                if (t instanceof IndexOutOfBoundsException) {
                    return;
                }
                t.printStackTrace();
            }
        }
        
        public Task queue(final Runnable runnable) {
            final Task task = new Task(runnable);
            this.tasks.add(0, task);
            while (this.tasks.size() > 500) {
                this.tasks.remove(500);
            }
            return task;
        }
        
        public List<Task> getTasks() {
            return this.tasks;
        }
        
        public class Task
        {
            private final Runnable runnable;
            private boolean cancelled;
            
            public Task(final Runnable runnable) {
                this.runnable = runnable;
            }
            
            public void cancel() {
                if (this.cancelled) {
                    return;
                }
                this.cancelled = true;
                QueuedExecutor.this.tasks.remove(this);
            }
            
            public boolean isCancelled() {
                return this.cancelled;
            }
        }
    }
}
