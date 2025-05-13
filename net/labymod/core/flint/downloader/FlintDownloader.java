// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.downloader;

import net.labymod.api.util.io.IOUtil;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.labymod.api.Constants;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.List;
import java.io.IOException;
import net.labymod.core.flint.FlintDefaultModifications;
import net.labymod.core.flint.marketplace.FlintModification;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class FlintDownloader
{
    private final Set<String> scheduledForRemoval;
    private final Map<String, FlintDownloadTask> runningTasks;
    
    @Inject
    public FlintDownloader() {
        this.scheduledForRemoval = new HashSet<String>();
        this.runningTasks = new HashMap<String, FlintDownloadTask>();
    }
    
    public boolean isScheduledForRemoval(final FlintModification modification) {
        return this.isScheduledForRemoval(modification.getNamespace());
    }
    
    public boolean isScheduledForRemoval(final String namespace) {
        return this.scheduledForRemoval.contains(namespace);
    }
    
    public void scheduleForRemoval(final String namespace) throws IOException {
        if (this.scheduledForRemoval.contains(namespace)) {
            this.scheduledForRemoval.remove(namespace);
            FlintDefaultModifications.instance().install(namespace);
        }
        else {
            this.scheduledForRemoval.add(namespace);
        }
        this.writeScheduleForRemoval();
    }
    
    public void scheduleForRemoval(final FlintModification modification) throws IOException {
        this.scheduleForRemoval(modification.getNamespace());
    }
    
    public FlintDownloadTask downloadModification(final FlintModification modification, final List<String> skippedDependencies, final Consumer<FlintDownloadTask> finished) {
        final String namespace = modification.getNamespace();
        if (this.isScheduledForRemoval(namespace)) {
            try {
                this.scheduleForRemoval(namespace);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
            finished.accept(null);
            return null;
        }
        FlintDefaultModifications.instance().install(namespace);
        final FlintDownloadTask flintDownloadTask = this.runningTasks.get(namespace);
        if (flintDownloadTask != null) {
            flintDownloadTask.setFinishedCallback(finished);
            return flintDownloadTask;
        }
        final FlintDownloadTask task = new FlintDownloadTask(modification, skippedDependencies, downloadTask -> {
            if (downloadTask.isFinished()) {
                this.runningTasks.remove(namespace);
            }
            if (finished != null) {
                finished.accept(downloadTask);
            }
            return;
        });
        this.runningTasks.put(namespace, task);
        task.download();
        return task;
    }
    
    public Optional<FlintDownloadTask> getDownloadTask(final FlintModification modification) {
        return Optional.ofNullable(this.runningTasks.get(modification.getNamespace()));
    }
    
    public void setDownloadTask(final FlintModification modification, final FlintDownloadTask task) {
        this.runningTasks.put(modification.getNamespace(), task);
    }
    
    private void writeScheduleForRemoval() throws IOException {
        Files.write(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL, this.scheduledForRemoval, new OpenOption[0]);
        IOUtil.hideFileInWindowsFileSystem(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL);
    }
}
