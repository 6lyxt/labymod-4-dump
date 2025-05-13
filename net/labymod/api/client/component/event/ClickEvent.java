// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.event;

import java.util.Objects;
import java.net.URL;
import net.labymod.api.client.component.ComponentService;
import org.jetbrains.annotations.NotNull;

public interface ClickEvent
{
    default ClickEvent openUrl(@NotNull final String url) {
        return ComponentService.clickEvent(Action.OPEN_URL, url);
    }
    
    default ClickEvent openUrl(@NotNull final URL url) {
        Objects.requireNonNull(url, "URL cannot be null!");
        return openUrl(url.toExternalForm());
    }
    
    default ClickEvent openFile(@NotNull final String path) {
        return ComponentService.clickEvent(Action.OPEN_FILE, path);
    }
    
    default ClickEvent runCommand(@NotNull final String command) {
        return ComponentService.clickEvent(Action.RUN_COMMAND, command);
    }
    
    default ClickEvent suggestCommand(@NotNull final String command) {
        return ComponentService.clickEvent(Action.SUGGEST_COMMAND, command);
    }
    
    default ClickEvent changePage(@NotNull final String page) {
        return ComponentService.clickEvent(Action.CHANGE_PAGE, page);
    }
    
    default ClickEvent changePage(final int page) {
        return changePage(String.valueOf(page));
    }
    
    default ClickEvent copyToClipboard(@NotNull final String text) {
        return ComponentService.clickEvent(Action.COPY_TO_CLIPBOARD, text);
    }
    
    Action action();
    
    String getValue();
    
    public enum Action
    {
        OPEN_URL, 
        OPEN_FILE, 
        RUN_COMMAND, 
        SUGGEST_COMMAND, 
        CHANGE_PAGE, 
        COPY_TO_CLIPBOARD;
    }
}
