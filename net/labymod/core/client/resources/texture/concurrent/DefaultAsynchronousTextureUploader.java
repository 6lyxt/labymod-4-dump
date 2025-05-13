// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.concurrent;

import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.InternalRefreshableTexture;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.resources.texture.concurrent.RefreshableTexture;
import net.labymod.api.client.gfx.texture.TextureFilter;
import java.util.concurrent.Executor;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import net.labymod.api.Laby;
import java.util.ArrayDeque;
import java.util.concurrent.Executors;
import net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureTask;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureUploader;

@Singleton
@Implements(AsynchronousTextureUploader.class)
public class DefaultAsynchronousTextureUploader implements AsynchronousTextureUploader, FrameContext
{
    private final ExecutorService singleThreadExecutor;
    private final RefreshableTextureFactory refreshableTextureFactory;
    private final Queue<AsynchronousTextureTask> taskQueue;
    
    @Inject
    public DefaultAsynchronousTextureUploader(final RefreshableTextureFactory refreshableTextureFactory) {
        this.refreshableTextureFactory = refreshableTextureFactory;
        this.singleThreadExecutor = Executors.newSingleThreadExecutor(task -> {
            final Thread thread = new Thread(task);
            thread.setName("Asynchronous-Texture-Uploader");
            return thread;
        });
        this.taskQueue = new ArrayDeque<AsynchronousTextureTask>();
        Laby.references().frameContextRegistry().register(this);
    }
    
    @Override
    public CompletableFuture<Void> prepareAndUploadTexture(final Runnable prepareTextureTask, final AsynchronousTextureTask uploadTask) {
        return CompletableFuture.runAsync(prepareTextureTask, this.singleThreadExecutor).thenRun(() -> this.queueTask(uploadTask));
    }
    
    @Override
    public RefreshableTexture newRefreshableTexture(final TextureFilter minFilter, final TextureFilter magFilter) {
        ThreadSafe.ensureRenderThread();
        final DefaultRefreshableTexture texture = new DefaultRefreshableTexture();
        final Texture parentTexture = this.refreshableTextureFactory.createTexture(texture);
        texture.setParentTexture(parentTexture);
        texture.init(minFilter, magFilter);
        if (parentTexture instanceof final InternalRefreshableTexture internalTexture) {
            internalTexture.init();
        }
        return texture;
    }
    
    private void queueTask(final AsynchronousTextureTask task) {
        this.taskQueue.add(task);
    }
    
    @Override
    public void beginFrame() {
    }
    
    @Override
    public void endFrame() {
        if (this.taskQueue.isEmpty()) {
            return;
        }
        final AsynchronousTextureTask task = this.taskQueue.poll();
        task.run();
    }
}
