// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.util.ThreadSafe;
import java.util.Objects;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.client.component.Component;
import java.io.File;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.event.EventBus;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ActionBar;
import net.labymod.api.client.chat.ChatExecutor;

public abstract class DefaultChatExecutor implements ChatExecutor
{
    private ActionBar currentContinuousActionBar;
    
    public DefaultChatExecutor() {
        final EventBus eventBus = Laby.references().eventBus();
        eventBus.registerListener(new ChatThemeUpdateListener(this));
        eventBus.registerListener(this);
    }
    
    @Override
    public void performAction(final ClickEvent event) {
        switch (event.action()) {
            case OPEN_URL: {
                final MinecraftOptions options = Laby.labyAPI().minecraft().options();
                if (!options.isChatLinksEnabled()) {
                    return;
                }
                final String link = event.getValue();
                this.openUrl(link, options.isChatLinkConfirmationEnabled());
                break;
            }
            case OPEN_FILE: {
                this.openFile(event.getValue());
                break;
            }
            case RUN_COMMAND: {
                this.runCommand(event.getValue());
                break;
            }
            case SUGGEST_COMMAND: {
                this.suggestCommand(event.getValue());
                break;
            }
            case CHANGE_PAGE: {
                break;
            }
            case COPY_TO_CLIPBOARD: {
                this.copyToClipboard(event.getValue());
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(event.action()));
            }
        }
    }
    
    @Override
    public void openUrl(final String value, final boolean confirmation) {
        if (confirmation) {
            Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new ConfirmLinkActivity(value, type -> {
                switch (type) {
                    case COPY: {
                        this.copyToClipboard(value);
                        break;
                    }
                    case OPEN: {
                        this.openUrl(value);
                        break;
                    }
                }
            }));
        }
        else {
            this.openUrl(value);
        }
    }
    
    @Override
    public void openUrl(final String value) {
        OperatingSystem.getPlatform().openUrl(value);
    }
    
    @Override
    public void openFile(final String value) {
        OperatingSystem.getPlatform().openFile(new File(value));
    }
    
    @Override
    public ActionBar displayActionBarContinuous(final Component message) {
        final AtomicReference<ActionBar> ref = new AtomicReference<ActionBar>();
        final ActionBar actionBar = new ActionBar(() -> {
            if (this.currentContinuousActionBar == ref.get()) {
                this.currentContinuousActionBar = null;
            }
            return;
        }, message);
        ref.set(actionBar);
        return this.currentContinuousActionBar = actionBar;
    }
    
    @Subscribe
    public void displayActionBar(final GameTickEvent event) {
        final ActionBar actionBar = this.currentContinuousActionBar;
        if (actionBar == null) {
            return;
        }
        if (actionBar.getTick() % 20 == 0) {
            this.displayActionBar(actionBar.message());
        }
        actionBar.setTick(actionBar.getTick() + 1);
    }
    
    protected void runCommand(final String command) {
        this.chat(command, false);
    }
    
    protected abstract void rescaleChat();
    
    public static class ChatThemeUpdateListener
    {
        private final DefaultChatExecutor executor;
        
        public ChatThemeUpdateListener(final DefaultChatExecutor executor) {
            this.executor = executor;
        }
        
        @Subscribe
        public void onThemeChange(final ThemeChangeEvent event) {
            if (event.phase() == Phase.PRE) {
                return;
            }
            final DefaultChatExecutor executor = this.executor;
            Objects.requireNonNull(executor);
            ThreadSafe.executeOnRenderThread(executor::rescaleChat);
        }
    }
}
